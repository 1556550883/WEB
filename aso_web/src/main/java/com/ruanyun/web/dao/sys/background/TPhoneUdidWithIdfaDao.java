package com.ruanyun.web.dao.sys.background;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.TPhoneUdidModel;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;

@Repository("tPhoneUdidWithIdfaDao")
public class TPhoneUdidWithIdfaDao extends BaseDaoImpl<TPhoneUdidWithIdfa> {
	
	public TPhoneUdidWithIdfa getUdidByIdfa(String idfa, String tableName)
	{
		StringBuffer sql = new StringBuffer("Select * from "+tableName+" where idfa= '").append(idfa).append("'");
		return sqlDao.get(TPhoneUdidWithIdfa.class,sql.toString());
	}
//	
//	public List<TPhoneUdidWithIdfa> getUdidByIdfaForXiaoshou(String idfa) {
//		StringBuffer sql = new StringBuffer("Select * from idfa_udid_xiaoshou where idfa= '").append(idfa).append("'");
//		return sqlDao.getAll(TPhoneUdidWithIdfa.class,sql.toString());
//	}
//	
	
	public int getUdidSumedNum(String udidType,String tableName, String time)
	{
		StringBuffer sql = new StringBuffer("Select count(1) from "+tableName+" where phone_model= '").append(udidType).append("'");
		sql.append(" and create_time >'").append(time).append("'");
		return sqlDao.getCount(sql.toString());
	}
	
	public void savePhoneInfo(TPhoneUdidWithIdfa model, String tableName) {
		StringBuilder sql = new StringBuilder("INSERT INTO "+tableName+" ");
		sql.append(" (idfa,udid,phone_model,phone_version,create_time) values ");
		sql.append("('"+model.getIdfa()+"','"+model.getUdid()+ "', '"+model.getPhoneModel()+"', '"+model.getPhoneVersion()+"' , '"+model.getCreateTime()+"')");
		sqlDao.execute(sql.toString());
	}
	
	public List<TPhoneUdidModel> getTPhoneUdidModel(String tableName) {
		StringBuffer sql = new StringBuffer("Select * from "+tableName + "");
		return sqlDao.getAll(TPhoneUdidModel.class,sql.toString());
	}
	
	public void saveList(List<TPhoneUdidModel> dataList, String tableName) {
		StringBuilder sql = new StringBuilder("INSERT INTO  ");
		sql.append(tableName);
		sql.append(" (udid,used,importTime) values ");
		for(int i = 0; i < dataList.size(); i++) 
		{
			sql.append("('"+dataList.get(i).getUdid()+"','"+dataList.get(i).getUsed()+ "','"+ dataList.get(i).getImportTime()+"'),");
		}
		
		String str = sql.toString();
		str = str.substring(0,sql.length() - 1) + ";";
		sqlDao.execute(str);
	}
	
	public void deleteModel(TPhoneUdidModel model, String tableName) {
		StringBuilder sql = new StringBuilder("Delete from "+tableName+" where udid = ");
		sql.append("'"+ model.getUdid()+"'");
		sqlDao.execute(sql.toString());
	}
	
	
	@SuppressWarnings("rawtypes")
	public List activated(String type) {
		StringBuffer sql = new StringBuffer("select udid from ");
		sql.append(type);
		sql.append(" where used = 0");
		return sqlDao.getAll(sql.toString());
	}
	
	
	public int updateUdidStatus(String udid,String tableName,int status)
	{
		StringBuilder sql = new StringBuilder("update ").append(tableName).append(" set used="+status+" WHERE udid= '").append(udid).append("'");
		return sqlDao.update(sql.toString());
	}
}
