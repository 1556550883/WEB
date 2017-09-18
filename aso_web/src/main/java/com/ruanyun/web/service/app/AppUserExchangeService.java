/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-21
 */
package com.ruanyun.web.service.app;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserExchangeDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TShopGoods;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserExchange;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserExchangeService;
import com.ruanyun.web.util.NumUtils;

/**
 *@author feiyang
 *@date 2016-1-21
 */
@Service
public class AppUserExchangeService extends BaseServiceImpl<TUserExchange>{

	@Autowired
	private UserExchangeDao userExchangeDao;
	
	@Autowired
	private UserExchangeService userExchangeService;
	@Autowired
	private UserAppService userAppService;
	
	/**
	 * 
	 * 功能描述:获取兑换记录
	 * @param page
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-21
	 */
	public AppCommonModel getMyExchangeList(Page<TUserExchange> page,String userNum){
		AppCommonModel model=new AppCommonModel();
		TUserExchange info=new TUserExchange();
		info.setExchangeUserNum(userNum);
		model.setResult(1);
		model.setObj(userExchangeDao.pageSql(page, info));
		return model;
	}
	/**
	 * 
	 * 功能描述::保存商品兑换记录
	 * @param tShopGoods 商品对象
	 * @param userNum  用户名
	 * @param exchangeRemarks  备注
	 * @return
	 *@author feiyang
	 *@date 2016-1-21
	 */
	public int saveUserExchange(TShopGoods tShopGoods ,String userNum,String exchangeRemarks){
		TUserApp userApp=userAppService.getUserAppByNum(userNum);
		if (EmptyUtils.isNotEmpty(tShopGoods)&&EmptyUtils.isNotEmpty(userApp)) {
			TUserExchange info=new TUserExchange();
			info.setExchangeName(tShopGoods.getGoodName());
			info.setExchangeScore(tShopGoods.getGoodPrice());
			info.setExchangeUserNum(userNum);
			info.setExchangeGoodsNum(tShopGoods.getGoodNum());
			info.setExchangeGoodsName(tShopGoods.getGoodName());
			info.setExchangeShopNum(tShopGoods.getShopNum());
			info.setExchangeCodeType(tShopGoods.getExchangeCodeType());//商品兑换方式
			info.setExchangeStatus(0);//审核中
			info.setExchangeRemarks(exchangeRemarks);//兑换备注
			info.setCreatetime(new Date());
			info.setZhifubao(userApp.getZhifubao());
			info.setZhifubaoName(userApp.getZhifubaoName());
			info.setWeixin(userApp.getWeixin());
			save(info);
			info.setExchangeNum(NumUtils.getCommondNum(NumUtils.EXCHANGE_NUM, info.getExchhangeId()));
			return 1;
		}
		return 0;
	}
	
	/**
	 * 功能描述: 保存订单信息
	 *
	 * @author yangliu  2016年3月29日 下午8:46:55
	 * 
	 * @param goodName 商品名称
	 * @param goodPrice 商品价格
	 * @param userNum 用户编号
	 * @param goodNum 商品编号
	 * @param exchangeRemarks 兑换备注
	 * @param shopNum 店铺编号
	 * @param exchangeCodeType 兑换类型
	 * @return
	 */
	public String  saveUserExchange(String goodName,Float goodPrice,String userNum,String goodNum,String exchangeRemarks,String shopNum,String exchangeCodeType ){
		if (EmptyUtils.isNotEmpty(userNum)) {
			TUserExchange info=new TUserExchange();
			info.setExchangeName(goodName);
			info.setExchangeScore(goodPrice);
			info.setExchangeUserNum(userNum);
			info.setExchangeGoodsNum(goodNum);
			info.setExchangeGoodsName(goodName);
			info.setExchangeShopNum(shopNum);
			info.setExchangeCodeType(exchangeCodeType);//商品兑换方式
			info.setExchangeStatus(0);//审核中
			info.setExchangeRemarks(exchangeRemarks);//兑换备注
			info.setCreatetime(new Date());
			save(info);
			info.setExchangeNum(NumUtils.getCommondNum(NumUtils.EXCHANGE_NUM, info.getExchhangeId()));
			return info.getExchangeNum();
		}
		return "";
	}
	
	/**
	 * 功能描述:审核信息
	 *
	 * @author yangliu  2016年3月29日 下午11:11:21
	 * 
	 * @param orderNum
	 * @param type
	 */
	public void updateStatus(String orderNum,String type){
		if("fail".equals(type)){
			userExchangeService.updateStatus(orderNum, -1);
		}else if("suc".equals(type)){
			userExchangeService.updateStatus(orderNum, -1);
		}
	}
	
	
	/**
	 * 
	 * 功能描述:获取最新的5条兑换记录
	 * @return
	 *@author feiyang
	 *@date 2016-4-29
	 */
	public AppCommonModel getMyExchangeList(){
		AppCommonModel model=new AppCommonModel(1,"查询成功");
		List<TUserExchange>list=userExchangeDao.getExchanges();	
		if (EmptyUtils.isNotEmpty(list)) {
			model.setObj(list);			
		}else {
			model.setMsg("{}");
		}
		return model;
	}
	
}
