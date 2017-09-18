package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.service.background.ChannelAdverInfoService;

/**
 * 
 *  #(c) ruanyun  <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 手机端使用的html5页面，统一处理
 * 
 *  <br/>创建说明: 2016年9月10日 下午5:11:18 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Controller
@RequestMapping("app/external/{userNum}")
public class AppPageController extends BaseController{
	
	@Autowired
	ChannelAdverInfoService channelAdverInfoService;
	/**
	 * 功能描述:广告详情
	 * @author wsp  2017-1-18 上午11:46:51
	 * @param articleNum
	 * @param model
	 * @param response
	 * @return
	 */
	@RequestMapping("{adverNum}/adverInfo")
	public String articleInfo(@PathVariable String adverNum,Model model,HttpServletResponse response){
		TChannelAdverInfo bean = channelAdverInfoService.get(TChannelAdverInfo.class, "adverNum", adverNum);
		addModel(model, "bean", bean);
		return "app/adverInfo";
	}
	
	
	@InitBinder
	public void initBinders(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd HH:mm:ss", true);
	}
	
}
