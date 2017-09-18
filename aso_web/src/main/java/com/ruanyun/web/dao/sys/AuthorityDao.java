package com.ruanyun.web.dao.sys;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TAuthority;
/**
 * 
 *  #(c) ruanyun  <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 权限数据访层
 * 
 *  <br/>创建说明: 2013-9-12 上午11:17:51 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Repository("authorityDao")
public class AuthorityDao extends BaseDaoImpl<TAuthority> {
	
	/**
	 * 功能描述:获取用户的权限
	 *
	 * @author yangliu  2013-9-17 下午03:58:53
	 * 
	 * @param userid 用户ID
	 * @param type 权限类型
	 * @param requestType 不等于的权限 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TAuthority> getListTAuthByUser(Integer userid,String type,String requestType){
		Assert.notNull(userid);
		String sql="SELECT DISTINCT ta.* FROM t_user_role tur,t_role_authority tra,t_authority ta WHERE ta.request_type !=? and tur.`role_id`=tra.`role_id` AND tra.`auth_code`=ta.`auth_code` AND user_id =? "+SQLUtils.popuHqlEq("ta.auth_type", type)+" order by ta.auth_orderby asc";
		SQLQuery query=sqlDao.createQuery(sql, requestType,userid);
		List<TAuthority> list=query.addEntity("ta",TAuthority.class).list();
		return list;
	}
	
	/**
	 * 功能描述:获取所用权限 并把属于角色的权限 绑定权限信息
	 *
	 * @author yangliu  2013-9-25 上午11:16:38
	 * 
	 * @param roleId 角色ID
	 * @return 权限列表
	 */
	public List<TAuthority> getListAuthBindingRoleId(Integer roleId){
		String sql="SELECT ta.*,tra.role_id FROM t_authority ta LEFT JOIN t_role_authority tra ON ta.auth_code=tra.auth_code AND tra.role_id=? ORDER BY ta.auth_orderby ASC";
		return sqlDao.getAll(TAuthority.class, sql, roleId);
	}

}
