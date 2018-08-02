package com.ruanyun.web.controller.sys.app;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruanyun.common.controller.BaseController;

@Controller
@RequestMapping("/invite")
public class AppUserInviteController extends BaseController
{
	@RequestMapping("guest")
	public String inviteGuest(HttpServletResponse response, HttpServletRequest request, Model model, String id)
	{
		addModel(model, "master", id);
		return "app/invite/inviteGuest";
	}
	
	@RequestMapping("deviceUuid")
	@ResponseBody
	public List<String> guestPhone(HttpServletResponse response, HttpServletRequest request, Model model, String masterid, String deviceUuid)
	{
		 List<String> ssList = new ArrayList<String>();
		 ssList.add("加入成功！");
		 return ssList;
	}
}
