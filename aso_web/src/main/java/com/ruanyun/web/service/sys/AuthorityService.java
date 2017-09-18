package com.ruanyun.web.service.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.dao.sys.AuthorityDao;
import com.ruanyun.web.model.sys.TAuthority;
import com.ruanyun.web.model.sys.TRoleAuthority;
import com.ruanyun.web.model.sys.TUser;

@Service("authorityService")
public class AuthorityService extends BaseServiceImpl<TAuthority>{
	
	@Autowired
	@Qualifier("authorityDao")
	private AuthorityDao authorityDao;
	@Autowired
	@Qualifier("roleAuthorityService")
	private RoleAuthorityService roleAuthorityService;
	@Autowired
	@Qualifier("operationLogService")
	private OperationLogService operationLogService;
	
	/**
	 * 功能描述:修改权限
	 *
	 * @author yangliu  2013-9-24 上午09:52:00
	 * 
	 * @param auth  权限对象
	 * @return
	 */
	public TAuthority update(TAuthority auth,TUser user) {
		TAuthority oldAuth=get(TAuthority.class, auth.getAuthId());
		BeanUtils.copyProperties(auth,oldAuth,new String[]{"authId","authCode","createdate"});
//		operationLogService.addOperationLogThead(user,"权限管理","修改权限操作",Constants.OPERA_TYPE_AUTH);
		return super.save(oldAuth);
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
		return authorityDao.getListAuthBindingRoleId(roleId);
	}
	
	/**
	 * 功能描述: 根据user 获取权限
	 *
	 * @author yangliu  2013-9-14 上午09:26:08
	 * 
	 * @param userid
	 * @param type
	 * @param requestType 不等于访问的类型
	 * @return
	 */
	public List<TAuthority> getListTAuthByUser(Integer userid,String type,String requestType){
		return authorityDao.getListTAuthByUser(userid, type,requestType);
	}

	/**
	 * 功能描述:设置菜单的子父级关系
	 *
	 * @author yangliu  2013-9-14 上午09:05:58
	 * 
	 * @param userAuthList 权限列表
	 * @return
	 */
	public List<TAuthority> getAuths(List<TAuthority> userAuthList){
		
		List<TAuthority> parentList = new ArrayList<TAuthority>();
		if (EmptyUtils.isNotEmpty(userAuthList)) {
			for (TAuthority parentMenu : userAuthList) {
				
				if ("A_0000".equals(parentMenu.getParentAuthCode()) ) {
					parentList.add(getChildMenu(parentMenu, userAuthList));
				}
			}
		}
		return parentList;
	}
	
	/**
	 * 功能描述: 获取子菜单
	 *
	 * @author yangliu  2013-9-13 下午06:02:53
	 * 
	 * @param auth 权限
	 * @param list 列表
	 * @return
	 */
	public TAuthority getChildMenu(TAuthority auth,List<TAuthority> list){
		List<TAuthority> childAuthList = new ArrayList<TAuthority>();

		for(TAuthority childAuth : list){
			if(auth.getAuthCode().equals(childAuth.getParentAuthCode())){
				getChildMenu(childAuth, list);
				childAuthList.add(childAuth);
				
			}
		}
		auth.setChildAuthority(childAuthList);
		return auth;
	}

	/**
	 * 功能描述:修改或者保存权限ID
	 *
	 * @author yangliu  2013-9-24 上午09:56:54
	 * 
	 * @param auth 权限
	 * @return
	 */
	
	public Integer saveOrUpdateAuth(TAuthority auth,TUser user){
		if(auth!=null){
			if(EmptyUtils.isEmpty(auth.getAuthId())){
				auth.setCreatedate(new Date());
				save(auth);
				//String opLogCon=EmptyUtils.isEmpty(auth.getAuthId())?"新增权限":"";
//				operationLogService.addOperationLogThead(user,"权限管理","新增权限为："+auth.getAuthName(),Constants.OPERA_TYPE_AUTH);
				auth.setAuthCode(getAuthCode(auth.getAuthId()));
				return 1;
			}else{
				update(auth);
//				operationLogService.addOperationLogThead(user,"权限管理","修改权限为:"+auth.getAuthName(),Constants.OPERA_TYPE_AUTH);
				return 1;
			}
		}
		return -1;
	}
	
	/**
	 * 功能描述:根据权限ID 自动生成权限code
	 *
	 * @author yangliu  2013-9-24 上午10:01:43
	 * 
	 * @param authId 权限ID
	 * @return
	 */
	private String getAuthCode(Integer authId){
		if(EmptyUtils.isEmpty(authId))
			return "A_"+TimeUtil.getCurrentDay(SysCode.DATE_FORMAT_NUM_L);
		return "A_"+(1000+authId);
	}
	
	/**
	 * 功能描述: 删除权限信息
	 *
	 * @author yangliu  2013-9-25 下午03:15:55
	 * 
	 * @param authCode 权限authCode
	 * @return
	 */
	public int delete(String authCode,TUser user) {
//		String authName="";
		if(EmptyUtils.isNotEmpty(authCode)){
			//获得删除的权限名称
//			TAuthority oldAu=super.get(TAuthority.class,"authCode", authCode);
//			authName=oldAu.getAuthName();
			//删除角色的权限数据
			roleAuthorityService.delete(TRoleAuthority.class,"authCode",authCode );
			//删除权限
			super.delete(TAuthority.class, "authCode", authCode);
			List<TAuthority> childList = getAllByCondition(TAuthority.class, "parentAuthCode", authCode);
			for(TAuthority auth : childList){
				delete(auth.getAuthCode(),user);
//				authName=auth.getAuthName();//被删除的  权限名称
				
			}
//			operationLogService.addOperationLogThead(user,"权限管理","删除权限:"+authName,Constants.OPERA_TYPE_AUTH);
			return 1;
		}
		return -1;
	}

}
