/**
  * 版权信息  : (C)ruanyun<br/>
  * 工程名称  : ruanyunmvc<br/>
  * 文件版本  : SessionUtils.java $id:$ <br/>
  * 创建者       : yangliu<br/>
  * 创建时间  : 2013-8-17 下午03:32:44<br/>
  * 
  **/
package com.ruanyun.common.orm;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 *  #(c) ruanyun ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 
 * 
 *  <br/>创建说明: 2013-8-17 下午03:32:44 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class SessionBase {
	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	protected Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * 打开session
	 * @return
	 */
	protected Session openSession() {
		return sessionFactory.openSession();
	}
	
	/**
	 * 关闭session
	 * @param session
	 */
	protected void closeSession(Session session) {
		if(session!=null){
			session.flush();
			if(session.isOpen())
				session.close();
		}
	}
}
