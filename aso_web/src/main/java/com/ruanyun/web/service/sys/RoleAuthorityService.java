package com.ruanyun.web.service.sys;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.RoleAuthorityDao;
import com.ruanyun.web.model.sys.TRoleAuthority;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.Constants;
@Service("roleAuthorityService")
public class RoleAuthorityService extends BaseServiceImpl<TRoleAuthority> {

	@Autowired
	@Qualifier("roleAuthorityDao")
	private RoleAuthorityDao roleAuthorityDao;
	@Autowired
	@Qualifier("operationLogService")
	private OperationLogService operationLogService;
	
	/**
	 * 功能描述:删除角色所关联的权限
	 *
	 * @author yangliu  2013-9-12 下午05:06:39
	 * 
	 * @param userId
	 * @return
	 */
	public Integer deleteByRoleId(Integer roleId,TUser user){
//		operationLogService.addOperationLogThead(user,"角色管理","删除角色权限",Constants.OPERA_TYPE_ROLE);
		return roleAuthorityDao.deleteByRoleId(roleId);
	}

	
	/**
	 * 功能描述: 保存角色与权限关联表
	 *
	 * @author yangliu  2013-9-12 下午05:33:17
	 * 
	 * @param roleidsStr 角色IDS(1,2,3)
	 * @param authIdStr 权限IDS(A_100,A_102,A_103)
	 * @return
	 */
	public Integer save(String roleidsStr,String authCodeStr,TUser user) {
		if(EmptyUtils.isNotEmpty(roleidsStr)&& EmptyUtils.isNotEmpty(authCodeStr)){
			Set<TRoleAuthority> set = new HashSet<TRoleAuthority>();
			String[] roleids=StringUtils.split(roleidsStr, SysCode.COMMA);
			String[] authCodes=StringUtils.split(authCodeStr, SysCode.COMMA);
			TRoleAuthority roleAuth=null;
			for(String roleid : roleids){
				roleAuthorityDao.deleteByRoleId(Integer.parseInt(roleid));
				for(String ahthCode : authCodes){
					roleAuth=new TRoleAuthority(ahthCode,Integer.parseInt(roleid));
					set.add(roleAuth);
				}
			}
//			operationLogService.addOperationLogThead(user,"角色管理","保存用户角色权限",Constants.OPERA_TYPE_USER);
			return baseDao.save(set);
		}else if(EmptyUtils.isEmpty(authCodeStr)){
			String[] roleids=StringUtils.split(roleidsStr, SysCode.COMMA);
			for(String roleid : roleids){
				roleAuthorityDao.deleteByRoleId(Integer.parseInt(roleid));
			}
		}
		
		return -1;
	}

}
