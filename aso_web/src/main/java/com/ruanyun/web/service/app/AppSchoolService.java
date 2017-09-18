package com.ruanyun.web.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TSchool;
import com.ruanyun.web.service.background.SchoolService;

@Service
public class AppSchoolService {
	@Autowired
	private SchoolService schoolService;
	
	/**
	 * 功能描述:获取学校列表
	 * @author cqm  2016-12-19 下午01:48:07
	 * @param school
	 * @return
	 */
	public AppCommonModel getSchoolList(TSchool school){		
		AppCommonModel model=new AppCommonModel(1,"");
		model.setObj(schoolService.getSchoolList(school));
		return model;
	}

}
