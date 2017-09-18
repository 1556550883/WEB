/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-16
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Date;

import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TUserScoreInfo;

/**
 *@author feiyang
 *@date 2016-1-16
 */
@Repository("userScoreInfoDao")
public class UserScoreInfoDao extends BaseDaoImpl<TUserScoreInfo> {

	/**
	 * 
	 * 手机端接口:获取个人的收益明细
	 * 
	 * @param page
	 * @param info
	 * type 1  是查看今日
	 * @return
	 *@author feiyang
	 *@date 2016-1-20
	 */
	public Page<TUserScoreInfo> pageSql(Page<TUserScoreInfo> page,TUserScoreInfo info,Integer type) {
		StringBuffer sql = new StringBuffer(" SELECT * from t_user_score_info WHERE 1=1 ");
		if (EmptyUtils.isNotEmpty(info)) {
			sql.append(" AND user_app_num='" + info.getUserAppNum() + "'");
		}
		if (EmptyUtils.isNotEmpty(type)) {
			if(type==1){   //今日收益
				sql.append(SQLUtils.popuHqlMinDate("score_time", new Date()));
				sql.append(SQLUtils.popuHqlMaxDate("score_time", new Date()));
			}
		}
		sql.append(" ORDER BY score_time DESC");
		return sqlDao.queryPage(page, TUserScoreInfo.class, sql.toString());
	}
}
