package com.ruanyun.web.controller.sys;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;

@Controller
public class IndexController extends BaseController
{
	@RequestMapping("/index")
	public String index(Model model,HttpSession session)
	{		
		return "pc/index";
	}
}
