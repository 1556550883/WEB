/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TChannelAdverInfo;

@Repository("channelAdverInfoDao")
public class ChannelAdverInfoDao extends BaseDaoImpl<TChannelAdverInfo> {
	@Override
	protected String queryPageSql(TChannelAdverInfo t, Map<String, Object> params) 
	{
		StringBuffer sql = new StringBuffer(" from TChannelAdverInfo where 1=1 ");
		
		if (EmptyUtils.isNotEmpty(t)) 
		{
			sql.append(SQLUtils.popuHqlEq("channelNum", t.getChannelNum()));
		}
		
		return sql.toString();
	}

	/**
	 * 查询广告列表（手机端显示）
	 */
	public Page<TChannelAdverInfo> PageSql(Page<TChannelAdverInfo> page, TChannelAdverInfo info)
	{
		StringBuffer sql = new StringBuffer("SELECT * from t_channel_adver_info WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getChannelNum()))
			{
				sql.append(" and channel_num='"+info.getChannelNum()+"'");
			}
			
			if (EmptyUtils.isNotEmpty(info.getTaskType())) 	
			{
				sql.append(" AND task_type ='"+info.getTaskType()+"'");
			}	
			
			if (EmptyUtils.isNotEmpty(info.getPhoneType())) 
			{
				sql.append(" AND phone_type in('"+info.getPhoneType()+"','无要求')");
			}
			
			sql.append(SQLUtils.popuHqlMax2("adver_day_start", new Date()));
			sql.append(SQLUtils.popuHqlMin2("adver_day_end", new Date()));
		}
		
		sql.append(" and adver_status=1");
		sql.append(" ORDER BY adver_createtime desc");
		
		return sqlDao.queryPage(page, TChannelAdverInfo.class, sql.toString());
	}

	/**
	 * 查询广告列表（手机端显示）
	 */
	public Page<TChannelAdverInfo> PageSql2(Page<TChannelAdverInfo> page, String channelType, String systemType, String phoneType, int level, String osversion, int userType)
	{
		StringBuffer sql = new StringBuffer("SELECT * from t_channel_adver_info WHERE 1=1 ");
		//sql.append(" and channel_num in (select b.channel_num from t_channel_info b where b.channel_type='").append(channelType).append("' and b.system_type='").append(systemType).append("')");
		sql.append(SQLUtils.popuHqlMax("phone_type", phoneType));
		sql.append(SQLUtils.popuHqlMax("ios_version", Integer.parseInt(osversion)));
		//sql.append(SQLUtils.popuHqlMax2("adver_day_start", new Date()));
		sql.append(SQLUtils.popuHqlMin2("adver_day_end", new Date()));
		sql.append(SQLUtils.popuHqlMax("level", level));
		sql.append(" and (is_open = 0 or is_open =" + userType);
		//sql.append(" and adver_status=1");
		sql.append(") ORDER BY adver_status asc,adver_createtime desc");
		return sqlDao.queryPage(page, TChannelAdverInfo.class, sql.toString());
	}
	
	/**
	 * 查询广告列表（后台显示）
	 */
	public Page<TChannelAdverInfo> PageSql3(Page<TChannelAdverInfo> page, String channelNum)
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE 1=1 ");
		sql.append(" and channel_num='").append(channelNum);
		sql.append("' and adver_createtime>'").append(GetYestdayDate());
		//sql.append(SQLUtils.popuHqlMin2("create_date", new Date()));
		sql.append("' and adver_status in('0','1','2')");
		sql.append(" ORDER BY DATE_FORMAT(adver_createtime,'%Y%m%d') asc,adid asc");
		Page<TChannelAdverInfo> page2 = sqlDao.queryPage(page, TChannelAdverInfo.class, sql.toString());
		
		if(page2 != null && page2.getResult() != null){
			for(TChannelAdverInfo item:page2.getResult()){
				sql = new StringBuilder("select count(1) from t_userappid_adverid where adver_id=").append(item.getAdverId()).append(" and status='2'");
				item.setAdverCountComplete(sqlDao.getCount(sql.toString()));
			}
		}
		
		return page2;
	}
	
	//获取昨天的日期
	@SuppressWarnings("static-access")
	public String GetYestdayDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE,-1);
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}
	
	public int getCountComplete(String adverId) 
	{
		StringBuilder sql = new StringBuilder("select count(1) from t_userappid_adverid where adver_id=").append(adverId).append(" and status='2'");;
		return sqlDao.getCount(sql.toString());
	}
	/**
	 * 查询当前的广告列表
	 */
	public List<TChannelAdverInfo> queryCurrentAdverList() {
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE 1=1 ")
							.append(SQLUtils.popuHqlMax2("adver_day_start", new Date()))
							.append(SQLUtils.popuHqlMin2("adver_day_end", new Date()));
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	/**
	 * 功能描述：删除
	 */
	public void delAll(String ids) {
		StringBuffer sql = new StringBuffer("delete from t_channel_adver_info where adver_id in (" + ids + ")");
		sqlDao.execute(sql.toString());
	}
	
	/**
	 * 批量审核
	 */
	public void updateAdverStatus(Integer status, String ids)
	{
		StringBuilder sql;
		//当时启动任务的时候，默认任务最高等级
		if(status == 1) 
		{
			sql = new StringBuilder("update t_channel_adver_info set adver_status ="+status+",level=10 where adver_id in ("+ids+")");
		}
		else
		{
			sql = new StringBuilder("update t_channel_adver_info set adver_status ="+status+" where adver_id in ("+ids+")");
		}
		
		sqlDao.execute(sql.toString());
	}
	
	
	/**
	 * 修改任务状态
	 */
	public void updateAdverStatusAll(Integer status)
	{
		StringBuilder sql;
		//当时启动任务的时候，默认任务最高等级
		sql = new StringBuilder("update t_channel_adver_info set adver_status ="+status+"");
		
		sqlDao.execute(sql.toString());
	}
	/**
	 * 批量支付
	 */
	public void updateAdverStatus2Pay(Integer status,String ids){
		StringBuilder sql=new StringBuilder("update t_channel_adver_info set adver_status ="+status+" where adver_id in ("+ids+")");
		sqlDao.execute(sql.toString());
		sql=new StringBuilder("update t_userappid_adverid set status='"+status+"' where adver_id in ("+ids+") and status='2'");
		sqlDao.execute(sql.toString());
	}
	
	/**
	 * 
	 * 功能描述:根据编号获取详情
	 * @param adverNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	public TChannelAdverInfo getDetailByAdverNum(String adverNum){
		StringBuffer sql = new StringBuffer(" SELECT * from  t_channel_adver_info WHERE adver_num='"+adverNum+"' AND adver_status=1");
		return sqlDao.get(TChannelAdverInfo.class, sql.toString());
	}
	
	/**
	 * 通用查询
	 */
	public List<TChannelAdverInfo> getByCondition(TChannelAdverInfo adverInfo)
	{
		StringBuilder sql = new StringBuilder("select * from t_channel_adver_info WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverStatusEnd()))
				sql.append(" and adver_status<=").append(adverInfo.getAdverStatusEnd());
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverAdid()))
				sql.append(" and adver_adid=").append(adverInfo.getAdverAdid());
			if (EmptyUtils.isNotEmpty(adverInfo.getChannelNum()))
				sql.append(" and channel_num=").append(adverInfo.getChannelNum());
		}
		
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	public int updateAdverCountRemainMinus1(TChannelAdverInfo adverInfo)
	{
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set adver_count_remain=adver_count_remain-1 WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		
		return sqlDao.update(sql.toString());
	}
	
	public int updateAdverActivationRemainMinus1(TChannelAdverInfo adverInfo)
	{
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set adver_activation_count=adver_activation_count- ");
		sql.append(adverInfo.getAdverActivationCount() + " WHERE 1=1");
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		
		return sqlDao.update(sql.toString());
	}
	
	public int updateAdverCountRemain(TChannelAdverInfo adverInfo)
	{
		StringBuilder sql = new StringBuilder("update t_channel_adver_info a set adver_count_remain=adver_count-(select count(1) from t_userappid_adverid b where a.adver_id=b.adver_id and b.status<>'1.6') WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		return sqlDao.update(sql.toString());
	}
	
	public int updateAdverActivationCount(TChannelAdverInfo info) 
	{
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set adver_activation_count=?, download_count=? WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
		}
		
		Object[] params = new Object[2];
		params[0] = info.getAdverActivationCount();
		params[1] = info.getDownloadCount();
		
		return sqlDao.update(params, sql.toString());
	}
	
	/*
	 * CREATE TABLE 新表
	 *SELECT * FROM 旧表 
	 */
	public void adverInfoTableBak() 
	{
		int result1 = -1;
		int result2 = -1;
		Calendar date = Calendar.getInstance();
		int year = date.get(Calendar.YEAR);
		int month = date.get(Calendar.MONTH) + 1;
		int day = date.get(Calendar.DAY_OF_MONTH);
		String iYear = year + "_";
		String iMonth = month + "_";
		String iDay   = day + "";
		String adverInfoName = "t_channel_adver_info_" + iYear + iMonth + iDay;
		String adverInfoDetailName = "t_userappid_adverid_" + iYear + iMonth + iDay;
		
		String oYear = year + "-";
		String oMonth = month + "-";
		String oDay = day + "";
		String yesterdayString = oYear + oMonth + oDay;//昨天 yyyy-MM-d
		//去掉任务备份功能
		//StringBuilder sql1 = new StringBuilder("CREATE TABLE " + adverInfoName + " SELECT * FROM t_channel_adver_info where adver_createtime < '" + yesterdayString + "'");
		StringBuilder sql2 = new StringBuilder("CREATE TABLE " + adverInfoDetailName + " SELECT * FROM t_userappid_adverid");
		//result1 = sqlDao.execute(sql1.toString());
		result2 = sqlDao.execute(sql2.toString());
		
		if(result2 != -1) 
		{
			//DELETE `t_userappid_adverid` FROM `t_userappid_adverid`  LEFT JOIN `t_user_app` ON `t_userappid_adverid`.user_app_id= t_user_app.user_app_id WHERE t_user_app.`user_appp_type` = 1
			//StringBuilder sql3 = new StringBuilder("Delete from t_channel_adver_info where adver_createtime < '" + yesterdayString + "'");
			StringBuilder sql4 = new StringBuilder("DELETE t_userappid_adverid FROM t_userappid_adverid  LEFT JOIN t_user_app ON t_userappid_adverid.user_app_id = t_user_app.user_app_id WHERE t_user_app.user_appp_type = 1");
			//sqlDao.execute(sql3.toString());
			sqlDao.execute(sql4.toString());
		}
	}
	
	//导出idfa
	@SuppressWarnings("rawtypes")
	public List exportExcel(String adverIds)
	{
		StringBuffer sql = new StringBuffer("select B.adver_name, ip, idfa, date_format(complete_time, '%Y-%m-%d %H:%i:%s') as complete_time"
				+ " from t_userappid_adverid as A left  join  `t_channel_adver_info` as B  ON A.adver_id = B.adver_id WHERE A.status = '2'");
		sql.append(" and B.adver_id IN  (");
		sql.append(adverIds);
		sql.append(") ORDER BY B.adver_name DESC, A.complete_time ASC");
		return sqlDao.getAll(sql.toString());
	}
}
