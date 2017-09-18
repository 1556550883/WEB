/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-6
 */
package com.ruanyun.web.service.background;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ChannelInfoDao;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.UploadCommon;

/**
 *@author feiyang
 *@date 2016-1-6
 */
@Service
public class ChannelInfoService extends BaseServiceImpl<TChannelInfo> {

	@Autowired
	@Qualifier("channelInfoDao")
	private ChannelInfoDao channelInfoDao;

	@Autowired
	private UserService userService;

	@Override
	public Page<TChannelInfo> queryPage(Page<TChannelInfo> page, TChannelInfo t) {
		// TODO Auto-generated method stub
		return channelInfoDao.queryPage(page, t);
	}
	
	/**
	 * idfa统计
	 */
	public Page<TUserappidAdverid> queryIdfaStatistics(Page<TUserappidAdverid> page, String channelNum, String completeTime) throws ParseException {
		return channelInfoDao.queryIdfaStatistics(page, channelNum, completeTime);
	}
	
	/**
	 * 员工idfa统计
	 */
	public Page<TUserappidAdverid> queryEmployeeIdfaStatistics(Page<TUserappidAdverid> page, Integer userAppId, String completeTime) throws ParseException {
		return channelInfoDao.queryEmployeeIdfaStatistics(page, userAppId, completeTime);
	}

	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TChannelInfo info, HttpServletRequest request,
			TUser user,MultipartFile picFile) {
	
		try {
			if (picFile.getSize()!=0) {
				UploadVo vo = UploadCommon.uploadPic(picFile, request,
						Constants.FILE_IMG, "gif,jpg,jpeg,bmp,png");
				if (vo.getResult()==1) {
					info.setChannelImg(vo.getFilename());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (info != null) {
			if (EmptyUtils.isNotEmpty(info.getChannelId())
					&& info.getChannelId() != 0) {
				TChannelInfo n = super.get(TChannelInfo.class, info
						.getChannelId());
				BeanUtils
						.copyProperties(info, n, new String[] { "channelNum","createDate","channelType"});
				channelInfoDao.update(n);
			} else {
				info.setCreateDate(new Date());
				info.setIsEnable(0);//默认不启用
				channelInfoDao.save(info);
				info.setChannelNum(NumUtils.getCommondNum(
						NumUtils.CHANNEL_INFO, info.getChannelId()));

			}
		}
		return 1;
	}
	/**
	 * 
	 * 功能描述:更改启动状态
	 * @param id
	 * @param isEnable 0/1 未启用/启用
	 * @return
	 *@author feiyang
	 *@date 2016-1-22
	 */
	public int updateIsEnable(Integer id,Integer isEnable){
		TChannelInfo oldChannelInfo=get(TChannelInfo.class, id);
		if (EmptyUtils.isNotEmpty(oldChannelInfo)) {
			oldChannelInfo.setIsEnable(isEnable);
			update(oldChannelInfo);
			return 1;
		}
		return 0;
	}
	
	/**
	 * 
	 * 功能描述:根据Id获得详细信息
	 * @param id
	 * @return
	 *@author feiyang
	 *@date 2016-1-8
	 */
	public TChannelInfo getInfoById(Integer id) {
		TChannelInfo info = super.get(TChannelInfo.class, id);
		TUser user = userService.getInfoByUserNum(info.getChannelNum());
		if (EmptyUtils.isNotEmpty(user))
			info.setLoginName(user.getLoginName());
		return info;
	}
	
	public TChannelInfo getInfoByNum(String channelNum) {
		TChannelInfo info = super.get(TChannelInfo.class,"channelNum", channelNum);
		return info;
	}
	
	public TChannelInfo getChannelInfoByChannelType(String channelType,String systemType,String userNum){
		//TChannelInfo channelInfo =super.get(TChannelInfo.class, "channelType",channelType);
		TChannelInfo channelInfo =channelInfoDao.getChannelInfoBySystemType(channelType, systemType,userNum);
		return channelInfo;
	}
	

}
