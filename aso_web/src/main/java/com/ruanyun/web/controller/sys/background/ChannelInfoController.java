package com.ruanyun.web.controller.sys.background;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TPhoneUdidModel;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.model.sys.TDictionary;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelInfoService;
import com.ruanyun.web.service.background.UdidService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.util.AddressUtils;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.EncrypDES;
import com.ruanyun.web.util.FileUtils;
import com.ruanyun.web.util.HttpRequestUtil;
import com.ruanyun.web.util.HttpSessionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
	@Autowired
	private AppChannelAdverInfoService appChannelAdverInfoService;
	@Autowired
	private UdidService udidService;
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
		page = channelInfoService.queryPage(page, info);
		List<TChannelInfo> channels = page.getResult();
		for(TChannelInfo t : channels) {
			Date date = new Date();		
			String dateStr = TimeUtil.doFormatDate(date,"yyyy-MM");
			String dateStrday = TimeUtil.doFormatDate(date,"yyyy-MM-dd");
			t.setMonNum(channelInfoService.calculate(t, dateStr));
			t.setTodayNum(channelInfoService.calculate(t, dateStrday));
		}
		
		addModel(model, "pageList", page);
		addModel(model, "bean", info);
		
		return "pc/channelInfo/list";
	}
	
	@RequestMapping("exportChannelData")
	public void exportChannelData(HttpServletResponse response)
	{
		channelInfoService.exportChannelData(response);
	}
	
	@RequestMapping("clearData")
	public void clearData(HttpServletResponse response)
	{
		channelInfoService.exportChannelData(response);
		channelInfoService.clearData();
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
		@SuppressWarnings("static-access")
		List<TDictionary> list=publicCache.getItemList("CHANNEL_TYPE");
		 super.writeJsonData(response, list);
   }
	
	@RequestMapping("activated")
	public void activated(HttpServletResponse response) {
		List<String> rest = udidService.importCsv();
		try {
			//0 tablename
			if(rest.get(0) != null) {
				appChannelAdverInfoService.activated(response,rest.get(0));
			}
		} catch (Exception e) {
		}
		
		JSONArray result = JSONArray.fromObject(rest);
		super.writeJsonData(response, result);
	}
	
	@RequestMapping("upload")
	public String upload(HttpServletResponse response){
		return "pc/channelInfo/upload";
	}
	
	@RequestMapping("saveFile")
	public void saveFile(HttpServletRequest request,HttpServletResponse response,MultipartFile udid, Integer isTest, String cookie) throws Exception{
		  String savePath = "C://Program Files//Apache Software Foundation//import//";
			// String filePath = savePath+udid.getName();
			 FileUtils upload = new FileUtils();
			 int upload_result=upload.uploadFile(udid,savePath,udid.getName() + ".csv","csv",request);
				//判断返回的结果  --显示给用户
			if (upload_result == 1 && isTest == 1) {
				//对udid进行分析是否有效
				List<TPhoneUdidModel> tPhoneUdidModels = udidService.getUdidFromFile();
				if(tPhoneUdidModels  != null && tPhoneUdidModels.size() > 2000) {
					//5000
					tPhoneUdidModels = tPhoneUdidModels.subList(0, 1999);
				}
				
				List<String> reustl = HttpRequestUtil.posts(tPhoneUdidModels, cookie);
				List<TPhoneUdidWithIdfa> ls = new ArrayList<TPhoneUdidWithIdfa>();
				for(String str : reustl) {
					JSONObject jsonObject = JSONObject.fromObject(str);
					JSONArray arr = (JSONArray) jsonObject.get("devices");
					if(arr.size()>0)
					{
						for(int i=0;i<arr.size();i++)
						{
							JSONObject job = arr.getJSONObject(i); 
							TPhoneUdidWithIdfa info = new TPhoneUdidWithIdfa();
							info.setUdid(job.get("deviceNumber").toString());
							if(job.get("model") == null) {
								info.setPhoneModel("未知");
							}else {
								info.setPhoneModel(job.get("model").toString());
							}
							
							ls.add(info);
						}
					}
				}
				
				channelInfoService.exprotPhoneUdid(response,ls);
			} else {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","channelInfo/list", "closeCurrent"));
			}
	}
	
	public static void main(String[] args) {
	}
}
