package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.util.SerializationUtils;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class UdidQueueConsumer extends EndPoint
{
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
			QueueingConsumer consumerT = new QueueingConsumer(channel);  
			channel.basicConsume(endpointName, false, consumerT);
			QueueingConsumer.Delivery delivery = consumerT.nextDelivery(500);
			if(delivery != null) 
			{
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				messageBody = (String)SerializationUtils.deserialize(delivery.getBody());
				close();
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
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
		} 
		
		return messageBody;
	}
}
