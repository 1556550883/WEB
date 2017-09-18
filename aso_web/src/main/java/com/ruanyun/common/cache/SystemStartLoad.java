package com.ruanyun.common.cache;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ruanyun.common.utils.EmptyUtils;


/**
 * 
 *  #(c) ruanyun ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: Web容器启动时加载的方法。
 * 
 *  <br/>创建说明: 2013-8-22 下午02:49:43 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class SystemStartLoad implements ServletContextListener {
	
	Logger logger = Logger.getLogger(this.getClass());


	/**
	 * Web容器结束时执行的方法
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		if (logger.isInfoEnabled())
			logger.info("--> contextDestroyed(ServletContextEvent arg0)");

	}

	/**
	 * WEB容器启动时执行的方法
	 */
	public synchronized void contextInitialized(ServletContextEvent arg0) {
		if (this.logger.isInfoEnabled())
			logger.info("启动--> contextInitialized(ServletContextEvent arg0)");
		List<String> classList=getLoadClass();
		if (EmptyUtils.isNotEmpty(classList)) {
			for (int i = 0; i < classList.size(); i++) {
				SystemCacheService scs = (SystemCacheService) WebApplicationContextUtils
						.getWebApplicationContext(arg0.getServletContext())
						.getBean(classList.get(i));
				scs.run();
				System.out.println(scs.getCacheName());
			}
		}
		logger.info("启动结束--> contextInitialized(ServletContextEvent arg0)");
	}
	
	public List<String> getLoadClass(){
		List<String> list = new ArrayList<String>();
		list.add("publicCache");
		list.add("copyCach");//字段赋值(放在，publicCache后面，staticObjectCache前面)
		list.add("staticObjectCache");
		list.add("authCache");
		list.add("areaCache");
		return list;
	}
}
