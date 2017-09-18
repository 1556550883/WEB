/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.controller.sys.background;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TUserLevel;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.UserLevelService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Controller
@RequestMapping("userLevel")
public class UserLevelController extends BaseController{

	
	@Autowired
	private  UserLevelService userLevelService;
	
	/**
	 * 
	 * 功能描述:用户等级管理
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("list")
	public String getUserLevelList(Page<TUserLevel> page,TUserLevel info,Model model){
		addModel(model, "pageList", userLevelService.queryPage(page, info));
		addModel(model, "bean", info);
		return "pc/userLevel/list";
	}
	
	
	
	
	/**
	 * 
	 * 功能描述:删除
	 * @param request
	 * @param response
	 *@author feiyang
	 *@date 2016-1-7
	 */
	@RequestMapping("/delAll")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("ids");
		try {
			if (id != null) {
				// 说明是批量删除
				if (id.contains(",")) {
					String[] ids = id.split(",");
					for (int i = 0; i < ids.length; i++) {
						userLevelService.delete(TUserLevel.class, Integer.valueOf(ids[i]));
					}
				} else {
					userLevelService.delete(TUserLevel.class, Integer.valueOf(id));
				}
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","/userLevel/list", "redirect"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	
	/**
	 * 
	 * 功能描述:增加和编辑
	 * @param info
	 * @param model
	 * @param session
	 * @param type
	 * @return
	 *@author feiyang
	 *@date 2016-1-7
	 */
	@RequestMapping("toedit")
	public String toedit(TUserLevel info, Model model,HttpSession session,Integer type) {
		if (EmptyUtils.isNotEmpty(info.getLevelId())) {
			addModel(model, "info", userLevelService.get(TUserLevel.class,info.getLevelId()));			
		}
		addModel(model, "type", type);
		return "pc/userLevel/edit";
	}
	
	
	/**
	 * 
	 * 功能描述:保存和修改
	 * @param request
	 * @param model
	 * @param info
	 * @param session
	 * @param response
	 *@author feiyang
	 *@date 2016-1-7
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request, Model model,TUserLevel info , HttpSession session,HttpServletResponse response) {
		TUser user = HttpSessionUtils.getCurrentUser(session);
		int result = userLevelService.saveOrupdate(info, request, user);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","userLevel/list", "closeCurrent"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
}
