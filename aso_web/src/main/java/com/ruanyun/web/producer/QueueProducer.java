package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Component;

@Component
public class QueueProducer extends EndPoint
{  
	private QueueProducer() throws IOException, TimeoutException 
	{
		super("socre", false);
	}
}