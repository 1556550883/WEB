package com.ruanyun.web.producer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.ruanyun.web.model.TChannelAdverInfo;

public class ArrayBlockScoreProducer
{
	public static Map<String, LinkedBlockingQueue<TChannelAdverInfo>> mScoreQueueMap = new HashMap<String, LinkedBlockingQueue<TChannelAdverInfo>>();
	private ArrayBlockScoreProducer() {} 
	private ArrayBlockScoreProducer mInstance;
	
	public synchronized ArrayBlockScoreProducer getInstance() 
	{
		if(mInstance == null) 
		{
			mInstance = new ArrayBlockScoreProducer();
		}
		
		return mInstance;
	}
}
