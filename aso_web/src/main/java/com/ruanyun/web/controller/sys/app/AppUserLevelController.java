/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-25
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserLevel;
import com.ruanyun.web.service.app.AppUserLevelService;

/**
 *@author feiyang
 *@date 2016-1-25
 */
@Controller
@RequestMapping("app/userLevel")
public class AppUserLevelController extends BaseController{
	@Autowired
	private AppUserLevelService appUserLevelService;
	

	/**
	 * 
	 *手机端接口:获取等级说明
	 * @param response
	 * @param page
	 * @param info
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-25
	 */
	@RequestMapping("getUserLevelList")
	public void getUserLevelList(HttpServletResponse response,Page<TUserLevel> page,String userNum,String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model = appUserLevelService.getUserLevelList(page);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}

	/**
	 * 
	 * 手机端接口:根据等级编号获取该等级的得分比例
	 * @param response
	 * @param levelNum
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-25
	 */
	@RequestMapping("getDetail")
	public void getUserLevelDetail(HttpServletResponse response,String levelNum,String userNum,String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model=appUserLevelService.getUserLevelDetail(levelNum);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
	
	
	/**
	 * 
	 * 功能描述:收徒等级
	 * @param response
	 * @param page
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-3-22
	 */
	@RequestMapping("getApprenticeLevelList")
	public void getApprenticeLevelList(HttpServletResponse response,Page<TUserLevel> page,String userNum,String sign) {
		AppCommonModel model = new AppCommonModel();
		try {
			model = appUserLevelService.getApprenticeLevelList(page);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
}
