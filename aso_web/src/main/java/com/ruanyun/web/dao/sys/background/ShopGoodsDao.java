package com.ruanyun.web.dao.sys.background;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TShopGoods;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.ConstantAuth;
import com.ruanyun.web.util.SecurityUtils;

@Repository()
public class ShopGoodsDao extends BaseDaoImpl<TShopGoods> {
	@Override
	protected String queryPageSql(TShopGoods t, Map<String, Object> params) {
		StringBuffer sql = new StringBuffer("from TShopGoods where 1=1 ");
		if (EmptyUtils.isNotEmpty(t)) {
			sql.append(SQLUtils.popuHqlEq("shopNum", t.getShopNum()));
		}

		return sql.toString();
	}

	
	/**
	 * 
	 * 手机端接口:获取商城列表
	 * @param page
	 * @param tUser
	 * @param goods
	 * @return
	 *@author feiyang
	 *@date 2016-1-20
	 */
	public Page<TShopGoods> appPageSql(Page<TShopGoods> page,TShopGoods goods) {
		StringBuffer sql = new StringBuffer("SELECT * from t_shop_goods WHERE 1=1 ");
		if (EmptyUtils.isNotEmpty(goods.getGoodType())) {
			sql.append(" AND good_type ='"+goods.getGoodType()+"'");			
		}
		return sqlDao.queryPage(page, TShopGoods.class,sql.toString());
	}
	/**
	 * 
	 * 功能描述:后台分页
	 * 
	 * @param page
	 * @param tUser
	 * @param goods
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */
	public Page<TShopGoods> PageSql(Page<TShopGoods> page, TUser tUser,TShopGoods goods) {
		StringBuffer sql = new StringBuffer("SELECT * from t_shop_goods WHERE 1=1 ");
		if (SecurityUtils.isGranted(ConstantAuth.SHOP_CODE, tUser)) {
			sql.append(" AND shop_num='" + tUser.getUserNum() + "'");
		}
		if(EmptyUtils.isNotEmpty(goods.getGoodName())){
			sql.append("  AND good_name LIKE '%"+goods.getGoodName()+"%'");
		}
		if(EmptyUtils.isNotEmpty(goods.getGoodType())){
			sql.append(" AND good_type="+goods.getGoodType());
		}
		return sqlDao.queryPage(page, TShopGoods.class,sql.toString());
	}

	/**
	 * 
	 * 功能描述:根据商品编号获取商品信息
	 * 
	 * @param goodNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-11
	 */
	public TShopGoods getGoods(String goodNum) {
		StringBuffer sql = new StringBuffer(
				" SELECT * FROM t_shop_goods WHERE good_num='" + goodNum + "'");

		return sqlDao.get(TShopGoods.class, sql.toString());
	}
}
