/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Date;

import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TRedPackageLottery;


/**
 *@author feiyang
 *@date 2016-3-2
 */
@Repository()
public class RedPackageLotteryDao extends BaseDaoImpl<TRedPackageLottery>{

	

	/**
	 * 
	 * 功能描述:后台管理红包
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public Page<TRedPackageLottery> PageSql(Page<TRedPackageLottery> page,TRedPackageLottery info,Integer type) {
		StringBuffer sql = new StringBuffer("SELECT * from t_red_package_lottery WHERE 1=1 ");
		if (type==1) { //手机端往期开奖
			sql.append(" AND lottery_status =1" );
		}
		sql.append(" ORDER BY create_time DESC" );
		return sqlDao.queryPage(page, TRedPackageLottery.class,sql.toString());
	}
	
	
	/**
	 * 
	 * 功能描述:今日是否已经开奖
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public int isDayLottery(){
		StringBuffer sql = new StringBuffer(" SELECT COUNT(*) from t_red_package_lottery WHERE 1=1");
		sql.append(SQLUtils.popuHqlMinDate("create_time", new Date()));
		sql.append(SQLUtils.popuHqlMaxDate("create_time", new Date()));	
		return sqlDao.getCount(sql.toString());
	}
	
	
	/**
	 * 
	 * 功能描述:获取往期的开奖
	 * @return
	 *@author feiyang
	 *@date 2016-3-3
	 */
	public TRedPackageLottery getPastLottery(){
		StringBuffer sql = new StringBuffer("SELECT * from t_red_package_lottery WHERE 1=1");
		sql.append(SQLUtils.popuHqlMaxToDay("create_time", new Date()));
		sql.append("  ORDER BY create_time DESC LIMIT 1");
		return sqlDao.get(TRedPackageLottery.class, sql.toString());
	}
	
	
	/**
	 * 
	 * 功能描述:根据开奖编号获取本期开奖红包
	 * @param lotteryNum
	 * @return
	 *@author feiyang
	 *@date 2016-3-3
	 */
	public TRedPackageLottery getLotteryByluLotteryNum(){
		StringBuffer sql = new StringBuffer("SELECT * from t_red_package_lottery WHERE 1=1");
		sql.append("  ORDER BY create_time DESC LIMIT 1");
		return sqlDao.get(TRedPackageLottery.class, sql.toString());
	}
}
