package com.ruanyun.web.controller.sys.background;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TUserScoreInfo;
import com.ruanyun.web.service.background.UserScoreInfoService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;

@Controller
@RequestMapping("/userAppForwardRecord")
public class AppUserForwardRecordController extends BaseController
{
	@Autowired
	private UserScoreInfoService userScoreInfoService;
	
	
	@RequestMapping("list")
	public String getUserForwardList(Page<TUserScoreInfo> page,TUserScoreInfo info,Model model)
	{
		page.setNumPerPage(20);
		addModel(model, "pageList", userScoreInfoService.queryPage(page, info));
		addModel(model, "bean", info);
		
		return "pc/userForwardRecord/list";
	}
	
	
	@RequestMapping("verify")
	public void forwardVerify(HttpServletRequest request, HttpServletResponse response)
	{
		String userScoreInfoId = request.getParameter("userScoreInfoId");
		
		try 
		{
			int result = userScoreInfoService.updateScoreInfoStatus(userScoreInfoId);
			
			if (result == 1) 
			{
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","userAppForwardRecord/list", "redirect"));
			} 
		} 
		catch (Exception e) 
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
}
