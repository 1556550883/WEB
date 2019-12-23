package com.ruanyun.web.service.background;

import java.util.Date;

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
	
	public Integer getIPLimitCount(String ip, int digit, String tablename) 
	{
		return userappidAdveridDao.getIPLimitCount(ip,digit,tablename);
	}
	
	public Integer getIPlocalLimitCount(String iplocal, String tablename) 
	{
		return userappidAdveridDao.getIPlocalLimitCount(iplocal,tablename);
	}
	
	public int saveTask(String tablename, TUserappidAdverid task)
	{
		return userappidAdveridDao.saveTask(tablename,task);
	}
	
	public Page<TUserappidAdverid> queryMission(Page<TUserappidAdverid> page, TUserappidAdverid info, String tablename) 
	{
		return userappidAdveridDao.PageSql(page, info,tablename);
	}
	
	public TUserappidAdverid queryByCondition(TUserappidAdverid info, String tablename,String receTime) 
	{
		return userappidAdveridDao.queryByCondition(info,tablename,receTime);
	}
	//queryByCondition
	public Page<TUserappidAdverid> queryMissionDistinct(Page<TUserappidAdverid> page, TUserappidAdverid info)
	{
		return userappidAdveridDao.PageSqlDistinct(page, info);
	}
	
	public Page<TUserScoreDetail> queryUserScoreDetail(Page<TUserScoreDetail> page, String appid)
	{
		return userappidAdveridDao.getScoreDetails(page, appid);
	}
	
	public int updateStatus2OpenApp(String tablename,TUserappidAdverid info)
	{
		return userappidAdveridDao.updateStatus2OpenApp(tablename, info);
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
	
	public int updateSpecialComplete(String tablename, String status,String completetime,String adverid, String idfa) 
	{
		return userappidAdveridDao.updateSpecialComplete(tablename, status,completetime,adverid,idfa);
	}
	
	public int updateSpecialComplete(String tablename, String status,Date completetime,String adverid, String idfa) 
	{
		return userappidAdveridDao.updateSpecialComplete(tablename, status,completetime,adverid,idfa);
	}
	
//	public int updateStatus2Complete(TUserappidAdverid info) 
//	{
//		return userappidAdveridDao.updateStatus2Complete(info);
//	}
	
	public int updateReceiveTime(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateReceiveTime(info);
	}
	
	public int updateAdverStatus(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateAdverStatus(info);
	}
	
	public int updateStatus(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateStatus(info);
	}
	
	public int updateTaskStatus(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateTaskStatus(info);
	}
	
	public int updateSpecialTaskStatus(TUserappidAdverid info) 
	{
		return userappidAdveridDao.updateSpecialTaskStatus(info); 
	}
	/**
	 * 更新超时未完成任务的状态，并返回更新行数
	 */
	public int updateStatus2Invalid(TChannelAdverInfo adverInfo, String tablename) 
	{
		return userappidAdveridDao.updateStatus2Invalid(adverInfo,tablename);
	}
	
	public Page<TUserappidAdverid> getTasks(String adid, String idfa, String ip) 
	{
		return userappidAdveridDao.getTasks(adid, idfa, ip);
	}
	
	public Page<TUserappidAdverid> getTasksByIdfaOrIP(String idfa, String ip,String channelID, String adid)
	{
		return userappidAdveridDao.getTasksByIdfaOrIP(idfa, ip,channelID,adid);
	}
	
	//散户方法
	public Page<TUserappidAdverid> getTasksByIdfa(String idfa) 
	{
		return userappidAdveridDao.getTasksByIdfa(idfa);
	}
	
	//获取任务明细
	public TUserappidAdverid getTaskDetailByIdfa(String idfa,String tablename) 
	{
		return userappidAdveridDao.getTaskDetailByIdfa(idfa,tablename);
	}
	
	public Page<TUserappidAdverid> getTasks() 
	{
		return userappidAdveridDao.getTasks();
	}
	
	//获取任务
	public TUserappidAdverid getTask(String tablename,String idfa,String adverId) 
	{
		return userappidAdveridDao.getTask(tablename,idfa,adverId);
	}
	
	public Page<TUserappidAdverid> getTasking(String idfa) 
	{
		return userappidAdveridDao.getTasking(idfa);
	}
	
	public Page<TUserappidAdverid> getLastSpecialTask(Page<TUserappidAdverid> page ,String tableName, String adverId) {
		return userappidAdveridDao.getLastSpecialTask(page, tableName, adverId);
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
