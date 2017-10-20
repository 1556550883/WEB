package com.ruanyun.web.producer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.service.background.UserScoreService;

public class ArrayBlockScoreProducer
{
	public static Map<String, LinkedBlockingQueue<TChannelAdverInfo>> mScoreQueueMap = new HashMap<String, LinkedBlockingQueue<TChannelAdverInfo>>();
	private ArrayBlockScoreProducer() {} 
	private static ArrayBlockScoreProducer mInstance;
	
	public static synchronized ArrayBlockScoreProducer getInstance() 
	{
		if(mInstance == null) 
		{
			mInstance = new ArrayBlockScoreProducer();
		}
		
		return mInstance;
	}
	
	public void countPrice(String userNum, LinkedBlockingQueue<TChannelAdverInfo> adverQueue, UserScoreService userScoreService) 
	{
		Collection<TChannelAdverInfo> c = new ArrayList<TChannelAdverInfo>();
		int count = adverQueue.drainTo(c);
		for(int i = 0; i < count; i++) 
		{
			if( c.iterator().hasNext()) 
			{
				TChannelAdverInfo s = c.iterator().next();
				userScoreService.updateScore(userScoreService.getScore(userNum), s.getAdverPrice());
			}
		}
	}
	
	public LinkedBlockingQueue<TChannelAdverInfo> addCompleteToQueue(String userNum, TChannelAdverInfo adverInfo) 
	{
		LinkedBlockingQueue<TChannelAdverInfo> adverQueue;
		if(mScoreQueueMap.containsKey(userNum)) 
		{
			adverQueue = mScoreQueueMap.get(userNum);
		}
		else
		{
			adverQueue = new LinkedBlockingQueue<TChannelAdverInfo>();
			mScoreQueueMap.put(userNum, adverQueue);
		}
		
		try 
		{
			adverQueue.put(adverInfo);
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		return adverQueue;
	}
}
