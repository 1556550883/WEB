/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-12
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
import com.ruanyun.web.model.TUserExchange;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.UserExchangeService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 *@author feiyang
 *@date 2016-1-12
 */
@Controller
@RequestMapping("userExchange")
public class UserExchangeController extends BaseController {

	@Autowired
	private UserExchangeService userExchangeService;

	/**
	 * 
	 * 功能描述:兑换明细表查询
	 * 
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("list")
	public String getUserAppList(HttpSession session, Page<TUserExchange> page,
			TUserExchange info, Model model) {
		TUser tUser = HttpSessionUtils.getCurrentUser(session);
		addModel(model, "pageList", userExchangeService.queryPage(page, info,
				tUser));
		addModel(model, "bean", info);
		return "pc/userExchange/list";
	}

	/**
	 * 
	 * 功能描述:兑换审核
	 * 
	 * @param response
	 * @param info
	 * @param isType
	 *@author feiyang
	 *@date 2016-1-12
	 */
	@RequestMapping("/updateStatus")
	public void updateStatus(HttpServletRequest request,
			HttpServletResponse response, TUserExchange info, Integer isType) {
		try {		
			userExchangeService.updateStatus(info.getExchhangeId(), isType, info.getExchangeCode());
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(
						Constants.STATUS_SUCCESS_CODE,
						Constants.MESSAGE_SUCCESS, "main_",
						"userExchange/list", "redirect"));
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(
					Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "",
					"", ""));
		}
	}

	/**
	 * 
	 * 功能描述:积分兑换
	 * 
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */
	@RequestMapping("exchange")
	public String exchange(HttpServletResponse response, TUserExchange info) {
		return "pc/userExchange/exchange";
	}

	/**
	 * 
	 * 功能描述:验证积分
	 * 
	 * @param response
	 * @param info
	 *@author feiyang
	 *@date 2016-1-12
	 */
	@RequestMapping("exchangeCode")
	public void exchangeCode(HttpSession session, HttpServletResponse response,
			TUserExchange info) {
		TUser tUser = HttpSessionUtils.getCurrentUser(session);
		try {
			String msg = userExchangeService.useExchangeCode(info, tUser);
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(
					Constants.STATUS_SUCCESS_CODE, msg, "main_",
					"userExchange/exchange", "forwardUrl"));
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(
					Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "",
					"", ""));
		}
	}

	/**
	 * 
	 * 功能描述:手动输入兑换码
	 * 
	 * @param response
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-18
	 */
	@RequestMapping("manual")
	public String manualCode(HttpServletResponse response, TUserExchange info,
			Model model) {
		addModel(model, "info", info);
		return "pc/userExchange/manual";
	}

	/**
	 * 
	 * 功能描述:手动输入验证码
	 * @param request
	 * @param response
	 * @param info
	 * @param isType
	 *@author feiyang
	 *@date 2016-1-18
	 */
	@RequestMapping("upManual")
	public void upManual(HttpServletRequest request,
			HttpServletResponse response, TUserExchange info, Integer isType) {
		try {
			userExchangeService.updateStatus(info.getExchhangeId(), isType, info.getExchangeCode());
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(
					Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS,
					"main_index", "userExchange/list", "forward"));

		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(
					Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "",
					"", ""));

		}
	}
}
