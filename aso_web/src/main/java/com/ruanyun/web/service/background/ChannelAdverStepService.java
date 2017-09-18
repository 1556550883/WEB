package com.ruanyun.web.service.background;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.orm.ICommonHqlDao;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.dao.sys.ChannelAdverStepDao;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelAdverStep;
import com.ruanyun.web.model.TChannelAdverStepUser;
import com.ruanyun.web.model.TChannelAdverUser;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;

@Service
public class ChannelAdverStepService extends BaseServiceImpl<TChannelAdverStep> {
	@Autowired
	private ChannelAdverStepDao channelAdverStepDao;
	@Autowired
	private ChannelAdverStepUserService channelAdverStepUserService;
	@Autowired
	private ChannelAdverUserService channelAdverUserService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	
	
	/**
	 * 功能描述:根据广告编号
	 *
	 * @author yangliu  2016年1月20日 下午10:08:01
	 * 
	 * @param adverNum
	 * @param adverStepCountNum
	 * @return
	 */
	public TChannelAdverStep getChannelAdverStep(String adverNum,int adverStepCountNum){
		return get(TChannelAdverStep.class, new String[]{"adverStepCountNum","adverNum"}, new Object[]{adverStepCountNum,adverNum});
	}
	
	public TChannelAdverStep getChannelAdverStep(String adverStepNum){
		return get(TChannelAdverStep.class, "adverStepNum", adverStepNum);
	}
	
	/**
	 * 
	 * 功能描述：增加广告步骤
	 * @auther xqzhang
	 * 时间：2016-1-19
	 * @param infos
	 * @param stepName
	 * @param stepDesc
	 * @param stepRates
	 * @param stepTime
	 * @param stepScore
	 * @param stepUseTime
	 * @param stepType
	 * @param stepMinCount
	 */
	public void saveAdverStep(TChannelAdverInfo infos,String[] stepName, String[] stepDesc, Integer[] stepRates, String[] stepTime, Float[] stepScore, Integer[] stepUseTime, String[] stepType, Integer[] stepMinCount){
		delete(TChannelAdverStep.class, "adverNum", infos.getAdverNum());
		for(int i=0;i<stepName.length;i++){
			TChannelAdverStep channelAdverStep=new TChannelAdverStep();
			channelAdverStep.setAdverStepNum(String.valueOf(i+1));
			channelAdverStep.setAdverStepName(stepName[i]);
			channelAdverStep.setStepDesc(stepDesc[i]);
			channelAdverStep.setConversionsRates(stepRates[i]);
			channelAdverStep.setEffectiveTimeRates(TimeUtil.doFormatDate(stepTime[i], "yyyy-MM-dd"));
			channelAdverStep.setPrice(stepScore[i]);
			channelAdverStep.setUseTimeDay(stepUseTime[i]);
			channelAdverStep.setEffectiveType(stepType[i]);
			channelAdverStep.setEffectiveMinCount(stepMinCount[i]);
			channelAdverStep.setAdverNum(infos.getAdverNum());
			channelAdverStep.setEffectiveSource(infos.getEffectiveSource());
			channelAdverStep.setOrderNum(i+1);
			save(channelAdverStep);
			channelAdverStep.setAdverStepNum(NumUtils.getCommondNum(NumUtils.CHANNEL_ADVER_STEP_INFO, channelAdverStep.getStepId()));
		}
	}
	
	
	/**
	 * 
	 * 功能描述：根据广告编号获得步骤
	 * @auther xqzhang
	 * 时间：2016-1-19
	 * @param adverNum
	 * @return
	 */
	public List<TChannelAdverStep> getAllStepByAdverId(String adverNum){
		return getAll(TChannelAdverStep.class, "adverNum", adverNum,"orderNum",ICommonHqlDao.ORDER_ASC);
	}
	
	
	
	
	/**
	 * 功能描述: 获取步骤
	 *
	 * @author yangliu  2016年1月20日 下午10:01:44
	 * 
	 * @param userNum 用户编号
	 * @param adverNum
	 * @param phoneSerialNumber
	 * @param systemVersion
	 * @param imei
	 * @param idfa
	 * @param openudid
	 * @param mac
	 * @param phoneModel
	 * @param ip
	 * @param type
	 * @return
	 */
	public TChannelAdverStep getTChannelAdverStep(String userNum,String adverNum,String phoneSerialNumber,String systemVersion,String imei,String idfa,String openudid,String mac,String phoneModel,String ip){
		if(EmptyUtils.isEmpty(phoneSerialNumber) || EmptyUtils.isEmpty(adverNum) ||EmptyUtils.isEmpty(phoneSerialNumber) ){
			return null;
		}
		TChannelAdverStep channelAdverStep=null;
		TChannelAdverStepUser channelAdverStepUser=channelAdverStepUserService.getNewAdverStepUser(adverNum,"", phoneSerialNumber,"");
		if(channelAdverStepUser==null){
			return null;
		}
		TChannelAdverInfo adverInfo=channelAdverInfoService.getInfoByAdverNum(adverNum);
		if(adverInfo==null){
			return null;
		}
		
		//安装后第一部
		if(Constants.CHANNEL_STEP_NUM_INSTALL.equals(channelAdverStepUser.getAdverStepNum())){
			if(!channelAdverStepUser.getUserAppNum().equals(userNum)){  //不是当前用户安装的
				return null;
			}
			channelAdverStep=getChannelAdverStep(adverNum, 1);
			channelAdverStepUserService.saveChannelAdverStepUser(adverInfo.getChannelNum(),userNum, adverNum, channelAdverStep.getAdverStepNum(),channelAdverStep.getAdverStepName(), Constants.ADVER_USER_STEP_STATUS_NO, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
			return channelAdverStep;
		}
		channelAdverStep=getChannelAdverStep(channelAdverStepUser.getAdverStepNum());
		// 任务没有完成的
		if(Constants.ADVER_USER_STEP_STATUS_NO==channelAdverStepUser.getAdverUserStepStatus()){
			channelAdverStepUser.setUpdatetime(new Date());
			channelAdverStepUserService.update(channelAdverStepUser);
			return channelAdverStep;
		}
		TChannelAdverUser channelAdverUser=channelAdverUserService.getChannelAdverUser("", adverNum, phoneSerialNumber);
		if(channelAdverUser==null)  //没有广告下载记录
			return null;
		if(!channelAdverUser.getUserNum().equals(userNum))  //最新的不是当前用户的
			return null;
		if(channelAdverUser.getAdverUserStatus()==Constants.ADVER_USER_STATUS_YES)  //改广告已完成了
			return null;
		channelAdverStep=getChannelAdverStep(adverNum, channelAdverUser.getAdverStepNumCurrent()+1);
		if(channelAdverStep==null)
			return null;
		//保存当前操作的步骤
		channelAdverStepUserService.saveChannelAdverStepUser(adverInfo.getChannelNum(),userNum, adverNum, channelAdverStep.getAdverStepNum(),channelAdverStep.getAdverStepName(), Constants.ADVER_USER_STEP_STATUS_NO, phoneSerialNumber, systemVersion, imei, idfa, openudid, mac, phoneModel, ip);
		return channelAdverStep;
	}
	
}
