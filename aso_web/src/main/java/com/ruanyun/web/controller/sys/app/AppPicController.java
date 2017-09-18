/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TPic;
import com.ruanyun.web.service.app.AppPicService;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Controller
@RequestMapping("app/pic")
public class AppPicController extends BaseController {

	@Autowired
	private AppPicService appPicService;

	/**
	 * 
	 * 手机端接口:根据位置 获取图片集合
	 * 
	 * @param response
	 * @param info
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-18
	 */
	@RequestMapping("getPic")
	public void getPiclListByPosition(HttpServletResponse response, TPic info,
			String userNum, String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model = appPicService.getPiclListByPosition(info);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
}
