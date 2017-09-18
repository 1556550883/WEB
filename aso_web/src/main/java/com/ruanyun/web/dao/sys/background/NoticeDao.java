/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-29
 */
package com.ruanyun.web.dao.sys.background;

import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TNotice;

/**
 *@author feiyang
 *@date 2016-4-29
 */
@Repository
public class NoticeDao extends BaseDaoImpl<TNotice>{

	
	/**
	 * 
	 * 功能描述:公告
	 * @param t
	 * @param params
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	public Page<TNotice> queryPageSql(Page<TNotice>page,TNotice info) {
		StringBuffer sql=new StringBuffer(" SELECT * from t_notice where 1=1 ");
		sql.append(" order by create_date desc");
		return sqlDao.queryPage(page,TNotice.class, sql.toString());
	}
	
	/**
	 * 
	 * 功能描述:获取最新的一条公告
	 * @return
	 *@author feiyang
	 *@date 2016-4-29
	 */
	public TNotice getNotice(){
		StringBuffer sql=new StringBuffer(" SELECT * from t_notice where 1=1 ");
		sql.append(" order by create_date desc LIMIT 1");
		return sqlDao.get(TNotice.class, sql.toString());
	}
}
