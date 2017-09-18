package com.ruanyun.web.dao.sys;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TLoginLog;
@Repository("loginLogDao")
public class LoginLogDao extends BaseDaoImpl<TLoginLog> {

	public TLoginLog addLoginLog(TLoginLog log){
		Session session=openSession();
		Transaction tra=session.beginTransaction();
		session.save(log);
		tra.commit();
		closeSession(session);
		return log;
	}

	/**
	 * 功能描述:登录日志列表
	 *
	 * @author houkun  2013-10-25 下午03:43:16
	 * 
	 * @param loginlog
	 * @param params
	 * @return sql
	 */
	@Override
	public String queryPageSql(TLoginLog loginlog, Map<String, Object> params) {
		StringBuffer sql = new StringBuffer("from TLoginLog where 1 = 1 ");
		if(loginlog != null){
			sql.append(SQLUtils.popuHqlLike("loginName",loginlog.getLoginName(),params));
			sql.append(SQLUtils.popuHqlMin("loginTime", loginlog.getLoginTime(), params));
			sql.append(SQLUtils.popuHqlMax("loginTime",  loginlog.getEndDate(), params));
		}
		sql.append(" order by logId desc");
		return sql.toString();
	}
	
	/**
	 * 功能描述:查询登录日志列表(筛选条件)
	 *
	 * @author houkun  2013-11-2 上午11:06:03
	 * 
	 * @param loginlog
	 * @param params
	 * @return
	 */
	public List<TLoginLog> getList(TLoginLog loginlog,Map<String,Object> params){
		StringBuffer sql = new StringBuffer("from TLoginLog where 1=1 ");
		if(loginlog != null){
			sql.append(SQLUtils.popuHqlLike("loginName",loginlog.getLoginName(),params));
			sql.append(SQLUtils.popuHqlMin("loginTime", "loginTime", loginlog.getLoginTime(), params));
			//sql.append(SQLUtils.popuHqlMax("loginTime", "loginTimeEnd", loginlog.getLoginTimeEnd(), params));
		}
		sql.append(" order by logId desc ");
		return hqlDao.getAll(sql.toString());
	}
	/**
	 * 功能描述:通过用户id查询用户登录情况
	 *
	 * @author L H T  2013-12-14 下午01:08:33
	 * 
	 * @param userId
	 * @return
	 */
	public List<TLoginLog> getLoginLogByUserId(Integer userId){
		StringBuffer hql = new StringBuffer("from TLoginLog  where logUserId=? order by logId desc limit 2");
		return hqlDao.getAll(hql.toString(), userId);
	}
	/**
	 * 功能描述:通过用户id查询用户登录的总次数
	 *
	 * @author L H T  2013-12-14 下午01:45:15
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getCountByUserId(Integer userId){
		StringBuffer sql=new StringBuffer("select count(*) from t_login_log where log_user_id=?");
		return sqlDao.getCount(sql.toString(), userId);
		
	}
	
	
}
