/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-6
 */
package com.ruanyun.web.service.background;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.background.ChannelInfoDao;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.ExcelUtils;

@Service
public class ChannelInfoService extends BaseServiceImpl<TChannelInfo>
{

	@Autowired
	@Qualifier("channelInfoDao")
	private ChannelInfoDao channelInfoDao;

	@Autowired
	private UserService userService;

	@Override
	public Page<TChannelInfo> queryPage(Page<TChannelInfo> page, TChannelInfo t) 
	{
		return channelInfoDao.queryPage(page, t);
	}
	
	public int calculate(String channelNum, String da,String yestda) 
	{
		return channelInfoDao.calculate(channelNum, da, yestda);
	}
	
	/**
	 * idfa统计
	 */
	public Page<TUserappidAdverid> queryIdfaStatistics(Page<TUserappidAdverid> page, String channelNum, String completeTime) 
			throws ParseException 
	{
		return channelInfoDao.queryIdfaStatistics(page, channelNum, completeTime);
	}
	
	/**
	 * 员工idfa统计
	 */
//	public Page<TUserappidAdverid> queryEmployeeIdfaStatistics(Page<TUserappidAdverid> page, Integer userAppId, String completeTime) 
//			throws ParseException 
//	{
//		return channelInfoDao.queryEmployeeIdfaStatistics(page, userAppId, completeTime);
//	}

	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TChannelInfo info, HttpServletRequest request,
			TUser user) 
	{
		if (info != null) 
		{
			if (EmptyUtils.isNotEmpty(info.getChannelId()) && info.getChannelId() != 0) 
			{
				TChannelInfo n = super.get(TChannelInfo.class, info.getChannelId());
				BeanUtils.copyProperties(info, n, new String[] { "channelNum","createDate","channelType"});
				channelInfoDao.update(n);
			}
			else 
			{
				info.setCreateDate(new Date());
				info.setIsEnable(0);//默认不启用
				channelInfoDao.save(info);
				info.setChannelNum(info.getChannelId() + "");
			}
		}
		
		return 1;
	}
	
	
	public void updateDayTotal(){
		channelInfoDao.updateDayTotal();
	}
	
	/**
	 * 
	 * 功能描述:更改启动状态
	 * @param id
	 * @param isEnable 0/1 未启用/启用
	 * @return
	 */
	public int updateIsEnable(Integer id,Integer isEnable)
	{
		TChannelInfo oldChannelInfo = get(TChannelInfo.class, id);
		if (EmptyUtils.isNotEmpty(oldChannelInfo)) 
		{
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
	public TChannelInfo getInfoById(Integer id) 
	{
		TChannelInfo info = super.get(TChannelInfo.class, id);
		TUser user = userService.getInfoByUserNum(info.getChannelNum());
		if (EmptyUtils.isNotEmpty(user)) 
		{
			info.setLoginName(user.getLoginName());
		}
		
		return info;
	}
	
	public TChannelInfo getInfoByNum(String channelNum) 
	{
		TChannelInfo info = super.get(TChannelInfo.class,"channelNum", channelNum);
		return info;
	}
	
	public TChannelInfo getChannelInfoByChannelType(String channelType,String systemType,String userNum)
	{
		//TChannelInfo channelInfo =super.get(TChannelInfo.class, "channelType",channelType);
		TChannelInfo channelInfo = channelInfoDao.getChannelInfoBySystemType(channelType, systemType,userNum);
		
		return channelInfo;
	}
	
	public TChannelInfo getChannelByNum(String channelnum)
	{
		return channelInfoDao.getChannelByNum(channelnum);
	}
	
	public void updateChannelInfo()
	{
		channelInfoDao.updateChannelInfo();
	}
	
	public void exportChannelData(HttpServletResponse response)
	{
		List list = channelInfoDao.exportChannelData();
		String fileName = "channelDetail";
		String[] columns = {"channel_num","channel_name","last_month_total","total"};
		String[] headers = {"渠道号","渠道名","上月总金额","上月总数量"};
		try {
			ExcelUtils.exportExcel(response, fileName, list, columns, headers,
			SysCode.DATE_FORMAT_STR_L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
