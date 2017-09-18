package com.ruanyun.common.model;

/**
 * 
 *  #(c) ruanyun ruanyunmvc <br/>
 *
  * 
 *  <br/>创建说明: 2013-8-22 下午04:57:38 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class Menu {
	
	/**
	 * 菜单名称
	 */
	private String name;
	
	/**
	 * 权限code
	 */
	private String authCode;
	/**
	 * 访问路径
	 */
	private String url;
	/**
	 * 父类菜单
	 */
	private Menu parentMenu;
	
	/**
	 * 父权限ID
	 */
	private String parentCode;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Menu getParentMenu() {
		return parentMenu;
	}
	public void setParentMenu(Menu parentMenu) {
		this.parentMenu = parentMenu;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	
	
	

}
