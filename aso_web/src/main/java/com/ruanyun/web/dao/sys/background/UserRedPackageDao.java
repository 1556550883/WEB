/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.dao.sys.background;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TUserRedPackage;

/**
 *@author feiyang
 *@date 2016-3-2
 */
@Repository
public class UserRedPackageDao extends BaseDaoImpl<TUserRedPackage>{

	/**
	 * 
	 * 功能描述:当前用户的猜红包的历史记录
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public Page<TUserRedPackage> pageSql(Page<TUserRedPackage> page,TUserRedPackage info){
		StringBuffer sql=new StringBuffer(" SELECT * from t_user_red_package WHERE 1=1 ");
		if (EmptyUtils.isNotEmpty(info.getUserNum())) {
			sql.append("  AND user_num='"+info.getUserNum()+"'");
		}
		sql.append("  ORDER BY create_date desc");
		return sqlDao.queryPage(page, TUserRedPackage.class, sql.toString());
	}
}
