package com.ruanyun.web.listener;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ruanyun.web.producer.ArrayBlockQueueProducer;
import com.ruanyun.web.producer.ScoreQueueConsumer;

@Component
public class AdverListener implements ApplicationContextAware
{
	@Autowired
	private ArrayBlockQueueProducer mAdverProducer;
	@Autowired
	private ScoreQueueConsumer mScoreQueueConsumer;
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException 
	{
		System.out.println("------------------------web start------------------------");
		
		ArrayBlockQueueProducer.pool.execute(mAdverProducer);
		ScoreQueueConsumer.pool.execute(mScoreQueueConsumer);
	}
} 
