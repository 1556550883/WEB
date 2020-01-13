/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.controller.sys.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.service.app.AppUserLoginService;
import com.ruanyun.web.service.background.DictionaryService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.util.PhoneFormatCheckUtils;

@Controller
@RequestMapping("app/user")
public class AppUserLoginController extends BaseController
{
	@Autowired
	private AppUserLoginService appUserLoginService;
	@Autowired	
	private UserAppService userAppService;
	@Autowired
	private DictionaryService dictionaryService;
	/**
	 * 
	 * 手机端接口:登陆和第三方登陆
	 * 
	 * @param tUserLogin
	 * @param request
	 * @param response
	 *            LoginType 登录类型 1--账号登陆 2-QQ登陆 3-微信登陆 4--微博登陆 5-- 游客登录 手机唯一序列号
	 *            phoneSerialNumber 手机序列号
	 * @param session 
	 */
	@RequestMapping("login")
	public void doLogin(HttpServletResponse response, HttpServletRequest request, TUserLogin tUserLogin,
			HttpSession session, String phoneSerialNumber, String userNum, String sign, String masterID)
	{
		AppCommonModel acm = null;
		String ip = request.getRemoteAddr();
		try 
		{
			acm = appUserLoginService.addLogin(request, tUserLogin, phoneSerialNumber, ip, masterID);
		} 
		catch (Exception e) 
		{
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	/**
	 * 查询系统参数
	 */
	@RequestMapping("getSystemParameter")
	public void getSystemParameter(HttpServletResponse response, String phoneType) 
	{
		AppCommonModel model = new AppCommonModel();
		
		try
		{
			Map<String,Object> map = new HashMap<String,Object>(4);
			map.put("appleIdCheck", dictionaryService.getAppleIdCheck());
			map.put("leastTaskTime", dictionaryService.getLeastTaskTime());
			map.put("leastForward", dictionaryService.getLeastForward());
			map.put("notice", dictionaryService.getNotice());
			map.put("downloadUrl", dictionaryService.getDownloadUrl());
			map.put("appVersion", dictionaryService.getAppVersion());
			map.put("idfaCheck", dictionaryService.getIdfaCheck());
			map.put("phoneModelPercent", dictionaryService.getPhoneModelPercent());
			map.put("openApplication", dictionaryService.getOpenApplication());
			model.setObj(map);
			model.setResult(1);
			model.setMsg("成功！");
		}
		catch (Exception e) 
		{
			model.setResult(-1);
			model.setMsg("出错！");
		}
		
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 功能描述:发送短信
	 * 
	 * @param response
	 * @param userNum
	 * @param user
	 * @param request
	 */
//	@RequestMapping("sendmsg")
//	public void sendMsg(HttpServletResponse response, String phoneNumber, HttpServletRequest request) 
//	{
//		AppCommonModel model = null;
//		try 
//		{
//			if(PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)) 
//			{
//				model = appUserLoginService.sendMsg(phoneNumber);
//			}
//			else
//			{
//				model = new AppCommonModel(-1,"错误的手机号码！");
//			}
//			
//			TUserApp tUserApp = userAppService.getUserAppByUserName(udid);
//			
//			if(model.getResult() == 1)
//			{
//				String smsCode =  model.getObj() + "";
//				
//				userAppService.updateSmsCode(request, tUserApp, smsCode);
//			}
//		}
//		catch (Exception e) 
//		{
//			logger.error("send_msg:"+e.getMessage());
//			model = new AppCommonModel(-1,e.getMessage());
//		}
//		
//		model.setObj("{}");
//		super.writeJsonDataApp(response, model);
//	}	
	
	
	
	/**
	 * 功能描述:发送短信
	 * 
	 * @param response
	 * @param userNum
	 * @param user
	 * @param request
	 */
	@RequestMapping("send_msg")
	public void sendMsg(HttpServletResponse response,String udid, String phoneNumber, HttpServletRequest request) 
	{
		AppCommonModel model = null;
		try 
		{
			if(PhoneFormatCheckUtils.isPhoneLegal(phoneNumber)) 
			{
				model = appUserLoginService.sendMsg(phoneNumber);
			}
			else
			{
				model = new AppCommonModel(-1,"错误的手机号码！");
			}
			
			TUserApp tUserApp = userAppService.getUserAppByUserName(udid);
			
			if(model.getResult() == 1)
			{
				String smsCode =  model.getObj() + "";
				
				userAppService.updateSmsCode(request, tUserApp, smsCode);
			}
		}
		catch (Exception e) 
		{
			logger.error("send_msg:"+e.getMessage());
			model = new AppCommonModel(-1,e.getMessage());
		}
		
		model.setObj("{}");
		super.writeJsonDataApp(response, model);
	}	

	@RequestMapping("verifySmsCode")
	public void verifySmsCode(HttpServletResponse response,String udid, String phoneNumber, String smsCode,HttpServletRequest request) 
	{
		AppCommonModel model = new AppCommonModel();
		if(phoneNumber == null || smsCode == null) 
		{
			model.setResult(-1);
			model.setMsg("号码和验证码不能为空！");
			model.setObj("{}");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TUserApp tUserApp = userAppService.getUserAppByUserName(udid);
		
		if( tUserApp.getInvitationCode() != null && tUserApp.getInvitationCode().equals(smsCode)) 
		{
			try 
			{
				int code = userAppService.updatePhoneNum(request, tUserApp, phoneNumber);
				if(code == -1) {
					model.setResult(-1);
					model.setMsg("查询反羊毛服务失败，请稍后再试！");
					model.setObj("{}");
				}else {
					model.setResult(1);
					model.setMsg("绑定成功！");
					model.setObj("{}");
				}
			}
			catch (Exception e) 
			{
				model.setResult(-1);
				model.setMsg("请求失败，请重新尝试！");
				model.setObj("{}");
			}
		}
		else
		{
			model.setResult(-1);
			model.setMsg("号码和验证码不能为空！");
			model.setObj("{}");
		}
		
		super.writeJsonDataApp(response, model);
	}
	/**
	 * 
	 * 手机端接口:获取个人信息
	 * 
	 * @param response
	 * @param session
	 */
	@RequestMapping("getUser")
	public void getUser(HttpServletResponse response, TUserApp tUserApp,
			String userNum, String sign) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.getUser(tUserApp);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}

	@RequestMapping("getUserForUdid")
	public void getUserForUdid(HttpServletResponse response, String udid) 
	{
		AppCommonModel acm = new AppCommonModel();
		try
		{
			acm = appUserLoginService.getUserForUdid(udid);
		}
		catch (Exception e) 
		{
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("updateUserByUdid")
	public void updateUserByUdid(HttpServletRequest request , HttpServletResponse response, String udid, String idfa, String phoneModel, String phoneVersion) 
	{
		AppCommonModel acm = new AppCommonModel();
		try 
		{
			//创建用户登录session
			acm = appUserLoginService.updateUserByUdid(udid, idfa, phoneModel, phoneVersion);
		} 
		catch (Exception e)
		{
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("invite")
	public void invite(HttpServletResponse response,String id)
	{
		AppCommonModel acm = new AppCommonModel();
		try 
		{
		} 
		catch (Exception e)
		{
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	/**
	 * 
	 * 手机端接口:修改个人信息
	 * @param response
	 * @param tUserApp
	 * @param userNum
	 * @param sign
	 * @param picFile
	 */
	@RequestMapping("updateUser")
	public void updateUser(HttpServletResponse response, TUserApp tUserApp,String userNum,String sign,Integer type) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateUser(tUserApp,type);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("updateUserWeiXin")
	public void updateUserWeiXin(HttpServletResponse response,String udid,String weiXinName,String headImgUrl, String openID) 
	{
		AppCommonModel acm = new AppCommonModel();
		
		try
		{
			acm = appUserLoginService.updateUserWeiXin(udid,weiXinName,headImgUrl,openID);
		}
		catch (Exception e) 
		{
			acm.setMsg("请求失败，请重新尝试！");
			acm.setResult(-1);
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	
	@RequestMapping("getWeChatApi")
	public void getWeChatApi(HttpServletResponse response,String udid, String accessToken,String openID) 
	{
		AppCommonModel acm = new AppCommonModel();
		
		try
		{
			acm = appUserLoginService.getWeChatApi(udid, accessToken,openID);
		}
		catch (Exception e) 
		{
			acm.setMsg("请求失败，请重新尝试！");
			acm.setResult(-1);
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("updateUserName")
	public void updateUserName(HttpServletResponse response,String userNum,String userName)
	{
		AppCommonModel acm = new AppCommonModel();
		try 
		{
			acm = appUserLoginService.updateUserName(userNum,userName);
		}
		catch (Exception e)
		{
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("updateUserAlipay")
	public void updateUserAlipay(HttpServletResponse response, String alipay, String udid,String usernick) 
	{
		AppCommonModel acm = new AppCommonModel();
		try 
		{
			acm = appUserLoginService.updateUserAlipay(alipay, udid,usernick);
		} 
		catch (Exception e)
		{
			acm.setMsg("请求失败，请重新尝试！");
			acm.setResult(-1);
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("tixianRequest")
	public void tixianRequest(HttpServletResponse response,String userNum) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.tixianRequest(userNum);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("updateUserPhoneNum")
	public void updateUserPhoneNum(HttpServletResponse response,String userNum,String phoneNum) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateUserPhoneNum(userNum,phoneNum);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
	/**
	 * 
	 * 功能描述:修改个人头像
	 * @param request
	 * @param response
	 * @param tUserApp
	 * @param userNum
	 * @param sign
	 * @param picFile
	 */
	@RequestMapping("updateUserHeadImg")
	public void updateUserHeadImg(HttpServletRequest request,HttpServletResponse response, TUserApp tUserApp, String userNum,
			String sign, MultipartFile picFile) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateUserHeadImg(request, picFile, tUserApp);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
	/**
	 * 功能描述:修改苹果账号
	 * @author wsp  2017-1-20 下午03:10:59
	 * @param response
	 * @param userNum
	 * @param appStore
	 */
	@RequestMapping("updateAppStore")
	public void updateAppStore(HttpServletResponse response, String userNum,String appStore) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateAppStore(userNum, appStore);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	

	
	/**
	 * 
	 * 手机端接口:修改密码
	 * @param response
	 * @param tUserLogin
	 * @param newPassword
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-21
	 */
	@RequestMapping("updatePassword")
	public void updatePassword(HttpServletResponse response, TUserLogin tUserLogin,String newPassword, String userNum,String sign) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updatePassword(tUserLogin, newPassword);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("resetPassword")
	public void updatePassword(HttpServletResponse response, String loginName,String newPassword, String userNum,String sign) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.resetPassword(loginName, newPassword);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	/**
	 * 功能描述: 注册码
	 *
	 * @author yangliu  2016年1月19日 下午2:18:44
	 * 
	 * @param invintNum
	 * @param userNum
	 * @param sign
	 * @param response
	 */
	@RequestMapping("updateInvited")
	public void updateUserApprentice(String invintNum,String userNum,String sign,HttpServletResponse response){
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateUserApprentice(invintNum,userNum);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
		
	}
	/**
	 * 
	 * 手机端接口:根据手机 序列号一键注册
	 * @param response
	 * @param tUserApp
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-18
	 */
	@RequestMapping("addUser")
	public void registerByPhoneSerialNumber(HttpServletResponse response,TUserApp tUserApp,TUserLogin userLogin,String parentUserId){
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.addUserByPhoneSerialNumber(tUserApp,userLogin,parentUserId);			
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
}
