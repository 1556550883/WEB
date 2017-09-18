package com.ruanyun.web.dao.sys;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.sys.TUserRole;

/**
 * 
 *  #(c) ruanyun  <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 角色与用户关联
 * 
 *  <br/>创建说明: 2013-9-12 下午03:16:55 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Repository("userRoleDao")
public class UserRoleDao extends BaseDaoImpl<TUserRole> {
	
	/**
	 * 功能描述:删除用户所关联的角色
	 *
	 * @author yangliu  2013-9-12 下午05:06:39
	 * 
	 * @param userId
	 * @return
	 */
	public Integer deleteByUserId(Integer userId){
		return hqlDao.delete(TUserRole.class,"userId", userId);
	}
	
	/**
	 * 功能描述:删除用户所关联的角色
	 *
	 * @author yangliu  2013-9-12 下午05:06:39
	 * 
	 * @param roleId
	 * @return
	 */
	public Integer deleteByRoleId(Integer roleId){
		return hqlDao.delete(TUserRole.class,"roleId", roleId);
	}

	
}
