/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-21
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserExchange;
import com.ruanyun.web.service.app.AppUserExchangeService;

/**
 *@author feiyang
 *@date 2016-1-21
 */
@Controller
@RequestMapping("app/userExchange")
public class AppUserExchangeController extends BaseController{

	@Autowired	
	private AppUserExchangeService appUserExchangeService;
	/**
	 * 
	 * 功能描述: 手机端接口:兑换记录分页
	 * @param response
	 * @param page
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-21
	 */
	@RequestMapping("getList")
	public void getMyExchangeList(HttpServletResponse response,Page<TUserExchange>page,String userNum,String sign){
		AppCommonModel model=new AppCommonModel();		
		try {
			model=appUserExchangeService.getMyExchangeList(page, userNum);
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 
	 * 功能描述: 手机端接口:兑换记录分页
	 * @param response
	 * @param page
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-21
	 */
	@RequestMapping("getFive")
	public void getFive(HttpServletResponse response,String userNum,String sign){
		AppCommonModel model=new AppCommonModel();		
		try {
			model=appUserExchangeService.getMyExchangeList();
		} catch (Exception e) {
			model=new AppCommonModel(-1,"数据异常");
		}		
		super.writeJsonDataApp(response, model);
	}
	
}
