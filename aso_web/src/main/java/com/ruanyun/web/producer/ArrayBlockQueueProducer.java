package com.ruanyun.web.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserappidAdveridService;

public class ArrayBlockQueueProducer implements  Runnable
{
	private ChannelAdverInfoService mChannelAdverInfoService;
	private AppChannelAdverInfoService mAppChannelAdverInfoService;
	private UserappidAdveridService mUserappidAdveridService;
	protected ArrayBlockingQueue<String> mArrayBlockQueue;
	public static Map<String, ArrayBlockingQueue<String>> mQueueMap = new HashMap<String, ArrayBlockingQueue<String>>();
	public static ExecutorService pool = Executors.newCachedThreadPool();  
	private String mAdverId;
	private Object o = new Object();
	
	public ArrayBlockQueueProducer(ArrayBlockingQueue<String> arrayBlockQueue, String adverId, 
			ChannelAdverInfoService channelAdverInfoService, AppChannelAdverInfoService appChannelAdverInfoService, UserappidAdveridService userappidAdveridService)
	{
		this.mArrayBlockQueue = arrayBlockQueue;
		this.mAdverId= adverId;
		this.mChannelAdverInfoService = channelAdverInfoService;
		this.mAppChannelAdverInfoService = appChannelAdverInfoService;
		this.mUserappidAdveridService = userappidAdveridService;
		
		if(mQueueMap.containsKey(mAdverId)) 
		{
			mQueueMap.remove(mAdverId);
		}
		
		mQueueMap.put(mAdverId, mArrayBlockQueue);
	}

	@Override
	public void run() 
	{
		while (true)
		{
			try 
			{	
				//同步生产数量
				synchronized(o)
				{
					TChannelAdverInfo info = mChannelAdverInfoService.getInfoById(Integer.parseInt(mAdverId));
					
					if(info.getDownloadCount() == info.getAdverCount()) 
					{
						mChannelAdverInfoService.updateAdverStatus(2, mAdverId);
						System.out.println("task complete");
						break;
					}
					
					if(info.getAdverStatus() == 1) 
					{
						 if(info.getAdverActivationCount() > 0)
						 {
							//更新剩余产品数量
							mAppChannelAdverInfoService.updateAdverActivationRemainMinus1(info);
							String data = UUID.randomUUID().toString();
							System.out.println("Put:" + data);
							mArrayBlockQueue.put(data);
						 }
						 else 
						 {
							 mUserappidAdveridService.updateStatus2Invalid(info);
							 mAppChannelAdverInfoService.updateAdverCountRemain(info);
							 int addAdverActivation = info.getAdverCountRemain() - mArrayBlockQueue.size();
							 int countComplete = mChannelAdverInfoService.getCountComplete(mAdverId);
							 info.setDownloadCount(countComplete);//用这个来记录完成数量
							 info.setAdverActivationCount(addAdverActivation);
							 mChannelAdverInfoService.updateAdverActivationCount(info);
							 //更新任务数量
							 Thread.sleep(60000);
						 }
					}
					else
					{
						mQueueMap.remove(info.getAdverId() + "");
						break;
					}
				}
            } 
			catch (InterruptedException e)
			{
                e.printStackTrace();
            }
		}
	}
}
