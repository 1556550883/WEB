/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TChannelAdverInfo;

/**
 *@author feiyang
 *@date 2016-1-7
 */
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
			
			if (EmptyUtils.isNotEmpty(info.getAdverType())) 	
			{
				sql.append(" AND adver_type ='"+info.getAdverType()+"'");
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
	public Page<TChannelAdverInfo> PageSql2(Page<TChannelAdverInfo> page, String channelType, String systemType, String phoneType)
	{
		StringBuffer sql = new StringBuffer("SELECT * from t_channel_adver_info WHERE 1=1 ");
		sql.append(" and channel_num in (select b.channel_num from t_channel_info b where b.channel_type='").append(channelType).append("' and b.system_type='").append(systemType).append("')");
		sql.append(" and phone_type in('"+phoneType+"','无要求')");
		sql.append(SQLUtils.popuHqlMax2("adver_day_start", new Date()));
		sql.append(SQLUtils.popuHqlMin2("adver_day_end", new Date()));
		sql.append(" and adver_status=1");
		sql.append(" ORDER BY adver_createtime desc");
		return sqlDao.queryPage(page, TChannelAdverInfo.class, sql.toString());
	}
	
	/**
	 * 查询广告列表（后台显示）
	 */
	public Page<TChannelAdverInfo> PageSql3(Page<TChannelAdverInfo> page, String channelNum)
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_channel_adver_info WHERE 1=1 ");
		sql.append(" and channel_num='").append(channelNum);
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
	public void updateAdverStatus(Integer status,String ids){
		StringBuilder sql=new StringBuilder("update t_channel_adver_info set adver_status ="+status+" where adver_id in ("+ids+")");
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
	public List<TChannelAdverInfo> getByCondition(TChannelAdverInfo adverInfo){
		StringBuilder sql = new StringBuilder("select * from t_channel_adver_info WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(adverInfo)){
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverStatusEnd()))
				sql.append(" and adver_status<=").append(adverInfo.getAdverStatusEnd());
		}
		return sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
	}
	
	public int updateAdverCountRemainMinus1(TChannelAdverInfo adverInfo){
		StringBuilder sql = new StringBuilder("update t_channel_adver_info set adver_count_remain=adver_count_remain-1 WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(adverInfo)){
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		return sqlDao.update(sql.toString());
	}
	
	public int updateAdverCountRemain(TChannelAdverInfo adverInfo){
		StringBuilder sql = new StringBuilder("update t_channel_adver_info a set adver_count_remain=adver_count-(select count(1) from t_userappid_adverid b where a.adver_id=b.adver_id and b.status<>'1.6') WHERE 1=1 ");
		if(EmptyUtils.isNotEmpty(adverInfo)){
			if (EmptyUtils.isNotEmpty(adverInfo.getAdverId()))
				sql.append(" and adver_id="+adverInfo.getAdverId());
		}
		return sqlDao.update(sql.toString());
	}
	
}
