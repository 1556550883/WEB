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
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.service.app.AppRedPackageService;



/**
 *@author feiyang
 *@date 2016-3-2
 */
@Controller
@RequestMapping("app/redPackage")
public class AppRedPackageController extends BaseController{
	
	@Autowired
	private AppRedPackageService appRedPackageService;
	
	/**
	 * 
	 * 手机端接口:获取红包列表 （包括是否已猜）
	 * @param userNum 当前用户
	 * @param sign
	 *@author feiyang
	 *@date 2016-3-2
	 */
	@RequestMapping("getList")
	public void getgetListByUser(HttpServletResponse response,String userNum, String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model=appRedPackageService.getgetListByUser(userNum);
		} catch (Exception e) {
			model = new AppCommonModel(-1, e.getMessage());
		}
		super.writeJsonDataApp(response, model);
	}
}
