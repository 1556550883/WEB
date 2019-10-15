package com.ruanyun.web.dao.sys.background;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.TPhoneUdidModel;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;

@Repository("tPhoneUdidWithIdfaDao")
public class TPhoneUdidWithIdfaDao extends BaseDaoImpl<TPhoneUdidWithIdfa> {
	
	public List<TPhoneUdidWithIdfa> getUdidByIdfa(String idfa) {
		StringBuffer sql = new StringBuffer("Select * from idfa_udid where idfa= '").append(idfa).append("'");
		return sqlDao.getAll(TPhoneUdidWithIdfa.class,sql.toString());
	}
	
	public void saveList(List<TPhoneUdidModel> dataList, String tableName) {
		StringBuilder sql = new StringBuilder("INSERT INTO  ");
		sql.append(tableName);
		sql.append(" (udid,used) values ");
		for(int i = 0; i < dataList.size(); i++) 
		{
			sql.append("('"+dataList.get(i).getUdid()+"','"+dataList.get(i).getUsed()+ "'),");
		}
		
		String str = sql.toString();
		str = str.substring(0,sql.length() - 1) + ";";
		sqlDao.execute(str);
	}
}
