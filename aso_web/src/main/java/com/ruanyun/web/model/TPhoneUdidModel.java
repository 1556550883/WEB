package com.ruanyun.web.model;

public class TPhoneUdidModel {
	private int id;
	private String udid;
	private int used;
	private String importTime;
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
}
