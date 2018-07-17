/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.service.background;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.AdverEffectiveInfoDao;
import com.ruanyun.web.dao.sys.background.UserappidAdveridDao;
import com.ruanyun.web.model.TAdverEffectiveInfo;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.service.app.AppUserService;
import com.ruanyun.web.util.NumUtils;

@Service
public class AdverEffectiveInfoService extends BaseServiceImpl<TAdverEffectiveInfo>{
	
	@Autowired
	@Qualifier("adverEffectiveInfoDao")
	private AdverEffectiveInfoDao adverEffectiveInfoDao;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private UserappidAdveridDao userappidAdveridDao;
	@Autowired
	private AppUserService appUserService;
	
	/**
	 * 广告完成列表
	 */
	public Page<TUserappidAdverid> completeList(Page<TUserappidAdverid> page, TUserappidAdverid t) {
		page = userappidAdveridDao.PageSql(page, t);
		for(TUserappidAdverid task:page.getResult()){
			TChannelAdverInfo adverInfo = channelAdverInfoService.getInfoById(task.getAdverId());
			task.setAdverName(adverInfo.getAdverName());
			task.setAdverPrice(adverInfo.getAdverPrice());
			TUserApp userApp = appUserService.get(TUserApp.class, "userAppId", task.getUserAppId());
			task.setLoginName(userApp.getLoginName());
		}
		
		return page;
	}
	
	@Override
	public Page<TAdverEffectiveInfo> queryPage(Page<TAdverEffectiveInfo> page, TAdverEffectiveInfo t)
	{
		return adverEffectiveInfoDao.queryPage(page, t);
	}
	
	/**
	 * 功能描述: 保存有效任务记录
	 *
	 * @author yangliu  2016年1月21日 下午10:31:44
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param adverStepNum 广告步骤编号
	 * @param adverStepName 广告步骤名称
	 * @param score 分数
	 * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 系统版本号
	 * @param imei 
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 */
	public void saveAdverEffectiveInfo(String userNum,String adverNum,String adverStepNum,String adverStepName,Float score,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		TChannelAdverInfo channelAdverInfo=channelAdverInfoService.getInfoByAdverNum(adverNum);
		TChannelInfo channelInfo=channelInfoService.getInfoByNum(channelAdverInfo.getChannelNum());
		TAdverEffectiveInfo adverEffectiveInfo = new TAdverEffectiveInfo();
		
		adverEffectiveInfo.setAdverNum(adverNum);
		adverEffectiveInfo.setAdverName(channelAdverInfo.getAdverName());
		adverEffectiveInfo.setChannelNum(channelInfo.getChannelNum());
		adverEffectiveInfo.setChannelName(channelInfo.getChannelName());
		
		adverEffectiveInfo.setAdverStepNum(adverStepNum);
		adverEffectiveInfo.setAdverStepName(adverStepName);
		adverEffectiveInfo.setIdfa(idfa);
		adverEffectiveInfo.setImei(imei);
		adverEffectiveInfo.setIp(ip);
		adverEffectiveInfo.setMac(mac);
		adverEffectiveInfo.setOpenudid(openudid);
		adverEffectiveInfo.setPhoneModel(phoneModel);
		adverEffectiveInfo.setPhoneSerialNumber(phoneSerialNumber);
		adverEffectiveInfo.setSystemVersion(systemVersion);
		adverEffectiveInfo.setUserNum(userNum);
		adverEffectiveInfo.setScore(score);
		saveAdverEffectiveInfo(userNum, adverEffectiveInfo);
	}
	
	/**
	 * 功能描述: 保存有效任务记录
	 *
	 * @author yangliu  2016年1月21日 下午10:31:44
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param adverStepNum 广告步骤编号
	 * @param adverStepName 广告步骤名称
	 * @param score 分数
	 * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 系统版本号
	 * @param imei 
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 */
	public void saveAdverEffectiveInfo(String userNum,TAdverEffectiveInfo adverEffectiveInfo){
		
		adverEffectiveInfo.setUpdatetime(new Date());
		adverEffectiveInfo.setUserNum(userNum);
		adverEffectiveInfo.setCreatetime(new Date());
		save(adverEffectiveInfo);
		adverEffectiveInfo.setEffectiveNum(NumUtils.getCommondNum(NumUtils.EFFECTIVE_NUM, adverEffectiveInfo.getEffectiveId()));
		userScoreService.addApprenticeScore(userNum, adverEffectiveInfo.getAdverName(), adverEffectiveInfo.getScore());
	}
	
	
	/**
	 * 功能描述: 更加渠道编号 与 唯一ID 查询个数 
	 *
	 * @author yangliu  2016年1月26日 下午10:54:09
	 * 
	 * @param channelNum 渠道编号
	 * @param uniquePrimaryKey 唯一ID
	 * @return
	 */
	public Integer getEffectiveInfo(String channelNum,String uniquePrimaryKey){
		TAdverEffectiveInfo info = new TAdverEffectiveInfo();
		info.setUniquePrimaryKey(uniquePrimaryKey);
		info.setChannelNum(channelNum);
		return adverEffectiveInfoDao.getCountByInfo(info);
	}
	
	/**
	 * 功能描述: 更加渠道编号 与 唯一ID 查询个数 
	 *
	 * @author yangliu  2016年1月26日 下午10:54:09
	 * 
	 * @param channelNum 渠道编号
	 * @param uniquePrimaryKey 唯一ID
	 * @return
	 */
	public Integer getEffectiveInfoByAdverNum(String adverNum,String uniquePrimaryKey){
		TAdverEffectiveInfo info = new TAdverEffectiveInfo();
		info.setUniquePrimaryKey(uniquePrimaryKey);
		info.setAdverNum(adverNum);
		return adverEffectiveInfoDao.getCountByInfo(info);
	}
	/**
	 * 功能描述: 更加条件查询 个数
	 *
	 * @author yangliu  2016年1月26日 下午10:43:59
	 * 
	 * @param info
	 * @return
	 */
	public Integer getEffectiveInfo(TAdverEffectiveInfo info){
		return adverEffectiveInfoDao.getCountByInfo(info);
	}
}
