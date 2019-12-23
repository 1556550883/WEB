package com.ruanyun.web.model;

public class TPhoneUdidModel 
{
	private int id;
	private String udid;
	private int used;
	private String importTime;
	private String udidType;
	private int totalNum;
	private int todayAmountNum;
	private int yestDayAmountNum;
	
	public TPhoneUdidModel() {
		
	}
	
	public TPhoneUdidModel(String udid, int used, String importTime) {
		this.udid = udid;
		this.used = used;
		this.importTime = importTime;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}

	public String getImportTime() {
		return importTime;
	}

	public void setImportTime(String importTime) {
		this.importTime = importTime;
	}
	
	public String getUdidType() {
		return udidType;
	}

	public void setUdidType(String udidType) {
		this.udidType = udidType;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTodayAmountNum() {
		return todayAmountNum;
	}

	public void setTodayAmountNum(int todayAmountNum) {
		this.todayAmountNum = todayAmountNum;
	}

	public int getYestDayAmountNum() {
		return yestDayAmountNum;
	}

	public void setYestDayAmountNum(int yestDayAmountNum) {
		this.yestDayAmountNum = yestDayAmountNum;
	}
}
