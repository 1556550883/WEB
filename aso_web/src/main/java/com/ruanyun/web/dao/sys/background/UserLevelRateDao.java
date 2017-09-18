/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TUserLevelRate;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Repository("userLevelRateDao")
public class UserLevelRateDao extends BaseDaoImpl<TUserLevelRate>{
	/**
	 * 
	 * 功能描述:后台徒弟等级比例
	 * @param t
	 * @param params
	 * @return
	 *@author feiyang
	 *@date 2016-1-22
	 */
	@Override
	protected String queryPageSql(TUserLevelRate t, Map<String, Object> params) {
		StringBuffer sql=new StringBuffer(" from TUserLevelRate where 1=1 ");
		if (EmptyUtils.isNotEmpty(t.getLevelNum())) {			
			sql.append(" and levelNum='"+t.getLevelNum()+"'");
		}
		return sql.toString();
	}

	/**
	 * 
	 * 手机端接口 :根据等级获取得分比例
	 * @param levelNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	public List<TUserLevelRate> getListByLevelNum(String levelNum){
		StringBuffer sql=new StringBuffer(" SELECT * from t_user_level_rate WHERE 1=1");
		if (EmptyUtils.isNotEmpty(levelNum)) {			
			sql.append(" and level_num='"+levelNum+"'");
		}
		sql.append(" ORDER BY rate_type ASC");
		return sqlDao.getAll(TUserLevelRate.class, sql.toString());
	}
}
