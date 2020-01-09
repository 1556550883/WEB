package com.ruanyun.web.producer;

import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
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
	
	public static ExecutorService pool = Executors.newFixedThreadPool(1);
	private AppCommonModel model = new AppCommonModel(-1, "出错！");
	@Override
	public void run() 
	{
		while (true)
		{
			try
			{
				List<TChannelAdverInfo> advers = mChannelAdverInfoService.queryAllStartAdvers();
				
				for(TChannelAdverInfo info : advers) 
				{
					String adverid = info.getAdverId() + "";
					String endPointName = info.getAdverId() + "_" + info.getAdverName() ;
					//如果任务还没开始就直接跳过
					if(info.getAdverDayStart().getTime()/1000 > ChannelClassification.getTimestamp())
					{continue;}
					
					//任务已经完结就可以移除任务  衡量下需要用什么来定位任务的数量，如果自增任务也结束了才算真的结束 需要判断是否自增结束
					//getAddTaskLimit-任务需要自动增加的总数
					if((info.getAdverCount() == info.getDownloadCount() && info.getAddTaskLimit() <= 0) || info.getAdverCount() < info.getDownloadCount()) 
					{
						AdverQueueConsumer sume = new AdverQueueConsumer(endPointName);
						Channel channel = sume.getChannel();
						//清楚消息
						//channel.queuePurge(endPointName);
						//删除队列
						channel.queueDelete(endPointName);
						sume.close();
						
						//更新任务状态 任务完结 完结任务应该在自增任务完全之后才执行
						mChannelAdverInfoService.updateAdverStatus(2, adverid);
					}
					else if (info.getAddTaskLimit() > 0 && info.getAdverCount() == info.getDownloadCount()) 
					{
						//说明是需要自增的任务，而且任务数量为0 更新任务中间的结束时间
						if(info.getTaskEndTime() != null) 
						{
							int tim = (int)(new Date().getTime() - info.getTaskEndTime().getTime())/1000;
							if(tim - info.getTaskInterval() > 5) 
							{
								mChannelAdverInfoService.updateAdverEndTime(adverid);
							}
						}
						else 
						{
							mChannelAdverInfoService.updateAdverEndTime(adverid);
						}
					}
					
					//自动增加任务 如果任务需要增加的总数大于0 就代表需要增加任务，而且到了增加任务的时间就自增任务
					int addTask = 0;
					if(info.getTaskEndTime() != null 
							&& ((new Date().getTime() - info.getTaskEndTime().getTime()) / 1000) >= info.getTaskInterval() 
							&& info.getAdverCount() == info.getDownloadCount()
							&& info.getAddTaskLimit() > 0) 
					{
						//需要自增的数量
						addTask = info.getAddTask();
						if(info.getAddTaskLimit() < info.getAddTask())
						{
							addTask = info.getAddTaskLimit();
							//说明马上结束自增
							info.setAddTaskLimit(0);
						}
						else
						{
							info.setAddTaskLimit(info.getAddTaskLimit() - info.getAddTask());
						}
						
						int advercount = info.getAdverCount() + addTask;
						info.setAdverCount(advercount);
						//如果这里不去更新任务数量和增加任务 就需要在后面进行任务处理
					}
					
					//自动提交任务
					//所有任务都进行自动提交，只是获取到任务最早需要提交的task 如果提交间隔大于0就说明需要帮提交间隔随机，不然直接上报
					//需要获取表中状态是2.1的任务，然后进行自动提交任务
					String tablename = "t_adver_"+ info.getChannelNum() + "_" + info.getAdid();	
					Page<TUserappidAdverid> pageList = new Page<TUserappidAdverid>();
					
					//回调任务是不需要自动提交的
					if(!"1".equals(info.getTaskType())) 
					{
						pageList = mUserappidAdveridService.getLastSpecialTask(pageList,tablename,adverid);
						if(pageList.getResult().size() > 0)
						{
							//获取当前表中存在的2.1数据，然后对这个数据进行上报
							for(TUserappidAdverid taskinfo : pageList.getResult()) 
							{
								//间距多少s了
								//超过提交时间就直接提交
								if(new Date().getTime() >= taskinfo.getCompleteTime().getTime())
								{
									//判断是否点击激活成功
									String phoneModel = taskinfo.getPhoneModel();
									String phoneOs = taskinfo.getPhoneVersion();
									String [] arr1=phoneModel.split("-");
									String [] arr2=phoneOs.split("-");
									
									//任务真实激活时间
									model = ChannelClassification.channelActive(model,info, taskinfo.getIdfa(), taskinfo.getIp(), arr1, arr2, taskinfo.getUserUdid());
									//激活成功
									if(info.getChannelNum().equals("3") || model.getResult() == 1) 
									{
										TUserScore score = new TUserScore();
										score.setUserNick(taskinfo.getIdfa());//标记任务的idfa
										String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, taskinfo.getUserAppId());
										score.setUserNum(userNum);
										score.setLevelName(tablename);
										score.setUserScoreId(info.getAdverId());//标记得分的 任务
										score.setType(0);
										//只有工作室
										float sco = ArithUtil.subf(info.getAdverPrice(), info.getPriceDiff());
										score.setScore(sco);
										//发送任务到积分系统
										QueueProducer.getQueueProducer().sendMessage(score, "socre");
									}
									else
									{
										//激活失败
										//1.7代表放弃的状态
										taskinfo.setStatus("1.7");
										mUserappidAdveridService.updateSpecialTaskStatus(taskinfo,tablename);
									}
								}
							}
						}
					}
				
					//自动更新任务数量
					//大于0就说明任务进行了自动增加操作 就需要对任务进行自动 还需要对任务的数量进行更新
					//任务剩余的数量，对任务状态进行时间查看，如果超过有效时间就设定为1.6
					//更新任务超时的数量
					int giveupNum = mUserappidAdveridService.updateStatus2Invalid(info, tablename);
					int remain = addTask + giveupNum;
					if(remain > 0)
					{
						//创建任务队列
						//生成队列
						AdverProducer ap = new AdverProducer(endPointName);
						//ap.getMessageCount()
						for(int i = 1; i <= remain; i++) 
						{
							//更新剩余有效产品数量
							String data = UUID.randomUUID().toString();
							System.out.println("Put:" + data);
							ap.sendMessage(data, endPointName);
						}
						//关闭此通道
						ap.close();
						//更新任务数量
					}
					
					//更新任务剩余的数量，每次都执行，否则前端显示不准确
					//判断队列这种是否还存在任务,如果不存在就根据任务剩余的量发送任务
					mAppChannelAdverInfoService.updateAdverCountAndRemain(tablename, info, addTask);
				}
            } 
			catch (Exception e)
			{
				e.printStackTrace();
				setChanged();
		        notifyObservers();
			}
		}
	}
}
