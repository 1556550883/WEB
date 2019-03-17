package com.ruanyun.web.service.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.UserappidAdveridDao;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserScoreDetail;
import com.ruanyun.web.model.TUserappidAdverid;

/**
 *任务service
 */
@Service
public class UserappidAdveridService extends BaseServiceImpl<TUserappidAdverid> 
{
	
	@Autowired
	private UserappidAdveridDao userappidAdveridDao;
	
	public Integer queryMissionCount(TUserappidAdverid info) 
	{
		return userappidAdveridDao.queryMissionCount(info);
	}
	
	public Page<TUserappidAdverid> queryMission(Page<TUserappidAdverid> page, TUserappidAdverid info) 
	{
		return userappidAdveridDao.PageSql(page, info);
	}
	
	public Page<TUserappidAdverid> queryMissionDistinct(Page<TUserappidAdverid> page, TUserappidAdverid info)
	{
		return userappidAdveridDao.PageSqlDistinct(page, info);
	}
	
	public Page<TUserScoreDetail> queryUserScoreDetail(Page<TUserScoreDetail> page, String appid)
	{
		return userappidAdveridDao.getScoreDetails(page, appid);
	}
	
	public int updateStatus2OpenApp(TUserappidAdverid info)
	{
		return userappidAdveridDao.updateStatus2OpenApp(info);
	}
	
	public int deleteOverTimeAdver(TUserappidAdverid info)
	{
		String[] propertyNames = new String[2];
		propertyNames[0] = "idfa";
		propertyNames[1] = "adverId";
		Object[] values = new Object[2];
		values[0] = info.getIdfa();
		values[1] = info.getAdverId();
		return super.delete(TUserappidAdverid.class, propertyNames, values);
	}
	
	public int updateStatus2Complete(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateStatus2Complete(info);
	}
	
	public int updateReceiveTime(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateReceiveTime(info);
	}
	
	public int updateTaskStatus(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateTaskStatus(info);
	}
	
	/**
	 * 更新超时未完成任务的状态，并返回更新行数
	 */
	public int updateStatus2Invalid(TChannelAdverInfo adverInfo) 
	{
		return userappidAdveridDao.updateStatus2Invalid(adverInfo);
	}
	
	public Page<TUserappidAdverid> getTasks(String adid, String idfa, String ip) 
	{
		return userappidAdveridDao.getTasks(adid, idfa, ip);
	}
	
	public Page<TUserappidAdverid> getTasksByIdfaOrIP(String idfa, String ip) 
	{
		return userappidAdveridDao.getTasksByIdfaOrIP(idfa, ip);
	}
	
	public Page<TUserappidAdverid> getTasksByIdfa(String idfa) 
	{
		return userappidAdveridDao.getTasksByIdfa(idfa);
	}
	
	/**
	 * 检查appleId是否已经使用
	 */
	@SuppressWarnings("null")
	public boolean checkAppleIdIsUsed(String adid, String appleId)
	{
		boolean isUsed = false;
		
		if(StringUtils.hasText(adid) || StringUtils.hasText(appleId))
		{
			Page<TUserappidAdverid> taskList = userappidAdveridDao.getAppleIdMap(adid, appleId);
			
			if (taskList.getResult().size() > 0) 
			{
				isUsed = true;
			}
		}
		
		return isUsed;
	}
}
