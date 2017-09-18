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
import com.ruanyun.web.dao.sys.UserRoleDao;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.TUserRole;
import com.ruanyun.web.util.Constants;

/**
 * 
 *  #(c) ruanyun  <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 用户与角色关联表
 * 
 *  <br/>创建说明: 2013-9-12 下午03:18:21 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */

@Service("userRoleService")
public class UserRoleService extends BaseServiceImpl<TUserRole> {

	/**
	 * 用户权限1.商铺
	 */
	public final static int ROLE_TYPE_9=9;
	public final static int ROLE_TYPE_7=7;
	@Autowired
	@Qualifier("userRoleDao")
	private UserRoleDao userRoleDao;
	@Autowired
	@Qualifier("operationLogService")
	private OperationLogService operationLogService;
	
	/**
	 * 功能描述:保存用户与角色关联信息
	 *
	 * @author yangliu  2013-9-12 下午04:10:56
	 * 
	 * @param roleidsStr 角色ID (1,2,3)
	 * @param useridsStr 用户ID(11,12,13)
	 * @return 保存数量
	 */
	public Integer save(String roleidsStr,String useridsStr,TUser user) {
		
		if(EmptyUtils.isNotEmpty(roleidsStr)&& EmptyUtils.isNotEmpty(useridsStr)){
			Set<TUserRole> set = new HashSet<TUserRole>();
			String[] roleids=StringUtils.split(roleidsStr, SysCode.COMMA);
			String[] userids=StringUtils.split(useridsStr, SysCode.COMMA);
			TUserRole userRole=null;
			for(String roleid : roleids){
				for(String userid : userids){
					userRole=new TUserRole(Integer.parseInt(userid),Integer.parseInt(roleid));
					set.add(userRole);
				}
			}
//			operationLogService.addOperationLogThead(user,"用户管理","修改用户角色",Constants.OPERA_TYPE_USER);
			return baseDao.save(set);
		}
		return -1;
	}
	
	/**
	 * 功能描述:删除用户所关联的角色
	 *
	 * @author yangliu  2013-9-12 下午05:06:39
	 * 
	 * @param userId
	 * @return
	 */
	public Integer deleteByUserId(Integer userId){
		return userRoleDao.deleteByUserId(userId);
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
		return userRoleDao.deleteByRoleId(roleId);
	}

	
}
