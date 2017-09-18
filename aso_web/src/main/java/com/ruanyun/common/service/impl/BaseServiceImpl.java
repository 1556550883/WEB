package com.ruanyun.common.service.impl;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;

/**
 * 
 *  #(c) ruanyun fangweima <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 业务处理类
 * 
 *  <br/>创建说明: 2013-8-26 下午02:26:13 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 * 	@param <T>
 */
@Service("baseServiceImpl")
public  class BaseServiceImpl<T>  {
	protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("baseDaoImpl")
	public BaseDaoImpl<T> baseDao;
	
	/**
	 * 功能描述: 删除对象
	 *
	 * @author yangliu  2013-9-11 下午01:47:04
	 * 
	 * @param obj 对象
	 * @return
	 */
	public int delete(T obj) {
		return baseDao.delete(obj);
	}
	
	/**
	 * 功能描述: 根据主键删除信息
	 *
	 * @author yangliu  2013-9-11 下午01:46:42
	 * 
	 * @param clz class对象
	 * @param pk 主键
	 * @return
	 */
	public int delete(Class<T> clz, Serializable pk) {
		return baseDao.delete(clz,pk);
	}
	
	/**
	 * 功能描述: 获取所有
	 *
	 * @author yangliu  2013-9-11 下午01:46:18
	 * 
	 * @param clz class 对象
	 * @return
	 */
	public List<T> getAll(Class<T> clz) {
		return baseDao.getAll(clz);
	}
	
	/**
	 * 功能描述:获取所有 如果dao层从写此方法  则service 也需要重写
	 *
	 * @author yangliu  2013-9-11 下午01:45:08
	 * 
	 * @param t 对象
	 * @return
	 */
	public List<T> getAll(T t) {
		return baseDao.getAll(t);
	}
	
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
	public List<T> getAll(Class<T> clz,String [] orderByColumns,String [] orderBys) {
		return baseDao.getAll(clz, orderByColumns, orderBys);
	}
	
	
	/**
	 * 功能描述:获取所有
	 *
	 * @author yangliu  2013-10-14 上午09:45:52
	 * 
	 * @param clz 类对象
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @param orderByColumn 排序名称
	 * @param orderBy 排序值
	 * @return
	 */
	public List<T> getAll(Class<T> clz,String propertyName,Object value,String  orderByColumn,String orderBy) {
		return baseDao.getAll(clz,propertyName,value, orderByColumn, orderBy);
	}
	
	public List<T> getAll(Class<T> clz,String[] propertyNames,Object[] values,String[]  orderByColumns,String[] orderBys) {
		return baseDao.getAll(clz, propertyNames, values, orderByColumns, orderBys);
	}
	
	/**
	 * 功能描述:获取所有
	 *
	 * @author yangliu  2013-10-14 上午09:46:30
	 * 
	 * @param clz 类对象
	 * @param propertyName  属性名称
	 * @param value 属性值
	 * @return
	 */
	public List<T> getAllByCondition(Class<T> clz,String propertyName,Object value){
		return baseDao.getAllByCondition(clz,propertyName,value);
	}
	
	/**
	 * 功能描述: 获取所有
	 *
	 * @author yangliu  2013-10-14 上午09:46:52
	 * 
	 * @param clz 类对象
	 * @param propertyNames 属性名称
	 * @param values 属性值
	 * @return
	 */
	public List<T> getAllByCondition(Class<T> clz,String[] propertyNames,Object[] values){
		return baseDao.getAllByCondition(clz,propertyNames,values);
	}
	
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
	public List<T> getAll(Class<T> clz,String  orderByColumn,String orderBy) {
		return baseDao.getAll(clz, orderByColumn, orderBy);
	}
	
	/**
	 * 功能描述: 获取分页无条件
	 *
	 * @author yangliu  2013-9-11 下午01:44:39
	 * 
	 * @param page 分页对象
	 * @param clz 对象的class
	 * @return
	 */
	public Page<T> queryPage(Page<T> page, Class<T> clz) {
		return baseDao.queryPage(page, clz);
	}
	
	/**
	 * 功能描述:获取分页  如果dao层了 queryPage  service 层 也许重写此方法
	 *
	 * @author yangliu  2013-9-11 下午01:41:57
	 * 
	 * @param page page对象
	 * @param t 对象
	 * @return
	 */
	public Page<T> queryPage(Page<T> page, T t) {
		return baseDao.queryPage(page, t);
	}

	/**
	 * 功能描述:保存对象
	 *
	 * @author yangliu  2013-8-16 下午04:34:22
	 * 
	 * @param <X>
	 * @param X 保存对象
	 * @return  返回序列保存对象
	 */
	public T save(T x) {
		
		return baseDao.save(x);
	}
	
	
	/**
	 * 功能描述: 修改对象
	 *
	 * @author yangliu  2013-9-11 下午01:49:20
	 * 
	 * @param x 对象
	 * @return
	 */
	public T saveOrUpdate(T x) {
		return baseDao.saveOrUpdate(x);
	}
	
	/**
	 * 功能描述: 修改对象
	 *
	 * @author yangliu  2013-8-16 下午04:34:58
	 * 
	 * @param <X> 返回对象
	 * @param obj 修改的对象
	 * @return
	 */
	public T update(T x) {
		return baseDao.update(x);
	}

	/**
	 * 功能描述:根据主键获取对象
	 *
	 * @author yangliu  2013-9-11 下午01:40:14
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param id 主键
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T get(Class<T> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}
	
	/**
	 * 功能描述:根据属性获取对象
	 *
	 * @author yangliu  2013-9-11 下午01:40:36
	 * 
	 * @param <T>
	 * @param entityClass 对象的class
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T get(Class<T> entityClass, String propertyName, Object value) {
		return baseDao.get(entityClass, propertyName, value);
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-9-11 下午01:41:14
	 * 
	 * @param <T>
	 * @param entityClass 对象的class
	 * @param propertyNames 属性数组
	 * @param values 值数组
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T get(Class<T> entityClass, String[] propertyNames,
			Object[] values) {
		return baseDao.get(entityClass, propertyNames, values);
	}
	
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
	public Integer delete(Class<T> entityClass, String propertyName,
			Object value) {
		return baseDao.delete(entityClass, propertyName, value);
	}
	
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
	@SuppressWarnings("hiding")
	public <T> Integer delete(Class<T> entityClass, String[] propertyNames,
			Object[] values) {
		return baseDao.delete(entityClass, propertyNames, values);
	}

}
