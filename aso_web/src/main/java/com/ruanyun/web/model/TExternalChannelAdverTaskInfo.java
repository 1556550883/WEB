package com.ruanyun.web.model;

public class TExternalChannelAdverTaskInfo
{
	private Integer adverId;
	private String keywords;
	private String num;
	private String channelKey;
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Integer getAdverId() {
		return adverId;
	}
	public void setAdverId(Integer adverId) {
		this.adverId = adverId;
	}
	public String getChannelKey() {
		return channelKey;
	}
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
}
