package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class AdverQueueConsumer extends EndPoint
{
	public static Map<String, QueueingConsumer> consumerMap = new HashMap<String, QueueingConsumer>();
	public static Map<String, Channel> channelMap = new HashMap<String, Channel>();
	public static Map<String, AdverQueueConsumer> adverQueueConsumerMap = new HashMap<String, AdverQueueConsumer>();
	public AdverQueueConsumer(String endpointName) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException
	{
		super(endpointName, true);
		
		if(!consumerMap.containsKey(endpointName)) 
		{
			int prefetchCount = 1; 
			channel.basicQos(prefetchCount);
			QueueingConsumer consumer = new QueueingConsumer(channel);
			consumerMap.put(endpointName, consumer);
			channelMap.put(endpointName, channel);
		}
	}
	
	public boolean getMessage(String endpointName)
	{
		boolean exist = false;
		try
		{
			//boolean ack = false;
			QueueingConsumer consumerT = consumerMap.get(endpointName);
			Channel channelT = channelMap.get(endpointName);
			if(channelT.isOpen()) 
			{
				channelT.basicConsume(endpointName, false, consumerT);
				QueueingConsumer.Delivery delivery = consumerT.nextDelivery(1000);
				if(delivery != null) 
				{
					//String messageBody = (String)SerializationUtils.deserialize(delivery.getBody());
					channelT.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					exist = true;
					return exist;
				}
			}
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
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
		
		return exist;
	}
}

