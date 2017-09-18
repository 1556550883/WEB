package com.ruanyun.web.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TLoginIp;
import com.ruanyun.web.service.background.LoginIpService;

/**
 * 
 *
 *  创建说明: 2017-1-20 上午11:06:16 wsp  创建文件<br/>
 */
@Controller
@RequestMapping("/loginip")
public class LoginIpController extends BaseController {
	
	@Autowired
	@Qualifier("loginIpService")
	private LoginIpService loginIpService;


	/**
	 * 功能描述:手机登录IP记录
	 * @author wsp  2017-1-20 上午11:07:41
	 * @param page
	 * @param loginIp
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String queryPage(Page<TLoginIp>page,TLoginIp loginIp,Model model){
		addModel(model, "pageList", loginIpService.queryPage(page, loginIp));
		addModel(model, "bean", loginIp);
		return "pc/loginip/list";
	}
}






