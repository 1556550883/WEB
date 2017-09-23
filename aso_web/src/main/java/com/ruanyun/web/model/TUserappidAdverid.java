package com.ruanyun.web.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="t_userappid_adverid")
public class TUserappidAdverid implements Serializable 
{
	private static final long serialVersionUID = 8987602249264493702L;
	
	private Integer userAppId;
	private String ip;
	private String idfa;
	private String appleId;
	private String adid;
	private Integer adverId;
	private String status;
	private Date receiveTime;
	private Date openAppTime;
	private Date completeTime;
	private Date payTime;
	
	private String statusStart;
	private String statusEnd;
	private String statusDescription;
	private String adverName;
	private String os;
	private String completeTimeStr;
	private Float adverPrice;
	private String loginName;
	
    @Column(name="user_app_id")
    public Integer getUserAppId()
    {
        return this.userAppId;
    }
    
    public void setUserAppId(Integer userAppId)
    {
        this.userAppId = userAppId;
    }
    
    @Column(name="ip")
    public String getIp()
    {
        return this.ip;
    }
    
    public void setIp(String ip)
    {
        this.ip = ip;
    }
    
    @Id
    @Column(name="idfa")
    public String getIdfa() 
    {
        return this.idfa;
    }
    
    public void setIdfa(String idfa) 
    {
        this.idfa = idfa;
    }
    
    @Column(name="apple_id")
	public String getAppleId()
    {
		return appleId;
	}

	public void setAppleId(String appleId) 
	{
		this.appleId = appleId;
	}
    
    @Id
    @Column(name="adver_id")
    public Integer getAdverId()
    {
        return this.adverId;
    }
    
    public void setAdverId(Integer adverId) 
    {
        this.adverId = adverId;
    }
    
    @Column(name="status")
    public String getStatus()
    {
        return this.status;
    }
    
    public void setStatus(String status) 
    {
        this.status = status;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="receive_time", length=19)
    public Date getReceiveTime()
    {
        return this.receiveTime;
    }
    
    public void setReceiveTime(Date receiveTime) 
    {
        this.receiveTime = receiveTime;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="open_app_time", length=19)
	public Date getOpenAppTime() 
    {
		return openAppTime;
	}

	public void setOpenAppTime(Date openAppTime)
	{
		this.openAppTime = openAppTime;
	}
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="complete_time", length=19)
    public Date getCompleteTime() 
    {
        return this.completeTime;
    }
    
    public void setCompleteTime(Date completeTime) 
    {
        this.completeTime = completeTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="pay_time", length=19)
	public Date getPayTime() 
    {
		return payTime;
	}

	public void setPayTime(Date payTime) 
	{
		this.payTime = payTime;
	}
    
    @Transient
	public String getStatusStart() 
    {
		return statusStart;
	}

	public void setStatusStart(String statusStart) 
	{
		this.statusStart = statusStart;
	}

	@Transient
	public String getStatusEnd() 
	{
		return statusEnd;
	}

	public void setStatusEnd(String statusEnd)
	{
		this.statusEnd = statusEnd;
	}

	@Transient
	public String getAdverName() 
	{
		return adverName;
	}

	public void setAdverName(String adverName) 
	{
		this.adverName = adverName;
	}

	@Column(name="adid")
	public String getAdid() 
	{
		return adid;
	}

	public void setAdid(String adid) 
	{
		this.adid = adid;
	}

	@Transient
	public String getOs() 
	{
		return os;
	}

	public void setOs(String os) 
	{
		this.os = os;
	}

	@Transient
	public String getCompleteTimeStr()
	{
		return completeTimeStr;
	}

	public void setCompleteTimeStr(String completeTimeStr)
	{
		this.completeTimeStr = completeTimeStr;
	}

	@Transient
	public String getStatusDescription() 
	{
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) 
	{
		this.statusDescription = statusDescription;
	}

	@Transient
	public Float getAdverPrice()
	{
		return adverPrice;
	}

	public void setAdverPrice(Float adverPrice)
	{
		this.adverPrice = adverPrice;
	}

	@Transient
	public String getLoginName()
	{
		return loginName;
	}

	public void setLoginName(String loginName) 
	{
		this.loginName = loginName;
	}
}
