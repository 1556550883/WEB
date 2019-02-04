package com.ruanyun.web.model;

import java.util.Date;

public class TUserScoreDetail implements Comparable<TUserScoreDetail>{
	private String adverName;
	private Float adverPrice;
	private Float priceDiff;
	private String taskType;
 	private String status;
 	private Date receiveTime;
 	private Date completeTime;
 	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}


 	
	public String getAdverName() {
		return adverName;
	}
	public void setAdverName(String adverName) {
		this.adverName = adverName;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Float getAdverPrice() {
		return adverPrice;
	}
	public void setAdverPrice(Float adverPrice) {
		this.adverPrice = adverPrice;
	}
	public Float getPriceDiff() {
		return priceDiff;
	}
	public void setPriceDiff(Float priceDiff) {
		this.priceDiff = priceDiff;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCompleteTime() {
		return completeTime;
	}
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}
	
	@Override
	public int compareTo(TUserScoreDetail o) {
		return o.getReceiveTime().compareTo(this.getReceiveTime());
	}
}
