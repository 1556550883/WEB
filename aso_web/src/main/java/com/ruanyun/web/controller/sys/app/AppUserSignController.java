/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-23
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserSign;
import com.ruanyun.web.service.app.AppUserSignService;

/**
 *@author feiyang
 *@date 2016-1-23
 */
@Controller
@RequestMapping("app/appUserSign")
public class AppUserSignController extends BaseController
{
	@Autowired
	private AppUserSignService appUserSignService;
	
	/**
	 * 
	 * 功能描述:签到
	 * @param response
	 * @param userNum
	 * @param sign
	 * @param tUserSign
	 *@author feiyang
	 *@date 2016-1-23
	 */
	@RequestMapping("addTUserSign")
	public void addTUserSign(HttpServletResponse response,String userNum,String sign,TUserSign tUserSign)
	{
		AppCommonModel model = new AppCommonModel();
		try 
		{
			model=appUserSignService.addTUserSign(userNum, tUserSign.getSignTime());
		} 
		catch (Exception e) 
		{
			model = new AppCommonModel(-1, "数据异常");
		}
		
		super.writeJsonDataApp(response, model);
	}
}
