/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.dao.sys;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.model.UserAppModel;

/**
 *@author feiyang
 *@date 2016-1-11
 */
@Repository
public class UserLoginDao extends BaseDaoImpl<TUserLogin>
{
	/**
	 * 
	 * 功能描述:根据用户名获取用户
	 * @param loginName
	 * @return
	 *@author feiyang
	 *@date 2016-1-11
	 */
	public TUserLogin getUserByLoginName(String loginName,Integer loginType){
		StringBuffer sql=new StringBuffer(" SELECT * FROM t_user_login WHERE login_name='"+loginName+"' and login_type="+loginType);
		return sqlDao.get(TUserLogin.class, sql.toString());
	}
	
	/**
	 * 	
	 * 功能描述:根据用户编码获取用户
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */
	public TUserLogin getUserByUserNum(String userNum,Integer loginType ){
		StringBuffer sql=new StringBuffer("  SELECT * FROM t_user_login WHERE user_num='"+userNum+"'");
		sql.append(" and login_type="+loginType);
		return sqlDao.get(TUserLogin.class, sql.toString());
	}
	
	/**
	 * 
	 * 手机端接口:根据用户编码获取用户积分和个人信息
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	public UserAppModel getUserModelByNum(String userNum)
	{
		StringBuffer sql = new StringBuffer("SELECT tua.*,tus.score_day,tus.score,tus.score_sum,tus.apprentice_count_day,tus.apprentice_count,tus.user_level_num,tul.level_name FROM t_user_app tua ,t_user_score tus, t_user_level tul");
		sql.append(" WHERE tua.user_num='"+userNum+"'");
		sql.append(" AND tus.user_num='"+userNum+"'");
		sql.append(" AND tul.level_num=tus.user_level_num ");
		return sqlDao.get(UserAppModel.class, sql.toString());
	}
}
