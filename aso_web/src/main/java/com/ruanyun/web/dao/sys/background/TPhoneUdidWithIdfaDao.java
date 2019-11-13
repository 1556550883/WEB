package com.ruanyun.web.dao.sys.background;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.TPhoneUdidModel;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;

@Repository("tPhoneUdidWithIdfaDao")
public class TPhoneUdidWithIdfaDao extends BaseDaoImpl<TPhoneUdidWithIdfa> {
	
	public List<TPhoneUdidWithIdfa> getUdidByIdfa(String idfa, String tableName) {
		StringBuffer sql = new StringBuffer("Select * from "+tableName+" where idfa= '").append(idfa).append("'");
		return sqlDao.getAll(TPhoneUdidWithIdfa.class,sql.toString());
	}
//	
//	public List<TPhoneUdidWithIdfa> getUdidByIdfaForXiaoshou(String idfa) {
//		StringBuffer sql = new StringBuffer("Select * from idfa_udid_xiaoshou where idfa= '").append(idfa).append("'");
//		return sqlDao.getAll(TPhoneUdidWithIdfa.class,sql.toString());
//	}
//	
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
}
