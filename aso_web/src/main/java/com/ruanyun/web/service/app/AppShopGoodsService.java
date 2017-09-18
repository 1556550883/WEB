/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.service.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ShopGoodsDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TShopGoods;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.duiba.CreditConsumeParams;

/**
 *@author feiyang
 *@date 2016-1-11
 */
@Service
public class AppShopGoodsService extends BaseServiceImpl<TShopGoods>{

	@Autowired
	private ShopGoodsDao shopGoodsDao;
	
	@Autowired
	private AppUserScoreService appUserScoreService;
	
	@Autowired
	private UserScoreService userScoreService;
	
	@Autowired
	private AppUserExchangeService appUserExchangeService;
	/**
	 * 
	 * 功能描述:获取所有商品列表
	 * @param page
	 * @param t
	 * @return
	 *@author feiyang
	 *@date 2016-1-11
	 */
	public AppCommonModel getAllGoods(Page<TShopGoods> page,TShopGoods t){
		AppCommonModel model=new AppCommonModel();
		model.setObj(shopGoodsDao.appPageSql(page, t));
		model.setResult(1);
		model.setMsg("成功");				
		return model;
	}
	
	/**
	 * 
	 * 功能描述:使用积分兑换商品
	 * @param tUserLogin
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */
	public AppCommonModel useScore( TShopGoods info,String userNum,String exchangeRemarks){
		AppCommonModel model=new AppCommonModel(-1,"兑换失败");
			TShopGoods goods=	shopGoodsDao.getGoods(info.getGoodNum());
			TUserScore userScore=userScoreService.getScore(userNum);
			if (EmptyUtils.isNotEmpty(userScore)&&EmptyUtils.isNotEmpty(goods)) {
				if (userScore.getScore()>=goods.getGoodPrice()) {					
					userScoreService.addScore(userNum, goods.getGoodName(),Constants.USER_SCORE_INFO_TYPE_3, -goods.getGoodPrice(), 1);				
					int re=appUserExchangeService.saveUserExchange(goods, userNum,exchangeRemarks);//保存兑换记录
					if(re==1){
						model.setResult(1);
						model.setMsg("兑换成功");					
					}
				}else {
					model.setMsg("积分不足");		
				}
			}
		return model;
	}
	
	
	/**
	 * 功能描述: 保存兑换记录
	 *
	 * @author yangliu  2016年3月29日 下午8:57:32
	 * 
	 * @param params 参数类型
	 * @return
	 */
	public String  userScore(CreditConsumeParams params){
		TUserScore userScore=userScoreService.getScore(params.getUid());
		if (EmptyUtils.isNotEmpty(userScore)){
			float goodPrice=(float)params.getCredits();
			if(userScore.getScore()<goodPrice)
				throw new NullPointerException("积分不足");
			userScoreService.addScore(params.getUid(), getTypeName(params.getType()),Constants.USER_SCORE_INFO_TYPE_3, -goodPrice, 1);
			 return appUserExchangeService.saveUserExchange(getTypeName(params.getType()), (float)params.getCredits(), params.getUid(), params.getOrderNum(), params.getParams(), "", params.getType());
		}
		return "";
	}
	
	public String getTypeName(String type){
		if("alipay".equals(type)){
			return "支付宝";
		}else if("qb".equals(type)){
			return "Q币";
		}else if("qb".equals(type)){
			return "Q币";
		}else if("coupon".equals(type)){
			return "优惠券";
		}else if("phonebill".equals(type)){
			return "话费";
		}else if("phoneflow".equals(type)){
			return "流量";
		}else if("virtual".equals(type)){
			return "虚拟商品";
		}else if("virtual".equals(type)){
			return "虚拟商品";
		}else if("turntable".equals(type)){
			return "大转盘";
		}else if("singleLottery".equals(type)){
			return "单品抽奖";
		}
		return type;
	}
}
