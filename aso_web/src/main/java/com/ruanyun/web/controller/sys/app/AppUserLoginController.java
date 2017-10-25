/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.model.UserAppModel;
import com.ruanyun.web.service.app.AppUserLoginService;

@Controller
@RequestMapping("app/user")
public class AppUserLoginController extends BaseController
{
	@Autowired
	private AppUserLoginService appUserLoginService;

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
			HttpSession session, String phoneSerialNumber, String userNum, String sign)
	{
		AppCommonModel acm = null;
		String ip = request.getRemoteAddr();
		try 
		{
			acm = appUserLoginService.addLogin(request, tUserLogin, phoneSerialNumber, ip);
		} 
		catch (Exception e) 
		{
			acm = new AppCommonModel(e.getMessage(), "{}");
		}
		
		super.writeJsonDataApp(response, acm);
	}
	
	/**
	 * 手机端接口:游客登录
	 */
	@RequestMapping("visitorLogin")
	public void visitorLogin(HttpServletResponse response,HttpServletRequest request,TUserApp tUserApp) 
	{
		AppCommonModel model = new AppCommonModel(-1, "登录失败！");
		String ip = request.getRemoteAddr();
		if(tUserApp == null || !StringUtils.hasText(tUserApp.getIdfa()))
		{
			model.setResult(-1);
			model.setMsg("请求参数idfa不能为空！");
		}
		else
		{
			try
			{
				//model = appUserLoginService.visitorLogin(request, tUserApp, ip);
				model.setResult(-1);
				model.setMsg("此版本未开放！");
			}
			catch (Exception e) 
			{
				model.setResult(-1);
				model.setMsg(e.getMessage());
			}
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
	@RequestMapping("send_msg")
	public void sendMsg(HttpServletResponse response, String loginName, String type) 
	{
		AppCommonModel model = null;
		try 
		{
			model = appUserLoginService.sendMsg(loginName, type);
		}
		catch (Exception e) 
		{
			logger.error("send_msg:"+e.getMessage());
			model = new AppCommonModel(-1,e.getMessage());
		}
		
		super.writeJsonDataApp(response, model);
	}	

	/**
	 * 
	 * 手机端接口:获取个人信息
	 * 
	 * @param response
	 * @param session
	 *@author feiyang
	 *@date 2016-1-14
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

	/**
	 * 
	 * 手机端接口:修改个人信息
	 * @param response
	 * @param tUserApp
	 * @param userNum
	 * @param sign
	 * @param picFile
	 *@author feiyang
	 *type 1/ 新手任务
	 *@date 2016-1-21
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
