/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.service.app;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.UserLoginDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserApprentice;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.model.UserAppModel;
import com.ruanyun.web.model.sys.UploadVo;
import com.ruanyun.web.service.background.LoginIpService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserApprenticeService;
import com.ruanyun.web.service.background.UserLoginService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;
import com.ruanyun.web.util.SendSms;
import com.ruanyun.web.util.UploadCommon;

@Service
public class AppUserLoginService extends BaseServiceImpl<TUserLogin> 
{
	@Autowired
	private UserLoginDao userLoginDao;
	@Autowired
	private UserApprenticeService userApprenticeService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private LoginIpService loginIpService;

	/**
	 * 
	 * 功能描述:登陆和第三方登陆
	 * 
	 * @param tUserLogin
	 * @param request
	 *            LoginType 登录类型 1--账号登陆 2-QQ登陆 3-微信登陆 4--微博登陆 5-- 游客登录 手机唯一序列号
	 * @return
	 */
	public AppCommonModel addLogin(HttpServletRequest request, TUserLogin tUserLogin, String phoneSerialNumber,String ip, String masterID) 
	{

		AppCommonModel model = new AppCommonModel(-1, "登录失败！");

		if (EmptyUtils.isNotEmpty(tUserLogin.getLoginType())) 
		{
			TUserLogin user = userLoginDao.getUserByLoginName(tUserLogin.getLoginName(), tUserLogin.getLoginType());
			if (EmptyUtils.isEmpty(user)) 
			{
				model.setMsg("用户名不存在.");
				return model;
			}
			
			TUserApp userApp = appUserService.getUserByUserNum(user.getUserNum());
			
			if (userApp.getUserApppType() == 1 && !user.getPassword().equals(tUserLogin.getPassword()))
			{
				model.setMsg("密码错误.");
				return model;
			}
			
			if ("0".equals(userApp.getLoginControl())) 
			{
				model.setMsg("该用户被禁止登录.");
				return model;
			}
			
			if (EmptyUtils.isNotEmpty(userApp))
			{
				if (userApp.getUserApppType() == 2 && userApp.getPhoneSerialNumber() != null && userApp.getPhoneSerialNumber().equals("-1"))
				{
					userApp.setPhoneSerialNumber(phoneSerialNumber); // 如果序列号不一致就更改序列号
					appUserService.update(userApp);
				}
				else if(userApp.getUserApppType() == 2 && userApp.getPhoneSerialNumber() != null && !phoneSerialNumber.equals(userApp.getPhoneSerialNumber()))
				{
					model.setMsg("手机广告标识已被修改,请联系管理员！");
					return model;
				}
			}
			
			UserAppModel appModel = userLoginDao.getUserModelByNum(user.getUserNum());
			appModel.setLoginName(user.getLoginName());
			appModel.setLoginType(user.getLoginType());
			appModel.setUserNum(user.getUserNum());
			appModel.setUserApppType(userApp.getUserApppType());
			appModel.setWeChatHeadUrl(userApp.getFlag5());
			appModel.setWeixin(userApp.getWeixin());
			appModel.setPhoneNum(userApp.getPhoneNum());
			appModel.setMasterIDExisted(EmptyUtils.isNotEmpty(userApp.getMasterID()));
			
//			if(EmptyUtils.isNotEmpty(userApp.getMasterID())) 
//			{
//				model.setMsg("禁止重复添加邀请人！");
//			}
//			else
//			{
//				if(userApp.getUserApppType() == 2)
//				{
//					
//					userAppService.updateMaterID(request,userApp,masterID);
//				}
//			}
			
			model.setObj(appModel);
			userAppService.updateIp(request,userApp,ip);//保存最近登录ip地址
			loginIpService.saveOrUpdate(request,ip);//记录登录IP地址
		}
		else 
		{
			return model;
		}
		
		model.setResult(1);
		model.setMsg("登录成功！");
		return model;
	}
	
	/**
	 * 功能描述:游客登录
	 */
	@Transactional(rollbackFor = Exception.class)
	public AppCommonModel visitorLogin(HttpServletRequest request, TUserApp tUserApp, String ip) 
	{
		AppCommonModel model = new AppCommonModel(-1, "登录失败！");
		
		String idfa = tUserApp.getIdfa();
		tUserApp = appUserService.get(TUserApp.class, "idfa", idfa);
		if (tUserApp == null)
		{
			//创建游客用户
			tUserApp = new TUserApp();
			tUserApp.setUserApppType(2);
			tUserApp.setLoginName((int)(1000 + Math.random() * 9000) + "");
			
			while(true) 
			{
				TUserApp app = super.get(TUserApp.class, "loginName", tUserApp.getLoginName());
				if(EmptyUtils.isNotEmpty(app))
				{
					tUserApp.setLoginName((int)(1000 + Math.random() * 9000) + "");
				}
				else 
				{
					break;
				}
			}
			
			tUserApp.setIdfa(idfa);
			tUserApp.setCreateDate(new Date());
			tUserApp = userAppService.save(tUserApp);
			tUserApp.setUserNum(NumUtils.getCommondNum(NumUtils.USER_APP_NUM, tUserApp.getUserAppId()));
			//保存用户登录
			TUserLogin userLogin = new TUserLogin();
			userLogin.setUserNum(tUserApp.getUserNum());
			userLogin.setPassword(tUserApp.getLoginPwd());
			userLogin.setLoginName(tUserApp.getLoginName());
			userLogin.setLoginType(1);
			userLoginService.save(userLogin);
			
			//保存用户积分
			userScoreService.addNewUserScore(tUserApp.getUserNum(), 2);//手机app
		}
		
		UserAppModel appModel = userLoginDao.getUserModelByNum(tUserApp.getUserNum());
		appModel.setUserAppId(tUserApp.getUserAppId());
		appModel.setLoginName(tUserApp.getLoginName());
		appModel.setLoginType(2);
		appModel.setUserApppType(2);

		userAppService.updateIp(request, tUserApp, ip);//保存最近登录ip地址
		loginIpService.saveOrUpdate(request, ip);//记录登录IP地址		
		model.setObj(appModel);
		model.setResult(1);
		model.setMsg("登录成功！");
		
		return model;
	}
	
	public AppCommonModel sendMsg(String loginName) 
	{
		int random = (int) (1000 + Math.random() * 9000);
		SendSms.sendMessage(loginName, random);
		
		return new AppCommonModel(1, "", random);
	}

	/**
	 * 
	 * 功能描述:根据NUM和序列号获取个人信息
	 * 
	 * @param tUserApp
	 * @return
	 * @author feiyang
	 * @date 2016-1-14
	 */
	public AppCommonModel getUser(TUserApp tUserApp) {
		AppCommonModel model = new AppCommonModel(-1, "");
		if (EmptyUtils.isNotEmpty(tUserApp)) {
			TUserApp userApp = appUserService.getUserBySerialNum(tUserApp.getPhoneSerialNumber());
			TUserApp userApp1 = appUserService.getUserByUserNum(tUserApp.getUserNum());
			if (EmptyUtils.isNotEmpty(userApp)) {
				UserAppModel appModel = userLoginDao.getUserModelByNum(userApp.getUserNum());
				model.setResult(1);
				model.setObj(appModel);
			} else if (EmptyUtils.isNotEmpty(userApp1)) {
				UserAppModel appModel = userLoginDao.getUserModelByNum(userApp1.getUserNum());
				model.setResult(1);
				model.setObj(appModel);
			} else {
				model.setMsg("用户不存在");
			}
		}
		return model;
	}

	public AppCommonModel getUserForUdid(String udid, String idfa)
	{
		AppCommonModel model = new AppCommonModel(-1, "");
		if (udid == null) 
		{
			model.setMsg("failed");
			return model;
		}
		
		TUserLogin user = userLoginDao.getUserByLoginName(udid, 1);
		
		if(user != null)
		{
			TUserApp userApp = appUserService.getUserByUserNum(user.getUserNum());

			if ("0".equals(userApp.getLoginControl())) 
			{
				model.setMsg("该用户被禁止登录.");
				return model;
			}
			
			UserAppModel appModel = userLoginDao.getUserModelByNum(user.getUserNum());
			
			if (EmptyUtils.isNotEmpty(userApp))
			{
				if (userApp.getUserApppType() == 2 && userApp.getIdfa() == null)
				{
					userAppService.updateIdfa(userApp, idfa);
				}
				else if(userApp.getUserApppType() == 2 && userApp.getIdfa() != null && !idfa.equals(userApp.getIdfa()))
				{
					model.setMsg("手机广告标识已被修改,请联系管理员！");
					return model;
				}
			}
			appModel.setLoginName(user.getLoginName());
			appModel.setLoginType(user.getLoginType());
			appModel.setUserNum(user.getUserNum());
			appModel.setUserApppType(userApp.getUserApppType());
			appModel.setWeChatHeadUrl(userApp.getFlag5());
			appModel.setWeixin(userApp.getWeixin());
			appModel.setPhoneNum(userApp.getPhoneNum());
			appModel.setMasterIDExisted(EmptyUtils.isNotEmpty(userApp.getMasterID()));
			model.setObj(appModel);
			model.setResult(1);
			model.setMsg("success");
		} 
		else
		{
			model.setMsg("用户不存在");
		}
		
		return model;
	}
	/**
	 * 
	 * 手机端接口:修改头像
	 * 
	 * @param request
	 * @param picFile
	 * @param tUserApp
	 * @return
	 */
	public AppCommonModel updateUserHeadImg(HttpServletRequest request, MultipartFile picFile, TUserApp tUserApp) {
		AppCommonModel model = new AppCommonModel(-1, "修改头像失败");
		try {
			TUserApp oldUserApp = appUserService.getUserByUserNum(tUserApp.getUserNum());
			if (EmptyUtils.isNotEmpty(oldUserApp)) {
				if (picFile.getSize() != 0) {
					UploadVo vo = UploadCommon.uploadPic(picFile, request, Constants.FILE_IMG, "gif,jpg,jpeg,bmp,png");
					if (vo.getResult() == 1) {
						oldUserApp.setHeadImg(vo.getFilename());
					}
				}
				userAppService.update(oldUserApp);
				model.setResult(1);
				model.setMsg("修改成功");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return model;
	}

	/**
	 * 
	 * 手机端接口：修改密码
	 * 
	 * @param tUserLogin
	 * @param newPassword
	 * @return
	 */
	public AppCommonModel updatePassword(TUserLogin tUserLogin, String newPassword) {
		AppCommonModel model = new AppCommonModel(-1, "修改失败");
		TUserLogin user = userLoginDao.getUserByUserNum(tUserLogin.getUserNum(), 1);
		if (EmptyUtils.isNotEmpty(user)) {
			user.setPassword(newPassword);
			update(user);
			model.setResult(1);
			model.setMsg("修改成功");
		}
		return model;
	}

	@Transactional
	public AppCommonModel resetPassword(String loginName, String newPassword) {
		AppCommonModel model = new AppCommonModel(-1, "修改失败");
		TUserLogin user = userLoginDao.getUserByLoginName(loginName, 1);
		if (EmptyUtils.isNotEmpty(user)) {
			user.setPassword(newPassword);
			update(user);
			model.setResult(1);
			model.setMsg("修改成功");
		}
		return model;
	}

	/**
	 * 
	 * 功能描述:修改个人信息
	 * 
	 * @param tUserApp
	 * @param tUserLogin
	 * @param newPassword
	 *            \type 1 是新手任务
	 * @return
	 */
	public AppCommonModel updateUser(TUserApp tUserApp, Integer type) {
		AppCommonModel model = new AppCommonModel(-1, "修改失败");
		TUserApp oldUserApp = appUserService.getUserByUserNum(tUserApp.getUserNum());
		if (EmptyUtils.isNotEmpty(oldUserApp)) {
			if (EmptyUtils.isNotEmpty(tUserApp.getBirthday()))
				oldUserApp.setBirthday(tUserApp.getBirthday());
			if (EmptyUtils.isNotEmpty(tUserApp.getUserNick()))
				oldUserApp.setUserNick(tUserApp.getUserNick());
			if (EmptyUtils.isNotEmpty(tUserApp.getSex()))
				oldUserApp.setSex(tUserApp.getSex());
			if (EmptyUtils.isNotEmpty(tUserApp.getPhoneNum())) {
				oldUserApp.setPhoneNum(tUserApp.getPhoneNum());
			}
			if (EmptyUtils.isNotEmpty(tUserApp.getWeixin())) {
				oldUserApp.setWeixin(tUserApp.getWeixin());
			}
			if (EmptyUtils.isNotEmpty(tUserApp.getZhifubao())) {
				oldUserApp.setZhifubao(tUserApp.getZhifubao());
			}
			if (EmptyUtils.isNotEmpty(tUserApp.getZhifubaoName())) {
				oldUserApp.setZhifubaoName(tUserApp.getZhifubaoName());
			}

			userAppService.update(oldUserApp);
			appUserService.update(oldUserApp);
			model.setMsg("修改成功");
			model.setResult(1);
			if (oldUserApp.getTaskNewStatus() == 1) {
				if (EmptyUtils.isNotEmpty(oldUserApp.getUserNick()) && EmptyUtils.isNotEmpty(oldUserApp.getBirthday())
						&& EmptyUtils.isNotEmpty(oldUserApp.getSex())
						&& EmptyUtils.isNotEmpty(oldUserApp.getHeadImg())) {
					oldUserApp.setTaskNewStatus(2);
					userScoreService.addScoreSum(tUserApp.getUserNum(), "新手任务奖励", Constants.USER_SCORE_INFO_TYPE_10,
							Constants.TASK_NEW_NO, Constants.USER_TYPE_APP);
					model.setMsg("新手任务完成");
				}
			}
		}
		return model;
	}

	
	
	/**
	 * 
	 * 功能描述:根据手机 序列号一键注册
	 * 
	 * @param tUserApp
	 * @return
	 * @author feiyang
	 * @date 2016-1-18
	 */
	public AppCommonModel addUserByPhoneSerialNumber(TUserApp tUserApp, TUserLogin userLogin, String parentUserId) {
		AppCommonModel model = new AppCommonModel(1, "");
		TUserApp userApp = appUserService.getUserBySerialNum(tUserApp.getPhoneSerialNumber());
		if (EmptyUtils.isNotEmpty(userApp)) {
			userApp.setPhoneNum("");
			appUserService.update(userApp);
		}

		TUserLogin userLoginOld = userLoginService.getUserLogin(userLogin.getLoginName(), 1);
		if (EmptyUtils.isNotEmpty(userLoginOld)) {
			UserAppModel appModel = userLoginDao.getUserModelByNum(userApp.getUserNum());
			model.setMsg("该手机号已经注册过");
			model.setResult(2);
			model.setObj(appModel);
			return model;
		}
		TUserApp parentUserApp = updateUserApprentice(parentUserId);
		tUserApp.setCreateDate(new Date());
		tUserApp.setUserApppType(Constants.USER_APPP_TYPE_1);// 普通用户
		tUserApp.setTaskNewStatus(1);// 新手任务默认未完成
		appUserService.save(tUserApp);
		tUserApp.setUserNum("1" + NumUtils.getCommondUserNum(tUserApp.getUserAppId()));
		userLogin.setUserNum(tUserApp.getUserNum());
		userLogin.setLoginType(1);
		save(userLogin);
		tUserApp.setInvitationCode(String.valueOf(tUserApp.getUserAppId()));
		userScoreService.addNewUserScore(tUserApp.getUserNum(), 1);
		model.setMsg("注册成功");
		if(parentUserApp!=null){
			userScoreService.addOneApprentice(parentUserApp.getUserNum(), tUserApp.getUserNum());
		}
		
		UserAppModel appModel = new UserAppModel();
		appModel.setUserAppId(tUserApp.getUserAppId());
		appModel.setUserNum(tUserApp.getUserNum());
		appModel.setUserLevelNum("LV1");
		appModel.setLoginName(tUserApp.getUserAppId().toString());
		appModel.setInvitationCode(tUserApp.getInvitationCode());
		model.setObj(appModel);
		return model;
	}

	public TUserApp updateUserApprentice(String invintedNum) {
		if (EmptyUtils.isNotEmpty(invintedNum)) {

			if (!StringUtils.isNumeric(invintedNum)) { // 邀请码格式错误
				throw new NullPointerException("邀请码格式错误");
			}
			Integer userId = Integer.parseInt(invintedNum);
			TUserApp parentUserApp = userAppService.getUserAppById(userId);

			if (EmptyUtils.isEmpty(parentUserApp)) { // 邀请人不存在
				throw new NullPointerException("邀请码不存在");
			}
			return parentUserApp;
		}
		return null;
	}

	/**
	 * 功能描述: 修改用户的上下级关系
	 *
	 * @author yangliu 2016年1月19日 下午2:47:38
	 * 
	 * @param invintedNum
	 *            邀请编号
	 * @param userNum
	 *            用户编号
	 * @return
	 */
	@SuppressWarnings("static-access")
	public synchronized AppCommonModel updateUserApprentice(String invintedNum, String userNum) {

		AppCommonModel model = new AppCommonModel();
		float score = Constants.USER_SCORE_INFO_TYPE_INVITED_NO;
		if (EmptyUtils.isNotEmpty(invintedNum)) {

			if (!StringUtils.isNumeric(invintedNum)) { // 邀请码格式错误
				model.setResult(model.ERROR);
				model.setMsg("邀请码格式错误");
				return model;
			}
			Integer userId = Integer.parseInt(invintedNum);
			TUserApp parentUserApp = userAppService.getUserAppById(userId);

			if (EmptyUtils.isEmpty(parentUserApp)) { // 邀请人不存在
				model.setResult(model.ERROR);
				model.setMsg("邀请码不存在");
				return model;
			} else {
				model.setMsg("邀请成功");
			}

			String parentUserAppNum = parentUserApp.getUserNum();

			score = Constants.USER_SCORE_INFO_TYPE_INVITED_YES;
			List<TUserApprentice> list = userApprenticeService.getListAllByApprenticeUserNum(userNum);
			if (EmptyUtils.isNotEmpty(list)) {
				model.setMsg("您的师傅已存在");
				model.setResult(-1);
				return model;
			}
			userScoreService.addOneApprentice(parentUserAppNum, userNum);

		}
		userScoreService.addScoreSum(userNum, "注册奖励", Constants.USER_SCORE_INFO_TYPE_4, score, Constants.USER_TYPE_APP);
		return model;
	}

	/**
	 * 功能描述:修改 苹果账号
	 * @author wsp  2017-1-20 下午03:10:27
	 * @param userNum
	 * @param appStore
	 * @return
	 */
	public AppCommonModel updateAppStore(String userNum, String appStore) {
		AppCommonModel model = new AppCommonModel();
		int result = appUserService.updateAppStore(userNum,appStore);
		if(result == 2){
			model.setMsg("无该用户");
		}else if(result == 3){
			model.setMsg("请输入苹果账号");
		}else{
			model.setMsg("修改成功");
		}
		model.setResult(result);
		return model;
	}
	
	public AppCommonModel updateUserWeiXin(String userNum,String weiXinName,String headImgUrl,String openID) 
	{
		AppCommonModel model = new AppCommonModel();
		int result = appUserService.updateUserWeiXin(userNum,weiXinName,headImgUrl, openID);
		if(result == 1) 
		{
			model.setMsg("修改成功");
		}
		else
		{
			model.setMsg("此微信已经被绑定！");
		}
		model.setResult(result);
		return model;
	}
	
	//updateUserName
	public AppCommonModel updateUserName(String userNum,String userName) 
	{
		AppCommonModel model = new AppCommonModel();
		int result = appUserService.updateUserName(userNum,userName);
		model.setMsg("修改成功");
		model.setResult(result);
		return model;
	}
	
	//updateUserAlipay
	public AppCommonModel updateUserAlipay(String userNum,String alipay) 
	{
		AppCommonModel model = new AppCommonModel();
		int result = appUserService.updateUserAlipay(userNum,alipay);
		model.setMsg("修改成功");
		model.setResult(result);
		return model;
	}
	
	public AppCommonModel updateUserPhoneNum(String userNum,String phoneNum)
	{
		AppCommonModel model = new AppCommonModel();
		int result = appUserService.updateUserPhoneNum(userNum,phoneNum);
		model.setMsg("修改成功");
		model.setResult(result);
		return model;
	}

	public AppCommonModel tixianRequest(String userNum)
	{
		AppCommonModel model = new AppCommonModel();
		//int result = appUserService.tixianRequest(userNum);
		model.setMsg("修改成功");
		model.setResult(1);
		return model;
	}
}
