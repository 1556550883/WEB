/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.dao.sys;

import java.util.Date;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TUserApprentice;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Repository("userApprenticeDao")
public class UserApprenticeDao extends BaseDaoImpl<TUserApprentice>{

/**
 * 
 * 手机端接口:获取收徒明细
 * @param page
 * @param info
 * @param type 1/2 今日/全部
 * userApprenticeType 1 徒弟 2徒孙
 * @return
 */
	public Page<TUserApprentice> pageSql(Page<TUserApprentice>page,TUserApprentice info,Integer type,Integer userApprenticeType){
		StringBuffer sql=new StringBuffer(" SELECT tua.*,tua1.user_nick user_nick");
		sql.append(" from t_user_apprentice tua INNER JOIN t_user_app tua1 ON tua.apprentice_user_num=tua1.user_num ");
		sql.append(" AND tua.user_num='"+info.getUserNum()+"'");
		if (EmptyUtils.isNotEmpty(type)) {
			if (type==1) {
				sql.append(SQLUtils.popuHqlMinDate("tua.apprentice_time", new Date()));
				sql.append(SQLUtils.popuHqlMaxDate("tua.apprentice_time", new Date()));
			}
		}
		if (userApprenticeType>0) {
			sql.append(" and tua.user_apprentice_type="+userApprenticeType);
		}
		sql.append(" ORDER BY tua.apprentice_time  DESC");
		return sqlDao.queryPage(page, TUserApprentice.class, sql.toString());
	}
	
	public Page<TUserApprentice> pageSql(Page<TUserApprentice>page, String userNum)
	{
		StringBuffer sql=new StringBuffer("SELECT * from t_user_apprentice where user_num = '"+userNum+"'");
		sql.append(" ORDER BY apprentice_time  DESC");
		return sqlDao.queryPage(page, TUserApprentice.class, sql.toString());
	}
}
