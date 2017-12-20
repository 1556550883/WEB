/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.dao.sys.background;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.web.model.TRedPackage;

@Repository
public class RedPackageDao extends BaseDaoImpl<TRedPackage>{

	
	/**
	 * 
	 * 功能描述:获取全部红包
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public List<TRedPackage> getAllRedPackage(){
		StringBuffer sql=new StringBuffer(" SELECT * from t_red_package WHERE 1=1 ORDER BY orderby asc");
		return sqlDao.getAll(TRedPackage.class, sql.toString());
	}
	
	/**
	 * 
	 * 手机端接口:获取红包列表 （包括是否已猜）
	 * @param userNum 当前用户
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getListByUserNum(String userNum,String lotteryNum){
		StringBuffer sql=new StringBuffer(" SELECT trp.red_package_num redPackageNum,trp.red_package_count_day redPackageCountDay,");
		sql.append(" (SELECT COUNT(*)  from t_user_red_package  turp   ");	
		sql.append(" WHERE turp.user_num='"+userNum+"'");
		sql.append(" and turp.lottery_num='"+lotteryNum+"'");
		sql.append(" and trp.red_package_num=turp.user_package_num)status");
		sql.append(" from t_red_package  trp ");
		return sqlDao.getAll(sql.toString());
	}
	
	
	/**
	 * 
	 * 功能描述:根据红包编号获取红包
	 * @param redPackageNum
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public TRedPackage getRedPackageByRedPackageNum(String redPackageNum){
		StringBuffer sql=new StringBuffer(" SELECT * from t_red_package  WHERE red_package_num ='"+redPackageNum+"'");
		return sqlDao.get(TRedPackage.class, sql.toString());
	}
	
	
	/**
	 * 
	 * 功能描述:获取本期猜将总数
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public int getRedPackageDaySum(){
		StringBuffer sql=new StringBuffer(" SELECT SUM(red_package_count_day) from t_red_package ");
		return  sqlDao.getCount(sql.toString());
	}
	
	/**
	 * 功能描述:清楚日抢红包量
	 *
	 * @author yangliu  2016年4月7日 上午11:21:47
	 * 
	 * @return
	 */
	public int clearDay(){
		String sql="UPDATE t_red_package SET red_package_count_day=0";
		return sqlDao.execute(sql);
	}
}

