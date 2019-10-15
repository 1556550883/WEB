package com.ruanyun.web.model;

public class TPhoneUdidModel {
	private int id;
	private String udid;
	private int used;
	public TPhoneUdidModel() {
		
	}
	
	public TPhoneUdidModel(String udid, int used) {
		this.udid = udid;
		this.used = used;
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
}
