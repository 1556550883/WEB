package com.ruanyun.web.controller.sys.background;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.cache.impl.PublicCache;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.model.sys.TDictionary;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.ChannelInfoService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.util.AddressUtils;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.EncrypDES;
import com.ruanyun.web.util.HttpSessionUtils;

@Controller
@RequestMapping("channelInfo")
public class ChannelInfoController extends BaseController
{
	@Autowired
	private ChannelInfoService channelInfoService;
	
	@Autowired
	private PublicCache publicCache;
	@Autowired
	private UserAppService userAppService;
	/**
	 * 
	 * 功能描述:渠道列表
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String getChannelInfoList(Page<TChannelInfo> page, TChannelInfo info, Model model)
	{
		addModel(model, "pageList", channelInfoService.queryPage(page, info));
		addModel(model, "bean", info);
		
		return "pc/channelInfo/list";
	}
	
	/**
	 * idfa统计
	 */
	@RequestMapping("idfaStatistics")
	public String idfaStatistics(HttpServletResponse response, Page<TUserappidAdverid> page, 
			String channelNum, String completeTime, Model model) throws ParseException
	{
		channelNum = StringUtils.hasText(channelNum)?channelNum:"1";
		completeTime = StringUtils.hasText(completeTime)?completeTime.replaceAll("-", ""):new SimpleDateFormat("yyyyMMdd").format(new Date());
		try 
		{
			addModel(model, "pageList", channelInfoService.queryIdfaStatistics(page, channelNum, completeTime));
			TChannelAdverInfo adverInfo = new TChannelAdverInfo();
			adverInfo.setChannelNum(channelNum);
			addModel(model, "adverInfo", adverInfo);
			addModel(model, "completeTime", new SimpleDateFormat("yyyyMMdd").parse(completeTime));
		} catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		return "pc/idfa/list";
	}
	
	/**
	 * 员工idfa统计
	 */
	@RequestMapping("employeeIdfaStatistics")
	public String employeeIdfaStatistics(Page<TUserappidAdverid> page, Integer userAppId, String completeTime, Model model) throws ParseException
	{
		completeTime = StringUtils.hasText(completeTime)?completeTime.replaceAll("-", ""):new SimpleDateFormat("yyyyMMdd").format(new Date());
		page = channelInfoService.queryEmployeeIdfaStatistics(page, userAppId, completeTime);
		TUserApp userApp = userAppService.getUserAppById(userAppId);
		if(userApp.getUserApppType() == 2) {
			for(TUserappidAdverid task : page.getResult()) {
				task.setLocaltion(AddressUtils.getAddressByIP(task.getIp()));
			}
		}
		
		addModel(model, "pageList",page);
		try 
		{
			addModel(model, "userAppId", userAppId);
			addModel(model, "completeTime", new SimpleDateFormat("yyyyMMdd").parse(completeTime));
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return "pc/employeeIdfa/list";
	}
	
///**
// * 
// * 功能描述:删除
// * @param request
// * @param response
// *@author feiyang
// *@date 2016-1-6
// */
//	@RequestMapping("/delAll")
//	public void delete(HttpServletRequest request, HttpServletResponse response) {
//		String id = request.getParameter("ids");
//		try {
//			if (id != null) {
//				// 说明是批量删除
//				if (id.contains(",")) {
//					String[] ids = id.split(",");
//					for (int i = 0; i < ids.length; i++) {
//						channelInfoService.delete(TChannelInfo.class, Integer.valueOf(ids[i]));
//					}
//				} else {
//					channelInfoService.delete(TChannelInfo.class, Integer.valueOf(id));
//				}
//				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","/channelInfo/list", "redirect"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
//		}
//	}

	@RequestMapping("/updateIsEnable")
	public void updateIsEnable(HttpServletRequest request, HttpServletResponse response,Integer isEnable) {
		String id = request.getParameter("ids");
		try {
			if (id != null) {
				// 说明是批量删除
				if (id.contains(",")) {
					String[] ids = id.split(",");
					for (int i = 0; i < ids.length; i++) {
						channelInfoService.updateIsEnable(Integer.valueOf(ids[i]), isEnable);
					}
				} else {
					channelInfoService.updateIsEnable(Integer.valueOf(id), isEnable);
				}
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","/channelInfo/list", "redirect"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
/**
 * 
 * 功能描述:查看
 * @param info
 * @param model
 * @param session
 * @return
 *@author feiyang
 *@date 2016-1-6
 */
	@RequestMapping("toEdit")
	public String toEdit(TChannelInfo info, Model model,HttpSession session,Integer type) {
		addModel(model, "info", info);
		if (EmptyUtils.isNotEmpty(info.getChannelId())) {
			addModel(model, "info", channelInfoService.getInfoById(info.getChannelId()));
			
		}	
		addModel(model, "type", type);
		return "pc/channelInfo/edit";
	}

	
	/**
	 * 
	 * 功能描述:增加或者修改渠道
	 * @param info
	 * @param response
	 * @param loginName
	 * @param session
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request, Model model,TChannelInfo info , HttpSession session,HttpServletResponse response,MultipartFile picFile) {
		TUser user = HttpSessionUtils.getCurrentUser(session);		
		int result = channelInfoService.saveOrupdate(info, request, user,picFile);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","channelInfo/list", "closeCurrent"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}

	@RequestMapping("toLookReq")
	public String toLookReq(TChannelInfo info, Model model,HttpServletRequest request)
	{
		String path=request.getContextPath();
		//String path1=request.getRealPath("url"); // 虚拟目录映射为实际目录
		//String path2=request.getRealPath("./");    // 网页所在的目录
		request.getContextPath();    // 应用的web目录的名称
		addModel(model, "path", path);
		addModel(model,"channalNum",EncrypDES.encryptEde(info.getChannelNum()));
		return "pc/channelInfo/lookreq";
	}

	
	@RequestMapping("getChannelType")
	 public void getChannelType(HttpServletResponse response){
		List<TDictionary> list=publicCache.getItemList("CHANNEL_TYPE");
		 super.writeJsonData(response, list);
   }
}
