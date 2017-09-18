/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Map;
import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TUserLevel;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Repository("userLevelDao")
public class UserLevelDao extends BaseDaoImpl<TUserLevel>{

	
	@Override
	protected String queryPageSql(TUserLevel t, Map<String, Object> params) {
		StringBuffer sql=new StringBuffer(" from TUserLevel where 1=1 ");
		if(EmptyUtils.isNotEmpty(t)){
			sql.append(SQLUtils.popuHqlLike("levelName", t.getLevelName()));
		}
		return sql.toString();
	}
	
	/**
	 * 
	 * 手机端接口:获取等级说明
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	public Page<TUserLevel> pageSql(Page<TUserLevel>page){
		StringBuffer sql=new StringBuffer(" SELECT  * from t_user_level WHERE 1=1");
		sql.append(" ORDER BY LENGTH(level_name),level_name ASC");
		return sqlDao.queryPage(page, TUserLevel.class, sql.toString());
	}

	/**
	 * 功能描述: 获取等级
	 *
	 * @author yangliu  2016年1月25日 下午12:51:12
	 * 
	 * @param proportionCount
	 * @return
	 */
	public TUserLevel getUserLevel(Integer proportionCount){
		String sql = "SELECT * FROM t_user_level WHERE proportion_start<="+proportionCount+" AND proportion_end>="+proportionCount;
		return sqlDao.get(TUserLevel.class, sql);
	}
	
	
	/**
	 * 
	 * 功能描述:收徒等级
	 * @param page
	 * @return
	 *@author feiyang
	 *@date 2016-3-22
	 */
	public Page<TUserLevel> pageSql1(Page<TUserLevel>page){
		StringBuffer sql=new StringBuffer(" SELECT  tul.*,tulr.level_rate from t_user_level  tul ");
		sql.append(" LEFT JOIN (SELECT * from  t_user_level_rate WHERE rate_type=1) tulr");
		sql.append(" ON tul.level_num= tulr.level_num");
		sql.append(" ORDER BY tul.proportion_start asc");
		return sqlDao.queryPage(page, TUserLevel.class, sql.toString());
	}
}
