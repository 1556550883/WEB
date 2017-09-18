package com.ruanyun.web.dao.sys;


import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.sys.TRoleAuthority;

@Repository("roleAuthorityDao")
public class RoleAuthorityDao extends BaseDaoImpl<TRoleAuthority> {
	
	/**
	 * 功能描述:删除角色所关联的权限
	 *
	 * @author yangliu  2013-9-12 下午05:06:39
	 * 
	 * @param userId
	 * @return
	 */
	public Integer deleteByRoleId(Integer roleId){
		return hqlDao.delete(TRoleAuthority.class,"roleId", roleId);
	}
	
	
	
	
}
