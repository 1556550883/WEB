/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.dao.sys;

import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TArticleInfo;


/**
 *@author feiyang
 *@date 2016-4-16
 */
@Repository
public class ArticleInfoDao extends BaseDaoImpl<TArticleInfo>{


	/**
	 * 
	 * 功能描述:文章信息表
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public Page<TArticleInfo> pageSql(Page<TArticleInfo> page, TArticleInfo info) {
		StringBuffer sql = new StringBuffer(" SELECT * from t_article_info WHERE 1=1");	
		if(EmptyUtils.isNotEmpty(info)){
			sql.append(SQLUtils.popuHqlLike("article_desc", info.getArticleDesc()));
			sql.append(SQLUtils.popuHqlEq("status", info.getStatus()));
		}
		if(EmptyUtils.isNotEmpty(info.getType())){
		if(info.getType()==1){
			sql.append(" order by create_time DESC");
		}
		if(info.getType()==2){
			sql.append(" order by task_quantity DESC");
		}
		if(info.getType()==3){
			sql.append(" order by forward_quantity DESC");
		}
		}
		return sqlDao.queryPage(page, TArticleInfo.class, sql.toString());
	}
	
	
	
	/**
	 * 
	 * 功能描述:根据用户编号获取当前用户未完成的文章列表
	 * @param page
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public Page<TArticleInfo> appPageSql(Page<TArticleInfo> page, String userNum,Integer isAuth) {
		StringBuffer sql = new StringBuffer(" SELECT ari.* from t_article_info ari  where 1=1");		
		if (EmptyUtils.isNotEmpty(isAuth)) {
			sql.append(" and ari.is_auth = "+isAuth);
		}
	if (EmptyUtils.isNotEmpty(userNum)) {
		sql.append(" and ari.article_num  not in (SELECT tua.article_num FROM t_user_article tua WHERE user_num = '"+userNum+"')");
	}
		return sqlDao.queryPage(page, TArticleInfo.class, sql.toString());
	}
	
}
