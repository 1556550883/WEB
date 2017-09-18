/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-25
 */
package com.ruanyun.web.dao.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TAdverEffectiveInfo;
import com.ruanyun.web.model.TAdverInferface;

/**
 *@author feiyang
 *@date 2016-1-25
 */
@Repository
public class AdverInferfaceDao extends BaseDaoImpl<TAdverInferface> {

	/**
	 * 功能描述:
	 * 
	 * @param page
	 * @param t
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	public Page<TAdverInferface> pageSql(Page<TAdverInferface> page,TAdverInferface info) {
		StringBuffer sql = new StringBuffer(" SELECT * from t_adver_inferface where 1=1");
		if (EmptyUtils.isNotEmpty(info.getAdverNum())) {
			sql.append(" and adver_num like '%"+info.getAdverNum()+"%'");
		}
		if (EmptyUtils.isNotEmpty(info.getParameterType())) {
			sql.append(" and parameter_type like '%"+info.getParameterType()+"%'");
		}
		if (EmptyUtils.isNotEmpty(info.getInferfaceRequestType())) {
			sql.append(" and inferface_request_type="+info.getInferfaceRequestType());
		}
		if (EmptyUtils.isNotEmpty(info.getInferfaceType())) {
			sql.append(" and inferface_type="+info.getInferfaceType());
		}

		return sqlDao.queryPage(page, TAdverInferface.class, sql.toString());
	}
	
	
}
