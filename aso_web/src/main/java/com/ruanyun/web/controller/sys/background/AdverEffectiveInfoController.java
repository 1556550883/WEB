package com.ruanyun.web.controller.sys.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TAdverEffectiveInfo;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.service.background.AdverEffectiveInfoService;

@Controller
@RequestMapping("adverEffectiveInfo")
public class AdverEffectiveInfoController extends BaseController
{
	@Autowired
	private AdverEffectiveInfoService adverEffectiveInfoService;
	
	/**
	 * 广告完成列表
	 */
	@RequestMapping("list")
	public String getAdverEffectiveInfoList(Page<TUserappidAdverid> page,TUserappidAdverid info,Model model)
	{
		info.setStatusStart("2");
		addModel(model, "pageList", adverEffectiveInfoService.completeList(page, info));
		addModel(model, "bean", info);
		return "pc/adverEffectiveInfo/list";
	}
	
	@RequestMapping("completeList")
	public String completeList(Page<TUserappidAdverid> page,TUserappidAdverid info,Model model)
	{
		page.setNumPerPage(10);
		info.setStatusStart("2");
		addModel(model, "pageList", adverEffectiveInfoService.completeList(page, info));
		addModel(model, "bean", info);
		return "pc/adverEffectiveInfo/list";
	}
	
	/**
	 * 功能描述:广告有效记录列表
	 */
//	@RequestMapping("list")
//	public String getAdverEffectiveInfoList(Page<TAdverEffectiveInfo> page, TAdverEffectiveInfo info, Model model)
//	{
//		addModel(model, "pageList", adverEffectiveInfoService.queryPage(page, info));
//		addModel(model, "bean", info);
//		
//		return "pc/adverEffectiveInfo/list";
//	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
}
