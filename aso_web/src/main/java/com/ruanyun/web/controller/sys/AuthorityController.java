package com.ruanyun.web.controller.sys;

import java.util.Map;

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
import com.ruanyun.web.service.sys.AuthorityService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 * 
 *  #(c) IFlytek ahsw <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 权限操作
 * 
 *  <br/>创建说明: 2013-11-22 上午11:15:59 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Controller
@RequestMapping("/auth")
public class AuthorityController extends BaseController {
	
	@Autowired
	@Qualifier("authorityService")
	private AuthorityService authorityService;

	/**
	 * 功能描述:返回 权限菜单列表
	 * @author L H T  2013-11-23 下午02:00:28
	 * @param model
	 * @param msg
	 * @param authCode
	 * @return
	 */
	@RequestMapping("auths")
	public String authoritys(Model model,String msg,String authCode){
		//获得分级后的  菜单
		addModel(model, "authList",authorityService.getAuths(authorityService.getAll(TAuthority.class,"authOrderby",ICommonHqlDao.ORDER_ASC)) );
		//addModel(model, MSG, msg);
		addModel(model, "authCode",authCode);
		return "pc/auth/list";
	}
	/**
	 * 功能描述:通过 菜单 code返回信息   修改
	 *
	 * @author L H T  2013-11-23 下午02:01:40
	 * 
	 * @param authId
	 * @param model
	 * @return
	 */
	@RequestMapping("edit")
	public String editAuth(Integer authId,Model model,String levels){
		addModel(model,"auth",authorityService.get(TAuthority.class, authId));
		addModel(model,"authList",authorityService.getAll(TAuthority.class,"authOrderby",ICommonHqlDao.ORDER_ASC));
		//当前循环第几层，页面在第四层给的死值，禁止添加第五层
		addModel(model, "levels", levels);
		return "pc/auth/ajax";
	}
	
	@RequestMapping("add")
	public String addAuth(TAuthority auth,Model model){
		addModel(model, "auth", auth);
		return "pc/auth/ajax";
	}
	/**
	 * 功能描述:修改和保存权限
	 *
	 * @author L H T  2013-11-27 下午02:56:35
	 * 
	 * @param auth
	 * @param model
	 * @param session
	 * @param response
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(TAuthority auth,Model model,HttpSession session,HttpServletResponse response){
		Integer result=authorityService.saveOrUpdateAuth(auth,HttpSessionUtils.getCurrentUser(session));
		if(result>0){
			addModel(model, "authCode", auth.getAuthCode());
		}
		//addModel(model, MSG, result);
		if (result==1) {
			Map<String, String> map =CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS,"main_A_1016", "auth/auths", "closeCurrent");
			super.writeJsonData(response,map);
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	/**
	 * 功能描述:删除权限
	 *
	 * @author L H T  2013-11-22 上午11:17:30
	 * 
	 * @param authCode 权限业务主键
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping("deleteAuth")
	public void delete(String authCode,Model model,HttpSession session,HttpServletResponse response){
		Integer result=authorityService.delete(authCode,HttpSessionUtils.getCurrentUser(session));
		//addModel(model, MSG, result);
		//return REDIRECT+"/auth/auths";
		if (result==1) {
			Map<String, String> map =CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS,"main_A_1016", "auth/auths", "closeCurrent");
			super.writeJsonData(response,map);
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
}
