/**
- * 手机端接口: files
 *@author feiyang
 *@date 2016-1-14
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverStepUser;
import com.ruanyun.web.service.app.AppChannelAdverStepUserService;

/**
 *@author feiyang
 *@date 2016-1-14
 */
@Controller
@RequestMapping("app/channelAdverStepUser")
public class AppChannelAdverStepUserController extends BaseController{
	@Autowired
	private AppChannelAdverStepUserService appChannelAdverUserService;
	
	/**
	 * 
	 * 手机端接口:保存用户渠道下载的任务
	 * @param response
	 * @param info
	 *@author feiyang
	 *@date 2016-1-14
	 */
	@RequestMapping("save")
	public void addChannelAdverStepUser(HttpServletResponse response,TChannelAdverStepUser info,String userNum,String sign){
		AppCommonModel model = new AppCommonModel();
		try {
			model = appChannelAdverUserService.addChannelAdverUser(info,userNum);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
}
