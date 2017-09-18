/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserArticle;
import com.ruanyun.web.service.app.AppUserArticleService;

/**
 *@author feiyang
 *@date 2016-4-16
 */
@Controller
@RequestMapping("app/userArticle")
public class AppUserArticleController extends BaseController{

	@Autowired
	private  AppUserArticleService appUserArticleService;

	
	
	/**
	 * 
	 * 功能描述:文章任务奖励
	 * @param response
	 * @param info
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-4-16
	 */
	@RequestMapping("addUserArticle")
	public void addUserArticle(HttpServletResponse response,TUserArticle info,String userNum,String sign){
		AppCommonModel model=new AppCommonModel();	
		try {
			model=appUserArticleService.addUserArticle(info);
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}
}
