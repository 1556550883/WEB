/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-20
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
import com.ruanyun.web.model.TUserScoreInfo;
import com.ruanyun.web.service.app.AppUserScoreInfoService;

/**
 *@author feiyang
 *@date 2016-1-20
 */
@Controller
@RequestMapping("app/userScoreInfo")
public class AppUserScoreInfoController extends BaseController {
	@Autowired
	private AppUserScoreInfoService appUserScoreInfoService;

	
	/**
	 * 
	 * 手机端接口:获取个人得分明细
	 * @param response
	 * @param session
	 * @param page
	 * type 1/2 今日/全部
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-20
	 */
	@RequestMapping("getMyScoreList")
	public void getMyScoreList(HttpServletResponse response, HttpSession session,Page<TUserScoreInfo>page,Integer type, String userNum, String sign) {
		AppCommonModel acm = null;
		try {
			page.setNumPerPage(20);
			acm = appUserScoreInfoService.getMyScoreList(page,userNum,type);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
}
