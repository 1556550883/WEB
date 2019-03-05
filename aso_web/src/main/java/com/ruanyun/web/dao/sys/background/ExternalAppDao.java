package com.ruanyun.web.dao.sys.background;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.TExternalChannelTask;

@Repository("externalAppDao")
public class ExternalAppDao extends BaseDaoImpl<TExternalChannelTask> 
{ 
	private static String table_prefix = "t_external_channel";

	public void save(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		String tablename = table_prefix+ adid + key;
			
		StringBuilder sql = new StringBuilder("INSERT INTO  ");
		sql.append(tablename);
		sql.append(" (ip,model,sysver,keywords,idfa,status,channel_key,receive_time) values ");
		sql.append("('"+tExternalChannelTask.getIp()+"','"+tExternalChannelTask.getModel()+"','"+tExternalChannelTask.getSysver()+"','"+tExternalChannelTask.getKeywords()+"','"+tExternalChannelTask.getIdfa()+"','"+tExternalChannelTask.getStatus()+"','"+key+"',NOW())");
		
		sqlDao.execute(sql.toString());
	}
	
	public void saveList(List<TExternalChannelTask> tExternalChannelTasks, String tableName) 
	{
		StringBuilder sql = new StringBuilder("INSERT INTO  ");
		sql.append(tableName);
		sql.append(" (ip,idfa,keywords,status,receive_time,complete_time) values ");
		for(int i = 0; i < tExternalChannelTasks.size(); i++) 
		{
			sql.append("('"+tExternalChannelTasks.get(i).getIp()+"','"+tExternalChannelTasks.get(i).getIdfa()+ "','"+tExternalChannelTasks.get(i).getKeywords()+"','3', NOW(), NOW()),");
		}
		
		String str = sql.toString();
		str = str.substring(0,sql.length() - 1) + ";";
		sqlDao.execute(str);
	}

	
	public int update(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		String tablename = table_prefix+ adid + key;
			
		StringBuilder sql = new StringBuilder("UPDATE "+tablename+" SET");
		sql.append(" status=");
		sql.append("'"+tExternalChannelTask.getStatus()+"'");
		sql.append(" ,ip=");
		sql.append("'"+tExternalChannelTask.getIp()+"'");
		sql.append(" ,keywords=");
		sql.append("'"+tExternalChannelTask.getKeywords()+"'");
		sql.append(" ,model=");
		sql.append("'"+tExternalChannelTask.getModel()+"'");
		sql.append(" ,sysver=");
		sql.append("'"+tExternalChannelTask.getSysver()+"'");
		sql.append(" ,callback=");
		sql.append("'"+tExternalChannelTask.getCallback()+"' ");
		sql.append("WHERE idfa=");
		sql.append(" '"+tExternalChannelTask.getIdfa()+"'");
		return sqlDao.execute(sql.toString());
	}
	
	public int updateStatus(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		String tablename = table_prefix+ adid + key;
			
		StringBuilder sql = new StringBuilder("UPDATE "+tablename+" SET");
		sql.append(" status=");
		sql.append("'"+tExternalChannelTask.getStatus()+"' ");
		sql.append(" ,complete_time = NOW() ");
		sql.append("WHERE idfa=");
		sql.append(" '"+tExternalChannelTask.getIdfa()+"'");
		return sqlDao.execute(sql.toString());
	}
	
	public TExternalChannelTask getExternalTaskInfo(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		String tablename = table_prefix+ adid + key;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(tablename);
		sql.append(" WHERE idfa=");
		sql.append(" '"+tExternalChannelTask.getIdfa()+"'");
		
		return sqlDao.get(TExternalChannelTask.class, sql.toString());
	}
}
