package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.service.background.ActivateionService;
@Controller
@RequestMapping("app/activation")
public class AdverActivationController extends BaseController
{
	@Autowired
	private ActivateionService activateionService;
	
	/**
	 * 功能描述:广告激活
	 *
	 * @param request
	 * @param response
	 * @param adverNum
	 */
	@RequestMapping(value="adver/{adverNum}")//,method = RequestMethod.POST
	public void adverActivateion(HttpServletRequest request,HttpServletResponse response,@PathVariable String adverNum)
	{
		String msg=activateionService.activateionAdver(adverNum, request);
		super.writeText(response, msg);
	}
	
	/**
	 * 功能描述:渠道激活
	 *
	 * @param request
	 * @param response
	 * @param channelNum
	 */
	@RequestMapping(value="channel/{channelNum}")//,method = RequestMethod.POST
	public void channelActivateion(HttpServletRequest request,HttpServletResponse response,@PathVariable String channelNum)
	{
		String msg=activateionService.activateionChannel(channelNum, request);
		super.writeText(response, msg);
	}
	
	@RequestMapping("test")
	public void  test(HttpServletRequest request,HttpServletResponse response) 
	{
		super.writeText(response, "dafadfa");
	}
}
