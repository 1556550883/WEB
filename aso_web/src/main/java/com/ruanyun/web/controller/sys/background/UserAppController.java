/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.controller.sys.background;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverStepUser;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserApprentice;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserScoreInfo;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.producer.QueueProducer;
import com.ruanyun.web.service.app.AppUserApprenticeService;
import com.ruanyun.web.service.background.ChannelAdverStepUserService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserScoreInfoService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;
import com.ruanyun.web.util.JsonDateValueProcessor;
import com.ruanyun.web.util.NumUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/userApp")
public class UserAppController extends BaseController
{
	@Autowired	
	private UserAppService userAppService;
	
	@Autowired
	private UserScoreInfoService userScoreInfoService;
	
	@Autowired
	private ChannelAdverStepUserService channelAdverStepUserService;
	@Autowired	
	private AppUserApprenticeService appUserApprenticeService;
	/**
	 * 
	 * 
	 * 功能描述:手机用户列表
	 * @param page
	 * @param info
	 * @param model
	 */
	@RequestMapping("list")
	public String getUserAppList(Page<TUserApp> page,TUserApp info,Model model)
	{
		info.setUserApppType(1);
		page.setNumPerPage(20);
		page = userAppService.queryPage(page, info);
		
		addModel(model, "pageList", page);
		addModel(model, "bean", info);
		
		return "pc/userApp/list";
	}
	
	@RequestMapping("changeUserScore")
	public String changeUserScore(TUserApp info,Model model){
		String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, info.getUserAppId());
		TUserScore ss = new TUserScore();
		ss.setUserNum(userNum);
		addModel(model, "userScore", ss);
		
		return "pc/userApp/changeScore";
	}
	
	@RequestMapping("saveChangeUserScore")
	public void saveChangeUserScore(TUserScore userScore, Model model){
		userScore.setType(6);
		try {
			QueueProducer.getQueueProducer().sendMessage(userScore, "socre");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	String phoneModelChange(String phoneModel) 
	{
		if(phoneModel == null) {
			return "";
		}
		
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
	
	@RequestMapping("effUserList")
	public String getEffUserAppList(Page<TUserApp> page,TUserApp info,Model model)
	{
		info.setUserApppType(2);
		page.setNumPerPage(20);
		page = userAppService.queryPage(page, info);
		
		for(TUserApp userApp : page.getResult()) {
			userApp.setPhoneModel(phoneModelChange(userApp.getPhoneModel()));
		}
		addModel(model, "pageList", page);
		addModel(model, "bean", info);
		
		return "pc/userApp/effUserList";
	}
	
	@RequestMapping("exportScore")
	public void exportScore(HttpServletResponse response, Page<TUserApp> page,TUserApp info)
	{
		page.setNumPerPage(Integer.MAX_VALUE);
		info.setUserApppType(1);
		page = userAppService.queryPage(page, info);
		userAppService.exportScore(response, page.getResult(), false);
	}
	
	@RequestMapping("clearWorkScore")
	public void clearScore(HttpServletResponse response, Page<TUserApp> page,TUserApp info)
	{
		info.setUserApppType(1);
		page.setNumPerPage(Integer.MAX_VALUE);
		page = userAppService.queryPage(page, info);
		userAppService.exportScore(response, page.getResult(), true);
		userAppService.clearScore();
	}
	
	
	@RequestMapping("removeMaster")
	public void removeMaster(HttpServletResponse response, String userAppId, String masterID)
	{
		userAppService.removeMaster(userAppId, masterID);
	}
	
	/**
	 * 
	 * 功能描述:积分明细
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
	@RequestMapping("getScoreInfoList")
	public String getScoreInfoList(Page<TUserScoreInfo> page,TUserScoreInfo info,Model model,String userAppNum){
		addModel(model, "pageList", userScoreInfoService.pageSql(page, info));
		addModel(model, "userAppNum", userAppNum);
		return "pc/userApp/scoreinfo";
	}
	
	/**
	 * 
	 * 功能描述:积分明细
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping("delScoreInfo")
	public void delScoreInfo(String userScoreInfoNum,Integer pageNum,String userAppNum,Model model,HttpServletResponse response){
		userScoreInfoService.deleteScoreInfo(userScoreInfoNum, userAppNum);
		//+userAppNum+"&pageNum="+pageNum
		super.writeJsonData(response,CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_index2", "userApp/getScoreInfoList", "redirect"));
	}
	
	
	/**
	 * 
	 * 功能描述:下载记录
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
	@RequestMapping("getStepUserList")
	public String getStepUserList(Page<TChannelAdverStepUser> page,TChannelAdverStepUser info,Model model){
		addModel(model, "pageList", channelAdverStepUserService.pageSql(page, info));
		addModel(model, "bean", info);
		return "pc/userApp/stepuser";
	}
	
	/**
	 * 功能描述:修改用户信息
	 */
	@RequestMapping("/saveOrUpdate")
	public void saveOrUpdate(TUserApp userApp,HttpServletResponse response,HttpServletRequest request,Model model,MultipartFile picFile)
	{
//		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userAppService.saveOrUpdate(userApp, request, picFile);
		if (result==1)
		{
			super.writeJsonData(response,CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "userApp/list", "redirect"));
		}
		else if (result==2)
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "登录名重复", "", "", ""));
		}
		else
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	/**
	 * 功能描述：绑定金融账号
	 */
	@RequestMapping("/bindFinancialAccount")
	public void bindFinancialAccount(HttpServletResponse response,HttpServletRequest request,Integer userAppId,String accountType,String accountNo){
		AppCommonModel model = new AppCommonModel(1, "绑定成功！");
		
		if(userAppId == null || !StringUtils.hasText(accountType) || !StringUtils.hasText(accountNo)){
			model.setResult(-1);
			model.setMsg("请求参数userAppId、accountType、accountNo不能为空！");
		}else{
			TUserApp tUserApp = userAppService.getUserAppById(userAppId);
			if(tUserApp == null){
				model.setResult(-1);
				model.setMsg("绑定金融账号失败！您可能还没有登录。");
			}else{
				if("1".equals(accountType)){
					tUserApp.setZhifubao(accountNo);
				}else if("2".equals(accountType)){
					tUserApp.setWeixin(accountNo);
				}
				tUserApp = userAppService.update(tUserApp);
				if(tUserApp == null){
					model.setResult(-1);
					model.setMsg("保存失败！");
				}
			}
		}
		
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 功能描述:进入修改页面
	 */
	@RequestMapping("/toEdit")
	public String userAppEdit(TUserApp userApp, Model model)
	{
		if (EmptyUtils.isNotEmpty(userApp.getUserNum()))
		{
			TUserApp tUserApp = userAppService.get(TUserApp.class, "userNum", userApp.getUserNum());
			addModel(model, "bean", tUserApp);
			//addModel(model, "userScoreInfo", userScoreInfoService.get(TUserStudentCart.class, "userAppNum", userApp.getUserNum()));
			addModel(model, "adverAuthoritys", userAppService.queryCurrentAdverList(tUserApp.getExcludeAdverId()));
		}
		
		return "pc/userApp/edit";
	}
	
	/**
	 * 徒弟
	 */
	@RequestMapping("/apprenticeList")
	public String apprenticeList(Page<TUserApp> page, TUserApp userApp, Model model)
	{
		if (EmptyUtils.isNotEmpty(userApp.getUserAppId()))
		{
			page.setNumPerPage(Integer.MAX_VALUE);
			page = userAppService.queryUserAppByMasterID(page, userApp.getUserAppId() + "");
			
			for(TUserApp user : page.getResult()) {
				userApp.setPhoneModel(phoneModelChange(user.getPhoneModel()));
			}
		}
		
		addModel(model, "pageList", page);
		
		return "pc/userApp/apprenticeList";
	}
	
	//师傅根据id
	@RequestMapping("/getmaster")
	public String getmaster(Page<TUserApp> page, TUserApp userApp, Model model)
	{
		if (EmptyUtils.isNotEmpty(userApp.getUserAppId()))
		{
			page.setNumPerPage(Integer.MAX_VALUE);
			page = userAppService.queryMasterUserAppByID(page, userApp.getUserAppId() + "");
			for(TUserApp user : page.getResult()) {
				userApp.setPhoneModel(phoneModelChange(user.getPhoneModel()));
			}
		}
		
		addModel(model, "pageList", page);
		
		return "pc/userApp/apprenticeList";
	}
	
	@RequestMapping("/getUserByUserNum")
	public String getUserByUserNum(Page<TUserApp> page, TUserApp userApp, Model model)
	{
		if (EmptyUtils.isNotEmpty(userApp.getUserNum()))
		{
			page.setNumPerPage(Integer.MAX_VALUE);
			page = userAppService.getUserByUserNum(page, userApp.getUserNum());
			for(TUserApp user : page.getResult()) {
				userApp.setPhoneModel(phoneModelChange(user.getPhoneModel()));
			}
		}
		
		addModel(model, "pageList", page);
		
		return "pc/userApp/apprenticeList";
	}
	
	@RequestMapping("/apprenticeScoreList")
	public String apprenticeScoreList(Page<TUserApprentice> page, TUserApp userApp, Model model)
	{
		if (EmptyUtils.isNotEmpty(userApp.getUserAppId()))
		{
			page.setNumPerPage(Integer.MAX_VALUE);
			String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, userApp.getUserAppId());
			page = appUserApprenticeService.getMyApprentices(page, userNum);
		}
		
		addModel(model, "pageList", page);
		
		return "pc/userApp/apprenticeScoreList";
	}
	
	
	@RequestMapping("/putwardList")
	public String putwardList(Page<TUserScoreInfo> page, TUserApp userApp, Model model)
	{
		if (EmptyUtils.isNotEmpty(userApp.getUserAppId()))
		{
			TUserScoreInfo info  = new TUserScoreInfo();
			String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, userApp.getUserAppId());
			info.setUserAppNum(userNum);
			page.setNumPerPage(Integer.MAX_VALUE);
			Page<TUserScoreInfo> userScoreInfos = userScoreInfoService.getforwardList(page, info);
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			JSONArray jsonObject = JSONArray.fromObject(userScoreInfos.getResult(), jsonConfig); 
			addModel(model, "userScoreInfos", jsonObject);
			addModel(model, "pageList", userScoreInfos);
		}
		
		return "pc/userApp/forwardList";
	}
	
	/**
	 * 功能描述:批量删除
	 * @param response
	 * @param request
	 * @param ids
	 */
	@RequestMapping("del")
	public void delete(HttpServletResponse response,HttpServletRequest request,String ids){
		int result = 0;
		if(EmptyUtils.isNotEmpty(ids)){
			result = userAppService.delete(ids);
		}
		if(result > 0){
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","userApp/list", "redirect"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	/**
	 * 功能描述:将学生用户设置为普通用户
	 * @author wsp  2017-1-19 下午03:53:02
	 * @param response
	 * @param request
	 * @param session
	 * @param ids
	 */
	@RequestMapping("updateUserApp")
	public void updateUserApp(HttpServletResponse response,HttpServletRequest request,HttpSession session,String ids){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result = 0;
		if(EmptyUtils.isNotEmpty(ids)){
			result = userAppService.updateUserApp(ids,currentUser);
		}
		if(result > 0){
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","userApp/list", "redirect"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
}
