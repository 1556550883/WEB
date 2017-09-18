package com.ruanyun.web.controller.sys.background;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TSchool;
import com.ruanyun.web.service.background.SchoolService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
@Controller
@RequestMapping("school")
public class SchoolController extends BaseController {
	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 查看学校
	 * zhujingbo
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String getSchoolList(Page<TSchool> page,TSchool info,Model model){
		addModel(model, "pageList", schoolService.queryPage(page, info));
		addModel(model, "bean", info);
		return "pc/school/list";
	}
	
	/**
	 * 跳转道增加修改页面
	 * zhujingbo
	 * @param model
	 * @param schoolId
	 * @return
	 */
	@RequestMapping("toEdit")
	public String toEdit(Model model,Integer schoolId){
	
		if (EmptyUtils.isNotEmpty(schoolId)) {
			addModel(model, "bean", schoolService.get(TSchool.class,schoolId));
		}
	    return "pc/school/edit";
	}
	/**
	 * 保存修改
	 * zhujingbo
	 * @param model
	 * @param bean
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws IOException
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(Model model,TSchool bean,HttpServletRequest request,HttpServletResponse response,MultipartFile fileName) throws IOException{
		int result = schoolService.saveOrUpdate(bean, request);
		if(result == 1){
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","school/list", "forward"));
		}else{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	/**
	 * 删除
	 * zhujingbo
	 * @param response
	 * @param schoolId
	 */
	@RequestMapping("del")
	public void delete(HttpServletResponse response,HttpServletRequest request,Integer schoolId){
		String id = request.getParameter("ids");
		try {
			if (id != null) {
				// 说明是批量删除
				if (id.contains(",")) {
					String[] ids = id.split(",");
					for (int i = 0; i < ids.length; i++) {
						schoolService.delete(TSchool.class, Integer.valueOf(ids[i]));
					}
				} else {
					schoolService.delete(TSchool.class, Integer.valueOf(id));
				}
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","school/list", "redirect"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
}
