package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserappidAdveridService;

public class ArrayBlockQueueProducer implements  Runnable
{
	private ChannelAdverInfoService mChannelAdverInfoService;
	private AppChannelAdverInfoService mAppChannelAdverInfoService;
	private UserappidAdveridService mUserappidAdveridService;
	public static List<String> adverList = new ArrayList<String>();
	public static List<String> removeAdverList = new ArrayList<String>();
	public static List<String> adverList1 = new ArrayList<String>();
	public static Map<String, AdverProducer> adverProducer = new HashMap<String, AdverProducer>();
	public static ExecutorService pool = Executors.newCachedThreadPool();  
	
	public ArrayBlockQueueProducer(ChannelAdverInfoService channelAdverInfoService, AppChannelAdverInfoService appChannelAdverInfoService, UserappidAdveridService userappidAdveridService)
	{
		this.mChannelAdverInfoService = channelAdverInfoService;
		this.mAppChannelAdverInfoService = appChannelAdverInfoService;
		this.mUserappidAdveridService = userappidAdveridService;
	}

	@Override
	public void run() 
	{
		while (true)
		{
			if(adverList.size() > 0) 
			{
				adverList1.addAll(adverList);
				adverList.clear();
			}
			
			try 
			{	
				if(removeAdverList.size() > 0) 
				{
					adverList1.removeAll(removeAdverList);
					for(String adverId : removeAdverList) 
					{
						TChannelAdverInfo infoA = mChannelAdverInfoService.getInfoById(Integer.parseInt(adverId));
						String endPointName = infoA.getAdverName() + "_" + infoA.getAdverId();
						Channel channel = AdverQueueConsumer.channelMap.get(endPointName);
						QueueingConsumer consumer = AdverQueueConsumer.consumerMap.get(endPointName);
						channel.basicConsume(endPointName, true, consumer);
						consumer.nextDelivery(1000);
						AdverQueueConsumer.channelMap.remove(endPointName);
						AdverQueueConsumer.consumerMap.remove(endPointName);
						adverProducer.get(endPointName).close();
						adverProducer.remove(endPointName);
						AdverQueueConsumer.adverQueueConsumerMap.get(endPointName).close();
						AdverQueueConsumer.adverQueueConsumerMap.remove(endPointName);
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
				
				for(String mAdverId : adverList1) 
				{
					TChannelAdverInfo info = mChannelAdverInfoService.getInfoById(Integer.parseInt(mAdverId));
					if(info == null) 
					{
						continue;
					}
					String endPointName = info.getAdverName() + "_" + info.getAdverId();
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
					if(!AdverQueueConsumer.adverQueueConsumerMap.containsKey(endPointName)) 
					{
						AdverQueueConsumer.adverQueueConsumerMap.put(endPointName, new AdverQueueConsumer(endPointName));
					}
					
					if(info.getDownloadCount() >= info.getAdverCount()) 
					{
						mChannelAdverInfoService.updateAdverStatus(2, mAdverId);
						removeAdverList.add(info.getAdverId() + "");
						System.out.print("task complete");
						// 关闭频道和资源  
						continue;
					}
					
					if(info.getAdverActivationCount() > 0 && info.getAdverCountRemain() > 0)
					{
						for(int i = 1; i <= info.getAdverActivationCount(); i++) 
						{
							//更新剩余有效产品数量
							mAppChannelAdverInfoService.updateAdverActivationRemainMinus1(info);
							String data = UUID.randomUUID().toString();
							System.out.println("Put:" + data);
							ap.sendMessage(data, endPointName);
						}
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
			catch (TimeoutException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			} 
			catch (ShutdownSignalException e)
			{
				e.printStackTrace();
			} 
			catch (ConsumerCancelledException e) 
			{
				e.printStackTrace();
			} 
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
