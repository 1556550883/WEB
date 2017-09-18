package com.ruanyun.web.controller.sys.background;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TUserStudentCart;
import com.ruanyun.web.service.background.UserStudentCartService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
@Controller
@RequestMapping("userStudentCart")
public class UserStudentCartController extends BaseController {
	@Autowired
	private UserStudentCartService userStudentCartService;
	
	/**
	 * 查看待审核的学生信息；
	 * zhujingbo
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String getSchoolList(Page<TUserStudentCart> page,TUserStudentCart info,Model model,Integer cartStatus){
		addModel(model, "pageList", userStudentCartService.pageSql(page, info, cartStatus));
		addModel(model, "cartStatus", cartStatus);
		addModel(model, "bean", info);
		return "pc/userStudentCart/list";
	}
	
	/**
	 * 审核
	 * @param response
	 * @param ids
	 * @param type
	 */
	@RequestMapping("cartStatus")
	public void cartStatus(HttpServletResponse response,Integer type,String ids,Integer cartStatus){
		int result=userStudentCartService.saveStatus(type , ids);
		if (result==1) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_", "userStudentCart/list?cartStatus="+cartStatus, "auditredirect"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}

}
