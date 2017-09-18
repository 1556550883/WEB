package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.service.background.LoginIpService;

@Controller
@RequestMapping("app/loginip")
public class AppLoginIpController extends BaseController{
	
	@Autowired
	private LoginIpService loginIpService;
	
	/**
	 * 功能描述:判断此IP是否登录
	 * @author wsp  2017-1-20 上午11:20:05
	 * @param request
	 * @return
	 */
	@RequestMapping("getIsUseIp")
	public void getIsUseIp(HttpServletResponse response,HttpServletRequest request,String ip) {
		AppCommonModel model = new AppCommonModel();
		try {
			Integer result = loginIpService.getIsUseIp(request,ip);
			if(result == 1){
				model.setResult(1);
				model.setMsg("今天未使用");
			}
			else if(result == 2){
				model.setResult(2);
				model.setMsg("今天已使用");
			}
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}

}
