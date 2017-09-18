package com.ruanyun.common.orm.sql;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.orm.ICommonDao;
import com.ruanyun.common.orm.SessionBase;

/**
 * 
 *  #(c) IFlytek ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 公共的sql执行类
 * 
 *  <br/>创建说明: 2013-8-17 上午10:22:34 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Repository("commonSqlDao")
@SuppressWarnings("unchecked")
public class CommonSqlDao extends SessionBase implements ICommonDao{

	public <X> X createQuery(String queryString, Object... params) {
		
		return createQuery(params, queryString);
	}

	public <X> X createQuery(Object[] params, String queryString) {
		SQLQuery query=getSession().createSQLQuery(queryString);
		setParameters(query, params);
		return (X) query;
	}
	
	public <X> X createQuery(String queryString,Map<String,Object> params) {
		Assert.notNull(queryString, "queryString 不能为空");
		SQLQuery query=getSession().createSQLQuery(queryString);
		if(params!=null)
			query.setProperties(params);
		return (X) query;
	}
	
	
	public <X> X get(String queryString, Object... params) {
		return get(params, queryString);
	}

	
	public <X> X get(Object[] params, String queryString) {
		Assert.notNull(queryString, "queryString 不能为空");
		SQLQuery query=this.createQuery(params, queryString);
		return (X) query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).uniqueResult();
		
	}

	public Integer getCount(String queryString, Object... params) {
		return this.getCount(params, queryString);
	}

	public Integer getCount(Object[] params, String queryString) {
		SQLQuery query=this.createQuery(params, queryString);
		Object obj=query.uniqueResult();
		return Integer.valueOf(String.valueOf(obj));
	}
	
	@Override
	public Integer getCount(String queryString, Map<String, Object> params) {
		SQLQuery query=this.createQuery(queryString,params);
		Object obj=query.uniqueResult();
		return Integer.valueOf(String.valueOf(obj));
	}

	public <X> Page<X> queryPage(Page<X> page, String queryString,
			Object... params) {
		return queryPage(params, queryString, page);
	}

	public <X> Page<X> queryPage(Object[] params, String queryString,
			Page<X> _page) {
		Page<X> page = new Page<X>();
		BeanUtils.copyProperties(_page, page);
		Assert.notNull(page, "page不能为空");
		queryString=setSqlOrderBy(page, queryString);
	 	SQLQuery q = createQuery(queryString, params);
	    Integer totalCount = queryPageCount(params, queryString);
	    page.setTotalCount(totalCount);
	    setPageParameter(q, page);
	    List result = q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    page.setResult(result);
	    return page;
	}
	@Override
	public <X> Page<X> queryPage(String queryString, Page<X> _page,
			Map<String, Object> params) {
		Page<X> page = new Page<X>();
		BeanUtils.copyProperties(_page, page);
		Assert.notNull(page, "page不能为空");
		queryString=setSqlOrderBy(page, queryString);
	 	SQLQuery q = createQuery(queryString, params);
	    Integer totalCount = queryPageCount(queryString,params);
	    page.setTotalCount(totalCount);
	    setPageParameter(q, page);
	    List result = q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	    page.setResult(result);
	    return page;
	}
	public Integer queryPageCount(String queryString, Object... params) {
		return queryPageCount(params, queryString);
	}

	public Integer queryPageCount(Object[] params, String queryString) {
	    String fromHql = queryString;

	    fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
	    fromHql = StringUtils.substringBefore(fromHql, "order by");

	    String countHql = "select count(*) " + fromHql;
	    try
	    {
	      return getCount(countHql, params);
	    } catch (Exception e) {
	      throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
	    }
	}
	
	@Override
	public Integer queryPageCount(String queryString, Map<String, Object> params) {
		 String fromHql = queryString;

		    fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		    fromHql = StringUtils.substringBefore(fromHql, "order by");

		    String countHql = "select count(*) " + fromHql;
		    try
		    {
		      return getCount(countHql, params);
		    } catch (Exception e) {
		      throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		    }
	}

	public <X> List<X> getAll(String queryString, Object... params) {
		return getAll(params, queryString);
	}

	public <X> List<X> getAll(Object[] params, String queryString) {
		SQLQuery query=createQuery(params, queryString);
		return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}
	
	
	/**
	 * 功能描述: 给parameters 设置参数
	 *
	 * @author l h t@iflytek.com  2013-7-16 下午04:43:46
	 * 
	 * @param query query 对象
	 * @param paramList 参数数组
	 */
	protected void setParameters(SQLQuery query, Object[] paramList) {
		if (paramList != null) {
			for (int i = 0; i < paramList.length; i++) {
				if(paramList[i] instanceof Date) {
				 query.setTimestamp(i, (Date)paramList[i]);
				 } else {
					 query.setParameter(i, paramList[i]);
				 }
			}
		}
	}
	
	/**
	 * 功能描述:设置 sql排序
	 *
	 * @author yangliu  2013-7-24 下午08:46:13
	 * 
	 * @param page 分页对象
	 * @param sql sql语句
	 * @return
	 */
	protected <X> String setSqlOrderBy(Page<X> page ,String sql){
		if(StringUtils.isNotEmpty(page.getOrderBy())){
	 		int index=sql.indexOf("order by");
	 		if(index>-1){
	 			sql=sql+","+page.getOrderBy()+" "+page.getOrder();
	 		}else{
	 			sql=sql+" order by "+page.getOrderBy()+" "+page.getOrder();
	 		}
	 	}
		
		return sql;
	}
	
	/**
	 * 功能描述: 获取sqlQuery
	 *
	 * @author yangliu  2013-7-24 下午08:21:23
	 * 
	 * @param <T> 参数
	 * @param q 查询query
	 * @param page 分页对象
	 * @return
	 */
	protected <T> SQLQuery setPageParameter(SQLQuery q, Page<T> page)
	{
	    q.setFirstResult(page.getFirstOfPage()-1);
	    q.setMaxResults(page.getNumPerPage());
	    return q;
	 }

	@Override
	public int execute(String queryString, Object... params) {
		return execute(params, queryString);
	}

	@Override
	public int execute(Object[] params, String queryString) {
		SQLQuery query=createQuery(params, queryString);
		return query.executeUpdate();
	}

	@Override
	public <X> List<X> getAll(String queryString, Map<String, Object> params) {
		SQLQuery query=createQuery(queryString, params);
		return query.list();
	}

	@Override
	public int execute(String queryString, Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}


}
