/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-27
 */
package com.ruanyun.web.dao.sys;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TView;

/**
 *@author feiyang
 *@date 2016-1-27
 */
@Repository
public class ViewDao extends BaseDaoImpl<TView>{

	
	/**
	 * 
	 * 功能描述:关于和帮助
	 * @param viewType 1/2  关于帮助
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
	public TView getView(String viewType){
		StringBuffer sql=new StringBuffer(" SELECT * from t_view WHERE 1=1");
		if (EmptyUtils.isNotEmpty(viewType)) {
			sql.append(" and view_type='"+viewType+"'");			
		}
		return sqlDao.get(TView.class, sql.toString());
	}
}
