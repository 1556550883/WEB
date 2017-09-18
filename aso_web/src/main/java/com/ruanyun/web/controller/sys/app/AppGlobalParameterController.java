/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-27
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.service.app.AppGlobalParameterService;

/**
 *@author feiyang
 *@date 2016-1-27
 */
@Controller
@RequestMapping("app/global")
public class AppGlobalParameterController extends BaseController{

	@Autowired
	private AppGlobalParameterService appGlobalParameterService;
	
	/**
	 * 
	 * 手机端接口:获取全局变量
	 * @param response
	 * @param userNum
	 * @param sign
	 * @param type
	 *@author feiyang
	 *@date 2016-1-27
	 */
	@RequestMapping("getGlobal")
	public void getGlobal(HttpServletResponse response,String userNum, String sign,Integer type) {
		AppCommonModel model = new AppCommonModel();
		try {
			model =appGlobalParameterService.getGlobalList(type);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
}
