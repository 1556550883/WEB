package com.ruanyun.web.controller.sys;


import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.IBaseService;
import com.ruanyun.common.utils.EmptyUtils;

import com.ruanyun.web.model.sys.TRole;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.sys.RoleService;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	

	@Autowired
	@Qualifier("userService")
	UserService userService;
	 
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
	
	/**
	 * 功能描述:用户列表
	 *
	 * @author yangliu  2013-9-16 上午09:17:34
	 * 
	 * @param tuser 用户信息
	 * @return
	 */
	@RequestMapping("/users")
	public String users(HttpSession session,TUser tuser,Page<TUser> page,Model model){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		addModel(model, "pageList",userService.queryPage(page, tuser,currentUser));
		addModel(model, "tuser", tuser);

		return "pc/user/list";
	}

	
	/**
	 * 功能描述:跳转到用户的添加和修改页面 
	 *
	 * @author L H T  2013-11-26 下午04:05:04
	 * 
	 * @param user 用户实体
	 * @param roleId 角色id  
	 * @return
	 */
	@RequestMapping("/toUserEdit")
	public String userAddOrEditJsp(TUser user,Model model){
		if (EmptyUtils.isNotEmpty(user.getUserId())) {
			//通过id查询用户信息
			user=userService.getUserById(user.getUserId());
			
			//通过用户id查询用户拥有的角色
			TRole  userRole =roleService.getRoleListByUserId(user.getUserId());
			//查询用户的组织信息
//			String orgname=orgService.getOrgNameById(user.getOrgId());
//			//将查出的组织名称放入用户实体里
//			user.setOrgName(orgname);
			addModel(model, "user",user );
			addModel(model, "userRole", userRole);
		}
		//查询所有角色
		List<TRole> allRoles=roleService.getAll(TRole.class,"orderby",IBaseService.ORDER_DESC);
		addModel(model, "allRoles", allRoles);
		return "pc/user/userEdit";
	}
	
	/**
	 * 功能描述://保存和修改用户
	 *
	 * @author L H T  2013-10-11 下午12:02:17
	 * 
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	public void saveOrUpdate(TUser user,Integer roleId,HttpSession session, HttpServletResponse response,Model model){
		//获取当前登录人的session信息
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userService.saveOrUpdate(currentUser, user, roleId);
		if (result==1) {
			super.writeJsonData(response,CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "user/users", "closeCurrent"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	/**
	 * 功能描述:删除用户
	 *
	 * @author L H T  2013-10-11 下午12:02:17
	 * 
	 * @return
	 */
	@RequestMapping("/del")
	public void userDel(Model model,TUser user,HttpSession session,HttpServletResponse response){
		
		int result=	userService.userDel(user,HttpSessionUtils.getCurrentUser(session));
	if (result==1) {
		super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "user/users", "redirect"));
		
	}else{
		super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
	}
		
		//return REDIRECT +"/user/users";
	}
	
	/***
	 * 
	 * ajax判断登录名是否存在
	 * 
	 */
	@RequestMapping("searchAjaxName")
	public void getUser(HttpServletResponse response,String loginName,Integer userId){
		String result = null;
		TUser info = userService.getAjaxLoginName(loginName);
		if(info == null||info.getUserId()==userId){
			result = "success";
		}else{
			result = "fail";
		}
		super.writeText(response,result);
	}
	/**
	 * 功能描述:跳转到修改个人信息页面
	 *
	 * @author L H T  2013-12-2 下午05:10:11
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("editPersion")
    public String showPersonalInfo(Model model,Integer userId){
    	//通过id查询用户信息
		TUser user=userService.getUserById(userId);
		////查询用户的组织信息
		//String orgname=orgService.getOrgNameById(user.getOrgId());
		//将查出的组织名称放入用户实体里
		//user.setOrgName(orgname);
		addModel(model, "user", user);
		return "pc/user/personal/personlEdit";
	}
	/**
	 * 功能描述: 更新个人信息修改
	 *
	 * @author L H T  2013-12-2 下午05:26:27
	 * 
	 * @param model
	 * @param session
	 * @param response
	 * @param user
	 */
	@RequestMapping("updatePersonalInfo")
	public void updatePersonalInfo(Model model,HttpSession session,HttpServletResponse response,TUser user){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userService.updatePersonalInfo(user, currentUser);
		if (result==1) {
			String url="user/editPersion?userId="+user.getUserId();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "persion_edit", url, ""));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	/**
	 * 功能描述:跳转到密码修改页面
	 *
	 * @author L H T  2013-12-3 上午09:21:02
	 * 
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("editpass")
	public String editPassword(Model model,Integer userId){
		//通过id查询用户信息
		TUser user=userService.getUserById(userId);
		addModel(model, "user", user);
		return "pc/user/personal/editPassword";
	}
	/**
	 * 功能描述:修改密码
	 *
	 * @author L H T  2013-12-3 上午09:57:41
	 * 
	 * @param user
	 * @param response
	 * @param session
	 */
	@RequestMapping("updatePass")
	public void updatePass(TUser user,HttpServletResponse response,HttpSession session){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userService.updatePass(user, currentUser);
		if (result==1) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,"密码修改成功！请重新登录...", "", "/loginout", "redirect"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	/**
	 * 功能描述:批量删除用户
	 *
	 * @author L H T  2013-12-10 上午11:00:17
	 * 
	 * @param response
	 * @param session
	 * @param ids
	 */
	@RequestMapping("batchDelete")
	public void batchDelete(HttpServletResponse response,HttpSession session,String ids){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userService.batchDelete(currentUser, ids);
		if (result==1) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_", "/users", "redirect"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
/**
 * 功能描述:
 *
 * @author L H T  2013-12-11 下午12:56:40
 * 
 * @param response
 * @param userinfo
 * @throws Exception
 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletResponse response,TUser userinfo) throws Exception{
		userService.exportExcel(response, userinfo);
    }
	/**
	 * 功能描述:重置用户的密码为默认密码
	 *
	 * @author L H T  2013-12-24 下午02:18:19
	 * 
	 * @param response
	 * @param session
	 */
	@RequestMapping("resetPassword")
	public void resetUserPassword(HttpServletResponse response,HttpSession session,Integer userId){
		TUser currentUser=HttpSessionUtils.getCurrentUser(session);
		int result=userService.resetUserPassword(currentUser, userId);
		if (result==1) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_", "user/users", "forward"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
}
