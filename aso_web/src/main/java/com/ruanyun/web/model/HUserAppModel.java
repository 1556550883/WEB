package com.ruanyun.web.model;

public class HUserAppModel {
	private Integer userAppId;
	private String userNum;
	 private Float scoreDay;
	 private Float score;
	 private Float scoreSum;
	 private String flag5;
	private String weixin;
	private String loginControl;//登录控制
	private String userNick;//真实姓名
	private String zhifubao;
	private String phoneNum;//手机号码
	
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getZhifubao() {
		return zhifubao;
	}
	public void setZhifubao(String zhifubao) {
		this.zhifubao = zhifubao;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
 
	    public String getFlag5() {
		return flag5;
	}
	public void setFlag5(String flag5) {
		this.flag5 = flag5;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	
	public Integer getUserAppId() {
		return userAppId;
	}
	public void setUserAppId(Integer userAppId) {
		this.userAppId = userAppId;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public Float getScoreDay() {
		return scoreDay;
	}
	public void setScoreDay(Float scoreDay) {
		this.scoreDay = scoreDay;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Float getScoreSum() {
		return scoreSum;
	}
	public void setScoreSum(Float scoreSum) {
		this.scoreSum = scoreSum;
	}
	public String getLoginControl() {
		return loginControl;
	}
	public void setLoginControl(String loginControl) {
		this.loginControl = loginControl;
	}
	 
	 
}
