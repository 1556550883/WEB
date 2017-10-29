/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TUserApp;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Repository("userAppDao")
public class UserAppDao extends BaseDaoImpl<TUserApp>{
	
	@Override
	protected String queryPageSql(TUserApp t, Map<String, Object> params) {
		StringBuffer sql=new StringBuffer(" from TUserApp where 1=1 ");
		if(t != null){
			sql.append(SQLUtils.popuHqlLike("userNum", t.getUserNum()));
			sql.append(SQLUtils.popuHqlLike("phoneNum", t.getPhoneNum()));
			sql.append(SQLUtils.popuHqlLike("userNick", t.getUserNick()));
			sql.append(SQLUtils.popuHqlEq("userApppType", t.getUserApppType()));
			sql.append(SQLUtils.popuHqlLike("flag2", t.getFlag2()));
			sql.append(SQLUtils.popuHqlLike("loginName", t.getLoginName()));
		}
		return sql.toString();
	}
	
	
	/**
	 * 
	 * 功能描述:根据NUM获取用户
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-14
	 */
	public TUserApp getUserByUserNum(String userNum)
	{
		StringBuffer sql=new StringBuffer(" SELECT * from t_user_app WHERE user_num ='"+userNum+"'");
		return sqlDao.get(TUserApp.class, sql.toString());
	}
	
	
	public TUserApp getUserByUserAppid(String userAppid)
	{
		StringBuffer sql=new StringBuffer(" SELECT * from t_user_app WHERE user_app_id ='" + userAppid + "'");
		return sqlDao.get(TUserApp.class, sql.toString());
	}
	/**
	 * 
	 * 功能描述:根据序列号获取用户
	 * @param phoneSerialNumber
	 * @return
	 *@author feiyang
	 *@date 2016-1-14
	 */
	public TUserApp getUserBySerialNum(String phoneSerialNumber){
		StringBuffer sql=new StringBuffer(" SELECT * from t_user_app WHERE phone_serial_number='"+phoneSerialNumber+"'");
		return sqlDao.get(TUserApp.class, sql.toString());
	}
	
	
	
	/**
	 * 
	 * 功能描述:获取手机的所有用户数量
	 * @return
	 *@author feiyang
	 *@date 2016-1-4
	 */
	public int getUserNum(Integer type ,Date createTime){
		StringBuffer sql = new StringBuffer(" SELECT COUNT(*) from t_user_app where 1=1");
		if(type==1){
			if(EmptyUtils.isEmpty(createTime)){
				sql.append(SQLUtils.popuHqlMinDate("create_date", new Date()));
				sql.append(SQLUtils.popuHqlMaxDate("create_date", new Date()));
			}else {				
				sql.append(SQLUtils.popuHqlMinDate("create_date", createTime));
				sql.append(SQLUtils.popuHqlMaxDate("create_date", createTime));
			}
	
		}
		return sqlDao.getCount(sql.toString());
	}
	
}
