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
	public static ExecutorService pool = Executors.newCachedThreadPool(); 
	private UserScoreService userScoreService;
	private static QueueProducer scoreQueue;
	public ScoreQueueConsumer(String endpointName, UserScoreService userScoreService) throws IOException, TimeoutException 
	{
		super(endpointName);
		this.userScoreService = userScoreService;
	}

	public static QueueProducer getQueueProducer() throws IOException, TimeoutException 
	{
		if(scoreQueue == null) 
		{
			scoreQueue = new QueueProducer("socre");
		}
		return scoreQueue;
	}
	
	@Override
	public void run()
	{
		QueueingConsumer consumer = new QueueingConsumer(channel);  
		try
		{
			channel.basicConsume("socre", true, consumer);
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
        }  
	}
}
