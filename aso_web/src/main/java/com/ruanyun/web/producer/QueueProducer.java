package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueProducer extends EndPoint
{  
	private static QueueProducer scoreQueue;
	
	private QueueProducer(String endpointName) throws IOException, TimeoutException 
	{
		super(endpointName, false);
	}
    
	public static QueueProducer getQueueProducer() throws IOException, TimeoutException 
	{
		if(scoreQueue == null) 
		{
			scoreQueue = new QueueProducer("socre");
		}
		
		return scoreQueue;
	}
}
