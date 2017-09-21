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
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.service.app.AppUserScoreService;

/**
 *@author feiyang
 *@date 2016-1-11
 */
@Controller
@RequestMapping("app/userScore")
public class AppUserScoreController extends BaseController{

	@Autowired
	private AppUserScoreService appUserScoreService;
	
//	/**
//	 * 
//	 * 手机端接口:判断当前用户是否可以兑换该商品
//	 * @param session
//	 * @param response
//	 * @param tUserLogin
//	 * @param goodNum
//	 *@author feiyang
//	 *@date 2016-1-11
//	 */
//	@RequestMapping("getScore")
//	public void getScore(HttpSession session,HttpServletResponse response,String goodNum){
//		AppCommonModel model=new AppCommonModel();
//		TUserLogin tUserLogin=HttpSessionUtils.getCurrentTUserLogin(session);
//		try {
//			model=appUserScoreService.getScore(tUserLogin, goodNum);
//		} catch (Exception e) {
//			model=new AppCommonModel(-1,"数据异常");
//		}		
//		super.writeJsonDataApp(response, model);
//	}


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
	@RequestMapping("getRankingList")
	public void getRankingList(HttpSession session,HttpServletResponse response,Page<TUserScore>page,TUserScore info,Integer type,String userNum,String sign){
		AppCommonModel model=new AppCommonModel();		
		try {
			model=appUserScoreService.getRankingList(page, info,type);
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}
}
