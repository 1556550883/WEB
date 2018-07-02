package com.ruanyun.web.model;
// Generated 2016-1-5 21:43:29 by Hibernate Tools 3.2.2.GA


import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * TChannelAdverInfo generated by hbm2java
 */
@Entity
@Table(name="t_channel_adver_info")
public class TChannelAdverInfo  implements java.io.Serializable {

	 private static final long serialVersionUID = -404920354101825918L;
	 
	 private Integer adverId;
     private String adverName;
     private String adverNum;
     private String adverDesc;
     
     private String taskType;//任务类型
     private String phoneType;//手机型号
     private String adid;//广告ID
     private String bundleId;//bundleId
     private Integer adverCount;//广告数量
     private Integer adverCountRemain;//广告剩余数量
     private Integer adverCountComplete;//广告完成数量
     private Float timeLimit;//任务时效
     
     private Float adverPrice;
     private Integer iosVersion;
     private String adverImg;
     private Date adverDayStart;
     private Date adverDayEnd;
     private String adverTimeStart;
     private String adverTimeEnd;
     private Integer adverStepCount;
     private Date adverCreatetime;
     private int adverActivationCount;
     private String fileType;
     private Float fileSize;
     private String fileUrl;
     private int downloadCount;
     private Integer adverStatus;
     private String effectiveSource;
     private String flag1;
     private String flag2;
     private String flag3;
     private String flag4;
     private Integer openTime;
     private Integer level; 
     private Integer isAuth;
     private String adverRemand;

     private String channelNum;
     private String packageName;
     private List<Map<String, Object>> adverStepList=new ArrayList<Map<String,Object>>();  //广告步骤
     private String commonNumName;
     private Integer adverStatusEnd;
     private String adversJson;
     private Integer downloadType;
     private String adverAdid;
     
     @Column(name="package_name")
     public String getPackageName() {
		return packageName;
	}
     public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
    public TChannelAdverInfo() {
    }

    public TChannelAdverInfo(String adverName, String adverNum, String adverDesc, 
    		Float adverPrice, Integer iosVersion, String adverImg, Date adverDayStart,
    		Date adverDayEnd, String adverTimeStart, String adverTimeEnd, Integer adverStepCount,
    		Date adverCreatetime, int adverActivationCount, String fileType, String fileUrl, 
    		int downloadCount, Integer adverStatus, String flag1, String flag2, String flag3, 
    		String flag4, Integer openTime, Integer level) 
    {
       this.adverName = adverName;
       this.adverNum = adverNum;
       this.adverDesc = adverDesc;
       this.adverPrice = adverPrice;
       this.iosVersion = iosVersion;
       this.adverImg = adverImg;
       this.adverDayStart = adverDayStart;
       this.adverDayEnd = adverDayEnd;
       this.adverTimeStart = adverTimeStart;
       this.adverTimeEnd = adverTimeEnd;
       this.adverStepCount = adverStepCount;
       this.adverCreatetime = adverCreatetime;
       this.adverActivationCount = adverActivationCount;
       this.fileType = fileType;
       this.fileUrl = fileUrl;
       this.downloadCount = downloadCount;
       this.adverStatus = adverStatus;
       this.flag1 = flag1;
       this.flag2 = flag2;
       this.flag3 = flag3;
       this.flag4 = flag4;
       this.openTime = openTime;
       this.level = level;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)
    
    @Column(name="adver_id", unique=true, nullable=false)
    public Integer getAdverId() {
        return this.adverId;
    }
    
    public void setAdverId(Integer adverId) {
        this.adverId = adverId;
    }
    
    @Column(name="task_type", length=1)
    public String getTaskType() {
        return this.taskType;
    }
    
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
    
    @Column(name="phone_type", length=10)
	public String getPhoneType() {
		return phoneType;
	}
	
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
    
    @Column(name="adid", length=100)
    public String getAdid() {
        return this.adid;
    }
    
    public void setAdid(String adid) {
        this.adid = adid;
    }
    
    @Column(name="bundle_id", length=50)
	public String getBundleId() {
		return bundleId;
	}
    
	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}
    
    @Column(name="adver_name", length=100)
    public String getAdverName() {
        return this.adverName;
    }
    
    public void setAdverName(String adverName) {
        this.adverName = adverName;
    }
    
    @Column(name="adver_num", length=100)
    public String getAdverNum() {
        return this.adverNum;
    }
    
    public void setAdverNum(String adverNum) {
        this.adverNum = adverNum;
    }
    
    @Column(name="adver_desc", length=100)
    public String getAdverDesc() {
        return this.adverDesc;
    }
    
    public void setAdverDesc(String adverDesc) {
        this.adverDesc = adverDesc;
    }
    
    @Column(name="adver_count")
    public Integer getAdverCount() {
        return this.adverCount;
    }
    
    public void setAdverCount(Integer adverCount) {
        this.adverCount = adverCount;
    }
    
    @Column(name="adver_count_remain")
    public Integer getAdverCountRemain() {
        return this.adverCountRemain;
    }
    
    public void setAdverCountRemain(Integer adverCountRemain) {
        this.adverCountRemain = adverCountRemain;
    }
    
    @Transient
	public Integer getAdverCountComplete() {
		return adverCountComplete;
	}
	
	public void setAdverCountComplete(Integer adverCountComplete) {
		this.adverCountComplete = adverCountComplete;
	}
    
    @Column(name="adver_price", precision=12, scale=0)
    public Float getAdverPrice() {
        return this.adverPrice;
    }
    
    public void setAdverPrice(Float adverPrice) {
        this.adverPrice = adverPrice;
    }
    
    @Column(name="ios_version")
    public Integer getIosVersion() {
        return this.iosVersion;
    }
    
    public void setIosVersion(Integer iosVersion) {
        this.iosVersion = iosVersion;
    }
    
    @Column(name="adver_img", length=200)
    public String getAdverImg() {
        return this.adverImg;
    }
    
    public void setAdverImg(String adverImg) {
        this.adverImg = adverImg;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="adver_day_start", length=19)
    public Date getAdverDayStart() {
        return this.adverDayStart;
    }
    
    public void setAdverDayStart(Date adverDayStart) {
        this.adverDayStart = adverDayStart;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="adver_day_end", length=19)
    public Date getAdverDayEnd() {
        return this.adverDayEnd;
    }
    
    public void setAdverDayEnd(Date adverDayEnd) {
        this.adverDayEnd = adverDayEnd;
    }
    
    @Column(name="adver_time_start", length=50)
    public String getAdverTimeStart() {
        return this.adverTimeStart;
    }
    
    public void setAdverTimeStart(String adverTimeStart) {
        this.adverTimeStart = adverTimeStart;
    }
    
    @Column(name="adver_time_end", length=50)
    public String getAdverTimeEnd() {
        return this.adverTimeEnd;
    }
    
    public void setAdverTimeEnd(String adverTimeEnd) {
        this.adverTimeEnd = adverTimeEnd;
    }
    
    @Column(name="adver_step_count")
    public Integer getAdverStepCount() {
        return this.adverStepCount;
    }
    
    public void setAdverStepCount(Integer adverStepCount) {
        this.adverStepCount = adverStepCount;
    }
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="adver_createtime", length=19)
    public Date getAdverCreatetime() {
        return this.adverCreatetime;
    }
    
    public void setAdverCreatetime(Date adverCreatetime) {
        this.adverCreatetime = adverCreatetime;
    }
    
    @Column(name="adver_activation_count")
    public int getAdverActivationCount() {
        return this.adverActivationCount;
    }
    
    public void setAdverActivationCount(int adverActivationCount) {
        this.adverActivationCount = adverActivationCount;
    }
    
    @Column(name="file_type", length=10)
    public String getFileType() {
        return this.fileType;
    }
    
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
  
    @Column(name="file_size")
    public Float getFileSize() {
		return fileSize;
	}

	public void setFileSize(Float fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name="file_url", length=200)
    public String getFileUrl() {
        return this.fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    @Column(name="download_count")
    public int getDownloadCount() {
        return this.downloadCount;
    }
    
    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }
    
    @Column(name="adver_status")
    public Integer getAdverStatus() {
        return this.adverStatus;
    }
    
    public void setAdverStatus(Integer adverStatus) {
        this.adverStatus = adverStatus;
    }
    
    @Column(name="flag1", length=100)
    public String getFlag1() {
        return this.flag1;
    }
    
    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }
    
    @Column(name="flag2", length=100)
    public String getFlag2() {
        return this.flag2;
    }
    
    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }
    
    @Column(name="flag3", length=100)
    public String getFlag3() {
        return this.flag3;
    }
    
    public void setFlag3(String flag3) {
        this.flag3 = flag3;
    }
    
    @Column(name="flag4", length=100)
    public String getFlag4() {
        return this.flag4;
    }
    
    public void setFlag4(String flag4) {
        this.flag4 = flag4;
    }
    
    @Column(name="open_time", length=100)
    public Integer getOpenTime() {
        return this.openTime;
    }
    
    public void setOpenTime(Integer openTime) {
        this.openTime = openTime;
    }
    
    @Column(name="level")
    public Integer getLevel() {
        return this.level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }


    @Column(name="channel_num")
    public String getChannelNum() 
    {
		return channelNum;
	}
    
    public void setChannelNum(String channelNum) 
    {
		this.channelNum = channelNum;
	}
    
    @Column(name="effective_source")
	public String getEffectiveSource() {
		return effectiveSource;
	}

	public void setEffectiveSource(String effectiveSource) {
		this.effectiveSource = effectiveSource;
	}
	
	@Transient
	public List<Map<String, Object>> getAdverStepList() {
		return adverStepList;
	}
	public void setAdverStepList(List<Map<String, Object>> adverStepList) {
		this.adverStepList = adverStepList;
	}
	
	@Column(name="is_auth")
	public Integer getIsAuth() {
		return isAuth;
	}
	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}
	
	@Transient
	public String getCommonNumName() {
		return commonNumName;
	}
	public void setCommonNumName(String commonNumName) {
		this.commonNumName = commonNumName;
	}
	
	@Column(name="adver_remand")
	public String getAdverRemand() {
		return adverRemand;
	}
	public void setAdverRemand(String adverRemand) {
		this.adverRemand = adverRemand;
	}
	
	@Transient
	public Integer getAdverStatusEnd() {
		return adverStatusEnd;
	}
	public void setAdverStatusEnd(Integer adverStatusEnd) {
		this.adverStatusEnd = adverStatusEnd;
	}
	
	@Transient
	public String getAdversJson() {
		return adversJson;
	}
	public void setAdversJson(String adversJson) {
		this.adversJson = adversJson;
	}
	
	@Column(name="time_limit")
	public Float getTimeLimit() 
	{
		return timeLimit;
	}
	public void setTimeLimit(Float timeLimit) 
	{
		this.timeLimit = timeLimit;
	}
	
	@Column(name="download_type")
	public Integer getDownloadType() 
	{
		return downloadType;
	}
	public void setDownloadType(Integer downloadType)
	{
		this.downloadType = downloadType;
	}
	
	@Column(name="adver_adid", length=100)
	public String getAdverAdid()
	{
		return adverAdid;
	}
	
	public void setAdverAdid(String adverAdid)
	{
		this.adverAdid = adverAdid;
	}
}