/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.dao.sys;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TUserArticle;

/**
 *@author feiyang
 *@date 2016-4-16
 */
@Repository
public class UserArticleDao extends BaseDaoImpl<TUserArticle>{

	
	/**
	 * 
	 * 功能描述:根据用户编号和关联ID 获取文章任务记录
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public TUserArticle getUserArticleByUseNum(TUserArticle info){
		StringBuffer sql=new StringBuffer(" SELECT * from t_user_article WHERE 1=1");
		if (EmptyUtils.isNotEmpty(info)) {
			if (EmptyUtils.isNotEmpty(info.getUserNum())) {
				sql.append(" and user_num='"+info.getUserNum()+"'");
			}
			if (EmptyUtils.isNotEmpty(info.getArticleNum())) {
				sql.append("  AND article_num='"+info.getArticleNum()+"'");
			}
		}
		sql.append(" LIMIT 1");
		return sqlDao.get(TUserArticle.class, sql.toString());
	}
}
