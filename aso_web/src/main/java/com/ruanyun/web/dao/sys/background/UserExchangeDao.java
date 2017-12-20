/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-12
 */
package com.ruanyun.web.dao.sys.background;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TUserExchange;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.ConstantAuth;
import com.ruanyun.web.util.SecurityUtils;

@Repository
public class UserExchangeDao extends BaseDaoImpl<TUserExchange> 
{
	/**
	 * 
	 * 功能描述:后台分页
	 * @param page
	 * @param info
	 * @param tUser
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	  public Page<TUserExchange> queryPageSql(Page<TUserExchange> page,TUserExchange info,TUser tUser){
		  StringBuffer sql = new StringBuffer(" SELECT * from t_user_exchange WHERE 1=1");
		  if(SecurityUtils.isGranted(ConstantAuth.SHOP_CODE, tUser)){
			  sql.append("  AND exchange_shop_num='"+tUser.getUserNum()+"'");
			  sql.append("  AND  exchange_status=2");
		  }
		  return sqlDao.queryPage(page, TUserExchange.class, sql.toString());
	  }
	/**
	 * 
	 * 功能描述:根据兑换码查找兑换明细
	 * @param info
	 * exchange_status 0/-1/1/2  审核中/审核失败/审核成功/已消费
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */
	public TUserExchange getExchangeByCode(TUserExchange info,TUser tUser){
		StringBuffer sql = new StringBuffer("SELECT * from t_user_exchange WHERE exchange_code='"+info.getExchangeCode()+"'");
		sql.append(" AND  exchange_status="+info.getExchangeStatus());
		if (SecurityUtils.isGranted(ConstantAuth.SHOP_CODE, tUser)) {
			sql.append(" AND exchange_shop_num='" + tUser.getUserNum() + "'");
		}
		return sqlDao.get(TUserExchange.class, sql.toString());
	}
	
	/**
	 * 
	 * 手机端接口:获取兑换记录
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-21
	 */
	public Page<TUserExchange> pageSql(Page<TUserExchange> page,TUserExchange info){
		 StringBuffer sql = new StringBuffer(" SELECT * from t_user_exchange WHERE 1=1");
		 if(EmptyUtils.isNotEmpty(info.getExchangeUserNum())){
			 sql.append(" AND exchange_user_num='"+info.getExchangeUserNum()+"'");			 
		 }
		 return sqlDao.queryPage(page, TUserExchange.class, sql.toString());
	}
	
	/**
	 * 
	 * 功能描述:获取最新的5条兑换记录
	 * @return
	 *@author feiyang
	 *@date 2016-4-29
	 */
	public List<TUserExchange> getExchanges(){
		 StringBuffer sql = new StringBuffer("  SELECT tu.user_nick , tux.*   from t_user_exchange tux");
		 sql.append(" LEFT JOIN t_user_app tu ON tu.user_num=tux.exchange_user_num");		 
		 sql.append(" ORDER BY tux.createtime DESC LIMIT 5");
		 return sqlDao.getAll(TUserExchange.class, sql.toString());
	}
}
