package com.ruanyun.common.orm;

import java.util.List;
import java.util.Map;

import com.ruanyun.common.model.Page;

/**
 * 
 *  #(c) ruanyun ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: sql 语句扩展接口
 * 
 *  <br/>创建说明: 2013-8-20 下午01:35:43 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public interface ICommonSqlDao extends ICommonDao{
	
	/**
	 * 功能描述: 获取单个对象
	 *
	 * @author yangliu  2013-8-19 下午03:06:20
	 * 
	 * @param <T>
	 * @param entityClass 类的class对象
	 * @param propertyName 属性
	 * @param value 属性值
	 * @return
	 */
	<T> T get(Class<T> entityClass,String sql,Object...params);
	
	/**
	 * 功能描述:获取单个对象
	 *
	 * @author yangliu  2013-8-19 下午03:24:28
	 * 
	 * @param <T>
	 * @param entityClass 类的class对象
	 * @param propertyNames 属性
	 * @param values 属性值
	 * @return
	 */
	<T> T get(Object[] values,Class<T> entityClass,String sql);
	
	/**
	 * 功能描述:获取单个对象
	 *
	 * @author yangliu  2013-8-19 下午03:24:28
	 * 
	 * @param <T>
	 * @param entityClass 类的class对象
	 * @param propertyNames 属性
	 * @param params 属性值
	 * @return
	 */
	<T> T get(Class<T> entityClass,String sql,Map<String,Object> params);
	
	/**
	 * 功能描述: 根据条件删除数据
	 *
	 * @author yangliu  2013-8-20 下午02:24:23
	 * 
	 * @param <T>
	 * @param params 参数
	 * @param sql sql语句
	 * @return
	 */
	int delete(Object[] params,String sql);
	
	/**
	 * 功能描述: 根据条件删除数据
	 *
	 * @author yangliu  2013-8-20 下午02:24:52
	 * 
	 * @param <T>
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
	int delete(String sql,Object...params);
	
	/**
	 * 功能描述: 根据条件删除数据
	 *
	 * @author yangliu  2013-8-20 下午02:24:52
	 * 
	 * @param <T>
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
	int delete(String sql,Map<String,Object> params);
	
	/**
	 * 功能描述: 根据条件删除数据
	 *
	 * @author yangliu  2013-8-20 下午02:25:07
	 * 
	 * @param <T>
	 * @param params 参数
	 * @param sql sql语句
	 * @return
	 */
	int update(Object[] params,String sql);
	
	/** 
	 * 功能描述: 根据条件删除数据
	 *
	 * @author yangliu  2013-8-20 下午02:25:34
	 * 
	 * @param <T>
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
	int update(String sql,Object...params);
	
	/** 
	 * 功能描述: 根据条件删除数据
	 *
	 * @author yangliu  2013-8-20 下午02:25:34
	 * 
	 * @param <T>
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
	int update(String sql,Map<String,Object> params);
	
	/**
	 * 功能描述:获取所有的
	 *
	 * @author yangliu  2013-8-19 下午02:36:16
	 * 
	 * @param <T>
	 * @param entityClass  类的class对象
	 * @param sql  sql语句
	 * @return
	 */
    <T> List<T> getAll(Class<T> entityClass,String sql);
    
	/**
	 * 功能描述: 获取所有的
	 *
	 * @author yangliu  2013-8-20 下午01:55:55
	 * 
	 * @param <T>
	 * @param entityClass 对象class
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
    <T> List<T> getAll(Class<T> entityClass,String sql,Object...params);
    
	/**
	 * 功能描述: 获取所有的
	 *
	 * @author yangliu  2013-8-20 下午01:55:55
	 * 
	 * @param <T>
	 * @param entityClass 对象class
	 * @param sql sql语句
	 * @param params 参数
	 * @return
	 */
    <T> List<T> getAll(Class<T> entityClass,String sql,Map<String,Object> params);
    
   /**
    * 功能描述: 获取所有的
    *
    * @author yangliu  2013-8-20 下午01:56:00
    * 
    * @param <T>
    * @param params 参数
    * @param entityClass 对象class
    * @param sql sql语句
    * @return
    */
    <T> List<T> getAll(Object[] params,Class<T> entityClass,String sql);
    
    /**
	 * 功能描述: 获取分页信息
	 *
	 * @author yangliu  2013-8-17 下午02:37:10
	 * 
	 * @param page 分页对象
	 * @param queryString 条件对象
	 * @param params 参数类型
	 * @return 分页对象   
	 */
	<X> Page<X> queryPage(Page<X> page,Class<X> entityClass,String queryString,Object...params);
	
	/** 功能描述:获取分页信息
	 *
	 * @author yangliu  2013-8-17 下午12:04:44
	 * 
	 * @param params 条件对象
	 * @param page 分页对象
	 * @param queryString 查询语句
	 * @return 分页对象 
	 */
	<X> Page<X> queryPage(Object[] params,String queryString,Page<X> page,Class<X> entityClass);
	
	/**
	 * 功能描述: 获取分页信息
	 *
	 * @author yangliu  2013-8-17 下午02:37:10
	 * 
	 * @param page 分页对象
	 * @param queryString 条件对象
	 * @param params 参数类型
	 * @return 分页对象   
	 */
	<X> Page<X> queryPage(Page<X> page,Class<X> entityClass,String queryString,Map<String,Object> params);

}
