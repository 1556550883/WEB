package com.ruanyun.web.controller.sys.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.service.background.ChannelInfoService;

@Controller
@RequestMapping("channelInfoData")
public class ChannelInfoDataController extends BaseController
{
	@Autowired
	private ChannelInfoService channelInfoService;
	@RequestMapping("list")
	public String getChannelInfoList(Page<TChannelInfo> page, TChannelInfo info, Model model)
	{
		page = channelInfoService.queryPage(page, info);
		for(TChannelInfo infoData : page.getResult()) {
			channelInfoService.calculate(infoData);
		}
		
		addModel(model, "pageList", page);
		addModel(model, "bean", info);
		return "pc/channelInfoData/list";
	}
}
