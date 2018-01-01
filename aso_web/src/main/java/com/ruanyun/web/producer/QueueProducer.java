package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueProducer extends EndPoint
{  
	private static QueueProducer scoreQueue;
	 
	private QueueProducer() throws IOException, TimeoutException 
	{
		super("socre", false);
	}
	
	public static QueueProducer getQueueProducer() throws IOException, TimeoutException 
	{
		if(scoreQueue == null) 
		{
			scoreQueue = new QueueProducer();
		}
		
		return scoreQueue;
	}
}