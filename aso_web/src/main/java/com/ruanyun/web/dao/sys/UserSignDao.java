/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-19
 */
package com.ruanyun.web.dao.sys;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TUserSign;

/**
 *@author feiyang
 *@date 2016-1-19
 */
@Repository("userSignDao")
public class UserSignDao extends BaseDaoImpl<TUserSign>{

	
	/**
	 * 功能描述:当前用户今天签到
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public TUserSign getTodayTUserSign(String userNum){
		StringBuffer sql=new StringBuffer(" SELECT * from t_user_sign WHERE 1=1");
		if (EmptyUtils.isNotEmpty(userNum)) {			
			sql.append(" and user_num='"+userNum+"'");
		}
		sql.append(SQLUtils.popuHqlMinDate("createtime", new Date()));
		sql.append(SQLUtils.popuHqlMaxDate("createtime", new Date()));
		return sqlDao.get(TUserSign.class, sql.toString());
	}
	
	/**
	 * 功能描述: 查询一段时间内的签到数量
	 *
	 * @author yangliu  2016年5月10日 下午8:30:49
	 * 
	 * @param userNum用户编号
	 * @param dayStr 当前日期
	 * @return
	 */
	public int getCountByDay(String userNum,String dayStr){
		String sql="select count(1) from t_user_sign where createtime >DATE_SUB('"+dayStr+" 00:00:00', INTERVAL '7 0:0:0' DAY_SECOND) and user_num='"+userNum+"'";
		return sqlDao.getCount(sql);
	}
}
