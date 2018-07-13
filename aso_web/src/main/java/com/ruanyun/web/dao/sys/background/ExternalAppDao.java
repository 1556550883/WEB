package com.ruanyun.web.dao.sys.background;

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
		sql.append(" (idfa,status,receive_time) values ");
		sql.append("('"+tExternalChannelTask.getIdfa()+"','"+tExternalChannelTask.getStatus()+"', NOW())");
		
		sqlDao.execute(sql.toString());
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
	
	public TExternalChannelTask getExternalAdverInfo(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		String tablename = table_prefix+ adid + key;
		
		StringBuilder sql = new StringBuilder("SELECT * FROM ");
		sql.append(tablename);
		sql.append(" WHERE idfa=");
		sql.append(" '"+tExternalChannelTask.getIdfa()+"'");
		
		return sqlDao.get(TExternalChannelTask.class, sql.toString());
	}
}
