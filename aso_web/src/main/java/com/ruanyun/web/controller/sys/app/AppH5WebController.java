package com.ruanyun.web.controller.sys.app;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.HUserAppModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserApprentice;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserScoreDetail;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.app.AppUserApprenticeService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.service.background.UserappidAdveridService;
import com.ruanyun.web.util.JsonDateValueProcessor;
import com.ruanyun.web.util.NumUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
public class AppH5WebController extends BaseController
{
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private AppChannelAdverInfoService appChannelAdverInfoService;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired	
	private AppUserApprenticeService appUserApprenticeService;
	@Autowired
	private UserappidAdveridService userappidAdveridService;
	
	@RequestMapping(value = "/main")
	public String mainPage(HttpServletRequest request)
	{
		return "app/h5web/main";
	}
	
	@RequestMapping(value = "/task")
	public String taskPage(HttpServletRequest request)
	{
		return "app/h5web/task";
	}
	
	@RequestMapping(value = "/taskDetail")
	public String taskPage(HttpServletRequest request, String taskID, Model model)
	{
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(taskID));
		addModel(model, "adverInfo", adverInfo);
		return "app/h5web/taskDetail";
	}
	
	@RequestMapping(value = "/user")
	public String user(HttpServletRequest request)
	{
		return "app/h5web/userinfo";
	}
	
	@RequestMapping(value = "/score")
	public String score(HttpServletRequest request, String id, Model model)
	{
		HUserAppModel userApp = userAppService.getHUserAppModelbyid(id);
		addModel(model, "HUserAppModel", userApp);
		return "app/h5web/putforword";
	}
	
	@RequestMapping(value = "/phone")
	public String phone(HttpServletRequest request)
	{
		return "app/h5web/phonebinding";
	}
	
	@RequestMapping(value = "/payfor")
	public String payfor(HttpServletRequest request)
	{
		return "app/h5web/payforbinding";
	}
	
	@RequestMapping(value = "/help")
	public String help(HttpServletRequest request)
	{
		return "app/h5web/userhelp";
	}
	
	@RequestMapping(value = "/invite")
	public String invite(HttpServletRequest request, String id, Model model)
	{
		addModel(model, "appuserid", id);
		String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, Integer.parseInt(id));
		TUserScore userScore = userScoreService.getScore(userNum);
		addModel(model, "appuserscore", userScore);
		
		return "app/h5web/invite";
	}
	
	@RequestMapping(value = "/scoredetail")
	public String scoredetail(HttpServletRequest request, Page<TUserScoreDetail> page, Page<TUserApprentice> pages, Model model)
	{
		page.setNumPerPage(Integer.MAX_VALUE);
		pages.setNumPerPage(Integer.MAX_VALUE);
		String appid = request.getParameter("id");
		String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, Integer.parseInt(appid));
		page = userappidAdveridService.queryUserScoreDetail(page, appid);
		//任务明细
		List<TUserScoreDetail>  userScoreDetail = page.getResult();
		//收徒 提现 的明细
		pages = appUserApprenticeService.getMyApprentices(pages, userNum);
		
		List<TUserApprentice>  tUserApprentices = pages.getResult();
		//type 0正常做任务得分  1邀请徒弟分红 2代表提现操作
		for(TUserApprentice userApprentice : tUserApprentices) {
			TUserScoreDetail userScoreDetail2 = new TUserScoreDetail();
			userScoreDetail2.setReceiveTime(userApprentice.getApprenticeTime());
			userScoreDetail2.setAdverPrice(userApprentice.getScore());
			userScoreDetail2.setTaskType(userApprentice.getUserApprenticeType() + "");
			if(userApprentice.getUserApprenticeType() == 1 || userApprentice.getUserApprenticeType() == 3) {
				userScoreDetail2.setAdverName(userApprentice.getApprenticeUserNum());//徒弟的usernum
			}
			
			userScoreDetail.add(userScoreDetail2);
		}
		
		Collections.sort(userScoreDetail); // 按receivetime 排序

		//JSONArray listArray = JSONArray.fromObject(userScoreDetail);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonObject = JSONArray.fromObject(userScoreDetail, jsonConfig); 
		
		addModel(model, "appuserscoredetails", jsonObject);
		
		return "app/h5web/scoredetail";
	}
	
	@RequestMapping(value = "/adverInfoLists")
	public void adverlist(HttpServletResponse response, HttpServletRequest request, 
			Page<TChannelAdverInfo>page, String udid)
	{
		AppCommonModel model = new AppCommonModel();
		
		TUserApp tUserApp = userAppService.getUserAppByUserName(udid);
		page.setNumPerPage(Integer.MAX_VALUE);
		
		if(tUserApp != null)
		{
			String[] isv = tUserApp.getPhoneVersion().split("\\.");
			String phonemodel = phoneModelChange(tUserApp.getPhoneModel());
			model = appChannelAdverInfoService.getAdverInfoByChannelNum2(page, "1", "2", phonemodel, tUserApp.getUserAppId(), isv[0]);
		}
		
		super.writeJsonDataApp(response, model);
	}
		
	private String  phoneModelChange(String phoneModel) 
	{
		switch (phoneModel)
		{
		  case "iPhone5,1":    
		  case "iPhone5,2":   
		  phoneModel= "iPhone5";
			  break;
		   case "iPhone5,3":
		   case"iPhone5,4":                
		   phoneModel = "iPhone5c";
			   break;
		   case "iPhone6,1":
		   case "iPhone6,2":                  
			   phoneModel = "iPhone5s";
			   break;
		   case "iPhone7,2":                           
			   phoneModel =  "iPhone6";
			   break;
		   case "iPhone7,1":                             
			   phoneModel =  "iPhone6" ; 
			   break;
		   case "iPhone8,1":                               
			   phoneModel =  "iPhone6s" ;
			   break;
		   case "iPhone8,2":                            
			   phoneModel =  "iPhone6s" ; 
			   break;
		   case "iPhone8,4":                             
			   phoneModel =  "iPhone6s" ; 
			   break;
			case "iPhone9,1":case "iPhone9,2": case "iPhone9,3":  case "iPhone9,4":  
				phoneModel =  "iPhone7";
				break;
			case "iPhone10,1": case "iPhone10,4": case "iPhone10,2": case "iPhone10,5":    
				phoneModel =  "iPhone8";
				break;
			case "iPhone10,3":case  "iPhone10,6":   
				phoneModel = "iPhoneX"; 
				break;
			case "iPhone11,2": case "iPhone11,4": case "iPhone11,6":    
				phoneModel =  "iPhoneXs"; 
				break;
			case "iPhone11,8":   
				phoneModel =  "iPhoneXr";
				break;
			default:
				if(phoneModel.compareTo("iPhone11,8") > 0) 
				{
					phoneModel =  "iPhoneXr";
				}
				else if(phoneModel.compareTo("iPhone5,1") < 0) 
				{
					phoneModel =  "iPhone4";
				}else {
					phoneModel =  "iPhone6";
				}
				
				break;
			}
		
			return phoneModel;
		}
}
