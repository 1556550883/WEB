package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserappidAdveridService;

@Component
public class ArrayBlockQueueProducer implements Runnable
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
					
					if(info.getDownloadCount() >= info.getAdverCount()) 
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
	
	private void removeDuplicate(List<String> list) 
	{
        HashSet<String> h = new HashSet<String>(list);
        list.clear();
        list.addAll(h);
    }
}
