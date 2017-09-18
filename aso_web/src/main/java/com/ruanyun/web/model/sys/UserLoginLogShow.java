package com.ruanyun.web.model.sys;

import java.util.Date;

/**
 * 
 *  #(c) IFlytek ahsw <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 保存用户 当前登录信息和上次登录信息
 * 
 *  <br/>创建说明: 2013-12-14 下午01:25:26 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class UserLoginLogShow {
	/**
	 * 用户本次登录ip
	 */
	private String loginIp;
	/**
	 * 用户本次登录时间
	 */
	private Date loginTime;
	/**
	 * 用户上次登录ip
	 */
	private String lastLoginIp;
	/**
	 * 用户上次登录时间
	 */
	private Date lastLoginTime;
	/**
	 * 用户登录总次数
	 */
	private Integer loginCount;
	

	public UserLoginLogShow() {
		
		super();
	}
	
	public UserLoginLogShow(String loginIp, Date loginTime,
			String lastLoginIp, Date lastLoginTime, Integer loginCount) {
		
		this.loginIp = loginIp;
		this.loginTime = loginTime;
		this.lastLoginIp = lastLoginIp;
		this.lastLoginTime = lastLoginTime;
		this.loginCount = loginCount;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}
	
	

}
