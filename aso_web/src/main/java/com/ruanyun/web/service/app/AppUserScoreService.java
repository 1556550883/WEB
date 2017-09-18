/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.service.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ShopGoodsDao;
import com.ruanyun.web.dao.sys.background.UserScoreDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TShopGoods;
import com.ruanyun.web.model.TUserExchange;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.service.background.UserExchangeService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.NumUtils;


/**
 *@author feiyang
 *@date 2016-1-11
 */
@Service
public class AppUserScoreService extends BaseServiceImpl<TUserScore> {

	@Autowired
	@Qualifier("userScoreDao")
	private UserScoreDao userScoreDao;

	@Autowired
	private ShopGoodsDao shopGoodsDao;
	
	@Autowired
	private UserExchangeService userExchangeService;
	
	@Autowired
	private UserScoreService userScoreService;

	/**
	 * 
	 * 功能描述:判断当前用户是否可以兑换该商品
	 * 
	 * @param tUserLogin
	 * @param goodNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-11
	 */
	public AppCommonModel getScore(TUserLogin tUserLogin, String goodNum) {
		AppCommonModel model = new AppCommonModel();
		TUserScore userScore = userScoreDao.getScore(tUserLogin.getUserNum());
		TShopGoods goods = shopGoodsDao.getGoods(goodNum);
		if (EmptyUtils.isEmpty(userScore) || EmptyUtils.isEmpty(goods)) {
			return model = new AppCommonModel(-1, "用户积分或者商品不存在");
		} else {
			if (userScore.getScoreSum() >= goods.getGoodPrice()) {
				model.setResult(1);
				model.setMsg("可以兑换");
			} else {
				model.setResult(-1);
				model.setMsg("积分不足");
			}
		}
		return model;
	}
/**
 * 
 * 功能描述:使用积分兑换商品
 * @param userNum
 * @param goodPrice
 * @return
 *@author feiyang
 *@date 2016-1-12
 */
	public int updateScore(String userNum, TShopGoods goods) {
		TUserScore userScore = userScoreDao.getScore(userNum);
		Float num = userScore.getScoreSum() - goods.getGoodPrice();
		if (num >= 0) {
			TUserExchange tUserExchange=new TUserExchange();
			tUserExchange.setExchangeName(goods.getGoodName());
			tUserExchange.setExchangeScore(goods.getGoodPrice());
			tUserExchange.setExchangeUserNum(userNum);
			tUserExchange.setExchangeGoodsNum(goods.getGoodNum());
			tUserExchange.setExchangeGoodsName(goods.getGoodName());
			tUserExchange.setExchangeShopNum(goods.getShopNum());
			tUserExchange.setCreatetime(new Date());
			tUserExchange.setExchangeStatus(0);//默认审核中
			userExchangeService.save(tUserExchange);
			tUserExchange.setExchangeNum(NumUtils.getCommondNum(NumUtils.EXCHANGE_NUM, tUserExchange.getExchhangeId())); 			
			userScore.setScoreSum(userScore.getScoreSum()-goods.getGoodPrice());
			userScoreService.update(userScore);
			return 1;
		}
		return 0;
	}

	
	/**
	 * 
	 * 手机端接口:获取收徒排行榜
	 * @param page
	 * @param info
	 * type 1/2  收益/收徒数量
	 * @return
	 *@author feiyang
	 *@date 2016-1-20
	 */
	public AppCommonModel getRankingList(Page<TUserScore>page,TUserScore info,Integer type){
		AppCommonModel model=new AppCommonModel(1,"查询成功");
		Page<TUserScore> list=userScoreDao.pageSql(page, info,type);
		for (int i = 0; i < list.getResult().size(); i++) {
			int j=page.getPageNum()*page.getNumPerPage();
			list.getResult().get(i).setRankingNum(Integer.toString(i+1+j));
		}
		model.setObj(list);
		return model;
	}
}
