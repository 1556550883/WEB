package com.ruanyun.web.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="t_external_channel_info")
public class TExternalChannelInfo implements java.io.Serializable 
{
	private static final long serialVersionUID = 8990708142932239585L;
	private Integer externalChannelId;
	private String externalChannelNum;
	private String externalChannelName;
	private Date createDate;
	private String externalChannelKey;
	private String externalChannelDesc;
	private Integer isEnable;//是否启用
	
	@Id 
	@GeneratedValue(strategy=IDENTITY)
	@Column(name="external_channel_id", unique=true, nullable=false)
	public Integer getExternalChannelId()
	{
		return externalChannelId;
	}
	
	public void setExternalChannelId(Integer externalChannelId) 
	{
		this.externalChannelId = externalChannelId;
	}
	
	 @Column(name="external_channel_name", length=200)
	public String getExternalChannelName() 
	{
		return externalChannelName;
	}
	
	public void setExternalChannelName(String externalChannelName)
	{
		this.externalChannelName = externalChannelName;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_date", length=10)
	public Date getCreateDate() 
	{
		return createDate;
	}
	
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}
	
	@Column(name="external_channel_key", length=100)
	public String getExternalChannelKey()
	{
		return externalChannelKey;
	}
	
	public void setExternalChannelKey(String externalChannelKey) 
	{
		this.externalChannelKey = externalChannelKey;
	}
	
	@Column(name="is_enable")
	public Integer getIsEnable() 
	{
		return isEnable;
	}
	
	public void setIsEnable(Integer isEnable)
	{
		this.isEnable = isEnable;
	}

	@Column(name="external_channel_desc", length=100)
	public String getExternalChannelDesc() {
		return externalChannelDesc;
	}

	public void setExternalChannelDesc(String externalChannelDesc) {
		this.externalChannelDesc = externalChannelDesc;
	}

	 @Column(name="external_channel_num", length=100)
	public String getExternalChannelNum() {
		return externalChannelNum;
	}

	public void setExternalChannelNum(String externalChannelNum) {
		this.externalChannelNum = externalChannelNum;
	}
}
