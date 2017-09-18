package com.ruanyun.web.controller.sys;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.orm.ICommonHqlDao;

import com.ruanyun.web.model.sys.TAuthority;
import com.ruanyun.web.model.sys.TRole;
import com.ruanyun.web.service.sys.AuthorityService;
import com.ruanyun.web.service.sys.RoleService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;
/**
 * 
 *  #(c) ruanyun  <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 角色管理
 * 
 *  <br/>创建说明: 2013-9-16 下午07:07:27 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Controller
@RequestMapping("role")
public class RoleController extends BaseController{
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@Autowired
	@Qualifier("authorityService")
	private AuthorityService authorityService;
	
	@RequestMapping("roles")
	public String roles(Model model){
		addModel(model,"roles", roleService.getAll(TRole.class, "orderby",ICommonHqlDao.ORDER_ASC));
		return "pc/role/list";
	}
	
	/**
	 * 功能描述:修改角色信息
	 *
	 * @author yangliu  2013-9-25 上午11:23:08
	 * 
	 * @param roleId 角色ID
	 * @param model
	 * @return
	 */
	@RequestMapping("edit")
	public String  updateRole(Integer roleId,Model model){
		TRole role=roleService.get(TRole.class,roleId);
		addModel(model, "role", role);
		addModel(model, "authList", authorityService.getAuths(authorityService.getListAuthBindingRoleId(roleId)));
		return "pc/role/addOrEdit";
	}
	
	/**
	 * 功能描述:添加角色信息
	 *
	 * @author yangliu  2013-9-25 上午11:31:32
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("add")
	public String addRole(Model model){
		addModel(model, "authList", authorityService.getAuths(authorityService.getAll(TAuthority.class, "authOrderby", ICommonHqlDao.ORDER_ASC)));
		return "pc/role/addOrEdit";
	}
	
	/**
	 * 功能描述:保存角色信息
	 *
	 * @author yangliu  2013-9-25 下午01:51:21
	 * 
	 * @param model model
	 * @param role 角色
	 * @param authCode 权限code  格式(A_1000,A_1001)
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(Model model,TRole role,String authCode,HttpSession session,HttpServletResponse response){
		int result=roleService.saveOrUpdateRole(role, authCode,HttpSessionUtils.getCurrentUser(session));

		if (result==1) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_A_1019", "/role/roles", "closeCurrent"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
		

	}
	
	/**
	 * 功能描述:删除角色
	 *
	 * @author houkun  2013-10-11 下午02:54:25
	 * 
	 * @return
	 */
	@RequestMapping("del")
	public void deleteRole(TRole role,HttpSession session,HttpServletResponse response){
		roleService.deleteRole(role,HttpSessionUtils.getCurrentUser(session));
		super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_A_1019", "/role/roles", ""));
	}
}
