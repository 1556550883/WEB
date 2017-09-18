/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserRedPackage;
import com.ruanyun.web.service.app.AppUserRedPackageService;

/**
 *@author feiyang
 *@date 2016-3-2
 */
@Controller
@RequestMapping("app/userRedPackage")
public class AppUserRedPackageController extends BaseController{
	@Autowired
	private AppUserRedPackageService appUserRedPackageService;
	
	/**
	 * 
	 * 手机端接口:用户猜红包
	 * @param response
	 * @param tUserRedPackage
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-3-2
	 */
	@RequestMapping("addUserRedPackage")
	public void addUserRedPackage(HttpServletResponse response,TUserRedPackage tUserRedPackage, String userNum, String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model=appUserRedPackageService.addUserRedPackage(tUserRedPackage);
		} catch (Exception e) {
			model = new AppCommonModel(-1, e.getMessage());
		}
		super.writeJsonDataApp(response, model);
	}
	
	
	/**
	 * 
	 * 手机端接口:当前用户 猜红包的历史记录
	 * @param response
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-3-2
	 */
	@RequestMapping("getList")
	public void getUserRedPackageList(HttpServletResponse response, Page<TUserRedPackage>page,TUserRedPackage tUserRedPackage,String userNum, String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model=appUserRedPackageService.getUserRedPackageList(page, tUserRedPackage);
		} catch (Exception e) {
			model = new AppCommonModel(-1, e.getMessage());
		}
		super.writeJsonDataApp(response, model);
	}
}
