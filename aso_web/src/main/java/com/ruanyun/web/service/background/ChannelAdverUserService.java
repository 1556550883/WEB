package com.ruanyun.web.service.background;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ChannelAdverUserDao;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelAdverUser;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
@Service
public class ChannelAdverUserService extends BaseServiceImpl<TChannelAdverUser> {
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	@Autowired
	private ChannelAdverUserDao channelAdverUserDao;
	
	/**
	 * 功能描述: 保存用户下载的任务
	 *
	 * @author yangliu  2016年1月20日 下午10:34:45
	 * 
	 * @param adverNum 广告编号=
	 * @param adverStepCount 广告步骤总数
	 * @param userAppNum 用户编号
	 * @return
	 */
	public TChannelAdverUser saveAdverUser(String adverNum,int adverStepCount,String userAppNum,String phoneSerialNumber){
		/*TChannelAdverInfo adverInfo=channelAdverInfoService.getInfoByAdverNum(adverNum);
		if(adverInfo==null)
			return null;*/
		Date currentDate=new Date();
		TChannelAdverUser channelAdverUser = new TChannelAdverUser();
		channelAdverUser.setAdverNum(adverNum);
		channelAdverUser.setUserNum(userAppNum);
		channelAdverUser.setAdverUserNum(userAppNum);
		channelAdverUser.setAdverStepNumCount(adverStepCount);
		channelAdverUser.setAdverStepNumCurrent(0);
		channelAdverUser.setPhoneSerialNumber(phoneSerialNumber);  //手机序列号
		channelAdverUser.setAdverUserStatus(Constants.ADVER_USER_STATUS_NO);
		channelAdverUser.setCreatetime(currentDate);
		channelAdverUser.setUpdatetime(currentDate);
		save(channelAdverUser);
		channelAdverUser.setAdverUserNum(NumUtils.getCommondNum(NumUtils.CHANNEL_ADVER_STEP_INFO, channelAdverUser.getAdverUserId()));
		return channelAdverUser;
	}
	
	/**
	 * 功能描述: 根据广告和用户 信息
	 *
	 * @author yangliu  2016年1月20日 下午10:42:40
	 * 
	 * @param userNum 用户编号
	 * @param adverNum 广告编号
	 * @param phoneSerialNumber 手机序列号
	 * @return
	 */
	public TChannelAdverUser getChannelAdverUser(String userNum,String adverNum,String phoneSerialNumber){
		return channelAdverUserDao.getChannelAdverUser(adverNum, userNum, phoneSerialNumber);
	}
	
	/**
	 * 功能描述: 修改完成步骤和状态
	 *
	 * @author yangliu  2016年1月20日 下午10:43:30
	 * 
	 * @param adverNum  广告编号
	 * @param userAppNum 用户编号
	 * @param adverStepNumCurrent 广告步骤  为空 不修改
	 * @param adverUserStatus 状态 为空 不修改
	 * @return
	 */
	public int updateAdverUser(String adverNum,String userAppNum,String  phoneSerialNumber,Integer adverStepNumCurrent,Integer adverUserStatus){
		TChannelAdverUser channelAdverUser=getChannelAdverUser("", adverNum,phoneSerialNumber);
		if(channelAdverUser==null)  // 没有记录
			return -1;
		if(!userAppNum.equals(channelAdverUser.getUserNum()))  //不是该用户下载的
			return -2;
		if(EmptyUtils.isNotEmpty(adverStepNumCurrent))
			channelAdverUser.setAdverStepNumCurrent(adverStepNumCurrent);
		if(EmptyUtils.isNotEmpty(adverUserStatus))
			channelAdverUser.setAdverUserStatus(adverUserStatus);
		channelAdverUser.setUpdatetime(new Date());
		update(channelAdverUser);
		return 1;
	}

}
