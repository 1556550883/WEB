package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class AdverQueueConsumer extends EndPoint
{
	public AdverQueueConsumer(String endpointName) throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException
	{
		super(endpointName, false);
	}
	
	public boolean getMessage(String endpointName)
	{
		boolean exist = false;
		try
		{
			//boolean ack = false;
			int prefetchCount = 1; 
			channel.basicQos(prefetchCount);
			QueueingConsumer consumerT = new QueueingConsumer(channel);
			//QueueingConsumer consumerT = consumerMap.get(endpointName);
			//Channel channelT = consumerT.getChannel();
			channel.basicConsume(endpointName, false, consumerT);
			QueueingConsumer.Delivery delivery = consumerT.nextDelivery(500);
			if(delivery != null) 
			{
				//String messageBody = (String)SerializationUtils.deserialize(delivery.getBody());
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				exist = true;
			}
			
			close();
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
			e.printStackTrace();
		} 
		
		return exist;
	}
}

