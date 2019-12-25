package com.ruanyun.web.listener;

import java.util.Observable;
import java.util.Observer;

import com.ruanyun.web.producer.ArrayBlockQueueProducer;

public class AdverBlockQueueObserver implements Observer {
	
    @Override
    public void update(Observable observable ,Object data){
        //重启线程
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        System.out.println("----------------------------重启任务线程------------------------------");
        ArrayBlockQueueProducer simpleObservable = new ArrayBlockQueueProducer();
		AdverBlockQueueObserver simpleObserver = new AdverBlockQueueObserver();
        //需要将观察者类加入到被观察者的观察者列表中
        simpleObservable.addObserver(simpleObserver);
        ArrayBlockQueueProducer.pool.execute(simpleObservable);
   }
 }
