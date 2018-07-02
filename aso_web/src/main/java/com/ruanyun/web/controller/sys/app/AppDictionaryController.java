/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.service.app.AppDictionaryService;

@Controller
@RequestMapping("app/dictionary")
public class AppDictionaryController extends BaseController{
	@Autowired
	private AppDictionaryService appDictionaryService;

	/**
	 * 
	 * 手机端接口:根据parentCode 获取集合
	 * @param response
	 * @param parentCode
	 *@author feiyang
	 *@date 2016-1-13
	 */
	@RequestMapping("getType")
	public void getDictionaryList(HttpServletResponse response,String parentCode,String userNum,String sign){
		AppCommonModel model = new AppCommonModel();
		try {
			model=appDictionaryService.getDictionaryList(parentCode);
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}

		super.writeJsonDataApp(response, model);
	}
}
