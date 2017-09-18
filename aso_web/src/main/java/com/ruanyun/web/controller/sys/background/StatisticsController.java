/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-2-24
 */
package com.ruanyun.web.controller.sys.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.service.background.UserAppService;

/**
 *@author feiyang
 *@date 2016-2-24
 */
@Controller
@RequestMapping("statistics")
public class StatisticsController extends BaseController{

	@Autowired
	
	private UserAppService userAppService;
	
	@RequestMapping("list")
	public String getDayList(Model model,TUserApp appUser){
		addModel(model, "dayuserNum", userAppService.getUserNum(1,appUser.getCreateDate()));
		addModel(model, "info", appUser);
		return "pc/statistics/list";
	}
	@InitBinder
	public void initBinders(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
}
