package com.ruanyun.web.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class TExternalChannelTask 
{
	private int id;
	private String ip;
	private String idfa;
	private String keywords;
	private String callback;
	private String adverId;
	private String status;
	private Date receiveTime;
	private Date completeTime;
	
	public TExternalChannelTask() {}
	
	public TExternalChannelTask(String ip, String idfa, String keywords, String status) 
	{
		this.ip = ip;
		this.idfa = idfa;
		this.keywords = keywords;
		this.status = status;
	}
	
	@Id @GeneratedValue(strategy=IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public int getId() 
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	@Column(name="ip")
	public String getIp() 
	{
		return ip;
	}
	
	public void setIp(String ip) 
	{
		this.ip = ip;
	}
	
	@Column(name="idfa")
	public String getIdfa()
	{
		return idfa;
	}
	
	public void setIdfa(String idfa) 
	{
		this.idfa = idfa;
	}
	
	@Column(name="keywords")
	public String getKeywords()
	{
		return keywords;
	}
	
	public void setKeywords(String keywords) 
	{
		this.keywords = keywords;
	}
	
	@Column(name="callback")
	public String getCallback()
	{
		return callback;
	}
	
	public void setCallback(String callback) 
	{
		this.callback = callback;
	}
	
	@Column(name="adver_id")
	public String getAdverId() 
	{
		return adverId;
	}
	
	public void setAdverId(String adverId)
	{
		this.adverId = adverId;
	}
	
	@Column(name="status")
	public String getStatus() 
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name="receive_time", length=19)
	public Date getReceiveTime()
	{
		return receiveTime;
	}
	
	public void setReceiveTime(Date receiveTime)
	{
		this.receiveTime = receiveTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="complete_time", length=19)
	public Date getCompleteTime() 
	{
		return completeTime;
	}
	
	public void setCompleteTime(Date completeTime) 
	{
		this.completeTime = completeTime;
	}
}
