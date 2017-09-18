package com.ruanyun.common.orm.hql;

import java.util.List;
import java.util.Map;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.orm.ICommonDao;
import com.ruanyun.common.orm.SessionBase;
import com.ruanyun.common.utils.EmptyUtils;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 *  #(c) IFlytek ruanyunmvc <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明:  公共的HQL语句执行类
 * 
 *  <br/>创建说明: 2013-8-17 上午10:21:45 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Repository("commonHqlDao")
@SuppressWarnings("unchecked")
public class CommonHqlDao extends SessionBase implements ICommonDao {
	
	public <X> X createQuery(String queryString, Object... params) {
		return createQuery(params, queryString);
	}

	public <X> X createQuery(Object[] params, String queryString) {
		 Assert.notNull(queryString, "queryString不能为空");
		 Query query = getSession().createQuery(queryString);
		 setQueryParams(query,params);
		 return (X) query;
	}
	
	public <X> X createQuery( String queryString,Map<String,Object> params) {
		 Assert.notNull(queryString, "queryString不能为空");
		 Query query = getSession().createQuery(queryString);
		 if(params !=null)
			 query.setProperties(params);
		 return (X) query;
	}

	public <X> X get(String queryString, Object... params) {
		return this.get(params, queryString);
	}

	public <X> X get(Object[] params, String queryString) {
		Query query=createQuery(params, queryString);
		List list=query.list();
		if(EmptyUtils.isNotEmpty(list))
			return (X) list.get(0);
		return null;
	}

	public Integer getCount(String queryString, Object... params) {
	   return getCount(params, queryString);
	}

	public Integer getCount(Object[] params, String queryString) {
		Query query= createQuery(params, queryString);
		Object obj= query.uniqueResult();
		return Integer.valueOf(String.valueOf(obj));
	}
	@Override
	public Integer getCount(String queryString, Map<String, Object> params) {
		Query query= createQuery(queryString,params);
		Object obj= query.uniqueResult();
		return Integer.valueOf(String.valueOf(obj));
	}
	public <X> Page<X> queryPage(Page<X> page, String queryString, Object... params) {
		return this.queryPage(params, queryString, page);
	}

	public <X> Page<X> queryPage(Object[] params, String queryString, Page<X> _page) {
		Page<X> page= new Page<X>();
		BeanUtils.copyProperties(_page, page);
		Assert.notNull(page, "page不能为空");
		Assert.notNull(queryString,"queryString不能为空");
		queryString=setHqlOrderBy(page, queryString);
	    Query q = createQuery(queryString, params);
	    Integer totalCount = queryPageCount(params, queryString);
	    page.setTotalCount(totalCount);
	    setQueryParams(q, params);
	    setPageParameter(q, page);
	    List result = q.list();
	    page.setResult(result);
	    return page;
	}
	
	@Override
	public <X> Page<X> queryPage(String queryString, Page<X> _page,
			Map<String, Object> params) {
		Page<X> page= new Page<X>();
		BeanUtils.copyProperties(_page, page);
		Assert.notNull(page, "page不能为空");
		Assert.notNull(queryString,"queryString不能为空");
	    Integer totalCount = queryPageCount( queryString,params);
	    Query q = createQuery(queryString, params);
	    page.setTotalCount(totalCount);
	    queryString=setHqlOrderBy(page, queryString);
	  //  q.setProperties(params);
	    setPageParameter(q, page);
	    List result = q.list();
	    page.setResult(result);
	    return page;
	}
	
	public Integer queryPageCount(String queryString, Object... params) {
		return this.queryPageCount(params, queryString);
	}

	public Integer queryPageCount(Object[] params, String queryString) {
		 	String fromHql = queryString;
		    fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		    fromHql = StringUtils.substringBefore(fromHql, "order by");

		    String countHql = "select count(*) " + fromHql;
		    try
		    {
		      return getCount(params, countHql);
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
	      return getCount( countHql,params);
	    } catch (Exception e) {
	      throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
	    }
	}
	
	public <X> List<X> getAll(String queryString, Object... params) {
		return getAll(params, queryString);
	}

	public <X> List<X> getAll(Object[] params, String queryString) {
		Query query=createQuery(params, queryString);
		return query.list();
	}
	
	/**
	 * 功能描述:设置query的参数值
	 *
	 * @author yangliu  2013-8-17 下午03:39:56
	 * 
	 * @param params 参数
	 * @param query query
	 */
	protected void setQueryParams(Query query,Object [] params){
		if(params!=null)
			for (int i = 0; i < params.length; ++i)
		        query.setParameter(i, params[i]);
	}
	
	/**
	 * 功能描述: 设置分页
	 *
	 * @author yangliu  2013-8-17 下午04:31:10
	 * 
	 * @param q
	 * @param page
	 */
	protected void setPageParameter(Query q, Page page)
	  {
	    q.setFirstResult(page.getFirstOfPage()-1);
	    q.setMaxResults(page.getNumPerPage());
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
	protected <X> String setHqlOrderBy(Page<X> page ,String sql){
		if(StringUtils.isNotEmpty(page.getOrder())){
	 		int index=sql.indexOf("order by");
	 		if(index>-1){
	 			sql=sql+" "+page.getOrderBy()+" "+page.getOrder();
	 		}else{
	 			sql=sql+" order by "+page.getOrderBy()+" "+page.getOrder();
	 		}
	 	}
		return sql;
	}

	@Override
	public int execute(String queryString, Object... params) {
		
		return execute(params, queryString);
	}

	@Override
	public int execute(Object[] params, String queryString) {
		Query query=createQuery(params, queryString);
		return query.executeUpdate();
	}

	@Override
	public <X> List<X> getAll(String queryString, Map<String, Object> params) {
		Query query=createQuery(queryString, params);
		return query.list();
	}

	@Override
	public int execute(String queryString, Map<String, Object> map) {
		Query query=getSession().createQuery(queryString).setProperties(map);
		return query.executeUpdate();
	}



}
