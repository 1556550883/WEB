package com.ruanyun.web.dao.sys;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.sys.TOperationLog;

/**
 * 操作日志
 * @author yangliu
 *
 */
@Repository("operationLogDao")
public class OperationLogDao extends BaseDaoImpl<TOperationLog>{

	public TOperationLog addOperationLog(TOperationLog log){
		Session session=openSession();
		Transaction tra=session.beginTransaction();
		session.save(log);
		tra.commit();
		closeSession(session);
		return log;
	}
/**
 * 分页查询
 */
	@Override
	public String queryPageSql(TOperationLog operationLog, Map<String, Object> params) {
		StringBuffer sql = new StringBuffer("from TOperationLog where 1=1 ");
		if(operationLog != null){
			sql.append(SQLUtils.popuHqlLike("operationUserName", operationLog.getOperationUserName()));
			sql.append(SQLUtils.popuHqlMin("createdate",operationLog.getCreatedate(),params));
			sql.append(SQLUtils.popuHqlMax("createdate",operationLog.getEndDate(),params));
		}
		sql.append(" order by operationId desc ");
		return sql.toString();
	}
	
	public List<TOperationLog> getList(TOperationLog operationLog, Map<String, Object> params){
		StringBuffer sql = new StringBuffer("from TOperationLog where 1=1 ");
		if(operationLog != null){
			sql.append(SQLUtils.popuHqlLike("operationUserName", operationLog.getOperationUserName()));
			sql.append(SQLUtils.popuHqlMin("createdate","createdate",operationLog.getCreatedate(),params));
			//sql.append(SQLUtils.popuHqlMax("createdate","createdateEnd",operationLog.getCreatedateEnd(),params));
		}
		return hqlDao.getAll(sql.toString(), params);
	}
}
