/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.model;

public class UserAppModel {
	private Integer userAppId;
	private String userNum;
	private String loginName;
	private Integer loginType;
	private Integer userApppType;//用户类型 1普通用户  2学生
	private Integer cartStatus;//学生审核状态
	private String userNick;
	private String birthday;
	private String sex;
	private String headImg;
    private Float scoreDay;
    private Float score;
    private Float scoreSum;
    private String userLevelNum; //用户等级编号
    private Integer apprenticeCountDay;//今日收徒数量
    private Integer apprenticeCount;
    private String phoneNum;//手机号码
    private String invitationCode;//邀请码
    private String levelName;//等级名称
    private Integer taskNewStatus;
    private String zhifubao;
    private String weixin;
    private String zhifubaoName;
    private String appStore;
    private String weChatHeadUrl;
    private boolean isMasterIDExisted;
    
	public UserAppModel() {

	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public Integer getLoginType() {
		return loginType;
	}
	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
	
	public Integer getUserApppType() {
		return userApppType;
	}
	public void setUserApppType(Integer userApppType) {
		this.userApppType = userApppType;
	}
	public String getUserNick() {
		return userNick;
	}
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
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
	public Integer getUserAppId() {
		return userAppId;
	}
	public void setUserAppId(Integer userAppId) {
		this.userAppId = userAppId;
	}
	public String getUserLevelNum() {
		return userLevelNum;
	}
	public void setUserLevelNum(String userLevelNum) {
		this.userLevelNum = userLevelNum;
	}
	public Integer getApprenticeCountDay() {
		return apprenticeCountDay;
	}
	public void setApprenticeCountDay(Integer apprenticeCountDay) {
		this.apprenticeCountDay = apprenticeCountDay;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getInvitationCode() {
		return invitationCode;
	}
	public void setInvitationCode(String invitationCode) {
		this.invitationCode = invitationCode;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public Integer getApprenticeCount() {
		return apprenticeCount;
	}
	public void setApprenticeCount(Integer apprenticeCount) {
		this.apprenticeCount = apprenticeCount;
	}
	public Integer getTaskNewStatus() {
		return taskNewStatus;
	}
	public void setTaskNewStatus(Integer taskNewStatus) {
		this.taskNewStatus = taskNewStatus;
	}
	public Integer getCartStatus() {
		return cartStatus;
	}
	public void setCartStatus(Integer cartStatus) {
		this.cartStatus = cartStatus;
	}
	public String getZhifubao() {
		return zhifubao;
	}
	public void setZhifubao(String zhifubao) {
		this.zhifubao = zhifubao;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getZhifubaoName() {
		return zhifubaoName;
	}
	public void setZhifubaoName(String zhifubaoName) {
		this.zhifubaoName = zhifubaoName;
	}
	
	public String getAppStore() {
		return appStore;
	}
	public void setAppStore(String appStore) {
		this.appStore = appStore;
	}
	public String getWeChatHeadUrl() {
		return weChatHeadUrl;
	}
	public void setWeChatHeadUrl(String weChatHeadUrl) {
		this.weChatHeadUrl = weChatHeadUrl;
	}
	public boolean isMasterIDExisted() {
		return isMasterIDExisted;
	}
	public void setMasterIDExisted(boolean isMasterIDExisted) {
		this.isMasterIDExisted = isMasterIDExisted;
	}
}
