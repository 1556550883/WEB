/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TShopGoods;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.model.sys.TLoginLog;
import com.ruanyun.web.service.app.AppShopGoodsService;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 *@author feiyang
 *@date 2016-1-11
 */
@Controller
@RequestMapping("app/shopGoods")
public class AppShopGoodsController extends BaseController {

	@Autowired
	private AppShopGoodsService appShopGoodsService;

	/**
	 * 
	 * 功能描述:手机端接口
	 * 
	 * @param session
	 * @param response
	 * @param page
	 * @param t
	 *@author feiyang
	 *@date 2016-1-11
	 */
	@RequestMapping("getAllGoods")
	public void getAllGoods(HttpSession session, HttpServletResponse response,
			Page<TShopGoods> page, TShopGoods t,String userNum,String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model = appShopGoodsService.getAllGoods(page, t);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}

		super.writeJsonDataApp(response, model);
	}
	/**
	 * 
	 * 手机端接口:积分兑换商品
	 * 
	 * @param session
	 * @param response
	 * @param goods
	 *@author feiyang
	 *@date 2016-1-12
	 */
	@RequestMapping("exchangeGoods")
	public void exchangeGoods(HttpSession session,HttpServletResponse response, TShopGoods info,String exchangeRemarks,String userNum,String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model=appShopGoodsService.useScore(info, userNum,exchangeRemarks);
		} catch (Exception e) {
			e.printStackTrace();
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
}
