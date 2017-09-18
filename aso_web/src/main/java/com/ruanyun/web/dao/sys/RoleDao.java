package com.ruanyun.web.dao.sys;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.sys.TRole;

@Repository("roleDao")
public class RoleDao extends BaseDaoImpl<TRole> {
	
	/**
	 * 功能描述:获取用户拥有的角色
	 *
	 * @author yangliu  2013-9-12 下午05:45:33
	 * 
	 * @param userId 用户ID
	 * @return 角色
	 */
	public TRole getRoleListByUserId(Integer userId){
		Assert.notNull(userId);
		String sql="SELECT tr.* FROM t_role tr ,t_user_role tur WHERE tr.role_id=tur.role_id AND tur.user_id=? order by orderby desc";
		List<TRole> list=sqlDao.getAll(TRole.class,sql, userId);
		if(EmptyUtils.isNotEmpty(list))
			return list.get(0);
		return null;
	}
	

	
	
	

}
