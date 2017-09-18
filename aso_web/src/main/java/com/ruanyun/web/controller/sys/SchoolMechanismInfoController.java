package com.ruanyun.web.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.TSchool;

import com.ruanyun.web.service.background.SchoolService;
@Controller
@RequestMapping("schoolmechanisminfo")
public class SchoolMechanismInfoController extends BaseController {
	
	@Autowired
	private SchoolService schoolService;
	
	
	/**
	 * 
	 * 功能描述:学校
	 * @param response
	 *@author 
	 *@date 
	 */
	@RequestMapping("getschool")
	 public void getschool(HttpServletResponse response,Integer schoolId){
		List<TSchool> list=schoolService.getAllschool(schoolId);
		 super.writeJsonData(response, list);
    }
	
	
	
	
	

}
