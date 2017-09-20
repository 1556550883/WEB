package com.ruanyun.common.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.orm.ICommonSqlDao;
import com.ruanyun.common.orm.SessionBase;
import com.ruanyun.common.orm.hql.CommonHqlExpandDao;
@Repository("baseDaoImpl")
public class BaseDaoImpl<T> extends SessionBase  {
	
	@Autowired
	@Qualifier("commonHqlExpandDao")
	protected CommonHqlExpandDao hqlDao;
	@Autowired
	@Qualifier("commonSqlExpandDao")
	protected ICommonSqlDao sqlDao;
	/**
	 * 功能描述: 删除对象
	 *
	 * @author yangliu  2013-9-11 下午01:47:34
	 * 
	 * @param obj对象
	 * @return
	 */
	public int delete(T obj) {
		try {
			hqlDao.delete(obj);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}

	public int delete(Class<T> clz, Serializable pk) {
		hqlDao.delete(clz, pk);
		return 1;
	}
	
	/**
	 * 功能描述:获取分页
	 *
	 * @author yangliu  2013-9-11 下午01:47:54
	 * 
	 * @param page 分页对象
	 * @param clz 类对象
	 * @return
	 */
	public Page<T> queryPage(Page<T> page,Class<T> clz) {
		
		String queryString="from "+clz.getName();
		return hqlDao.createQuery(queryString);
	}
	
	/**
	 * 功能描述:获取分页  如果有条件查询 则需要从写queryPageSql 方法
	 *
	 * @author yangliu  2013-9-11 下午01:48:15
	 * 
	 * @param page page对象
	 * @param t 对象
	 * @return
	 */
	public Page<T> queryPage(Page<T> page, T t) 
	{
		Map<String, Object> map = new HashMap<String, Object>();
		String queryString = queryPageSql(t,map);
		return hqlDao.queryPage(queryString,page, map);
	}
	
	/**
	 * 功能描述: 查询分页的 sql语句  如果有条件 可以重写该方法
	 *
	 * @author yangliu  2013-8-20 下午03:48:18
	 * 
	 * @param t 类对象
	 * @return
	 */
	protected String queryPageSql(T t,Map<String,Object> params) {
		String queryString="from "+t.getClass().getName();
		return queryString;
	}
	
	/**
	 * 功能描述: 保存对象
	 *
	 * @author yangliu  2013-9-11 下午01:49:04
	 * 
	 * @param x 对象
	 * @return
	 */
	public T save(T x) {
		getSession().save(x);
		return x;
	}
	
	public T saveByOpenSession(T x){
		return x;
	}
	/**
	 * 功能描述: 保存集合
	 *
	 * @author yangliu  2013-9-11 下午01:49:04
	 * 
	 * @param x 对象
	 * @return
	 */
	public int save(Collection<T> collection) {
		return hqlDao.save(collection);
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
		getSession().saveOrUpdate(x);
		return x;
	}
	
	/**
	 * 功能描述: 修改对象
	 *
	 * @author yangliu  2013-9-11 下午01:49:20
	 * 
	 * @param x 对象
	 * @return
	 */
	public T update(T x){
		getSession().saveOrUpdate(x);
		return x;
	}
	
	/**
	 * 功能描述: 获取所有的
	 *
	 * @author yangliu  2013-9-17 下午02:32:21
	 * 
	 * @param clz 类
	 * @return
	 */
	public List<T> getAll(Class<T> clz){
		return hqlDao.getAll(clz);
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
		return hqlDao.getAll(clz, orderByColumns, orderBys);
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
		return hqlDao.getAll(clz, orderByColumn, orderBy);
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
		return hqlDao.getAll(clz,propertyName,value, orderByColumn, orderBy);
	}
	
	
	public List<T> getAll(Class<T> clz,String[] propertyNames,Object[] values,String[]  orderByColumns,String[] orderBys) {
		return hqlDao.getAll(clz, propertyNames, values, orderByColumns, orderBys);
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
		return hqlDao.getAll(clz,propertyName,value,null,null);
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
		return hqlDao.getAll(clz,propertyNames,values,new String[]{},new String[]{});
	}
	/**
	 * 功能描述:获取所有  如果有条件 则需要重写getAllSql  方法
	 *
	 * @author yangliu  2013-9-11 下午01:49:38
	 * 
	 * @param t
	 * @return
	 */
	public List<T> getAll(T t) {
		String queryString=getAllSql(t);
		return hqlDao.getAll(queryString);
	}
	
	/**
	 * 功能描述: 查询所有的 sql语句  如果有条件 可以重写该方法
	 *
	 * @author yangliu  2013-8-20 下午03:48:18
	 * 
	 * @param t 类对象
	 * @return
	 */
	protected String getAllSql(T t) {
		String queryString="from "+t.getClass().getName();
		return queryString;
	}

	/**
	 * 功能描述: 根据主键获取对象
	 *
	 * @author yangliu  2013-9-11 下午01:50:34
	 * 
	 * @param <T> 对象
	 * @param entityClass class
	 * @param id 主键ID
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T get(Class<T> entityClass, Serializable id) {
		return hqlDao.get(entityClass, id);
	}
	
	/**
	 * 功能描述:根据属性获取对象
	 *
	 * @author yangliu  2013-9-11 下午01:51:00
	 * 
	 * @param <T>
	 * @param entityClass class
	 * @param propertyName 属性名称
	 * @param value
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T get(Class<T> entityClass, String propertyName, Object value) {
		return hqlDao.get(entityClass,propertyName,value);
	}
	
	/**
	 * 功能描述: 根据属性获取对象
	 *
	 * @author yangliu  2013-9-11 下午01:56:07
	 * 
	 * @param <T>
	 * @param entityClass 实体类的class对象
	 * @param propertyNames 属性数组
	 * @param values 属性值 数组
	 * @return
	 */
	@SuppressWarnings("hiding")
	public <T> T get(Class<T> entityClass, String[] propertyNames,
			Object[] values) {
		return hqlDao.get(entityClass, propertyNames, values);
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
		return hqlDao.delete(entityClass, propertyName, value);
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
		return hqlDao.delete(entityClass, propertyNames, values);
	}
	
}
