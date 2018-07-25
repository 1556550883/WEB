/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.controller.sys.app;

import java.util.Date;

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
	 * 功能描述:发送短信
	 * 
	 * @param response
	 * @param userNum
	 * @param user
	 * @param request
	 */
	@RequestMapping("send_msg")
	public void sendMsg(HttpServletResponse response, String loginName, HttpServletRequest request) 
	{
		AppCommonModel model = null;
		try 
		{
			if(PhoneFormatCheckUtils.isPhoneLegal(loginName)) 
			{
				model = appUserLoginService.sendMsg(loginName);
			}
			else
			{
				model = new AppCommonModel(-1,"错误的手机号码！");
			}
			
			TUserApp tUserApp = userAppService.getUserAppByUserName(loginName);
			
			if(model.getResult() == 1)
			{
				if(tUserApp == null) 
				{
					
					tUserApp = new TUserApp();
				}
				
				tUserApp.setUserApppType(2);
				tUserApp.setLoginName(loginName);
				tUserApp.setLoginPwd(model.getObj() + "");
				tUserApp.setLoginControl("1");
				tUserApp.setLevel(dictionaryService.getVestorLevel());
				tUserApp.setLimitTime(10);
				//tUserApp.setUserNick("手机号码注册登录");
				tUserApp.setCreateDate(new Date());
				
				userAppService.saveOrUpdate(tUserApp, request, null);
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
	public void updateUserWeiXin(HttpServletResponse response,String userNum,String weiXinName,String headImgUrl, String openID) 
	{
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateUserWeiXin(userNum,weiXinName,headImgUrl,openID);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("updateUserName")
	public void updateUserName(HttpServletResponse response,String userNum,String userName) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateUserName(userNum,userName);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		super.writeJsonDataApp(response, acm);
	}
	
	@RequestMapping("updateUserAlipay")
	public void updateUserAlipay(HttpServletResponse response,String userNum,String alipay) {
		AppCommonModel acm = new AppCommonModel();
		try {
			acm = appUserLoginService.updateUserAlipay(userNum, alipay);
		} catch (Exception e) {
			acm = new AppCommonModel(e.getMessage(), "{}");
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
	 *@author feiyang
	 *@date 2016-1-21
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
