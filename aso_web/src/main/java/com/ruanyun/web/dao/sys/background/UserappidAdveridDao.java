package com.ruanyun.web.dao.sys.background;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserappidAdverid;

/**
 *@author 向轴
 */
@Repository("userappidAdveridDao")
public class UserappidAdveridDao extends BaseDaoImpl<TUserappidAdverid> {
	
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
	
	public Integer queryMissionCount(TUserappidAdverid info) {
		StringBuilder sql = new StringBuilder("SELECT count(1) from t_userappid_adverid WHERE 1=1 ");
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
			if (EmptyUtils.isNotEmpty(info.getStatusEnd()))
				sql.append(" and status<='"+info.getStatusEnd()+"'");
		}
		
		return sqlDao.getCount(sql.toString());
	}
	
	public Page<TUserappidAdverid> PageSql(Page<TUserappidAdverid> page,TUserappidAdverid info) {
		StringBuilder sql = new StringBuilder("SELECT * from t_userappid_adverid WHERE 1=1 ");
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
			if (EmptyUtils.isNotEmpty(info.getStatusEnd()))
				sql.append(" and status<='"+info.getStatusEnd()+"'");
		}
		sql.append(" ORDER BY user_app_id desc,adver_id desc,complete_time desc");
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}

	public int updateStatus2OpenApp(TUserappidAdverid info) {
		StringBuilder sql = new StringBuilder("update t_userappid_adverid set status=?,open_app_time=? WHERE status='1' ");
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
	
	public int updateStatus2Complete(TUserappidAdverid info) 
	{
		StringBuilder sql = new StringBuilder("update t_userappid_adverid set status=?,complete_time=? WHERE status<='1.5' ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
			if (EmptyUtils.isNotEmpty(info.getIdfa()))
				sql.append(" and idfa='"+info.getIdfa()+"'");
		}
		
		Object[] params = new Object[2];
		params[0] = info.getStatus();
		params[1] = info.getCompleteTime();
		return sqlDao.update(params, sql.toString());
	}
	
	/**
	 * 更新超时未完成任务的状态，并返回更新行数
	 */
	public int updateStatus2Invalid(TChannelAdverInfo adverInfo)
	{
		StringBuilder sql = new StringBuilder("update t_userappid_adverid set status='1.6' WHERE TO_SECONDS(SYSDATE())-TO_SECONDS(receive_time) > " + adverInfo.getTimeLimit()*60 + " and status<='1.5' ");
		
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		
		return sqlDao.update(sql.toString());
	}
	
	public Page<TUserappidAdverid> getTasks(String adid, String idfa, String ip)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-2);
		
		StringBuilder sql = new StringBuilder("select * from t_userappid_adverid ")
			.append(" where adid='").append(adid).append("'")
			.append(" and receive_time>'").append(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime())).append("'")
			.append(" and (idfa='").append(idfa).append("' or ip='").append(ip).append("')");
		
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		page.setNumPerPage(Integer.MAX_VALUE);
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
	
	/**
	 * 查询已经使用的appleId
	 */
	public Page<TUserappidAdverid> getAppleIdMap(String adid, String appleId) 
	{
		StringBuilder sql = new StringBuilder("select adid,apple_id from t_userappid_adverid where status='2'")
				.append(" where adid='").append(adid).append("'")
				.append(" where appleId='").append(appleId).append("' ");
		
		Page<TUserappidAdverid> page = new Page<TUserappidAdverid>();
		
		return sqlDao.queryPage(page, TUserappidAdverid.class, sql.toString());
	}
}
