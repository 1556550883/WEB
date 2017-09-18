/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TArticleInfo;
import com.ruanyun.web.service.app.AppArticleInfoService;

/**
 *@author feiyang
 *@date 2016-4-16
 */
@Controller
@RequestMapping("app/articleInfo")
public class AppArticleInfoController extends BaseController{

	@Autowired
	private AppArticleInfoService appArticleInfoService;
	

	/**
	 * 
	 * 手机端接口:文章列表(当前用户未完成)
	 * @param response
	 * @param page
	 * @param info
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-4-16
	 */
	@RequestMapping("getAll")
	public void getTArticleInfoByUserNum(HttpServletResponse response,Page<TArticleInfo>page,String userNum,String sign,Integer isAuth){
		AppCommonModel model=new AppCommonModel();	
		try {
			model=appArticleInfoService.getTArticleInfoByUserNum(page, userNum,isAuth);
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 功能描述:文章详情
	 * @author wsp  2017-1-10 下午03:08:13
	 * @param response
	 * @param articleNum
	 */
	@RequestMapping("getTArticleInfoDetail")
	public void getTArticleInfoDetail(HttpServletResponse response,String articleNum){
		AppCommonModel model=new AppCommonModel();	
		try {
			model=appArticleInfoService.getTArticleInfoDetail(articleNum);
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}
	
}
