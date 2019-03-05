package com.ruanyun.web.dao.sys.background;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TExternalChannelAdverInfo;
import com.ruanyun.web.model.TExternalChannelAdverTaskInfo;
import com.ruanyun.web.model.TExternalChannelInfo;
import com.ruanyun.web.model.TExternalChannelTask;

@Repository("externalChannelAdverInfoDao")
public class ExternalChannelAdverInfoDao extends BaseDaoImpl<TExternalChannelAdverInfo>  
{
	/**
	 * 查询广告列表（后台显示）
	 */
	public Page<TExternalChannelAdverInfo> PageSql3(Page<TExternalChannelAdverInfo> page, String channelNum)
	{
		StringBuilder sql = new StringBuilder("SELECT * from t_external_channel_adver_info WHERE 1=1 ");
		sql.append(" and external_channel_num='").append(channelNum);
		sql.append("' and external_adver_status in('0','1','2')");
		sql.append(" ORDER BY DATE_FORMAT(external_adver_create_time,'%Y%m%d') asc,adid asc");
		Page<TExternalChannelAdverInfo> page2 = sqlDao.queryPage(page, TExternalChannelAdverInfo.class, sql.toString());
		
		if(page2 != null && page2.getResult() != null)
		{
//			for(TExternalChannelAdverInfo item:page2.getResult())
//			{
//				sql = new StringBuilder("select count(1) from t_userappid_adverid where adver_id=").append(item.getAdverId()).append(" and status='2'");
//				item.setAdverCountComplete(sqlDao.getCount(sql.toString()));
//			}
		}
		
		return page2;
	}
	
	//todo
	public TExternalChannelAdverInfo getTExternalChannelAdverInfo(String cpChannelKey, String adid) {
		StringBuilder sql = new StringBuilder("SELECT B.* FROM `t_external_channel_info` AS A LEFT JOIN `t_external_channel_adver_info` AS  B ON A.`external_channel_num` = B.`external_channel_num` WHERE 1=1 ");
		sql.append(" and A.external_channel_key='").append(cpChannelKey);
		sql.append("' and B.adid='").append(adid);
		sql.append("'");
		return sqlDao.get(TExternalChannelAdverInfo.class, sql.toString());
	}
	
	//创建对应的广告表：adid+key
	public int createAdverTable(String adid, String key) 
	{
		String tableName = "t_external_channel" + adid + key;
		String sql = "CREATE TABLE "+ tableName +" (" + 
				"  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID'," + 
				"  `ip` VARCHAR(20) NULL COMMENT '用户IP'," + 
				"  `idfa` VARCHAR(50) NOT NULL COMMENT '手机广告标识符'," + 
				"  `keywords` VARCHAR(100) NULL COMMENT '关键词'," + 
				"  `callback` VARCHAR(200) NULL COMMENT '回调地址'," + 
				"  `adver_id` INT(11) NULL COMMENT '广告ID'," + 
				"  `status` VARCHAR(3) NOT NULL COMMENT '状态（0-排重-1-点击-3-激活）'," + 
				"  `channel_key` VARCHAR(100) NULL COMMENT '关键词'," + 
				"  `model` VARCHAR(50) NOT NULL COMMENT '手机版本'," + 
				"  `sysver` VARCHAR(50) NOT NULL COMMENT '手机系统'," + 
				"  `receive_time` DATETIME DEFAULT NULL COMMENT '领取时间'," + 
				"  `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间'," + 
				"   CONSTRAINT idfa_no_repeat UNIQUE (idfa),"+
				"  PRIMARY KEY (`id`)) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='外放用户任务';";
		
		int result = sqlDao.execute(sql);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Page<TExternalChannelAdverTaskInfo> completeListInfo(Page<TExternalChannelAdverTaskInfo> page, TExternalChannelInfo externalChannelInfo, TExternalChannelAdverInfo t)
	{
		String tableName = "t_external_channel" + t.getAdid() + externalChannelInfo.getExternalChannelKey();
		String sql = "SELECT keywords, COUNT(keywords) AS num FROM " + tableName + " WHERE STATUS = 3 and channel_key = '"+externalChannelInfo.getExternalChannelKey()+"' GROUP BY keywords";
		Query query  = sqlDao.createQuery(sql);
		List<Object[]> result = query.list();
		List<TExternalChannelAdverTaskInfo> list = new ArrayList<TExternalChannelAdverTaskInfo>();
		for(Object[] objs : result)
		{
            TExternalChannelAdverTaskInfo info = new TExternalChannelAdverTaskInfo();
            info.setAdverId(t.getExternalAdverId());
            info.setKeywords(objs[0] + "");
            info.setNum(objs[1] + "");
            
            list.add(info);
	    }
		
		page.setResult(list);
		
		return page;
	}
	
	//查询任务详情
	@SuppressWarnings("unchecked")
	public Page<TExternalChannelTask> adverCompleteInfo(Page<TExternalChannelTask> page, TExternalChannelInfo externalChannelInfo, TExternalChannelAdverInfo t)
	{
		String tableName = "t_external_channel" + t.getAdid() + externalChannelInfo.getExternalChannelKey();
		String sql = "SELECT * FROM " + tableName + " WHERE status = 3 and channel_key = '"+externalChannelInfo.getExternalChannelKey()+"' and receive_time >= DATE_SUB(CURDATE(),INTERVAL 1 DAY)";
		Query query  = sqlDao.createQuery(sql);
		List<Object[]> result = query.list();
		List<TExternalChannelTask> list = new ArrayList<TExternalChannelTask>();
		java.text.SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(Object[] objs : result)
		{
			TExternalChannelTask info = new TExternalChannelTask();
            info.setAdverId(t.getExternalAdverId() + "");
            info.setIp(objs[1] + "");
            info.setIdfa(objs[2] + "");
            info.setKeywords(objs[3] + "");
            
            try 
            {
				info.setReceiveTime(formatter.parse(objs[8] + ""));
				info.setCompleteTime(formatter.parse(objs[9] + ""));
			} 
            catch (Exception e)
            {
				e.printStackTrace();
			}
            
            list.add(info);
	    }
		
		page.setResult(list);
		return page;
	}
}
