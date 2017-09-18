/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.dao.sys;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TPic;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Repository("picDao")
public class PicDao extends BaseDaoImpl<TPic> {

	
	/**
		 * 功能描述:
		 * @param page
		 * @param t
		 * @return
		 *@author feiyang
		 *@date 2016-1-23
		 */
		public Page<TPic> pageSql(Page<TPic> page, TPic t) {
			StringBuffer sql = new StringBuffer(" SELECT * from t_pic WHERE 1=1");			
			return sqlDao.queryPage(page, TPic.class, sql.toString());
		}
/**
 * 
 * 功能描述:获取位置图片
 * @param picPosition
 * @return
 *@author feiyang
 *@date 2016-1-18
 */
	public List<TPic> getPiclListByPosition(String picPosition) {
		StringBuffer sql = new StringBuffer(" SELECT * from t_pic WHERE 1=1");
		if (EmptyUtils.isNotEmpty(picPosition)) {
			sql.append(" AND pic_position='"+picPosition+"'");
		}
		sql.append(" AND pic_status =1  ORDER BY createtime DESC");
		return sqlDao.getAll(TPic.class, sql.toString());
	}
}
