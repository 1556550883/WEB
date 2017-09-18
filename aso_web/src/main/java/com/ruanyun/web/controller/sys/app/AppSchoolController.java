package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TSchool;
import com.ruanyun.web.service.app.AppSchoolService;

@Controller
@RequestMapping("app/school")
public class AppSchoolController extends BaseController{
	
	@Autowired
	private AppSchoolService appSchoolService;
	
	/**
	 * 功能描述:获取学校列表
	 * @author cqm  2016-12-19 下午01:51:40
	 * @param response
	 * @param school
	 */
	@RequestMapping("list")
	public void getAllAdverEffective(HttpServletResponse response,TSchool school){
		AppCommonModel model=new AppCommonModel();	
		try {
			model=appSchoolService.getSchoolList(school);
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}

}
