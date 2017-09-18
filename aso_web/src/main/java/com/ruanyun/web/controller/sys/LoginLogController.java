package com.ruanyun.web.controller.sys;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.sys.TLoginLog;
import com.ruanyun.web.service.sys.LoginLogService;

@Controller
@RequestMapping("loginlog")
public class LoginLogController extends BaseController {
	
	@Autowired
	@Qualifier("loginLogService")
	private LoginLogService loginLogService;
	
	@InitBinder
	public void initBinders(WebDataBinder binder){
		super.initBinder(binder,"yyyy-MM-dd",true);
	}

	/**
	 * 功能描述:登录日志列表
	 *
	 * @author houkun  2013-10-25 下午04:08:41
	 * 
	 * @param model
	 * @param page
	 * @param loginlog
	 * @return
	 */
	@RequestMapping("getlist")
	public String getlogList(Model model,Page<TLoginLog> page,TLoginLog loginlog){
		addModel(model,"pageList", loginLogService.queryPage(page,loginlog));
		addModel(model,"tloginlog",loginlog);
		return "pc/loginlog/list";
	}
	
	/**
	 * 功能描述:导出Excel
	 *
	 * @author houkun  2013-11-2 上午11:09:02
	 * 
	 * @param response
	 * @param loginlog
	 * @throws Exception 
	 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletResponse response,TLoginLog loginlog,Map<String,Object> params) throws Exception{
		loginLogService.getList(response, loginlog, params);
	}
	
}
