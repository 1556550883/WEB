package com.ruanyun.web.service.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.RoleDao;
import com.ruanyun.web.model.sys.TRole;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.Constants;

@Service("roleService")
public class RoleService extends BaseServiceImpl<TRole>{

	@Autowired
	@Qualifier("roleDao")
	private RoleDao roleDao;
	@Autowired
	@Qualifier("roleAuthorityService")
	private RoleAuthorityService roleAuthorityService;
	@Autowired
	@Qualifier("operationLogService")
	private OperationLogService operationLogService;
	
	
	/**
	 * 功能描述:获取用户拥有的角色
	 *
	 * @author yangliu  2013-9-12 下午05:45:33
	 * 
	 * @param userId 用户ID
	 * @return 角色
	 */
	public TRole getRoleListByUserId(Integer userId){
		return roleDao.getRoleListByUserId(userId);
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-9-25 下午01:46:37
	 * 
	 * @param role
	 * @param authCodesStr
	 */
	public Integer saveOrUpdateRole(TRole role,String authCodesStr,TUser user){
		
		if( EmptyUtils.isNotEmpty(role)){
				super.saveOrUpdate(role);
				roleAuthorityService.save(role.getRoleId().toString(), authCodesStr,user);
//				String content = null;
//				if(role.getRoleId()==null){
//					content = "添加角色操作："+role.getRoleName();
//				}else{
//					content = "修改角色操作："+role.getRoleName();
//				}
//				operationLogService.addOperationLogThead(user,"角色管理",content,Constants.OPERA_TYPE_ROLE);
				return 1;
		}
		return -1;
	}
	/**
	 * 功能描述:删除角色信息
	 *
	 * @author L H T  2013-11-27 上午09:26:01
	 * 
	 * @param role 角色实体
	 * @param user 当前登录人的session信息
	 */
	@Transactional(rollbackFor=Exception.class)
	public void deleteRole(TRole role,TUser user){
		TRole olsRole = roleDao.get(TRole.class, role.getRoleId());
//		operationLogService.addOperationLogThead(user,"角色管理","删除角色："+olsRole.getRoleName(),Constants.OPERA_TYPE_ROLE);
		roleDao.delete(olsRole);
		roleAuthorityService.deleteByRoleId(role.getRoleId(),user);
		
	}

}
