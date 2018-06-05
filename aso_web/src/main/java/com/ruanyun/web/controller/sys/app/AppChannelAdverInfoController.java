/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.UserAppService;

@Controller
@RequestMapping("app/channelAdverInfo")
public class AppChannelAdverInfoController extends BaseController
{

	@Autowired
	private AppChannelAdverInfoService appChannelAdverInfoService;
	@Autowired	
	private UserAppService userAppService;
	
	/**
	 * 手机端接口:获取广告列表
	 */
	@RequestMapping("getAdverList")
	public void getAdverInfoByChannelNum(HttpServletResponse response, HttpServletRequest request,
			Page<TChannelAdverInfo>page, String channelType, String systemType, String phoneType, Integer userAppId, String osversion)
	{
		AppCommonModel model = new AppCommonModel();
		
		page.setNumPerPage(Integer.MAX_VALUE);
		
		if(!StringUtils.hasText(channelType) || !StringUtils.hasText(systemType)
				||!StringUtils.hasText(phoneType) || userAppId == null || userAppId <= 0)
		{
			model.setResult(-1);

			model.setMsg("channelType、systemType、phoneType、userAppId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		userAppService.upPhoneDetail(userAppId, phoneType, osversion);
		String[] isv = osversion.split("\\.");
		model = appChannelAdverInfoService.getAdverInfoByChannelNum2(page, channelType, systemType, phoneType, userAppId, isv[0]);
		
		super.writeJsonDataApp(response, model);
	}
	/**
	 * 
	 * 手机端接口:广告详细页
	 * @param response
	 * @param info
	 */
	@RequestMapping("getAdverDetail")
	public void getDetailByAdverNum(HttpServletResponse response,String adverNum,String userNum,String sign){
		AppCommonModel model = new AppCommonModel();
		try {
			model=appChannelAdverInfoService.getDetailByAdverNum(adverNum,userNum);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
	
}
