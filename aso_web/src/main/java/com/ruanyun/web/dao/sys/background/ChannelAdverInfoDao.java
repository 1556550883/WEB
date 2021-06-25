/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.controller.sys.app.ChannelClassification;
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
		sql.append(SQLUtils.popuHqlMax("phone_type", Integer.parseInt(phoneType)));
		sql.append(SQLUtils.popuHqlMax("ios_version", Integer.parseInt(osversion)));
		//sql.append(SQLUtils.popuHqlMax2("adver_day_start", new Date()));
		sql.append(SQLUtils.popuHqlMin2("adver_day_end", new Date()));
		sql.append(SQLUtils.popuHqlMax("level", level));
		sql.append(" and (is_open = 0 or is_open =" + userType);
		//sql.append(" and adver_status=1");
		sql.append(") ORDER BY adver_status asc,adver_sort desc,adver_createtime desc");
		return sqlDao.queryPage(page, TChannelAdverInfo.class, sql.toString());
	}
	
	/**
	 * 查询广告列表（后台显示）
	 */
	public Page<TChannelAdverInfo> PageSql3(Page<TChannelAdverInfo> page, TChannelAdverInfo t, String queryAdverTime,String endTime)
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE 1=1 ");
		
		if(t.getChannelNum() != null && !t.getChannelNum().isEmpty()) {
			sql.append(" and channel_num='").append(t.getChannelNum()).append("'");
		}
		if(t.getAdverName() != null && !t.getAdverName().isEmpty()) {
			sql.append(" and adver_name='").append(t.getAdverName()).append("'");
		}
		if(t.getAdverAdid() != null && !t.getAdverAdid().isEmpty()) {
			sql.append(" and adver_adid='").append(t.getAdverAdid()).append("'");
		}
		if(t.getAdid() != null && !t.getAdid().isEmpty()) {
			sql.append(" and adid='").append(t.getAdid()).append("'");
		}
		
		if(endTime != null && !endTime.isEmpty()) {
			sql.append(" and adver_createtime<'").append(endTime).append("'");
		}
		
		sql.append(" and adver_day_start>'").append(queryAdverTime);
		sql.append("' ORDER BY adver_createtime desc");
		
		Page<TChannelAdverInfo> page2 = sqlDao.queryPage(page, TChannelAdverInfo.class, sql.toString());
				
		return page2;
	}

	public int getadverStartAndCompleteCount(String adverId, String tableName) 
	{
		StringBuilder sql = new StringBuilder("select count(1) from ").append(tableName)
				.append(" where adver_id=").append(adverId).append(" and status != 1.6 and status != 1.7");
		return sqlDao.getCount(sql.toString());
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
	
	public List<TChannelAdverInfo> queryAdversByIds(String ids) {
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE adver_id in (")
							.append(ids)
							.append(")");
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	//获取今日所有任务
	public List<TChannelAdverInfo> queryAllTodayAdvers()
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE adver_createtime >'")
				.append(ChannelClassification.GetdayDate()).append("'");
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	
	//获取所有正在启动的任务
	public List<TChannelAdverInfo> queryAllStartAdvers()
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE adver_status = 1 and adver_createtime >'")
				.append(ChannelClassification.GetYestdayDate()).append("'");
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	//获取所有未启动的服务
	public List<TChannelAdverInfo> queryAllNotStartAdvers()
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE adver_status < 2 and adver_createtime >'")
				.append(ChannelClassification.GetYestdayDate()).append("'");
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	//获取所有正在启动的任务的种类
	public List<TChannelAdverInfo> queryAllStartAdversGroup() 
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE adver_day_start >'")
				.append(TimeUtil.GetdayDate()).append("' group by channel_num,adid");
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
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set adver_status ="+status+" where adver_id in ("+ids+")");
		//当时启动任务的时候，默认任务最高等级
		
		sqlDao.execute(sql.toString());
	}
	
	public void updateAdverEndTime(String ids) {
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set task_end_time = Now() where adver_id in ("+ids+")");
		//当时启动任务的时候，默认任务最高等级
		
		sqlDao.execute(sql.toString());
	}
	
	public void autoAddAdverCount(TChannelAdverInfo info)
	{
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set adver_status=1,add_task_limit="+info.getAddTaskLimit()+",");
		sql.append("adver_count="+info.getAdverCount()+",");
		sql.append("adver_count_remain="+info.getAdverCountRemain()+"");
		sql.append(" where adver_id="+info.getAdverId()+"");
		sqlDao.execute(sql.toString());
	}
	
	//创建对应的广告表：adid+key
	public int createAdverTable(String adid, String channelID) 
	{
		String tableName = "t_adver_"+ channelID + "_" + adid;
		String sql = "CREATE TABLE "+ tableName +" (" + 
				"  `user_app_id` INT(11) NOT NULL COMMENT '用户ID'," + 
				"  `ip` VARCHAR(20) NULL COMMENT '用户IP'," + 
				"  `idfa` VARCHAR(50) NOT NULL COMMENT '手机广告标识符'," + 
				"  `apple_id` VARCHAR(50) DEFAULT NULL COMMENT 'appleid'," + 
				"  `adid` VARCHAR(100) NOT NULL COMMENT 'adid'," + 
				"  `adver_id`  int(11) NOT NULL COMMENT '广告IDidfa'," + 
				"  `status` VARCHAR(3) NOT NULL COMMENT '状态（1-领取1.5-打开app(快速任务)2-已完成3-已支付）'," +
				"  `receive_time` DATETIME DEFAULT NULL COMMENT '领取时间'," + 
				"  `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间'," +
				"  `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间'," + 
				"  `open_app_time` DATETIME DEFAULT NULL COMMENT '打开app时间'," +
				"  `phone_model` VARCHAR(50) NOT NULL COMMENT '手机版本'," + 
				"  `phone_version` VARCHAR(50) NOT NULL COMMENT '手机版本'," + 
				"  `user_udid` VARCHAR(100)  DEFAULT NULL COMMENT 'udid'," + 
				"  `ip_localtion` VARCHAR(100)  DEFAULT NULL COMMENT '地区'," + 
				//"  PRIMARY KEY (`adver_id`,`idfa`), KEY `ind_t_userappid_adverid_complete_time` (`complete_time`),KEY `ind_t_userappid_adverid_user_app_id` (`user_app_id`),KEY `ind_t_userappid_adverid_receive_time` (`receive_time`)) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='任务idfa';";
				"  PRIMARY KEY (`adver_id`,`idfa`), KEY `ind_t_userappid_adverid_complete_time` (`complete_time`),KEY `ind_t_userappid_adverid_user_app_id` (`user_app_id`),KEY `ind_t_userappid_adverid_receive_time` (`receive_time`))  DEFAULT CHARSET=utf8 COMMENT='任务idfa';";
		
		int result = sqlDao.execute(sql);
		
		return result;
	}
	
	/**
	 * 修改任务状态
	 */
	public void updateAdverStatusAll(Integer status)
	{
		StringBuilder sql;
		//当时启动任务的时候，默认任务最高等级
		sql = new StringBuilder("update t_channel_adver_info set adver_status ="+status+" where adver_day_start < '");
		sql.append(ChannelClassification.GetdayDate());
		sql.append("'");
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
			if (EmptyUtils.isNotEmpty(adverInfo.getAdid()))
				sql.append(" and adid=").append(adverInfo.getAdid());
		}
		
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	public List<TChannelAdverInfo> getadverlists()
	{
		StringBuilder sql = new StringBuilder("select * from t_channel_adver_info WHERE adver_day_start>'202-01-01' and download_count != 0 group by adver_adid");

		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	public int updateAdverDownloadCountAdd1(TChannelAdverInfo adverInfo)
	{
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set download_count=download_count+1 WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		
		return sqlDao.update(sql.toString());
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
	
	public int updateAdverCountAndRemain(String tablename, TChannelAdverInfo adverInfo, int addTask)
	{
		StringBuilder sql;
		//代表没有自动增加任务数量，不需要每次都更新任务数量
		if(addTask == 0) 
		{
			sql = new StringBuilder("update t_channel_adver_info a set add_task_limit= "+adverInfo.getAddTaskLimit()+",adver_count_remain="+adverInfo.getAdverCount()+"-(select count(1) from "+tablename+" b where a.adver_id=b.adver_id and b.status<>'1.6') WHERE 1=1 ");
		}else 
		{
			sql = new StringBuilder("update t_channel_adver_info a set add_task_limit= "+adverInfo.getAddTaskLimit()+",adver_count = "+adverInfo.getAdverCount()+",adver_count_remain="+adverInfo.getAdverCount()+"-(select count(1) from "+tablename+" b where a.adver_id=b.adver_id and b.status<>'1.6') WHERE 1=1 ");
		}
		
		if(EmptyUtils.isNotEmpty(adverInfo))
		{
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		return sqlDao.update(sql.toString());
	}
	
	public int updateAdverDownloadCount(TChannelAdverInfo info) 
	{
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set download_count=? WHERE ");
		if(EmptyUtils.isNotEmpty(info))
		{
			if (EmptyUtils.isNotEmpty(info.getAdverId()))
				sql.append(" and adver_id="+info.getAdverId());
		}
		
		Object[] params = new Object[1];
		params[0] = info.getDownloadCount();
		
		return sqlDao.update(params, sql.toString());
	}
	
	public static void main(String[] args)
	{
		System.err.println(TimeUtil.GetYestdayDate());
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
		//String adverInfoName = "t_channel_adver_info_" + iYear + iMonth + iDay;
		String adverInfoDetailName = "t_userappid_adverid_" + iYear + iMonth + iDay;
		
		String oYear = year + "-";
		String oMonth = month + "-";
		String oDay = day + "";
		String yesterdayString = oYear + oMonth + oDay;//昨天 yyyy-MM-d
		//去掉任务备份功能
		//StringBuilder sql1 = new StringBuilder("CREATE TABLE " + adverInfoName + " SELECT * FROM t_channel_adver_info where adver_createtime < '" + yesterdayString + "'");
		StringBuilder sql2 = new StringBuilder("CREATE TABLE " + adverInfoDetailName + " SELECT * FROM t_userappid_adverid where receive_time < '" + TimeUtil.GetYestdayDate() + "'");
		//result1 = sqlDao.execute(sql1.toString());
		result2 = sqlDao.execute(sql2.toString());
		
		if(result2 != -1) 
		{
			StringBuilder sql4 = new StringBuilder("DELETE t_userappid_adverid FROM t_userappid_adverid  LEFT JOIN t_user_app ON t_userappid_adverid.user_app_id = t_user_app.user_app_id WHERE t_user_app.user_appp_type = 1 and t_userappid_adverid.receive_time < '" + TimeUtil.GetYestdayDate() + "'");
			sqlDao.execute(sql4.toString());
		}
	}
	
	//导出idfa
	@SuppressWarnings("rawtypes")
	public List exportExcel(String tablename,String adverid)
	{
		StringBuffer sql = new StringBuffer("select B.adver_name, ip,ip_localtion, idfa, date_format(complete_time, '%Y-%m-%d %H:%i:%s') as complete_time"
				+ " from "+tablename+" as A left  join  `t_channel_adver_info` as B  ON A.adver_id = B.adver_id WHERE A.status = '2'");
		sql.append(" and B.adver_id='");
		sql.append(adverid);
		sql.append("' ORDER BY B.adver_name DESC, A.complete_time ASC");
		return sqlDao.getAll(sql.toString());
	}
	
	
	//导出idfa
	@SuppressWarnings("rawtypes")
	public List exportExcel1(String tablename,String date1,String date2)
	{
		StringBuffer sql = new StringBuffer("select idfa from "+tablename+" where receive_time>'"+date1+"' AND receive_time<'"+date2+"'");
		return sqlDao.getAll(sql.toString());
	}
		
	//导出idfa
	@SuppressWarnings("rawtypes")
	public List exportAdverInfoExcel(String channelNum, String day)
	{
		StringBuffer sql = new StringBuffer("select adver_name, adver_count, download_count "
				+ " from t_channel_adver_info WHERE channel_num =");
		sql.append(channelNum);
		sql.append(" and adver_createtime >'");
		if(day.equals("0")) 
		{
			sql.append(ChannelClassification.GetYestdayDate());
		}
		else
		{
			sql.append(ChannelClassification.GetdayDate()); 
		}
		sql.append("' ORDER BY adver_createtime desc");
		return sqlDao.getAll(sql.toString());
	}
	
	
	@SuppressWarnings("rawtypes")
	public List exportMonthAdver(String channelNum, String month)
	{
		StringBuffer sql = new StringBuffer("select adid, adver_name, adver_count, download_count,adver_createtime "
				+ " from t_channel_adver_info WHERE channel_num =");
		sql.append(channelNum);
		sql.append(" and adver_createtime >='");
		//0是上个月
		if(month.equals("0")) 
		{
			sql.append(ChannelClassification.GetYestMonthDate());
			sql.append("' and adver_createtime <'");
			sql.append(ChannelClassification.GetMonthDate());
		}
		else
		{
			sql.append(ChannelClassification.GetMonthDate()); 
		}
		sql.append("' ORDER BY adver_createtime desc");
		return sqlDao.getAll(sql.toString());
	}
	
	public int releaseIp(String channelNum) {
		StringBuilder sql = new StringBuilder("UPDATE  `t_userappid_adverid` AS A JOIN `t_channel_adver_info` AS B ON A.`adver_id` = B.`adver_id` SET ip = CONCAT(ip,\"ip\") WHERE B.channel_num = ")
				.append(channelNum).append(" and A.receive_time < '").append(ChannelClassification.GetdayDate()).append("'");
		
		return sqlDao.update(sql.toString());
	}
}
