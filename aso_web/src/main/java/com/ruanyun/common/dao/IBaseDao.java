package com.ruanyun.common.dao;

import java.io.Serializable;
import java.util.List;

import com.ruanyun.common.model.Page;

public interface IBaseDao<T> {
	
	
	/**
	 * 功能描述:保存对象
	 *
	 * @author yangliu  2013-8-16 下午04:34:22
	 * 
	 * @param <X>
	 * @param X 保存对象
	 * @return  返回序列保存对象
	 */
	T save(T x );
	
	/**
	 * 功能描述: 修改对象
	 *
	 * @author yangliu  2013-8-16 下午04:34:58
	 * 
	 * @param <X> 返回对象
	 * @param obj 修改的对象
	 * @return
	 */
	T update(T x);
	
	/**
	 * 功能描述: 修改对象
	 *
	 * @author yangliu  2013-8-16 下午04:34:58
	 * 
	 * @param <X> 返回对象
	 * @param obj 修改的对象
	 * @return
	 */
	T saveOrUpdate(T x);
	
	/**
	 * 功能描述:删除对象
	 *
	 * @author yangliu  2013-8-16 下午04:35:46
	 * 
	 * @param obj 删除对象
	 * @return
	 */
	int delete(T obj);
	
	/**
	 * 功能描述:删除对象
	 *
	 * @author yangliu  2013-8-16 下午04:37:54
	 * 
	 * @param clz 类 this.class
	 * @param pk 主键
	 * @return
	 */
	int delete(Class<T> clz,Serializable pk);
	
	/**
	 * 功能描述:获取分页对象
	 *
	 * @author yangliu  2013-8-16 下午05:10:32
	 * 
	 * @param page
	 * @param clz
	 * @return
	 */
	Page<T> queryPage(Page<T> page,Class<T> clz);
	
	/**
	 * 功能描述: 获取分页对象
	 *
	 * @author yangliu  2013-8-16 下午05:10:45
	 * 
	 * @param page 分页对象
	 * @param t
	 * @return
	 */
	Page<T> queryPage(Page<T> page,T t);
	
	/**
	 * 功能描述:获取所有
	 *
	 * @author yangliu  2013-8-20 下午03:41:31
	 * 
	 * @param clz 获取所有
	 * @return
	 */
	List<T> getAll(Class<T> clz);
	
	/**
	 * 功能描述: 获取所有
	 *
	 * @author yangliu  2013-8-20 下午03:41:58
	 * 
	 * @param t
	 * @return
	 */
	List<T> getAll(T t);
	
	/**
	 * 功能描述: 获取单个对象
	 *
	 * @author yangliu  2013-8-19 下午02:35:32
	 * 
	 * @param <T>
	 * @param entityClass 类的class对象
	 * @param id 主键
	 * @return
	 */
	@SuppressWarnings("hiding")
	<T> T get(Class<T> entityClass, Serializable id);
	
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
	@SuppressWarnings("hiding")
	<T> T get(Class<T> entityClass,String propertyName, Object value);
	
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
	@SuppressWarnings("hiding")
	<T> T get(Class<T> entityClass,String[] propertyNames, Object[] values);
	
	/**
	 * 功能描述:获取所有
	 *
	 * @author yangliu  2013-9-17 下午02:29:24
	 * 
	 * @param clz 类
	 * @param orderByColumns 排序字段
	 * @param orderBys 排序值
	 * @return
	 */
	List<T> getAll(Class<T> clz,String [] orderByColumns,String [] orderBys);
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-9-17 下午02:29:28
	 * 
	 * @param clz  类
	 * @param orderByColumn 排序字段
	 * @param orderBy 排序值
	 * @return
	 */
	List<T> getAll(Class<T> clz,String  orderByColumn,String orderBy);
	
	/**
	 * 功能描述:根据属性值删除对象
	 *
	 * @author yangliu  2013-9-25 下午03:10:34
	 * 
	 * @param <T>
	 * @param entityClass class类
	 * @param propertyName 属性数组
	 * @param value  属性值数组
	 * @return
	 */
	Integer delete(Class<T> entityClass, String[] propertyNames,Object[] values);
	
	/**
	 * 功能描述:根据属性值删除对象
	 *
	 * @author yangliu  2013-9-25 下午03:10:34
	 * 
	 * @param <T>
	 * @param entityClass class类
	 * @param propertyName 属性
	 * @param value  属性值
	 * @return
	 */
	Integer delete(Class<T> entityClass, String propertyName,Object value);
	
	/**
	 * 功能描述: 根据属性值 获取所有
	 *
	 * @author yangliu  2013-10-12 下午06:30:44
	 * 
	 * @param clz 类
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @param orderByColumn 排序属性
	 * @param orderBy  排序值
	 * @return
	 */
	public List<T> getAll(Class<T> clz,String propertyName,Object value,String  orderByColumn,String orderBy);
		
	
}
