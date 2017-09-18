/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-20
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;

import com.ruanyun.web.model.TUserApprentice;
import com.ruanyun.web.service.app.AppUserApprenticeService;

/**
 *@author feiyang
 *@date 2016-1-20
 */
@Controller
@RequestMapping("app/userApprentice")
public class AppUserApprenticeController extends BaseController{
	@Autowired	
	private AppUserApprenticeService appUserApprenticeService;
	
	/**
	 * 
	 * 手机端接口:获取收徒明细
	 * @param page
	 * @param info
	 * @param type 1/2 今日/全部
	 * @return
	 *@author feiyang
	 *@date 2016-1-21
	 */
	@RequestMapping("getList")
	public void getUserApprenticeList(HttpServletResponse response,Page<TUserApprentice>page,TUserApprentice info,Integer type,String userNum,String sign){
		AppCommonModel model = new AppCommonModel();
		try {
			model = appUserApprenticeService.getMyApprenticeList(page, info,type,0);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}

	/**
	 * 
	 * 功能描述:获取我的徒弟和徒孙
	 * @param response
	 * @param page
	 * @param info
	 * @param type
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-3-14
	 */
	@RequestMapping("getMyList")
	public void getMyUserApprenticeList(HttpServletResponse response,Page<TUserApprentice>page,TUserApprentice info,Integer userApprenticeType,String userNum,String sign){
		AppCommonModel model = new AppCommonModel();
		try {
			model = appUserApprenticeService.getMyApprenticeList(page, info,2,userApprenticeType);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}

	
}
