package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueProducer extends EndPoint
{  
	public QueueProducer() throws IOException, TimeoutException 
	{
		super("socre", false);
	}
}