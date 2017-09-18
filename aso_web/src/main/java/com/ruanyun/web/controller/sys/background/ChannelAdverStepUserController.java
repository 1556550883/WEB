/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.controller.sys.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TChannelAdverStepUser;
import com.ruanyun.web.service.background.ChannelAdverStepUserService;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Controller
@RequestMapping("channelAdverStepUser")
public class ChannelAdverStepUserController extends BaseController{

	@Autowired
	private ChannelAdverStepUserService channelAdverUserService;
	
	
	/**
	 * 
	 * 功能描述:用户渠道下载的任务列表
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("list")
	public String getAdverUserList(Page<TChannelAdverStepUser> page,TChannelAdverStepUser info,Model model){
		addModel(model, "pageList", channelAdverUserService.queryPage(page, info));
		addModel(model, "bean", info);
		return "pc/channelAdverStepUser/list";
	}
}
