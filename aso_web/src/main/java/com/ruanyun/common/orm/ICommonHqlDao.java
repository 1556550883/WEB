package com.ruanyun.common.orm;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

/**
 * 
 *  #(c) ruanyun ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: hql 语句查询扩展
 * 
 *  <br/>创建说明: 2013-8-19 上午09:48:29 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public interface ICommonHqlDao extends ICommonDao {
	
	/**
	 * 降序
	 */
	public static final String ORDER_DESC="DESC";
	/**
	 * 升序
	 */
	public static final String ORDER_ASC="ASC";
	
	/**
	 * 功能描述: save
	 *
	 * @author yangliu  2013-9-11 下午06:05:02
	 * 
	 * @param <T>
	 * @param model
	 * @return
	 */
	<T> T save(T model);
	
	/**
	 * 功能描述:修改对象信息
	 *
	 * @author yangliu  2013-9-12 上午10:26:27
	 * 
	 * @param <T>
	 * @param model
	 * @return
	 */
	<T>T update(T model);
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
	<T> T get(Class<T> entityClass,String[] propertyNames, Object[] values);
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-8-19 下午02:58:52
	 * 
	 * @param <X> 返回类型
	 * @param hql hql语句
	 * @param values 参数值
	 * @return
	 */
   <X> X get(String hql, Map<String, Object> values);
	
	/**
	 * 功能描述:获取所有的
	 *
	 * @author yangliu  2013-8-19 下午02:36:16
	 * 
	 * @param <T>
	 * @param entityClass  类的class对象
	 * @return
	 */
    <T> List<T> getAll(Class<T> entityClass);
    
    /**
     * 功能描述: 获取所有 排序
     *
     * @author yangliu  2013-9-17 上午11:50:09
     * 
     * @param <T>
     * @param entityClass
     * @param orderByColumns 需要排序的列
     * @return
     */
    <T> List<T> getAll(Class<T> entityClass,String [] orderByColumns);
    
    
   /**
    * 功能描述:获取所有 排序
    *
    * @author yangliu  2013-9-17 上午11:50:54
    * 
    * @param <T>
    * @param entityClass
    * @param orderByColumns 需要排序的列
    * @param orderBys 排序方式
    * @return
    */
    <T> List<T> getAll(Class<T> entityClass,String [] orderByColumns,String [] orderBys);
    
    
    /**
     * 功能描述:获取所有 排序
     *
     * @author yangliu  2013-9-17 上午11:51:57
     * 
     * @param <T>
     * @param entityClass
     * @param orderByColumn 属性名称
     * @param orderBy 排序方式
     * @return
     */
     <T> List<T> getAll(Class<T> entityClass,String orderByColumn,String orderBy);
     
     /**
      * 功能描述:更具条件获取所有
      *
      * @author yangliu  2013-10-12 下午05:14:18
      * 
      * @param <T>
      * @param entityClass
      * @param propertyName 属性名称
      * @param value 属性值
      * @param orderByColumn 排序字段
      * @param orderBy 排序类型
      * @return
      */
     <T> List<T> getAll(Class<T> entityClass,String propertyName, Object value,String orderByColumn,String orderBy);
     
     /**
      * 
      * @param <T>
      * @param entityClass
      * @param propertyNames 属性名称
      * @param values 属性值
      * @param orderByColumns 排序字段
      * @param orderBys 排序类型
      * @return
      */
 	 <T> List<T> getAll(Class<T> entityClass, String[] propertyNames,Object[] values, String[] orderByColumns, String[] orderBys);
	
	/**
	 * 功能描述: 排序获取所有的
	 *
	 * @author yangliu  2013-8-19 下午02:37:38
	 * 
	 * @param <T>
	 * @param entityClass 类的class对象
	 * @param orderBy 排序字段
	 * @param isAsc 是否升序
	 * @return
	 */
	<T> List<T> getAll(Class<T> entityClass, String orderBy, boolean isAsc);
	
	/**
	 * 功能描述:删除对象
	 *
	 * @author yangliu  2013-8-20 上午09:22:26
	 * 
	 * @param <T> 
	 * @param t 删除的对象
	 */
	<T> void delete(T t);
	
	/**
	 * 功能描述: 根据主键删除对象
	 *
	 * @author yangliu  2013-8-19 下午02:38:46
	 * 
	 * @param <T> 对象
	 * @param entityClass entityClass 类的class对象
	 * @param id 主键ID
	 */
	<T> void delete(Class<T> entityClass, Serializable id);
	
	/**
	 * 功能描述: 根据属性删除对象列表
	 *
	 * @author yangliu  2013-8-19 下午03:21:53
	 * 
	 * @param <T>
	 * @param entityClass class 类型行
	 * @param propertyName 属性名称
	 * @param value 属性值
	 */
	<T> Integer delete(Class<T> entityClass,String propertyName, Object value);
	
	/**
	 * 功能描述:根据属性删除对象列表
	 *
	 * @author yangliu  2013-8-19 下午03:23:06
	 * 
	 * @param <T>
	 * @param entityClass class 类对象
	 * @param propertyNames 属性名称
	 * @param values 属性值
	 */
	<T> Integer delete(Class<T> entityClass,String[] propertyNames, Object[] values);
	
	/**
	 * 功能描述:QBC 查询 创建一个模板
	 *
	 * @author yangliu  2013-8-19 下午03:34:23
	 * 
	 * @param <T>
	 * @param entityClass 类的class对象
	 * @param criterions 模板类型
	 * @return
	 */
    <T> Criteria createCriteria(Class<T> entityClass, Criterion[] criterions);
    
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-10-12 下午04:42:15
	 * 
	 * @param <T>
	 * @param entityClass 类的class对象
	 * @param criterions 模板类型
	 * @param aliasList 关联的对象
	 * @return
	 */
	public <T> Criteria createCriteria(Class<T> entityClass,
			Criterion[] criterions,List<String> aliasList);
    
    /**
     * 功能描述:QBC 查询 创建一个模板
     *
     * @author yangliu  2013-8-19 下午03:48:12
     * 
     * @param <T>
     * @param entityClass 类的class对象
     * @param criterion 模板类型
     * @return
     */
    <T> Criteria createCriteria(Class<T> entityClass, Criterion criterion);
    
    /**
	 * 功能描述: QBC 查询 创建一个模板
	 *
	 * @author yangliu  2013-8-19 下午03:54:10
	 * 
	 * @param <X>
	 * @param entityClass 类的class对象
	 * @param orderBy 排序字段
	 * @param isAsc 是否升序
	 * @param criterions  模板类型
	 * @return
	 */
	<X> Criteria createCriteria(Class<X> entityClass, String orderBy, boolean isAsc, Criterion[] criterions);
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-9-17 上午11:55:15
	 * 
	 * @param <X>
	 * @param entityClass
	 * @param orderByColumns 排序列
	 * @param orderBys 排序值
	 * @param criterions
	 * @return
	 */
	<X> Criteria createCriteria(Class<X> entityClass, String[] orderByColumns, String [] orderBys, Criterion[] criterions);
	
	/**
	 * 功能描述: QBC 查询 创建一个模板
	 *
	 * @author yangliu  2013-9-12 下午04:51:26
	 * 
	 * @param <X>
	 * @param entityClass 类的class对象
	 * @param propertyNames 属性
	 * @param value 值
	 * @return
	 */
	<X>Criteria createCriteria(Class<X> entityClass,String propertyNames,Object value);
	
	/**
	 * 功能描述: QBC 查询 创建一个模板
	 *
	 * @author yangliu  2013-9-12 下午04:51:26
	 * 
	 * @param <X>
	 * @param entityClass 类的class对象
	 * @param propertyNames 属性
	 * @param value 值
	 * @return
	 */
	<X>Criteria createCriteria(Class<X> entityClass,String[] propertyNames,Object[] value);
	
	
	/**
	 * 功能描述: 根据某个属性获取所有（属性等于值）
	 *
	 * @author yangliu  2013-9-12 下午05:00:57
	 * 
	 * @param <T>
	 * @param entityClass
	 * @param propertyNames 属性
	 * @param value 值
	 * @return
	 */
	<T>List<T> getAll(Class<T> entityClass,String propertyNames,Object value);
	 
	 /**
		 * 功能描述: 根据某个属性获取所有（属性等于值）
		 *
		 * @author yangliu  2013-9-12 下午05:00:57
		 * 
		 * @param <T>
		 * @param entityClass
		 * @param propertyNames 属性
		 * @param value 值
		 * @return
		 */
	<T>List<T> getAll(Class<T> entityClass,String[] propertyNames,Object[] value);
	 
	 /**
	  * 功能描述: 获取唯一的 可以是列，也可以是对象
	  *
	  * @author yangliu  2013-7-19 上午09:28:14
	  * 
	  * @param <X>
	  * @param queryString hql 语句
	  * @param params 参数值
	  * @return 返回结果
	  */
	 <X> X findUnique(String queryString,Object...params);
	 
	 /**
	  * 功能描述: 获取唯一的 可以是列，也可以是对象
	  *
	  * @author yangliu  2013-7-19 上午09:28:14
	  * 
	  * @param <X>
	  * @param queryString hql 语句
	  * @param params 参数值
	  * @return 返回结果
	  */
	 <X> X findUnique(Object [] params,String queryString);
	 
	 /**
	  * 功能描述: 批量保存数据
	  *
	  * @author yangliu  2013-9-12 下午03:43:23
	  * 
	  * @param <T>
	  * @param collection 集合
	  * @return 返回保存数量
	  */
	 <T>Integer save(Collection<T>  collection);
	 
	 /**
	  * 功能描述:保存或者删除
	  *
	  * @author yangliu  2013-9-12 下午05:08:30
	  * 
	  * @param <T>
	  * @param model
	  */
	<T> void saveOrUpdate(T model);
	
}
