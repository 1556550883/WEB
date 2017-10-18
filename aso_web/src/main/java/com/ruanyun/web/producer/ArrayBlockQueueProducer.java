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
		mQueueMap.put(mAdverId, mArrayBlockQueue);
	}

	@Override
	public void run() 
	{
		//启动的时候，移除以前有效的广告，重新生成
		mQueueMap.remove(mAdverId);
		
		while (true)
		{
			try 
			{	
				//同步生产数量
				synchronized(o)
				{
					TChannelAdverInfo info = mChannelAdverInfoService.getInfoById(Integer.parseInt(mAdverId));
					
					if(info.getAdverCountComplete() == info.getAdverCount()) 
					{
						mChannelAdverInfoService.updateAdverStatus(2, mAdverId);
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
							 int count = mUserappidAdveridService.updateStatus2Invalid(info);
							 //更新任务数量
							 if(count > 0) 
							 {
								 info.setAdverActivationCount(count);
								 mChannelAdverInfoService.updateAdverActivationCount(info);
								 mAppChannelAdverInfoService.updateAdverCountRemain(info);
								 System.out.println("this is count" + count);
							 }
							 else
							 {
								 System.out.println("this is 2");
								 Thread.sleep(60000);
							 }
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
