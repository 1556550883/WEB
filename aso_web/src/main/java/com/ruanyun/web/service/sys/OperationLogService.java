package com.ruanyun.web.service.sys;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.OperationLogDao;
import com.ruanyun.web.model.sys.TOperationLog;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.ExcelUtils;

@Service("operationLogService")
public class OperationLogService extends BaseServiceImpl<TOperationLog>{
	@Autowired
	@Qualifier("taskExecutor")
	private TaskExecutor taskExecutor; 
	@Autowired
	@Qualifier("operationLogDao")
	private OperationLogDao logDao;
	
	
	public void addOperationLogSession(TOperationLog log){
		log.setCreatedate(new Date());
		logDao.addOperationLog(log);
	}
	
	/**
	 * 
	 * @param tuser 当前用户
	 * @param name 姓名
	 * @param content 内容
	 * @param type 类型
	 */
	public void addOperationLogSession(TUser tuser,String name,String content,String type){
		TOperationLog log = new TOperationLog();
		log.setCreatedate(new Date());
		log.setOperationContent(content);
		log.setOperationName(name);
		log.setOperationUser(tuser.getUserId());
		log.setOperationUserName(tuser.getLoginName());
		log.setOperationType(type);
		logDao.addOperationLog(log);
	}
	
	/**
	 * 
	 * @param tuser 当前用户
	 * @param name 姓名
	 * @param content 内容
	 * @param type 类型
	 */
	public void addOperationLogThead(final TUser tuser,final String name,final String content,final String type){
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				addOperationLogSession(tuser, name, content,type);
			}
		});
	}
    /**
     * 分页查询
     */
	@Override
	public Page<TOperationLog> queryPage(Page<TOperationLog> page,TOperationLog operationLog) {
		return logDao.queryPage(page, operationLog);
	}
	/**
	 * 功能描述:导出excel
	 *
	 * @author L H T  2013-11-27 下午12:29:08
	 * 
	 * @param response
	 * @param operationLog
	 * @param params
	 * @throws Exception
	 */
	public void getList(HttpServletResponse response,TOperationLog operationLog, Map<String, Object> params) throws Exception{
		List<TOperationLog> list = logDao.getList(operationLog, params);
		String fileName = "操作日志";
		String[] headers = {"操作名","操作内容","操作人","时间"};
		String[] columns = {"operationName","operationContent","operationUserName","createdate"};
		ExcelUtils.exportExcel(response, fileName, list, columns, headers, SysCode.DATE_FORMAT_STR_S);
	}

}
