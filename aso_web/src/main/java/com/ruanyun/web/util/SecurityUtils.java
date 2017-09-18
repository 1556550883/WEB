package com.ruanyun.web.util;

import java.util.Iterator;
import java.util.List;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.model.sys.TAuthority;
import com.ruanyun.web.model.sys.TUser;

/**
 * 
 *  #(c) ruanyun  <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 权限帮助类
 * 
 *  <br/>创建说明: 2013-9-14 下午02:02:39 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public  final class SecurityUtils {
	
	/**
	 * 功能描述: 用户是否拥有权限
	 *
	 * @author yangliu  2013-9-14 下午01:52:54
	 * 
	 * @param authCode 权限Code
	 * @param user 用户信息
	 * @return
	 */
	public static boolean isGranted(String authCode,TUser user){
			return isGranted(authCode, user.getAuths());
	}
	
	
	
	/**
	 * 功能描述: 某些权限是否都在 权限列表中  全部都包含 才会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:01:23
	 * 
	 * @param authCodesStr 权限字符串 (A_100,A_1001)
	 * @param user 用户信息
	 * @return
	 */
	public static boolean isAllGranted(String authCodesStr,TUser user){
		if(EmptyUtils.isNotEmpty(authCodesStr) && EmptyUtils.isNotEmpty(user)){
			String[] authCodes=authCodesStr.split(SysCode.COMMA);
			return isAllGranted(authCodes, user.getAuths());
		}
		return false;
	}
	
	/**
	 * 功能描述: 某些权限是否都在 权限列表中 全部都包含 才会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:04:42
	 * 
	 * @param authCodes 权限数组
	 * @param user 用户信息
	 * @return
	 */
	public static boolean isAllGranted(String[] authCodes,TUser user){
		if(EmptyUtils.isNotEmpty(authCodes) && EmptyUtils.isNotEmpty(user)){
			return isAllGranted(authCodes, user.getAuths());
		}
		return false;
	}
	
	/**
	 * 功能描述:某些权限是否在权限列表中,有一个就会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:08:38
	 * 
	 * @param authCodes 权限codes 数组
	 * @param user 用户信息
	 * @return
	 */
	public static boolean isAnyGranted(String[] authCodes,TUser user){
		if(EmptyUtils.isNotEmpty(authCodes) && EmptyUtils.isNotEmpty(user)){
			return isAnyGranted(authCodes, user.getAuths());
		}
		return false;
	}
	
	/**
	 * 功能描述:某些权限是否在权限列表中，有一个就会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:08:38
	 * 
	 * @param authCodes 权限codes （A_001,A_002）
	 * @param user 用户信息
	 * @return
	 */
	public static boolean isAnyGranted(String authCodesStr,TUser user){
		if(EmptyUtils.isNotEmpty(authCodesStr) && EmptyUtils.isNotEmpty(user)){
			return isAnyGranted(authCodesStr, user.getAuths());
		}
		return false;
	}
	
	/**
	 * 功能描述: 某个权限是否在权限列表中
	 *
	 * @author yangliu  2013-9-14 下午01:54:18
	 * 
	 * @param authCode 权限Code
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isGranted(String authCode,List<TAuthority> authList){
		if(EmptyUtils.isNotEmpty(authCode) && EmptyUtils.isNotEmpty(authList)){
			Iterator<TAuthority> it =authList.iterator();
			while(it.hasNext()){
				if(authCode.equals(it.next().getAuthCode()))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 功能描述: 某个权限是否在权限列表中
	 *
	 * @author yangliu  2013-9-14 下午01:54:18
	 * 
	 * @param authCode 权限Code
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isNotGranted(String authCode,TUser currentUser){
		return isNotGranted(authCode, currentUser.getAuths());
	}
	
	/**
	 * 功能描述: 某个权限是否在权限列表中
	 *
	 * @author yangliu  2013-9-14 下午01:54:18
	 * 
	 * @param authCode 权限Code
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isNotGranted(String authCode,List<TAuthority> authList){
		return !isGranted(authCode, authList);
	}
	
	/**
	 * 功能描述: 某些权限都不在 权限列表中  全部不在列表返回true，有一个在列表权限中还回false
	 *
	 * @author yangliu  2013-9-14 下午02:01:23
	 * 
	 * @param authCodesStr 权限字符串 (A_100,A_1001)
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isNotAllGranted(String authCodesStr,List<TAuthority> authList){
		return !isAnyGranted(authCodesStr, authList);
	}
	
	/**
	 * 功能描述: 某些权限都不在 权限列表中  全部不在列表返回true，有一个在列表权限中还回false
	 *
	 * @author yangliu  2013-9-14 下午02:01:23
	 * 
	 * @param authCodesStr 权限字符串 (A_100,A_1001)
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isNotAllGranted(String authCodesStr,TUser currentUser){
		return isNotAllGranted(authCodesStr, currentUser.getAuths());
	}
	
	/**
	 * 功能描述: 某些权限是否都在 权限列表中  全部都包含 才会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:01:23
	 * 
	 * @param authCodesStr 权限字符串 (A_100,A_1001)
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isAllGranted(String authCodesStr,List<TAuthority> authList){
		if(EmptyUtils.isNotEmpty(authCodesStr) && EmptyUtils.isNotEmpty(authList)){
			String[] authCodes=authCodesStr.split(SysCode.COMMA);
			return isAllGranted(authCodes, authList);
		}
		return false;
	}
	
	/**
	 * 功能描述: 某些权限是否都在 权限列表中 全部都包含 才会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:04:42
	 * 
	 * @param authCodes 权限数组
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isAllGranted(String[] authCodes,List<TAuthority> authList){
		if(EmptyUtils.isNotEmpty(authCodes) && EmptyUtils.isNotEmpty(authList)){
			for(String authCode : authCodes){
				if(isNotGranted(authCode, authList)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 功能描述:某些权限是否在权限列表中,有一个就会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:08:38
	 * 
	 * @param authCodes 权限codes 数组
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isAnyGranted(String[] authCodes,List<TAuthority> authList){
		if(EmptyUtils.isNotEmpty(authCodes) && EmptyUtils.isNotEmpty(authList)){
			for(String authCode : authCodes){
				if(isGranted(authCode, authList)){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 功能描述:某些权限是否在权限列表中，有一个就会还回true
	 *
	 * @author yangliu  2013-9-14 下午02:08:38
	 * 
	 * @param authCodes 权限codes （A_001,A_002）
	 * @param authList 权限列表
	 * @return
	 */
	public static boolean isAnyGranted(String authCodesStr,List<TAuthority> authList){
		if(EmptyUtils.isNotEmpty(authCodesStr) && EmptyUtils.isNotEmpty(authList)){
			String[] authCodes=authCodesStr.split(SysCode.COMMA);
			return isAnyGranted(authCodes, authList);
		}
		return false;
	}
	
	
}
