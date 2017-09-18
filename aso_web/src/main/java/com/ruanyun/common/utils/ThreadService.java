package com.ruanyun.common.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 线程业池管理类
 * @author yangliu
 *
 */
public class ThreadService {
	
	/**
	 * 第一个参数为  线程池维护线程的最少数量
	 * 第二个参数为  线程池维护线程的最大数量
	 * 第三个参数为  线程池维护线程所允许的空闲时间
	 * 第四个参数为  线程池维护线程所允许的空闲时间的单位
	 * 第五个参数为  线程池所使用的缓冲队列
	 * 第六个参数为  线程池对拒绝任务的处理策略
	 */
	public static final ThreadPoolExecutor threadPool = 
		new ThreadPoolExecutor(1, 1, 20,
			TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>( 50 ),
			new ThreadPoolExecutor.AbortPolicy());
	/**
	 * 提交线程
	 * @param run
	 */
	public static void submit(Runnable run){
		threadPool.execute(run);
	}

}
