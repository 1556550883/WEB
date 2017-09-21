/**
  * 手机端接口: files
 *@author feiyang
 *@date 2016-1-14
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TAdverEffectiveInfo;
import com.ruanyun.web.service.app.AppAdverEffectiveInfoService;

/**
 *@author feiyang
 *@date 2016-1-14
 */
@Controller
@RequestMapping("app/adverEffective")
public class AppAdverEffectiveInfoController extends BaseController
{
	@Autowired
	private AppAdverEffectiveInfoService appAdverEffectiveInfoService;
	
	/**
	 * 
	 * 手机端接口:获取列表
	 * @param session
	 * @param response
	 * 根据UserNum获取分页
	 * @param page
	 *@author feiyang
	 *@date 2016-1-14
	 */
	@RequestMapping("getAll")
	public void getAllAdverEffective(HttpServletResponse response,Page<TAdverEffectiveInfo>page,String userNum,String sign)
	{
		AppCommonModel model=new AppCommonModel();	
		try 
		{
			page.setNumPerPage(20);
			model=appAdverEffectiveInfoService.getAllAdverEffective(page, userNum);
		} catch (Exception e) 
		{
			model=new AppCommonModel(-1,"数据异常");
		}		
		
		super.writeJsonDataApp(response, model);
	}
	/**
	 * 
	 * 手机端接口:获取最热任务
	 * @param response
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-21
	 */
	@RequestMapping("getHotList")
	public void getHotList(HttpServletResponse response,String userNum,String sign){
		AppCommonModel model=new AppCommonModel();	
		try {
			model=appAdverEffectiveInfoService.getHotList();
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}
}
