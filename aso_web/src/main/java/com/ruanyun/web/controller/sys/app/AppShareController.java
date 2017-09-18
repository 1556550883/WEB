package com.ruanyun.web.controller.sys.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.service.background.UserAppService;
@Controller
@RequestMapping("/app")
public class AppShareController extends BaseController {
	@Autowired
	private UserAppService userAppService;
	
	@RequestMapping("/{userNum}/share")
	public String share(@PathVariable String userNum,Model model){
		addModel(model,"user",userAppService.getUserAppByNum(userNum));
		return "pc/share";
	}

}
