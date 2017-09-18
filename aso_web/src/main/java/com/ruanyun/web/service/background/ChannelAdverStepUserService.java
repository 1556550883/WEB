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
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.dao.sys.background.ChannelAdverStepUserDao;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelAdverStep;
import com.ruanyun.web.model.TChannelAdverStepUser;
import com.ruanyun.web.model.TChannelAdverUser;
import com.ruanyun.web.util.Constants;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Service
public class ChannelAdverStepUserService extends BaseServiceImpl<TChannelAdverStepUser>{

	
	@Autowired
	@Qualifier("channelAdverStepUserDao")
	private ChannelAdverStepUserDao channelAdverStepUserDao;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	@Autowired
	private ChannelAdverUserService channelAdverUserService;
	@Autowired
	private ChannelAdverStepService channelAdverStepService;
	@Autowired
	private AdverEffectiveInfoService adverEffectiveInfoService;

	@Override
	public Page<TChannelAdverStepUser> queryPage(Page<TChannelAdverStepUser> page, TChannelAdverStepUser t) {
		return channelAdverStepUserDao.queryPage(page, t);
	}
	
	/**
	 * 功能描述: 保存广告步骤
	 *
	 * @author yangliu  2016年1月21日 下午10:42:01
	 * 
	 * @param adverStepUser
	 */
	public void saveChannelAdverStepUser(TChannelAdverStepUser adverStepUser){
		adverStepUser.setAdverUserStepStatus(Constants.ADVER_USER_STEP_STATUS_NO);
		adverStepUser.setCreatetime(new Date());
		adverStepUser.setUpdatetime(new Date());
		save(adverStepUser);
		adverStepUser.setAdverUserStepNum(getNewSetpNum(adverStepUser.getAdverUserStepId()));
	}
	
	public String getNewSetpNum(int id){
		return "CASU_"+String.format("%08d", id);
	}
	
	/**
	 * 功能描述:当前广告 用户的 最新的 任务步骤
	 *
	 * @author yangliu  2016年1月20日 下午5:13:17
	 * 
	 * @param adverNum 广告编号
	 * @param userAppNum 用户编号
	 * @param phoneSerialNumber 手机序列号
	 * @param adverStepNum 广告步骤编号
	 * @return
	 */
	public TChannelAdverStepUser getNewAdverStepUser(String adverNum,String userAppNum,String phoneSerialNumber,String adverStepNum ){
		TChannelAdverStepUser adverStepUser = new TChannelAdverStepUser();
		adverStepUser.setAdverNum(adverNum);
		adverStepUser.setUserAppNum(userAppNum);
		adverStepUser.setPhoneSerialNumber(phoneSerialNumber);
		adverStepUser.setAdverStepNum(adverStepNum);
		return channelAdverStepUserDao.getAdverStepUser(adverStepUser);
	}
	
	/**
	 * 功能描述: 保存安装记录
	 *
	 * @author yangliu  2016年1月20日 下午9:24:40
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 系统版本
	 * @param imei  
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @param type
	 */
	public  TChannelAdverStepUser saveInstall(String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		if(EmptyUtils.isEmpty(phoneSerialNumber)) return null;
		
		TChannelAdverStepUser channelAdverStepUser=getNewAdverStepUser(adverNum, "", phoneSerialNumber,"");
		TChannelAdverInfo adverInfo=channelAdverInfoService.getInfoByAdverNum(adverNum);
		if(EmptyUtils.isEmpty(adverInfo)){  //广告不存在
			logger.error(adverNum+"广告编号不存在");
			return null;
		}
		if(channelAdverStepUser ==null){
			logger.error(adverNum+" 用户还没有安装");
			channelAdverStepUser=saveChannelAdverStepUser(adverInfo.getChannelNum(),userNum, adverNum,Constants.CHANNEL_STEP_NUM_INSTALL,"安装成功",Constants.ADVER_USER_STEP_STATUS_YES, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
			channelAdverUserService.saveAdverUser(adverNum,adverInfo.getAdverStepCount(), userNum, phoneSerialNumber);
		}
		return channelAdverStepUser;
	}
	
	/**
	 * 功能描述: 激活广告信息
	 *
	 * @author yangliu  2016年1月21日 下午9:41:03
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param adverStepNum 广告步骤编号
	 * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 版本号
	 * @param imei imei
	 * @param idfa idfa
	 * @param openudid 
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @return
	 */
	public Integer updateAdverstepUser(String userNum,String adverNum,String adverStepNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		if(EmptyUtils.isEmpty(adverStepNum) || EmptyUtils.isEmpty(adverNum) || EmptyUtils.isEmpty(phoneSerialNumber)){
			logger.error("参数不全:adverStepNum："+adverStepNum+":adverNum "+adverNum+" phoneSerialNumber:"+phoneSerialNumber);
			return -1;
		}
		TChannelAdverStepUser channelAdverStepUser=getNewAdverStepUser(adverNum, "", phoneSerialNumber, adverStepNum);
		
		if(channelAdverStepUser==null){ 
			logger.error(userNum+",adverNum："+adverNum+"步骤不存在");
			return -2;
		}
		
		if(!userNum.equals(channelAdverStepUser.getUserAppNum())){
			logger.error("用户不匹配:userNum:"+userNum+",old userNu :"+channelAdverStepUser.getUserAppNum());
			return -3;
		}
		
		if(Constants.ADVER_USER_STATUS_YES==channelAdverStepUser.getAdverUserStepStatus()){
			logger.error(userNum+",adverNum："+channelAdverStepUser.getAdverStepNum()+"用户该步骤以获取积分");
			return -5;
		}
		TChannelAdverStep adverStep=channelAdverStepService.getChannelAdverStep(adverStepNum);
		Date currentDate=new Date();
		int second=TimeUtil.getBetweenSecond(channelAdverStepUser.getUpdatetime(), currentDate);  
		if(second<adverStep.getEffectiveMinCount()*60){  //运行时间不够
			logger.error("运行时间不够:");
			return -2;
		}
		
		TChannelAdverUser channelAdverUser=channelAdverUserService.getChannelAdverUser(userNum, adverNum, phoneSerialNumber);
		
		if(channelAdverUser==null){
			logger.error("没有用户记录channelAdverUser：adverNum:"+adverNum+",userNum："+userNum);
			return -3;
		}
		if(adverStep.getUseTimeDay()!=0){  //不是立即有效
			int day=TimeUtil.daysBetween(channelAdverUser.getCreatetime(), TimeUtil.getDateMaxHour(currentDate));
			if(day<adverStep.getUseTimeDay())   //时间不够
				logger.error("adverStep.getUseTimeDay() 不够：adverNum:"+adverNum+",userNum："+userNum);
				return -4;
		}
		
		int adverStepNumCurrent=channelAdverUser.getAdverStepNumCurrent()+1;
		Integer adverUserStatus=null;
		// 完成的步骤 等于广告所有的步骤  说明该广告已完成
		if(adverStepNumCurrent==channelAdverUser.getAdverStepNumCount())
			adverUserStatus=Constants.ADVER_USER_STATUS_YES;
		//修改广告的步骤 以及完成情况
		channelAdverUserService.updateAdverUser(adverNum, userNum, phoneSerialNumber, adverStepNumCurrent, adverUserStatus); 
		
		channelAdverStepUser.setAdverUserStepStatus(Constants.ADVER_USER_STEP_STATUS_YES);
		update(channelAdverStepUser);  //修改用户步骤状态
		if(Constants.EFFECTIVE_SOURCE_2.equals(adverStep.getEffectiveSource()))  //平台判断 才给用户以及上级 分层 并保存有效记录
			adverEffectiveInfoService.saveAdverEffectiveInfo(userNum, adverNum, adverStepNum, adverStep.getAdverStepName(), adverStep.getPrice(), phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		//如果需要 下面可以添加奖励
		return 1;
	}
	
	/**
	 * 功能描述: 保存用户广告步骤记录
	 *
	 * @author yangliu  2016年1月20日 下午9:57:40
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param adverStepNum 广告步骤编号
	 * @param adverUserStepStatus 用户广告步骤状态
	 * @param phoneSerialNumber 手机序列号
	 * @param systemVersion 手机系统版本号
	 * @param imei 
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel 手机型号
	 * @param ip ip地
	 * @return
	 */
	public TChannelAdverStepUser saveChannelAdverStepUser(String channelNum,String userNum,String adverNum,String adverStepNum,String adverStepName,int adverUserStepStatus,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		//TChannelAdverInfo adverInfo=channelAdverInfoService.getInfoByAdverNum(adverNum);
		TChannelAdverStepUser channelAdverStepUser=new TChannelAdverStepUser();
		channelAdverStepUser=new TChannelAdverStepUser();
		channelAdverStepUser.setUserAppNum(userNum);
		channelAdverStepUser.setAdverNum(adverNum);
		channelAdverStepUser.setChannelNum(channelNum);
		channelAdverStepUser.setPhoneSerialNumber(phoneSerialNumber);  // 唯一 必须
		channelAdverStepUser.setSystemVersion(systemVersion);
		channelAdverStepUser.setImei(imei);
		channelAdverStepUser.setIdfa(idfa);
		channelAdverStepUser.setOpenudid(openudid);
		channelAdverStepUser.setMac(mac);
		channelAdverStepUser.setAdverStepNum(adverStepNum);
		channelAdverStepUser.setIp(ip);
		channelAdverStepUser.setAdverUserStepStatus(adverUserStepStatus);
		channelAdverStepUser.setAdverStepName(adverStepName);
		saveChannelAdverStepUser(channelAdverStepUser);
		return channelAdverStepUser;
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2016年1月21日 下午8:56:26
	 * 
	 * @param page
	 * @param info
	 * @return
	 */
	public Page<TChannelAdverStepUser> pageSql(Page<TChannelAdverStepUser>page,TChannelAdverStepUser info){

		return channelAdverStepUserDao.pageSql(page, info);
	}
}
