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
 *  功能说明: 共用接口
 * 
 *  <br/>创建说明: 2013-8-17 下午01:50:14 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public interface ICommonDao {
	
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
	<X> Page<X> queryPage(Page<X> page,String queryString,Object...params);
	
	
	/** 功能描述:获取分页信息
	 *
	 * @author yangliu  2013-8-17 下午12:04:44
	 * 
	 * @param params 条件对象
	 * @param page 分页对象
	 * @param queryString 查询语句
	 * @return 分页对象 
	 */
	<X> Page<X> queryPage(String queryString,Page<X> page,Map<String, Object> params);
	
	/**
	 * 功能描述:获取分页信息
	 *
	 * @author yangliu  2013-8-17 下午02:36:05
	 * 
	 * @param page 分页对象
	 * @param queryString 查询语句
	 * @param params 条件
	 * @return 分页对象   
	 */
	Integer queryPageCount(String queryString,Map<String, Object> params);
	/**
	 * 功能描述:获取分页信息
	 *
	 * @author yangliu  2013-8-17 下午02:36:05
	 * 
	 * @param page 分页对象
	 * @param queryString 查询语句
	 * @param params 条件
	 * @return 分页对象   
	 */
	Integer queryPageCount(String queryString,Object...params);
	
	/**
	 * 功能描述: 获取分页信息
	 *
	 * @author yangliu  2013-8-17 下午02:35:18
	 * 
	 * @param params 参数
	 * @param queryString sql语句
	 * @param page 分页对象
	 * @return 分页对象  hql 
	 */
	Integer queryPageCount(Object[] params,String queryString);
	
	/**
	 * 功能描述: 获取条数
	 *
	 * @author yangliu  2013-8-17 下午02:24:11
	 * 
	 * @param <X> 返回类型
	 * @param queryString 查询语句
	 * @param params 参数
	 * @return 
	 */
	Integer getCount(String queryString,Map<String, Object> params);
	
	/**
	 * 功能描述: 获取条数
	 *
	 * @author yangliu  2013-8-17 下午02:24:11
	 * 
	 * @param <X> 返回类型
	 * @param queryString 查询语句
	 * @param params 参数
	 * @return 
	 */
	Integer getCount(String queryString,Object...params);
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-8-17 下午02:33:09
	 * 
	 * @param <X> 返回类型
	 * @param params 参数
	 * @param queryString 查询语句
	 * @return
	 */
	Integer getCount(Object[] params,String queryString);
	
	/**
	 * 功能描述: 获取单个对象  hql 为 对象  sql为map 获取不到为null
	 *
	 * @author yangliu  2013-8-17 下午03:04:50
	 * 
	 * @param <X> 返回类型  对象 或者map
	 * @param queryString  查询语句
	 * @param params 参数
	 * @return
	 */
	<X> X get(String queryString,Object...params);
	
	/**
	 * 功能描述:  获取单个对象  hql 为 对象  sql为map 获取不到为null
	 *
	 * @author yangliu  2013-8-17 下午03:14:34
	 * 
	 * @param <X> 返回类型  对象 或者map
	 * @param params  参数
	 * @param queryString 查询语句
	 * @return
	 */
	<X> X get(Object [] params,String queryString);
	
	/**
	 * 功能描述: 创建query 对
	 *
	 * @author yangliu  2013-8-17 下午03:11:55
	 * 
	 * @param <X> 返回 SQL Query 或者HQL query
	 * @param queryString 查询语句
	 * @param params 参数
	 * @return
	 */
	<X> X createQuery(String queryString,Object...params);
	
	/**
	 * 功能描述: 创建query 对象
	 *
	 * @author yangliu  2013-8-17 下午03:13:41
	 * 
	 * @param <X> <X> 返回 SQL Query 或者HQL query
	 * @param params 参数
	 * @param queryString  查询语句
	 * @return
	 */
	<X> X createQuery(Object[] params,String queryString);
	
	/**
	 * 功能描述:创建query 对象
	 *
	 * @author yangliu  2013-8-19 下午05:15:30
	 * 
	 * @param <X>
	 * @param queryString 查询语句
	 * @param params 参数
	 * @return
	 */
	<X> X createQuery(String queryString,Map<String, Object> params);
	
	/**
	 * 功能描述: 获取列表
	 *
	 * @author yangliu  2013-8-17 下午06:14:29
	 * 
	 * @param <X>
	 * @param queryString 查询语句
	 * @param params 条件
	 * @return 列表
	 */
	<X> List<X> getAll(String queryString,Object...params);
	
	/**
	 * 功能描述:获取列表
	 *
	 * @author yangliu  2013-8-17 下午06:15:20
	 * 
	 * @param <X> 返回条件
	 * @param params  条件
	 * @param queryString 查询语句
	 * @return
	 */
	<X> List<X> getAll(Object[] params,String queryString);
	
	/**
	 * 功能描述:获取列表
	 *
	 * @author yangliu  2013-8-17 下午06:15:20
	 * 
	 * @param <X> 返回条件
	 * @param params  条件
	 * @param queryString 查询语句
	 * @return
	 */
	<X> List<X> getAll(String queryString,Map<String,Object> params);
	
	/**
	 * 功能描述:执行语句
	 *
	 * @author yangliu  2013-8-28 下午03:21:40
	 * 
	 * @param queryString 执行语句
	 * @param params 参数
	 * @return
	 */
	int execute(String queryString,Object... params);
	
	/**
	 * 功能描述: 
	 *
	 * @author yangliu  2013-8-28 下午03:21:53
	 * 
	 * @param params 参数
	 * @param queryString 执行语句
	 * @return
	 */
	int execute(Object[] params,String queryString);
	
	/**
	 * 功能描述: 
	 *
	 * @author yangliu  2013-8-28 下午03:21:53
	 * 
	 * @param params 参数
	 * @param queryString 执行语句
	 * @return
	 */
	int execute(String queryString,Map<String,Object> map);
	
	
	
}
