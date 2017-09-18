package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.service.background.AdverauthService;
@Controller
@RequestMapping("app/adverauth")
public class AppAdverAuthController extends BaseController{
	@Autowired
	private AdverauthService adverauthService;//权限
	
	/**
	 * 功能描述:权限判断
	 * @author wsp  2017-1-10 下午02:38:44
	 * @param response
	 * @param userNum	用户编号
	 * @param commonNum	文章编号或者广告编号
	 * @param commonType 类型 1-文章 2-广告
	 */
	@RequestMapping("getIsAuthority")
	public void getIsAuthority(HttpServletResponse response,String userNum,String commonNum,Integer commonType){
		int result = 0;
		AppCommonModel model=new AppCommonModel();	
		try {
			result=adverauthService.getIsAuthority(userNum,commonNum,commonType,true);
			if(result == 1){
				model=new AppCommonModel(1,"权限通过");
			}else if(result == -1){
				model=new AppCommonModel(-1,"参数不全");
			}else{
				model=new AppCommonModel(2,"权限不足");
			}
		} catch (Exception e) {
			logger.equals(e.getMessage());
			model=new AppCommonModel(-1,e.getMessage());
		}		
		super.writeJsonDataApp(response, model);
	}

}
