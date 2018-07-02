package com.ruanyun.web.controller.sys.background;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TExternalChannelInfo;
import com.ruanyun.web.service.background.ExternalChannelInfoService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;

@Controller
@RequestMapping("externalInfo")
public class ExternalController extends BaseController
{
	@Autowired
	private ExternalChannelInfoService externalChannelInfoService;
	
	@RequestMapping("list")
	public String getExternalInfoList(Page<TExternalChannelInfo> page, TExternalChannelInfo info, Model model)
	{
		addModel(model, "pageList", externalChannelInfoService.queryPage(page, info));
		addModel(model, "bean", info);
		
		return "pc/externalInfo/list";
	}
	
	
	/**
	 * 
	 * 功能描述:查看
	 * @param info
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("toEdit")
	public String toEdit(TExternalChannelInfo info, Model model,HttpSession session,Integer type) 
	{
		addModel(model, "info", info);
		if (EmptyUtils.isNotEmpty(info.getExternalChannelId())) {
			addModel(model, "info", externalChannelInfoService.getInfoById(info.getExternalChannelId()));
			
		}	
		
		addModel(model, "type", type);
		return "pc/externalInfo/edit";
	}

	/**
	 * 
	 * 功能描述:增加或者修改渠道
	 * @param info
	 * @param response
	 * @param loginName
	 * @param session
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request, TExternalChannelInfo info,HttpServletResponse response) 
	{
		int result = externalChannelInfoService.saveOrupdate(info);
		try
		{
			if (result == 1) 
			{
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","externalInfo/list", "closeCurrent"));
			} 
		} 
		catch (Exception e) 
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	//更改状态
	@RequestMapping("/updateIsEnable")
	public void updateIsEnable(HttpServletRequest request, HttpServletResponse response,Integer isEnable) {
		String id = request.getParameter("ids");
		try {
			if (id != null) {
				if (id.contains(",")) {
					String[] ids = id.split(",");
					for (int i = 0; i < ids.length; i++) {
						externalChannelInfoService.updateIsEnable(Integer.valueOf(ids[i]), isEnable);
					}
				} else {
					externalChannelInfoService.updateIsEnable(Integer.valueOf(id), isEnable);
				}
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","/channelInfo/list", "redirect"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
}
