package com.ruanyun.web.producer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.QueueingConsumer;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.controller.sys.app.ChannelClassification;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserappidAdveridService;
import com.ruanyun.web.util.ArithUtil;
import com.ruanyun.web.util.NumUtils;

@Component
public class ArrayBlockQueueProducer extends Observable implements Runnable
{
	@Autowired
	private ChannelAdverInfoService mChannelAdverInfoService;
	@Autowired
	private AppChannelAdverInfoService mAppChannelAdverInfoService;
	@Autowired
	private UserappidAdveridService mUserappidAdveridService;
	
	public static List<String> addAdverList = new ArrayList<String>();
	public static List<String> removeAdverList = new ArrayList<String>();
	public static List<String> adverList = new ArrayList<String>();
	public static ExecutorService pool = Executors.newCachedThreadPool();  
	public static Map<String, AdverProducer> adverProducer = new HashMap<String, AdverProducer>();
	public static List<TChannelAdverInfo > autoAddAdverList = new ArrayList<TChannelAdverInfo>();
	public static List<TChannelAdverInfo > autoRemoveAddAdverList = new ArrayList<TChannelAdverInfo >();
	public static List<TChannelAdverInfo > autoTimeAdverList = new ArrayList<TChannelAdverInfo >();
	//需要卡时间的小手任务
	public static Map<String,TChannelAdverInfo > specialXSAdverList = new HashMap<String,TChannelAdverInfo >();
	private static java.util.Random random = new java.util.Random();
	private boolean isAutoAddAdver = false;
	private List<TUserappidAdverid> taskList = new ArrayList<TUserappidAdverid>();
	Page<TUserappidAdverid> pageList = new Page<TUserappidAdverid>();
	private static Map<String, List<TUserappidAdverid>> taskMap = new HashMap<String, List<TUserappidAdverid>>();
	private AppCommonModel model = new AppCommonModel(-1, "出错！");

	@Override
	public void run() 
	{
		while (true)
		{
			if(addAdverList.size() > 0) 
			{
				adverList.addAll(addAdverList);
				addAdverList.clear();
				removeDuplicate(addAdverList);
			}
			
			try 
			{	
				if(removeAdverList.size() > 0) 
				{
					adverList.removeAll(removeAdverList);
					//removeAdverList 执行不多，内存无隐患
					for(String adverId : removeAdverList) 
					{
						TChannelAdverInfo infoA = mChannelAdverInfoService.getInfoById(Integer.parseInt(adverId));
						String endPointName = infoA.getAdverName() + "_" + infoA.getAdverId();
						//移除消费者
						QueueingConsumer queueingConsumer = AdverQueueConsumer.consumerMap.get(endPointName);
						if(queueingConsumer != null) 
						{
							queueingConsumer.getChannel().close();
							queueingConsumer.getChannel().getConnection().close();
						}
				
						AdverQueueConsumer.consumerMap.remove(endPointName);
						//删除 rabbitmq queue
						AdverProducer adver = adverProducer.get(endPointName);
						if(adver == null) 
						{
							adver = new AdverProducer(endPointName);
						}
						
						adver.channel.queueDelete(endPointName, false, false);
						adver.close();
						
						adverProducer.remove(endPointName);
						mUserappidAdveridService.updateStatus2Invalid(infoA);
						//更新任务数量
						mAppChannelAdverInfoService.updateAdverCountRemain(infoA);
						
						TChannelAdverInfo newInfoAdver = mChannelAdverInfoService.getInfoById(Integer.parseInt(adverId));
						int countComplete = mChannelAdverInfoService.getCountComplete(adverId);
						newInfoAdver.setDownloadCount(countComplete);//用这个来记录完成数量
						newInfoAdver.setAdverActivationCount(newInfoAdver.getAdverCountRemain());
						mChannelAdverInfoService.updateAdverActivationCount(newInfoAdver);
					}
					
					removeAdverList.clear();
				}
				
			//根据adverid获取最新的任务
			if(adverList.size() > 0) {
				List<TChannelAdverInfo> advers = mChannelAdverInfoService.queryAllStartAdvers();
				//String ids = StringUtils.join(adverList, ",");
				//List<TChannelAdverInfo> advers = mChannelAdverInfoService.getInfoByIds(ids);
				//此处rds cpu使用率存在隐患
				for(TChannelAdverInfo info : advers) 
				{
					String mAdverId = info.getAdverId() + "";
					if(!adverList.contains(mAdverId)) {
						System.out.print("1");
						adverList.add(mAdverId);
					}
					//此处每次都去获取最新的任务 rds  改成5s去请求一次，减轻压力
					//TChannelAdverInfo info = mChannelAdverInfoService.getInfoById(Integer.parseInt(mAdverId));
					String endPointName = info.getAdverName() + "_" + info.getAdverId();
					isAutoAddAdver = false;
					for(TChannelAdverInfo inf: autoAddAdverList) {
						if(inf.getAdverId().equals(info.getAdverId())) {
							isAutoAddAdver = true;
							break;
						}
					}
					if(info.getAdverCountRemain() <= 0 && info.getAddTaskLimit() > 0  && !isAutoAddAdver) {
						int result = random.nextInt(10) + 1;
						if(result < 5) {
							result = 5;
						}
						
						int inter = (info.getTaskInterval() * result)/10  + 1;//最少1s
						info.setTaskInterval(inter);
						info.setTaskEndTime(new Date().getTime());
						autoAddAdverList.add(info);
					}
					
					//任务完结
					if(info.getDownloadCount() >= info.getAdverCount() && info.getAddTaskLimit() <= 0) 
					{
						//任务完成，结束
						mChannelAdverInfoService.updateAdverStatus(2, mAdverId);
						removeAdverList.add(info.getAdverId() + "");
						
						//任务完成之后去掉特殊任务列表
						ArrayBlockQueueProducer.specialXSAdverList.remove(mAdverId);
						
						continue;
					}
					
					//这里判断任务是否需要自动提交
					if(specialXSAdverList.containsKey(mAdverId)) 
					{
						TChannelAdverInfo interAdverInfo = ArrayBlockQueueProducer.specialXSAdverList.get(mAdverId);
						long now = ChannelClassification.getTimestamp();
						long interTime = now  - interAdverInfo.getSubmiTimeZone();
						//随机的一个数字
						int rad = random.nextInt(5) + 1;
						int s = interAdverInfo.getSubmitInterTime() * rad;
						if(interTime > s) 
						{
							//这里就是每次去激活任务不需要都去数据库里面获取数据
							//超过了时间间隔就需要自动提交一个任务并且更新timeZone
							interAdverInfo.setSubmiTimeZone(now);
							//自动提交任务，渠道任务2.1状态最早的一个进行提交
							if(taskMap.containsKey(mAdverId)) {
								taskList = taskMap.get(mAdverId);
								//说明有值 当激活列表为空的时候再去数据库里面获取 减少rds压力
								if(taskList.size() <= 0) {
									//获取最新的任务激活列表
									taskList = mUserappidAdveridService.getLastSpecialTask(pageList, mAdverId).getResult();
									taskMap.remove(mAdverId);
									taskMap.put(mAdverId, taskList);
								}
							}else {
								//第一次添加进入map
								taskList = mUserappidAdveridService.getLastSpecialTask(pageList, mAdverId).getResult();
								taskMap.put(mAdverId, taskList);
							} 
							Iterator<TUserappidAdverid> it = taskList.iterator();
							if(it.hasNext()) {
								TUserappidAdverid specialTask = taskList.get(0);
								//任务被使用之后就要移除队列
								taskList.remove(0);
								String phoneModel = specialTask.getPhoneModel();
								String phoneOs = specialTask.getPhoneVersion();
								String [] arr1=phoneModel.split("-");
								String [] arr2=phoneOs.split("-");
								model = ChannelClassification.channelActive(model,interAdverInfo,Integer.parseInt(interAdverInfo.getChannelNum())
										, specialTask.getIdfa(), specialTask.getIp(), arr1, arr2, specialTask.getUserUdid());
								//任务激活成功，进入正式队列
								TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
								tUserappidAdverid.setIdfa(specialTask.getIdfa());
								tUserappidAdverid.setAdverId(specialTask.getAdverId());
								if(model.getResult() == 1)
								{
									//把单子发送到队列
									//1.7代表放弃的状态 2.2表示进入真实结算队列状态
									tUserappidAdverid.setStatus("2.2");
									//此处进行io操作
									mUserappidAdveridService.updateAdverStatus(tUserappidAdverid);
									
									TUserScore score = new TUserScore();
									score.setUserNick(specialTask.getIdfa());//标记任务的idfa
									String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, specialTask.getUserAppId());
									score.setUserNum(userNum);
									score.setUserScoreId(Integer.valueOf(mAdverId));//标记得分的 任务
									score.setType(0);
									//只作用于工作室
									float sco = ArithUtil.subf(interAdverInfo.getAdverPrice(), interAdverInfo.getPriceDiff());
									//float sco = (float) (adverInfo.getAdverPrice() - adverInfo.getPriceDiff());
									score.setScore(sco);
									
									try {
										QueueProducer.getQueueProducer().sendMessage(score, "socre");
									} catch (Exception e) {
										e.printStackTrace();
									}
								}else {
									//1.7代表放弃的状态
									tUserappidAdverid.setStatus("1.7");
									mUserappidAdveridService.updateSpecialTaskStatus(tUserappidAdverid);
								}	
							}
						}
					}
					
					AdverProducer ap;
					if(!adverProducer.containsKey(endPointName)) 
					{
						ap = new AdverProducer(endPointName);
						adverProducer.put(endPointName, ap);
					}
					else
					{
						ap = adverProducer.get(endPointName);
					}
					
					//创建消费者
					if(!AdverQueueConsumer.consumerMap.containsKey(endPointName)) 
					{
						new AdverQueueConsumer(endPointName);
					}
					
					if(info.getAdverActivationCount() > 0 && info.getAdverCountRemain() > 0)
					{
						for(int i = 1; i <= info.getAdverActivationCount(); i++) 
						{
							//更新剩余有效产品数量
							String data = UUID.randomUUID().toString();
							System.out.println("Put:" + data);
							ap.sendMessage(data, endPointName);
						}
						
						mAppChannelAdverInfoService.updateAdverActivationRemainMinus1(info);
					}
					else 
					{	
						 //更新任务数量
						int count = mUserappidAdveridService.updateStatus2Invalid(info);
						mAppChannelAdverInfoService.updateAdverCountRemain(info);
						int countComplete = mChannelAdverInfoService.getCountComplete(mAdverId);
						info.setDownloadCount(countComplete);//用这个来记录完成数量
						info.setAdverActivationCount(count);
					
						mChannelAdverInfoService.updateAdverActivationCount(info);
					 }
				}
			}
				//任务自动增加 此处也不多，res内存无隐患
				for(TChannelAdverInfo info : autoAddAdverList) {
					
					if((new Date().getTime() - info.getTaskEndTime())/1000 >= info.getTaskInterval() &&  info.getAddTaskLimit() > 0) 
					{
						//增加任务 以及移除自动增加队列
						autoRemoveAddAdverList.add(info);
						int addlimit = info.getAddTaskLimit() - info.getAddTask();
						if(addlimit <= 0) {
							//不需要在自动增加就从自动增加队列移除
							addlimit = 0;
							info.setAddTask(info.getAddTaskLimit());
						}
						
						int advercount = info.getAdverCount() + info.getAddTask();
						int adverActivationCount = info.getAdverActivationCount() + info.getAddTask();
						info.setAddTaskLimit(addlimit);
						info.setAdverCount(advercount);
						info.setAdverStatus(1);
						info.setAdverCountRemain(info.getAddTask());
						info.setAdverActivationCount(adverActivationCount);
						mChannelAdverInfoService.autoAddAdverCount(info);
						//addAdverList.add(info.getAdverId() + "");
					}
				}
				
				autoAddAdverList.removeAll(autoRemoveAddAdverList);
				
				//下面是任务上报进行的限制
				Thread.sleep(2000);
            } 
			catch (Exception e)
			{
				e.printStackTrace();
				 setChanged();
		         notifyObservers();
			}
		}
	}
	
	private void removeDuplicate(List<String> list) 
	{
        HashSet<String> h = new HashSet<String>(list);
        list.clear();
        list.addAll(h);
    }
}
