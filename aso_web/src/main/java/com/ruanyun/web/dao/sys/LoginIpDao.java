package com.ruanyun.web.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TLoginIp;

@Repository("loginIpDao")
public class LoginIpDao extends BaseDaoImpl<TLoginIp> {

	public List<TLoginIp> getLoginIp(String ip,String loginTime) {
		String sql = "select * from t_login_ip where log_ip = '"+ip+"' and login_time like '"+loginTime+"%'";
		return sqlDao.getAll(sql);
	}

	@Override
	protected String queryPageSql(TLoginIp t, Map<String, Object> params) {
		StringBuffer hql =new StringBuffer("from TLoginIp where 1=1");
		if(t!=null){
			hql.append(SQLUtils.popuHqlLike("logIp", t.getLogIp()));
		}
		hql.append(" order by loginTime desc");
		return hql.toString();
	}
	/**
	 * 功能描述:删除所有记录
	 * @author wsp  2017-1-20 下午01:48:38
	 * @return
	 */
	public int delete(){
		String sql = "delete from t_login_ip";
		return sqlDao.execute(sql);
	}
	
	
}
