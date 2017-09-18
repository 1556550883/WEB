/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-27
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
import com.ruanyun.web.model.TView;
import com.ruanyun.web.service.background.ViewService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;

/**
 *@author feiyang
 *@date 2016-1-27
 */
@Controller
@RequestMapping("view")
public class ViewController extends BaseController {
	@Autowired
	private ViewService viewService;

	/**
	 * 
	 * 功能描述:关于和帮助
	 * 
	 * @param model
	 * @param viewType
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
	@RequestMapping("getView")
	public String getView(Model model, String viewType) {
		addModel(model, "info", viewService.getView(viewType));	
			return "pc/view/more";
	}

	/**
	 * 
	 * 功能描述:增加或者修改
	 * @param model
	 * @param info
	 * @param response
	 *@author feiyang
	 *@date 2016-1-27
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(Model model,TView info ,HttpServletResponse response) {
		int result =viewService.saveOrupdate(info);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","view/getView", "redirect"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
}
