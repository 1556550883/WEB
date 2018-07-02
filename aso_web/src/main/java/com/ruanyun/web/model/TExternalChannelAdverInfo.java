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
@Table(name="t_external_channel_adver_info")
public class TExternalChannelAdverInfo  implements java.io.Serializable
{
	private static final long serialVersionUID = -6565092489644182597L;
	private Integer externalAdverId;
    private String externalAdverName;
    private String externalAdverNum;
    private String externalAdverDesc;
    private String taskType;//任务类型
    private String adid;//广告ID
    private Integer externalAdverCount;
    private String externalAdverTimeStart;
    private String externalAdverTimeEnd;
    private Date externalAdverCreatetime;
    private int externalAdverActivationCount;
    private String externalChannelNum;
    private Float externalAdverPrice;
    private int externalAdverStatus;
    
    @Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="external_adver_id", unique=true, nullable=false)
	public Integer getExternalAdverId() {
		return externalAdverId;
	}
	public void setExternalAdverId(Integer externalAdverId) {
		this.externalAdverId = externalAdverId;
	}
	
	@Column(name="external_adver_name", length=100)
	public String getExternalAdverName() {
		return externalAdverName;
	}
	public void setExternalAdverName(String externalAdverName) {
		this.externalAdverName = externalAdverName;
	}
	
	@Column(name="external_adver_num", length=100)
	public String getExternalAdverNum() {
		return externalAdverNum;
	}
	public void setExternalAdverNum(String externalAdverNum) {
		this.externalAdverNum = externalAdverNum;
	}
	
	@Column(name="external_adver_desc", length=100)
	public String getExternalAdverDesc() {
		return externalAdverDesc;
	}
	public void setExternalAdverDesc(String externalAdverDesc) {
		this.externalAdverDesc = externalAdverDesc;
	}
	
	@Column(name="external_task_type", length=1)
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	
	@Column(name="adid", length=100)
	public String getAdid() {
		return adid;
	}
	public void setAdid(String adid) {
		this.adid = adid;
	}
	
    @Column(name="external_adver_count")
	public Integer getExternalAdverCount() {
		return externalAdverCount;
	}
	public void setExternalAdverCount(Integer externalAdverCount) {
		this.externalAdverCount = externalAdverCount;
	}
	
	@Column(name="external_adver_time_start", length=50)
	public String getExternalAdverTimeStart() {
		return externalAdverTimeStart;
	}
	public void setExternalAdverTimeStart(String externalAdverTimeStart) {
		this.externalAdverTimeStart = externalAdverTimeStart;
	}
	
	@Column(name="external_adver_time_end", length=50)
	public String getExternalAdverTimeEnd() {
		return externalAdverTimeEnd;
	}
	public void setExternalAdverTimeEnd(String externalAdverTimeEnd) {
		this.externalAdverTimeEnd = externalAdverTimeEnd;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="external_adver_create_time", length=19)
	public Date getExternalAdverCreatetime() {
		return externalAdverCreatetime;
	}
	public void setExternalAdverCreatetime(Date externalAdverCreatetime) {
		this.externalAdverCreatetime = externalAdverCreatetime;
	}
	
	@Column(name="external_adver_activation_count")
	public int getExternalAdverActivationCount() {
		return externalAdverActivationCount;
	}
	public void setExternalAdverActivationCount(int externalAdverActivationCount) {
		this.externalAdverActivationCount = externalAdverActivationCount;
	}
	
	@Column(name="external_channel_num", length=100)
	public String getExternalChannelNum() {
		return externalChannelNum;
	}
	public void setExternalChannelNum(String externalChannelNum) {
		this.externalChannelNum = externalChannelNum;
	}
	
	@Column(name="external_adver_price")
	public Float getExternalAdverPrice() 
	{
		return externalAdverPrice;
	}

	public void setExternalAdverPrice(Float externalAdverPrice) 
	{
		this.externalAdverPrice = externalAdverPrice;
	}
	
	@Column(name="external_adver_status")
	public int getExternalAdverStatus() {
		return externalAdverStatus;
	}
	public void setExternalAdverStatus(int externalAdverStatus) {
		this.externalAdverStatus = externalAdverStatus;
	}
}
