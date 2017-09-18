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
import com.ruanyun.web.model.sys.TOperationLog;
import com.ruanyun.web.service.sys.OperationLogService;

@Controller
@RequestMapping("operationLog")
public class OperationLogController extends BaseController {
	
	@Autowired
	@Qualifier("operationLogService")
	private OperationLogService operationLogService;
	
	@InitBinder
	public void initBinders(WebDataBinder binder){
		super.initBinder(binder,"yyyy-MM-dd",true);
	}

	/**
	 * 功能描述:返回操作日志列表
	 *
	 * @author houkun  2013-11-4 下午03:04:24
	 * 
	 * @param model
	 * @param page
	 * @param operationLog
	 * @return
	 */
	@RequestMapping("getlist")
	public String getList(Model model,Page<TOperationLog> page,TOperationLog operationLog){
		addModel(model,"pageList",operationLogService.queryPage(page,operationLog));
		addModel(model, "toperationlog", operationLog);
		return "pc/operationlog/list";
	}
	
	/**
	 * 功能描述:导出Excel
	 *
	 * @author houkun  2013-11-4 下午03:04:13
	 * 
	 * @param response
	 * @param operationLog
	 * @param params
	 * @throws Exception
	 */
	@RequestMapping("exportExcel")
	public void exportExcel(HttpServletResponse response,TOperationLog operationLog, Map<String, Object> params) throws Exception{
		operationLogService.getList(response, operationLog, params);
	}
}
