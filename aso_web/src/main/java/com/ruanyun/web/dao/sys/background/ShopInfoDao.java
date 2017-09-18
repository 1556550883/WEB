package com.ruanyun.web.dao.sys.background;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TShopsInfo;

@Repository
public class ShopInfoDao extends BaseDaoImpl<TShopsInfo> {
	@Override
	protected String queryPageSql(TShopsInfo t, Map<String, Object> params) {
		StringBuffer sql=new StringBuffer(" from TShopsInfo where 1=1 ");
		if(EmptyUtils.isNotEmpty(t)){
			sql.append(SQLUtils.popuHqlEq("shopName", t.getShopName()));
		}
		return sql.toString();
	}
	
	/**
	 * 
	 * 功能描述：删除
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param ids
	 */
	public void delAll(String ids){
		StringBuffer sql=new StringBuffer("delete from t_shops_info where shop_id in ("+ids+")");
		sqlDao.execute(sql.toString());
	}
	
	
	/**
	 * 
	 * 功能描述:获取所有店铺的列表
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public List<TShopsInfo> getAllShopList(){
		StringBuffer sql=new StringBuffer(" SELECT * FROM t_shops_info");
		return sqlDao.getAll(TShopsInfo.class, sql.toString());
	}
}
