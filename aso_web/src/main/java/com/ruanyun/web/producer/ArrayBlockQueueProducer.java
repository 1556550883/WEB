package com.ruanyun.web.producer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.QueueingConsumer;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserappidAdveridService;

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
	private static java.util.Random random = new java.util.Random();
	private boolean isAutoAddAdver = false;

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
				
				for(String mAdverId : adverList) 
				{
					TChannelAdverInfo info = mChannelAdverInfoService.getInfoById(Integer.parseInt(mAdverId));
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
					
					if(info.getDownloadCount() >= info.getAdverCount() && info.getAddTaskLimit() <= 0) 
					{
						//任务完成，结束
						mChannelAdverInfoService.updateAdverStatus(2, mAdverId);
						removeAdverList.add(info.getAdverId() + "");
						System.out.print("task complete");
						
						continue;
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
						//创建消费者
						if(!AdverQueueConsumer.consumerMap.containsKey(endPointName)) 
						{
							new AdverQueueConsumer(endPointName);
						}
						
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
				
				//任务自动增加 
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
				Thread.sleep(1000);
				
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
