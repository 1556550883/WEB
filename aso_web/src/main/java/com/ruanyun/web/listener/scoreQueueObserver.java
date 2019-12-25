package com.ruanyun.web.listener;

import java.util.Observable;
import java.util.Observer;

import com.ruanyun.web.producer.ScoreQueueConsumer;

public class scoreQueueObserver implements Observer {
	
    @Override
    public void update(Observable observable ,Object data){
        //重启线程
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        System.out.println("-----------------------重启积分线程-------------------------------------------");
        ScoreQueueConsumer simpleObservable = null;
		try {
			simpleObservable = new ScoreQueueConsumer();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
        scoreQueueObserver simpleObserver = new scoreQueueObserver();
        //需要将观察者类加入到被观察者的观察者列表中
        simpleObservable.addObserver(simpleObserver);
        ScoreQueueConsumer.pool.execute(simpleObservable);
   }
 }
