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
import com.ruanyun.web.model.TUserLevelRate;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.UserLevelRateService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Controller
@RequestMapping("userLevelRate")
public class UserLevelRateController extends BaseController {

	
	
	@Autowired
	private UserLevelRateService userLevelRateService;
	
	
	
	/**
	 * 
	 * 功能描述:对应的得分比例
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("list")
	public String geUserLevelRateList(Page<TUserLevelRate> page,TUserLevelRate info,Model model){
		addModel(model, "pageList", userLevelRateService.queryPage(page, info));
		addModel(model, "bean", info);
		return "pc/userLevelRate/rate";
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
						userLevelRateService.delete(TUserLevelRate.class, Integer.valueOf(ids[i]));
					}
				} else {
					userLevelRateService.delete(TUserLevelRate.class, Integer.valueOf(id));
				}
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_index2","/userLevelRate/list", "redirect"));
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
	public String toedit(TUserLevelRate info, Model model,HttpSession session,Integer type,String levelNum) {
		if (EmptyUtils.isNotEmpty(info.getLevelRateId())) {
			
			addModel(model, "info", userLevelRateService.get(TUserLevelRate.class,info.getLevelRateId()));	
		}else {
			addModel(model, "info", info);			
		}
		addModel(model, "type", type);
		return "pc/userLevelRate/rateEdit";
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
	public void saveOrUpdate(HttpServletRequest request, Model model,TUserLevelRate info ,HttpServletResponse response) {
		int result = userLevelRateService.saveOrupdate(info, request);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_index2","userLevelRate/list", "closeCurrent"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
}
