package com.ruanyun.web.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idfa_udid")
public class TPhoneUdidWithIdfa {
	private int id;
	private String idfa;
	private String udid;
	private String phoneModel;
	private String phoneVersion;
	private String createTime;
	public TPhoneUdidWithIdfa() {
		
	}
	
	public TPhoneUdidWithIdfa(String idfa,  String udid, String phoneModel, String phoneVersion,String createTime)
	{
		this.idfa = idfa;
		this.udid = udid;
		this.setPhoneModel(phoneModel);
		this.setPhoneVersion(phoneVersion);
		this.createTime = createTime;
	}
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="idfa")
	public String getIdfa() {
		return idfa;
	}
	public void setIdfa(String idfa) {
		this.idfa = idfa;
	}
	
	@Column(name="udid")
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}

	@Column(name="create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Column(name="phone_version")
	public String getPhoneVersion() {
		return phoneVersion;
	}

	public void setPhoneVersion(String phoneVersion) {
		this.phoneVersion = phoneVersion;
	}

	@Column(name="phone_model")
	public String getPhoneModel() {
		return phoneModel;
	}

	public void setPhoneModel(String phoneModel) {
		this.phoneModel = phoneModel;
	}
}
