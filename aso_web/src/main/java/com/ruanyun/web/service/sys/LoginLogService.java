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
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.LoginLogDao;
import com.ruanyun.web.model.sys.TLoginLog;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.model.sys.UserLoginLogShow;
import com.ruanyun.web.util.ExcelUtils;

@Service("loginLogService")
public class LoginLogService extends BaseServiceImpl<TLoginLog> {
	@Autowired
	@Qualifier("taskExecutor")
	private TaskExecutor taskExecutor; 
	@Autowired
	@Qualifier("loginLogDao")
	private LoginLogDao loginLogDao;
	
	
	public void addLoginLogSession(String loginName,String logIp,Integer userId){
		TLoginLog log = new TLoginLog();
		log.setLoginName(loginName);
		log.setLoginTime(new Date());
		log.setLogIp(logIp);
		//log.setUserId(userId);
		loginLogDao.addLoginLog(log);
	}
	

	public void addLoginLogSession(TUser user,String logIp){
		TLoginLog log = new TLoginLog();
		log.setLoginName(user.getLoginName());
		log.setLoginTime(new Date());
		log.setLogIp(logIp);
		log.setLogUserId(user.getUserId());
		loginLogDao.addLoginLog(log);
	}
	
	/**
	 * 保存
	 * @param loginName
	 * @param logIp
	 */
	public void addLoginLogThead(final TUser user,final String logIp){
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				addLoginLogSession(user,logIp);
			}
		});
	}
	/**
	 * 功能描述:通过用户id查询用户登录情况
	 *
	 * @author L H T  2013-12-14 下午01:08:33
	 * 
	 * @param userId
	 * @return
	 */
	public UserLoginLogShow getLoginLogByUserId(Integer userId)
	{
		//前两条
		List<TLoginLog> loginlog = loginLogDao.getLoginLogByUserId(userId);
		Integer loginLogCount = loginLogDao.getCountByUserId(userId);
		
		//获得本次的登录信息
		TLoginLog nowLog = null;
		if (EmptyUtils.isEmpty(loginlog)) 
		{
			return null;
		}
		
		nowLog = loginlog.get(0);
		UserLoginLogShow loginLogShow = new UserLoginLogShow();
		loginLogShow.setLoginCount(loginLogCount);
		
		loginLogShow.setLoginIp(nowLog.getLogIp());
		loginLogShow.setLoginTime(nowLog.getLoginTime());
		
		/**
		 * 如果集合中有两条数据就显示上次的登录信息
		 */
		if (loginlog.size() >= 2)
		{
			//获得上次的登录信息
			TLoginLog lastLog = loginlog.get(1);
			loginLogShow.setLastLoginIp(lastLog.getLogIp());
			loginLogShow.setLastLoginTime(lastLog.getLoginTime());
		}
		
		return loginLogShow;
	}

	/**
	 * 功能描述:登录日志列表
	 *
	 * @author houkun  2013-10-25 下午03:44:16
	 * 
	 * @param page
	 * @param loginlog
	 * @return 
	 */
	@Override
	public Page<TLoginLog> queryPage(Page<TLoginLog> page, TLoginLog loginlog) {
		return loginLogDao.queryPage(page,loginlog);
	}
	
	/**
	 * 功能描述:
	 *
	 * @author houkun  2013-11-2 上午09:01:57
	 * 
	 * @param response
	 * @param loginlog
	 * @throws Exception
	 */
	public void getList(HttpServletResponse response,TLoginLog loginlog,Map<String,Object> params) throws Exception{
		List<TLoginLog> list = loginLogDao.getList(loginlog,params);
		String fileName = "登录日志";
		String[] columns = {"loginName","logIp","loginTime"};
		String[] headers = {"登录名","登录IP","登录时间"};
		ExcelUtils.exportExcel(response, fileName, list, columns, headers, SysCode.DATE_FORMAT_STR_L);
	}
}
