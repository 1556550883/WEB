package com.ruanyun.common.cache;

/**
 * 
 *  #(c) ruanyun ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 系统缓存实现接口，该接口在WEB容器启动时可以自动加载缓存
 * 
 *  <br/>创建说明: 2013-8-22 下午02:49:56 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public interface SystemCacheService {

	/**
	 * 第一次WEB容器启动时加载缓存运行的方法。 建议用“synchronized”方法
	 */
	void run();

	/**
	 * 更新缓存的方法
	 */
	void update();

	/**
	 * 当前缓存模块名称
	 * 
	 * @return
	 */
	String getCacheName();

}