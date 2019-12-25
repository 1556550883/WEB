package com.ruanyun.web.dao.sys.background;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.controller.sys.app.ChannelClassification;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserScoreDetail;
import com.ruanyun.web.model.TUserappidAdverid;

@Repository("userappidAdveridDao")
public class UserappidAdveridDao extends BaseDaoImpl<TUserappidAdverid> 
{
	public int saveTask(String tablename, TUserappidAdverid appidAdverid) 
	{
		StringBuilder sql = new StringBuilder("INSERT INTO  ");
		sql.append(tablename);
		sql.append(" (user_app_id,ip,idfa,apple_id,adid,adver_id,status,receive_time,phone_model,phone_version,user_udid,ip_localtion) values ");
		sql.append("('"+appidAdverid.getUserAppId()+"','"+appidAdverid.getIp()+"','"+appidAdverid.getIdfa()+"','"+appidAdverid.getAppleId()+"','"+appidAdverid.getAdid()+"','"+appidAdverid.getAdverId()+"','"+appidAdverid.getStatus()+"',")
		.append("NOW(),'"+appidAdverid.getPhoneModel()+"','"+appidAdverid.getPhoneVersion()+"','"+appidAdverid.getUserUdid()+"','"+appidAdverid.getIpLocaltion()+"')");
		return sqlDao.execute(sql.toString());
	}
	
	public Page<TUserappidAdverid> PageSqlDistinct(Page<TUserappidAdverid> page,TUserappidAdverid info) {
		StringBuilder sql = new StringBuilder("SELECT distinct adver_id as adver_id from t_userappid_adverid WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(info)){
			if (EmptyUtils.isNotEmpty(info.getUserAppId()))
				sql.append(" and user_app_id="+info.getUserAppId());
			if (EmptyUtils.isNotEmpty(info.getStatus()))
				sql.append(" and status='"+info.getStatus()+"'");
		}
		sql.append(" ORDER BY adver_id desc");
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	//未被使用，如果需要使用需要加上时间索引来减少压力
	public Integer queryMissionCount(TUserappidAdverid info,String tablename) {
		StringBuilder sql = new StringBuilder("SELECT count(1) from "+tablename+" WHERE 1=1 ");
		//加上时间索引查询，挺高效率
//		sql.append(" and receive_time > '");
//		sql.append("11111")
//		sql.append("'");
		if(EmptyUtils.isNotEmpty(info)){
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getUserAppId()))
				sql.append(" and user_app_id="+info.getUserAppId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatus()))
				sql.append(" and status='"+info.getStatus()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatusStart()))
				sql.append(" and status>='"+info.getStatusStart()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatusEnd())) {
				sql.append(" and status<='"+info.getStatusEnd()+"'");
			}
		}
		
		return sqlDao.getCount(sql.toString());
	}
	
	public Integer getIPLimitCount(String ip, int digit, String tablename) 
	{
		StringBuilder sql = new StringBuilder("SELECT count(1) from "+tablename+" WHERE status != 1.6 ");
		sql.append(" and receive_time>'"+ChannelClassification.GetdayDate());
		sql.append("'");
		//检测ip前两段是否超出限制
		String[] ipstr = ip.split("\\.");
		if(digit == 2) 
		{
			String iplike = ipstr[0] + "." + ipstr[1] + ".%";
			sql.append(" and ip like '").append(iplike).append("'");
		}
		else
		{
			String iplike = ipstr[0] + "." + ipstr[1] + "." + ipstr[2] + ".%";
			sql.append(" and ip like '").append(iplike).append("'");
		}
		
		return sqlDao.getCount(sql.toString());
	}
	
	//检测ip地区是否超出限制
	public Integer getIPlocalLimitCount(String iplocal, String tablename)
	{
		StringBuilder sql = new StringBuilder("SELECT count(1) from "+tablename+" WHERE status != 1.6 ");
		sql.append(" and receive_time>'"+ChannelClassification.GetdayDate());
		sql.append("'");
		sql.append(" and ip_localtion ='").append(iplocal).append("'");
		
		return sqlDao.getCount(sql.toString());
	}
	
	public Page<TUserScoreDetail> getScoreDetails(Page<TUserScoreDetail> page, String appid,String tablename ){
		
		StringBuilder sql = new StringBuilder("SELECT A.adver_name,A.adver_price,A.price_diff,A.task_type,B.status,B.receive_time,B.complete_time from t_channel_adver_info AS A ");
		sql.append(" LEFT JOIN "+tablename+" AS B ON A.adver_id = B.adver_id");
		sql.append(" WHERE B.user_app_id='" + appid +"'");
		sql.append(" ORDER BY B.receive_time desc");
		return sqlDao.queryPage(page, TUserScoreDetail.class, sql.toString());
	}
	
	public Page<TUserappidAdverid> PageSql(Page<TUserappidAdverid> page,TUserappidAdverid info, String tablename)
	{
		StringBuilder sql = new StringBuilder("SELECT * from "+tablename+" WHERE 1=1 ");
		//加上时间索引直接后去一天前的信息
		sql.append(" and receive_time > '");
		sql.append(ChannelClassification.GetYestdayDate());
		sql.append("'");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getUserAppId()))
				sql.append(" and user_app_id='"+info.getUserAppId() + "'");
			if (EmptyUtils.isNotEmpty(info.getIp()))
				sql.append(" and ip='"+info.getIp()+"'");
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
			if (EmptyUtils.isNotEmpty(info.getAdid()))
				sql.append(" and adid='"+info.getAdid()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatus()))
				sql.append(" and status='"+info.getStatus()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatusStart()))
				sql.append(" and status>='"+info.getStatusStart()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatusEnd()))
				sql.append(" and status<='"+info.getStatusEnd()+"'");
		}
		
		sql.append(" ORDER BY receive_time desc,user_app_id desc,adver_id desc");
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}

	public TUserappidAdverid queryByCondition(TUserappidAdverid info, String tablename, String receTime)
	{
		StringBuilder sql = new StringBuilder("SELECT * from "+tablename+" WHERE 1=1 ");
		//加上时间索引直接后去一天前的信息
		sql.append(" and receive_time > '");
		sql.append(receTime);
		sql.append("'");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getUserAppId()))
				sql.append(" and user_app_id="+info.getUserAppId());
			if (EmptyUtils.isNotEmpty(info.getIp()))
				sql.append(" and ip='"+info.getIp()+"'");
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
			if (EmptyUtils.isNotEmpty(info.getAdid()))
				sql.append(" and adid='"+info.getAdid()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatus()))
				sql.append(" and status='"+info.getStatus()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatusStart()))
				sql.append(" and status>='"+info.getStatusStart()+"'");
			if (EmptyUtils.isNotEmpty(info.getStatusEnd()))
				sql.append(" and status<='"+info.getStatusEnd()+"'");
		}
		
		sql.append(" ORDER BY receive_time desc,user_app_id desc,adver_id desc");
		return sqlDao.get(TUserappidAdverid.class, sql.toString());
	}

	
	public int updateAdverStatus(TUserappidAdverid info, String tablename) {
		StringBuilder sql = new StringBuilder("update "+tablename+" set status=? WHERE status= '2.1' ");
		if(EmptyUtils.isNotEmpty(info)){
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
		}
		Object[] params = new Object[1];
		params[0] = info.getStatus();
		return sqlDao.update(params, sql.toString());
	}
	
	public int updateStatus2OpenApp(String tablename, TUserappidAdverid info) {
		StringBuilder sql = new StringBuilder("update "+tablename+" set status=?,open_app_time=? WHERE status='1' ");
		if(EmptyUtils.isNotEmpty(info)){
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
		}
		Object[] params = new Object[2];
		params[0] = info.getStatus();
		params[1] = info.getOpenAppTime();
		return sqlDao.update(params, sql.toString());
	}
	
	public int updateSpecialComplete(String tablename, String status,String completetime,String adverid, String idfa) 
	{
		StringBuilder sql = new StringBuilder("update "+ tablename +" set status=?,complete_time=? WHERE ");
		sql.append(" adver_id="+adverid);
		sql.append(" and idfa='"+idfa+"'");
		
		Object[] params = new Object[2];
		params[0] = status;
		params[1] = completetime;
		return sqlDao.update(params, sql.toString());
	}
	
	
	public int updateSpecialComplete(String tablename, String status,Date completetime,String adverid, String idfa) 
	{
		StringBuilder sql = new StringBuilder("update "+ tablename +" set status=?,complete_time=? WHERE ");
		sql.append(" adver_id="+adverid);
		sql.append(" and idfa='"+idfa+"'");
		
		Object[] params = new Object[2];
		params[0] = status;
		params[1] = completetime;
		return sqlDao.update(params, sql.toString());
	}
//	public int updateStatus2Complete(TUserappidAdverid info) 
//	{
//		StringBuilder sql = new StringBuilder("update t_userappid_adverid set status=?,complete_time=? WHERE status >= '1' ");
//		if(EmptyUtils.isNotEmpty(info))
//		{
//			if (EmptyUtils.isNotEmpty(info.getAdverId()))
//				sql.append(" and adver_id="+info.getAdverId());
//			if (EmptyUtils.isNotEmpty(info.getIdfa()))
//				sql.append(" and idfa='"+info.getIdfa()+"'");
//		}
//		
//		Object[] params = new Object[2];
//		params[0] = info.getStatus();
//		params[1] = info.getCompleteTime();
//		return sqlDao.update(params, sql.toString());
//	}
	
	public int updateReceiveTime(TUserappidAdverid info,String tablename) 
	{
		StringBuilder sql = new StringBuilder("update "+tablename+" set receive_time=?  WHERE ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
		}
		
		Object[] params = new Object[1];
		params[0] = info.getReceiveTime();
		return sqlDao.update(params, sql.toString());
	}
	
	public int updateStatus(TUserappidAdverid info,String tablename) 
	{
		StringBuilder sql = new StringBuilder("update "+tablename+" set status=? WHERE ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
		}
		
		Object[] params = new Object[1];
		params[0] = info.getStatus();
		return sqlDao.update(params, sql.toString());
	}
	
	
	public int updateTaskStatus(TUserappidAdverid info,String tablename) 
	{
		StringBuilder sql = new StringBuilder("update "+tablename+" set status=? WHERE status<='1.5' ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
		}
		
		Object[] params = new Object[1];
		params[0] = info.getStatus();
		return sqlDao.update(params, sql.toString());
	}
	
	public int updateSpecialTaskStatus(TUserappidAdverid info,String tablename) 
	{
		StringBuilder sql = new StringBuilder("update "+tablename+" set status=? WHERE status='2.1' ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
		}
		
		Object[] params = new Object[1];
		params[0] = info.getStatus();
		return sqlDao.update(params, sql.toString());
	}
	/**
	 * 更新超时未完成任务的状态，并返回更新行数 更新前一天
	 */
	public int updateStatus2Invalid(TChannelAdverInfo adverInfo,String tablename)
	{
		StringBuilder sql = new StringBuilder("update "+tablename+" set status='1.6' WHERE receive_time >'")
				.append(ChannelClassification.GetdayDate())
				.append("' and TO_SECONDS(SYSDATE())-TO_SECONDS(receive_time) > " + adverInfo.getTimeLimit()*60 + " and status not in('2','1.6','2.1')");
		
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		
		return sqlDao.update(sql.toString());
	}
	
	//未使用
	public Page<TUserappidAdverid> getTasks(String adid, String idfa, String ip,String tablename)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-2);
		
		StringBuilder sql = new StringBuilder("select * from "+tablename+" ")
			.append(" where adid='").append(adid).append("'")
			.append(" and receive_time>'").append(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).append("'")
			.append(" and (idfa='").append(idfa).append("' or ip='").append(ip).append("')");
		
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	//工作室排重使用
	public Page<TUserappidAdverid> getTasksByIdfaOrIP(String idfa, String ip, String channelID, String adid)
	{
		String tableName = "t_adver_"+ channelID + "_" + adid;
		StringBuilder sql = new StringBuilder("select * from ")
				.append(tableName)
				.append(" where receive_time> '")
				.append(ChannelClassification.GetYestdayDate())
				.append("' and  (idfa='").append(idfa).append("' or ip='").append(ip).append("')");
		
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	//散户使用方法
	public Page<TUserappidAdverid> getTasksByIdfa(String idfa,String tablename)
	{
		StringBuilder sql = new StringBuilder("select * from "+tablename+" ")
				.append(" where idfa='").append(idfa).append("' and status<=1.6");
		
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	public TUserappidAdverid getTaskDetailByIdfa(String idfa, String tablename)
	{
		StringBuilder sql = new StringBuilder("select * from ").append(tablename)
				.append(" where idfa='").append(idfa).append("'");
		
		return sqlDao.get(TUserappidAdverid.class, sql.toString());
	}
	
	public Page<TUserappidAdverid> getTasks()
	{
		StringBuilder sql = new StringBuilder("select * from `t_userappid_adverid_2019_5_27` where adid = '0ebd032e98aed1f40cce13abad1af4a7'  and status = 2");
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	
	public TUserappidAdverid getTask(String tablename,String idfa,String adverId)
	{
		StringBuilder sql = new StringBuilder("select * from ").append(tablename)
				.append(" where idfa='").append(idfa).append("' and adver_id='").append(adverId).append("'");
		
		return sqlDao.get(TUserappidAdverid.class, sql.toString());
	}
	
	//散户获取方法
	public Page<TUserappidAdverid> getTasking(String idfa,String tablename)
	{
		StringBuilder sql = new StringBuilder("select * from "+tablename+" ")
				.append(" where idfa='").append(idfa).append("' and status<1.6");
		
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	//获取所有需要自提交的任务
	public Page<TUserappidAdverid> getLastSpecialTask(Page<TUserappidAdverid> page , String tableName, String adverId) 
	{
		StringBuilder sql = new StringBuilder("select * from ").append(tableName)
				.append(" where receive_time > '").append(ChannelClassification.GetdayDate())
				.append("' and adver_id='").append(adverId).append("' and status=2.1 order by receive_time");
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	public static void main(String[] args)
	{
		StringBuilder sql = new StringBuilder("update t_userappid_adverid set status='1.6' WHERE receive_time >'")
				.append(ChannelClassification.beforeHourToNowDate(24))
				.append("' and ((TO_SECONDS(SYSDATE())-TO_SECONDS(receive_time) > " +1*60 + " and status<='1.5') or status = '1.7') ");
		
		System.out.print(sql);
	}
	/**
	 * 查询已经使用的appleId
	 */
	public Page<TUserappidAdverid> getAppleIdMap(String adid, String appleId,String tablename) 
	{
		StringBuilder sql = new StringBuilder("select adid,apple_id from "+tablename+" where status='2'")
				.append(" and adid='").append(adid).append("'")
				.append(" and apple_id='").append(appleId).append("'");
		
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
}
