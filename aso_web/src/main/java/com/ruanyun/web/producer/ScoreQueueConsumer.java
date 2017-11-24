package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;
import org.springframework.util.SerializationUtils;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.service.background.UserScoreService;

public class ScoreQueueConsumer extends EndPoint implements  Runnable
{
	public static ExecutorService pool = Executors.newFixedThreadPool(1); 
	private UserScoreService userScoreService;
	public ScoreQueueConsumer(String endpointName, UserScoreService userScoreService) throws IOException, TimeoutException 
	{
		super(endpointName, false);
		this.userScoreService = userScoreService;
	}

	@Override
	public void run()
	{
		int prefetchCount = 1;  
	    
		try 
	    {
			channel.basicQos(prefetchCount);
		}
	    catch (IOException e2) 
	    {
			e2.printStackTrace();
		} 
	    
		QueueingConsumer consumer = new QueueingConsumer(channel);  
		try
		{
			boolean ack = false;
			channel.basicConsume("socre", ack, consumer);
		}
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		
        while (true) 
        {  
			try
			{
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				TUserScore score = (TUserScore)SerializationUtils.deserialize(delivery.getBody());
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);  
				userScoreService.updateScore(userScoreService.getScore(score.getUserNum()), score.getScore());
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
			catch (IOException e) 
			{
				e.printStackTrace();
			}  
        }  
	}
}
