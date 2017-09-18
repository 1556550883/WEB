/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-27
 */
package com.ruanyun.web.controller.sys.app;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.service.background.ViewService;

/**
 *@author feiyang
 *@date 2016-1-27
 */
@Controller
@RequestMapping("app/view")
public class AppViewController extends BaseController{

	@Autowired
	private ViewService viewService;

	/**
	 * 
	 * 手机端接口:关于和帮助
	 * @param response
	 * @param viewType
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-27
	 */
	@RequestMapping("getView")
	public void getView(HttpServletResponse response, String viewType,
			String userNum, String sign) {
		AppCommonModel model = new AppCommonModel(1);
		try {	
			model.setObj(viewService.getView(viewType));
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
	/**
	 * 
	 * 手机端接口:关于和帮助
	 * @param model
	 * @param viewType
	 * @return
	 *@author feiyang
	 *@date 2016-1-28
	 */
	@RequestMapping("getAppView")
	public String getAppView(Model model,String viewType){
		addModel(model, "info", viewService.getView(viewType));
		return"pc/view/about";
	}
	
	
	
	/**
	 * 
	 * 手机端接口:如何收徒页面和为什么收徒
	 * @param response
	 * @param userNum
	 * type  1/2    如何/为什么
	 * @param sign
	 * @return
	 *@author feiyang
	 *@date 2016-3-7
	 */
	@RequestMapping("apprentice")
	public String getApprentice(HttpServletResponse response,String userNum, String sign,Integer type) {
		AppCommonModel model = new AppCommonModel(1);
		Map<String, String> map=new HashMap<String, String>();		
		map.put("url", "/app/view/apprentice?type="+type);
		if (type==1) {
			return"pc/view/howApprentice";
		}else {
			return"pc/view/whyApprentice";
		}
//		model.setObj(map);
//		super.writeJsonDataApp(response, model);
	
	}
	/**
	 * 
	 * 功能描述:首页静态页面
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-3-31
	 */
	@RequestMapping("getIndexView")
	public String getIndexView(Model model,HttpServletRequest request){
			String str=request.getHeader("user-agent");
			int systemType=0;
			int ios=str.indexOf("iPhone");
			int android=str.indexOf("Android");		
			int iPad=str.indexOf("iPad");
		if (ios>0||iPad>0) {
			systemType=2; //苹果
			
		}else if (android>0) {
			systemType=1;//安卓
		}
		addModel(model, "systemType", systemType);
		return"pc/view/index";
	}
}
