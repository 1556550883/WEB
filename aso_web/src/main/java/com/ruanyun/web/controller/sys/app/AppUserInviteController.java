package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invite")
public class AppUserInviteController 
{
	@RequestMapping("guest")
	public void inviteGuest(HttpServletResponse response, HttpServletRequest request, String masterId)
	{
		
	}
}
