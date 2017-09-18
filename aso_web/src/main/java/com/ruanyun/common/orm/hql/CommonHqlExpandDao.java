package com.ruanyun.common.orm.hql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ruanyun.common.orm.ICommonHqlDao;
import com.ruanyun.common.utils.EmptyUtils;

/**
 * 
 *  #(c) ruanyun ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: hql 语句执行扩展类
 * 
 *  <br/>创建说明: 2013-8-20 下午02:36:07 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Repository("commonHqlExpandDao")
@SuppressWarnings("unchecked")
public class CommonHqlExpandDao extends CommonHqlDao implements ICommonHqlDao {

	public <T> void delete(T t) {
		getSession().delete(t);
	}
	
	public <T> void delete(Class<T> entityClass, Serializable id) {
		delete(get(entityClass, id));
	}

	public <T> Integer delete(Class<T> entityClass, String propertyName,
			Object value) {
		
		return delete(entityClass,new String[]{propertyName},new Object[]{value});
	}

	public <T> Integer delete(Class<T> entityClass, String[] propertyNames,
			Object[] values) {
		StringBuffer hql=new StringBuffer("delete from "+entityClass.getName()+" where 1=1");
		for(String propertyName :propertyNames){
			hql.append(" and ").append(propertyName).append(" = ?");
		}
		Query query =createQuery(values, hql.toString());
		return query.executeUpdate();
		/*List<T> list=getAll(entityClass, propertyNames, values);
		for(T t: list){
			delete(t);
		}
		return list.size();*/
	}

	public <T> T get(Class<T> entityClass, Serializable id) {
		Assert.notNull(id, "id不能为空");
	    return (T) getSession().get(entityClass, id);
	}

	
	public <T> T get(Class<T> entityClass, String propertyName, Object value) {

		Assert.hasText(propertyName);
		@SuppressWarnings("unused")
		Criteria c =createCriteria(entityClass, propertyName, value);
		return get(entityClass, new String[]{propertyName}, new Object[]{value});
	}

	public <T> T get(Class<T> entityClass, String[] propertyNames,
			Object[] values) {
		Assert.notNull(propertyNames,"属性不能为空");
		if(EmptyUtils.isNotEmpty(propertyNames)&& EmptyUtils.isNotEmpty(values)){
			int j=propertyNames.length;
			List<Criterion> criterionList=new ArrayList<Criterion>();
			List<String> aliasList=new ArrayList<String>();
			if(j==values.length){
				for(int i=0;i<j;i++){
					String propertyName=propertyNames[i];
					Object value=values[i];
					if(EmptyUtils.isNotEmpty(propertyName)&& value!=null){
						criterionList.add(Restrictions.eq(propertyName, value));
						if(propertyName.indexOf(".")>0){
							String [] aliases=propertyName.split("\\.");
							String alias="";
							for(int a=0;a<aliases.length-1;a++){
								alias=aliases[a];
								if(!aliasList.contains(alias)){
									aliasList.add(alias);
								}
							}
						}
					}
				}
				Criterion [] creterions=new Criterion[criterionList.size()];
				 criterionList.toArray(creterions);
				return (T) createCriteria(entityClass,creterions,aliasList).uniqueResult();
			}
		}
		
		return null;
	}

	public <X> X get(String hql, Map<String, Object> values) {
		return createQuery(hql, values);
	}

	public <T> List<T> getAll(Class<T> entityClass) {
		 return createCriteria(entityClass, new Criterion[]{}).list();
	}

	public <T> List<T> getAll(Class<T> entityClass, String orderBy,
			boolean isAsc) {
		
		return createCriteria(entityClass, orderBy, isAsc, new Criterion[]{}).list();
	}

	public <T> Criteria createCriteria(Class<T> entityClass,
			Criterion[] criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if (EmptyUtils.isNotEmpty(criterions)) {
			int j = criterions.length;
			for (int i = 0; i < j; ++i) {
				Criterion c = criterions[i];
				criteria.add(c);
			}
		}
		return criteria;
	}

	public <T> Criteria createCriteria(Class<T> entityClass, Criterion criterion) {
		return createCriteria(entityClass, new Criterion[]{criterion});
	}

	public <X> Criteria createCriteria(Class<X> entityClass, String orderBy,
			boolean isAsc, Criterion[] criterions) {
		Assert.notNull(orderBy,"排序属性不能为空");
	    Criteria criteria = createCriteria(entityClass, criterions);
	    if (isAsc)
	      criteria.addOrder(Order.asc(orderBy));
	    else
	      criteria.addOrder(Order.desc(orderBy));
	    return criteria;
	}

	@Override
	public <X> X findUnique(String queryString, Object... params) {
		return findUnique(params, queryString);
	}

	@Override
	public <X> X findUnique(Object[] params, String queryString) {
		Query query=createQuery(params, queryString);
		return (X) query.uniqueResult();
	}

	@Override
	public <T> T save(T model) {
		return (T) getSession().save(model);
	}
	
	@Override
	public <T> Integer save(Collection<T>  collection) {
		Assert.notEmpty(collection);
		Iterator<T> iterator=collection.iterator();
		while(iterator.hasNext()){
			save(iterator.next());
		}
		return collection.size();
	}

	@Override
	public <T> T update(T model){
		 getSession().update(model);
		 return model;
	}

	@Override
	public <X> Criteria createCriteria(Class<X> entityClass,
			String propertyNames, Object value) {
		
		return createCriteria(entityClass,new String[]{ propertyNames},new Object[]{value});
	}

	@Override
	public <X> Criteria createCriteria(Class<X> entityClass,
			String[] propertyNames, Object[] values) {
		Assert.notNull(propertyNames,"属性不能为空");
		if(EmptyUtils.isNotEmpty(propertyNames)&& EmptyUtils.isNotEmpty(values)){
			int j=propertyNames.length;
			List<Criterion> criterionList=new ArrayList<Criterion>();
			if(j==values.length){
				for(int i=0;i<j;i++){
					String propertyName=propertyNames[i];
					Object value=values[i];
					if(EmptyUtils.isNotEmpty(propertyName)&& value!=null){
						criterionList.add(Restrictions.eq(propertyName, value));
					}
				}
				Criterion [] creterions=new Criterion[criterionList.size()];
				 criterionList.toArray(creterions);
				return  createCriteria(entityClass,creterions);
			}
		}
		return getSession().createCriteria(entityClass);
	}

	@Override
	public <T> List<T> getAll(Class<T> entityClass, String propertyNames, Object value) {
		return createCriteria(entityClass, propertyNames, value).list();
	}

	@Override
	public <T> List<T> getAll(Class<T> entityClass, String[] propertyNames,
			Object[] value) {
		return createCriteria(entityClass, propertyNames, value).list();
	}

	@Override
	public <T> void saveOrUpdate(T model) {
		getSession().saveOrUpdate(model);
	}

	@Override
	public <T> List<T> getAll(Class<T> entityClass, String[] orderByColumns) {
		
		return getAll(entityClass, orderByColumns,new String[]{});
	}

	@Override
	public <T> List<T> getAll(Class<T> entityClass, String[] orderByColumns,
			String[] orderBys) {
		return createCriteria(entityClass, orderByColumns, orderBys,new Criterion[]{}).list();
	}

	@Override
	public <T> List<T> getAll(Class<T> entityClass, String orderByColumn,
			String orderBy) {
		return getAll(entityClass, new String[]{orderByColumn}, new String[]{orderBy});
	}
	
	@Override
	public <T> List<T> getAll(Class<T> entityClass, String propertyName,
			Object value, String orderByColumn, String orderBy) {
		if(EmptyUtils.isEmpty(orderByColumn))
			return getAll(entityClass, new String[]{propertyName},new Object[]{value}, null,null);
		return getAll(entityClass, new String[]{propertyName},new Object[]{value}, new String[]{orderByColumn}, new String[]{orderBy});
	}

	@Override
	public <T> List<T> getAll(Class<T> entityClass, String[] propertyNames,
			Object[] values, String[] orderByColumns, String[] orderBys) {
		Criteria criteria=createCriteria(entityClass, propertyNames, values);
		if(EmptyUtils.isNotEmpty(orderByColumns) && EmptyUtils.isNotEmpty(orderBys)){
			Integer orderByLenth=orderBys.length;
		  for(int i=0;i<orderByColumns.length;i++){
			  if(ICommonHqlDao.ORDER_DESC.equals(orderBys[i])&& orderByLenth>i)
				  criteria.addOrder(Order.desc(orderByColumns[i]));
			  else
				  criteria.addOrder(Order.asc(orderByColumns[i]));
		  }
		}
		  return criteria.list();
	}

	@Override
	public <X> Criteria createCriteria(Class<X> entityClass, String[] orderByColumns,
			String[] orderBys, Criterion[] criterions) {
		  Criteria criteria = createCriteria(entityClass, criterions);
		  Integer orderByLenth=orderBys.length;
		  for(int i=0;i<orderByColumns.length;i++){
			  if(ICommonHqlDao.ORDER_DESC.equals(orderBys[i])&& orderByLenth>i)
				  criteria.addOrder(Order.desc(orderByColumns[i]));
			  else
				  criteria.addOrder(Order.asc(orderByColumns[i]));
		  }
		return criteria;
	}

	public <T> Criteria createCriteria(Class<T> entityClass,
			Criterion[] criterions,List<String> aliasList) {
		Criteria criteria = getSession().createCriteria(entityClass);
		if(EmptyUtils.isNotEmpty(aliasList)){
			for(String alias : aliasList)
			criteria.createAlias(alias, alias);
		}
		if (EmptyUtils.isNotEmpty(criterions)) {
			int j = criterions.length;
			for (int i = 0; i < j; ++i) {
				Criterion c = criterions[i];
				criteria.add(c);
			}
		}
		return criteria;
	}
	
}
