/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.service.app.AppChannelInfoService;

/**
 *@author feiyang
 *@date 2016-1-13
 */
@Controller
@RequestMapping("app/channelInfo")
public class AppChannelInfoController extends BaseController {

	@Autowired
	private AppChannelInfoService appChannelInfoService;

	/**
	 * 
	 * 手机端接口:根据类型获取渠道列表
	 * 
	 * @param response
	 * @param page
	 * @param info
	 * channelType 1/2/3 自由渠道/CP专区/快赚钱
	 *@author feiyang
	 *@date 2016-1-13
	 */
	@RequestMapping("getChannelByType")
	public void getChannelByType(HttpServletResponse response,Page<TChannelInfo> page, TChannelInfo info,String userNum,String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model = appChannelInfoService.getChannelByType(page, info);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}

}
