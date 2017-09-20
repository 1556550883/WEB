/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-6
 */
package com.ruanyun.web.dao.sys.background;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TUserappidAdverid;

@Repository("channelInfoDao")
public class ChannelInfoDao extends BaseDaoImpl<TChannelInfo> 
{
	/**
	 * 
	 * 功能描述:
	 * @param t
	 * @param params
	 * @return
	 * channelType 1/2/3 自由渠道/CP专区/快赚钱
	 *@author feiyang
	 *@date 2016-1-13
	 */
	@Override
	protected String queryPageSql(TChannelInfo t, Map<String, Object> params) 
	{
		StringBuffer sql=new StringBuffer(" from TChannelInfo where 1=1 ");
		if(EmptyUtils.isNotEmpty(t.getChannelType()))
		{
			sql.append(" and channelType='" + t.getChannelType()+"'");
		}
		if(EmptyUtils.isNotEmpty(t.getChannelName()))
		{
			sql.append(" and channelName='" + t.getChannelName()+"'");
		}
		
		sql.append(" order by createDate desc");
		return sql.toString();
	}
	
	/**
	 * 
	 * 功能描述:
	 * @param page
	 * @param info
	 *   channelType 1/2/3 自由渠道/CP专区/快赚钱
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	public Page<TChannelInfo> PageSql(Page<TChannelInfo> page,TChannelInfo info)
	{
		StringBuffer sql = new StringBuffer(" SELECT * from t_channel_info WHERE channel_type =" + info.getChannelType());
		if (EmptyUtils.isNotEmpty(info.getSystemType()))
		{
			sql.append(" and system_type='" + info.getSystemType() + "'");
		}
		sql.append(" ORDER BY create_date desc");
		
		return sqlDao.queryPage(page, TChannelInfo.class, sql.toString());
	}
	
	/**
	 * 
	 * 功能描述:更具类别获取
	 * @param channelType  渠道类别
	 * @param systemType   系统类别
	 * @return
	 *@author feiyang
	 *@date 2016-1-28
	 */
	public TChannelInfo getChannelInfoBySystemType(String channelType,String systemType,String userNum)
	{
		StringBuffer sql = new StringBuffer(" SELECT * from t_channel_info WHERE channel_type =" + channelType);
		if (EmptyUtils.isNotEmpty(systemType)) 
		{
			sql.append(" and system_type='" + systemType + "'");
		}
		sql.append(" ORDER BY create_date desc");
		
		return sqlDao.get(TChannelInfo.class, sql.toString());	
	}
	
	/**
	 * idfa统计
	 */
	public Page<TUserappidAdverid> queryIdfaStatistics(Page<TUserappidAdverid> page, String channelNum, String completeTime) 
			throws ParseException 
	{
		List<TUserappidAdverid> result = new ArrayList<TUserappidAdverid>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		int index = 0;
		int numPerPage = Integer.MAX_VALUE;
		int totalCount = 0;
		
		StringBuilder sql = new StringBuilder("SELECT adver_id,adver_name,adid")
		.append(" FROM t_channel_adver_info")
		.append(" WHERE channel_num='")
		.append(channelNum)
		.append("'")
		.append(" AND adver_price>0.02")
		.append(" AND exists(select 1 from t_userappid_adverid where adver_id=t_channel_adver_info.adver_id AND status='2'")
		.append(" AND complete_time between '")
		.append(sdf3.format(sdf2.parse(completeTime)))
		.append("' and '")
		.append(sdf3.format(sdf2.parse(completeTime)))
		.append(" 23:59:59')")
		.append(" ORDER BY adid ASC,adver_id ASC");
		List<TChannelAdverInfo> adverList = sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
		if(adverList != null)
		{
			for(TChannelAdverInfo adver:adverList)
			{
				sql = new StringBuilder("SELECT *")
					.append(" FROM t_userappid_adverid")
					.append(" WHERE adver_id=").append(adver.getAdverId())
					.append(" AND status='2'")
					.append(" AND complete_time between '").append(sdf3.format(sdf2.parse(completeTime))).append("' and '").append(sdf3.format(sdf2.parse(completeTime))).append(" 23:59:59'")
					.append(" ORDER BY complete_time ASC");
				
				List<TUserappidAdverid> taskList = sqlDao.getAll(TUserappidAdverid.class, sql.toString());
				
				if(taskList != null)
				{
					totalCount += taskList.size();
					for(TUserappidAdverid task:taskList)
					{
						index++;
						if(index <= (page.getPageNum()-1)*numPerPage)
						{
							continue;
						}
						else if(index > (page.getPageNum()-1)*numPerPage
								&& index <= page.getPageNum()*numPerPage)
						{
							task.setOs("");
							task.setAdverName(adver.getAdverName());
							task.setCompleteTimeStr(sdf.format(task.getCompleteTime()));
							result.add(task);
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		
		page.setResult(result);
		page.setNumPerPage(numPerPage);
		page.setTotalCount(totalCount);
		page.setTotalPage(totalCount/numPerPage + totalCount%numPerPage>0?1:0);
		return page;
	}
	
	/**
	 * 员工idfa统计
	 */
	public Page<TUserappidAdverid> queryEmployeeIdfaStatistics(Page<TUserappidAdverid> page, Integer userAppId, String completeTime) 
			throws ParseException 
	{
		List<TUserappidAdverid> result = new ArrayList<TUserappidAdverid>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		int index = 0;
		int numPerPage = Integer.MAX_VALUE;
		int totalCount = 0;
		
		StringBuilder sql = new StringBuilder("SELECT adver_id,adver_name,adver_price,adid")
		.append(" FROM t_channel_adver_info")
		.append(" WHERE adver_price>0.02")
		.append(" AND exists(select 1 from t_userappid_adverid where adver_id=t_channel_adver_info.adver_id")
		.append(" AND user_app_id=").append(userAppId)
		.append(" AND status>='2'")
		.append(" AND complete_time between '").append(sdf3.format(sdf2.parse(completeTime))).append("' and '").append(sdf3.format(sdf2.parse(completeTime))).append(" 23:59:59')")
		.append(" ORDER BY adid ASC,adver_id ASC");
		List<TChannelAdverInfo> adverList = sqlDao.getAll(TChannelAdverInfo.class, sql.toString());
		if(adverList != null)
		{
			for(TChannelAdverInfo adver:adverList)
			{
				sql = new StringBuilder("SELECT *")
					.append(" FROM t_userappid_adverid")
					.append(" WHERE adver_id=").append(adver.getAdverId())
					.append(" AND user_app_id=").append(userAppId)
					.append(" AND status>='2'")
					.append(" AND complete_time between '").append(sdf3.format(sdf2.parse(completeTime))).append("' and '").append(sdf3.format(sdf2.parse(completeTime))).append(" 23:59:59'")
					.append(" ORDER BY complete_time ASC");
				List<TUserappidAdverid> taskList = sqlDao.getAll(TUserappidAdverid.class, sql.toString());
				
				if(taskList != null)
				{
					totalCount += taskList.size();
					for(TUserappidAdverid task:taskList)
					{
						index++;
						if(index <= (page.getPageNum()-1)*numPerPage)
						{
							continue;
						}
						else if(index > (page.getPageNum()-1)*numPerPage
								&& index <= page.getPageNum()*numPerPage)
						{
							task.setStatusDescription("2".equals(task.getStatus()) ? "已完成":("3".equals(task.getStatus()) ? "已支付":""));
							task.setAdverName(adver.getAdverName());
							task.setAdverPrice(adver.getAdverPrice());
							task.setCompleteTimeStr(sdf.format(task.getCompleteTime()));
							result.add(task);
						}
						else
						{
							break;
						}
					}
				}
			}
		}
		
		page.setResult(result);
		page.setNumPerPage(numPerPage);
		page.setTotalCount(totalCount);
		page.setTotalPage(totalCount/numPerPage + totalCount%numPerPage>0?1:0);
		return page;
	}
	
}
