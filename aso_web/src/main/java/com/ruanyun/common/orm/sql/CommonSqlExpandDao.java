package com.ruanyun.common.orm.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.orm.ICommonSqlDao;
import com.ruanyun.common.orm.sql.utils.TransformersUtils;
import com.ruanyun.common.utils.EmptyUtils;

/**
 * 
 *  #(c) ruanyun ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: sql语句扩展实现类
 * 
 *  <br/>创建说明: 2013-8-20 下午02:34:54 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Repository("commonSqlExpandDao")
@SuppressWarnings("unchecked")
public class CommonSqlExpandDao extends CommonSqlDao implements ICommonSqlDao {
	
	
	public <T> T get(Class<T> entityClass, String sql, Object... params) {
		return get(params, entityClass, sql);
	}
	@Override
	public <T> T get(Class<T> entityClass, String sql,
			Map<String, Object> params) {
		List<T> list=getAll(entityClass, sql,params);
		if(EmptyUtils.isNotEmpty(list))
			return list.get(0);
		return null;
	}
	

	public <T> T get(Object[] params, Class<T> entityClass, String sql) {
		List<T> list=getAll(params, entityClass, sql);
		if(EmptyUtils.isNotEmpty(list))
			return list.get(0);
		return null;
	}

	public <T> List<T> getAll(Class<T> entityClass, String sql) {
		
		return getAll(entityClass, sql,new HashMap());
	}

	public <X> Page<X> queryPage(Page<X> page, Class<X> entityClass,
			String queryString, Object... params) {
		return queryPage(params, queryString, page,entityClass);
	}

	
	public <X> Page<X> queryPage(Object[] params, String queryString,
			Page<X> _page, Class<X> entityClass) {
		Page<X> page=new Page<X>();
		BeanUtils.copyProperties(_page, page);
		Assert.notNull(page, "page不能为空");
		queryString=setSqlOrderBy(page, queryString);
	 	SQLQuery q = createQuery(params,queryString);
	    Integer totalCount = queryPageCount(params, queryString);
	    page.setTotalCount(totalCount);
	    setPageParameter(q, page);
	    List<X> result = q.setResultTransformer(TransformersUtils.aliasToBean(entityClass)).list();
	    page.setResult(result);
	    return page;
	}
	
	@Override
	public <X> Page<X> queryPage(Page<X> _page, Class<X> entityClass,
			String queryString, Map<String, Object> params) {
		Page<X> page=new Page<X>();
		BeanUtils.copyProperties(_page, page);
		Assert.notNull(page, "page不能为空");
		queryString=setSqlOrderBy(page, queryString);
	 	SQLQuery q = createQuery(queryString, params);
	    Integer totalCount = queryPageCount(queryString,params);
	    page.setTotalCount(totalCount);
	    setPageParameter(q, page);
	    List<X> result = q.setResultTransformer(TransformersUtils.aliasToBean(entityClass)).list();
	    page.setResult(result);
	    return page;
	}

	public <T> List<T> getAll(Class<T> entityClass, String sql,
			Object... params) {
		return getAll(params,entityClass,sql);
	}

	public <T> List<T> getAll(Object[] params, Class<T> entityClass, String sql) {
		
		SQLQuery q = createQuery(params,sql);
		List<T> result = q.setResultTransformer(TransformersUtils.aliasToBean(entityClass)).list();
		return result;
	}
	@Override
	public <T> List<T> getAll(Class<T> entityClass,String sql, Map<String, Object> params) {
		SQLQuery q = createQuery(sql, params);
		List<T> result = q.setResultTransformer(TransformersUtils.aliasToBean(entityClass)).list();
		return result;
	}
	public int delete(Object[] params, String sql) {
		SQLQuery query=createQuery(params, sql);
		return query.executeUpdate();
	}
	@Override
	public int delete(String sql, Map<String, Object> params) {
		SQLQuery query=createQuery(sql,params);
		return query.executeUpdate();
	}
	public int delete(String sql, Object... params) {
		SQLQuery query=createQuery(params, sql);
		return query.executeUpdate();
	}

	public int update(Object[] params, String sql) {
		SQLQuery query=createQuery(params, sql);
		return query.executeUpdate();
	}

	public int update(String sql, Object... params) {
		SQLQuery query=createQuery(params, sql);
		return query.executeUpdate();
	}
	@Override
	public int update(String sql, Map<String, Object> params) {
		SQLQuery query=createQuery(sql,params);
		return query.executeUpdate();
	}

}
