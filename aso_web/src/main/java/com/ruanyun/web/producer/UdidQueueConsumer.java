package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.util.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class UdidQueueConsumer extends EndPoint
{
	QueueingConsumer consumerT = new QueueingConsumer(channel);  
	
	public UdidQueueConsumer(String endpointName, boolean autoDelete) throws IOException, TimeoutException 
	{
		super(endpointName, autoDelete);
	}
	
	public String getMessage(String endpointName)
	{
		int prefetchCount = 1;  
		String messageBody = null;
		try
		{
			channel.basicQos(prefetchCount);
			//boolean ack = false;
			Channel channelT = consumerT.getChannel();
			channelT.basicConsume(endpointName, false, consumerT);
			QueueingConsumer.Delivery delivery = consumerT.nextDelivery(500);
			if(delivery != null) 
			{
				channelT.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				messageBody = (String)SerializationUtils.deserialize(delivery.getBody());
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
		
		return messageBody;
	}
}
