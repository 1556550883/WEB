package com.ruanyun.web.service.background;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.LoginIpDao;
import com.ruanyun.web.model.TLoginIp;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.NumUtils;

@Service("loginIpService")
public class LoginIpService extends BaseServiceImpl<TLoginIp> {
	protected org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	
	@Autowired
	private LoginIpDao loginIpDao;
	
	/**
	 *分页查询 
	 */
	@Override
	public Page<TLoginIp> queryPage(Page<TLoginIp> page, TLoginIp t) {
		return loginIpDao.queryPage(page, t);
	}

	
	/**
	 * 功能描述:保存
	 * @author wsp  2017-1-20 下午12:01:07
	 * @param loginIp
	 * @param user
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer saveOrUpdate(HttpServletRequest request,String ip) {
		TLoginIp loginIp = new TLoginIp();
		loginIp.setLoginTime(new Date());
		loginIp.setLogIp(ip);
//		loginIp.setLogIp(request.getLocalAddr());
		super.save(loginIp);
		loginIp.setLogNum(NumUtils.getCommondNum(NumUtils.PIX_LOGIN_IP, loginIp.getId()));
		return 1;
	}
	
	/**
	 * 功能描述:判断此IP是否登录
	 * @author wsp  2017-1-20 上午11:20:05
	 * @param request
	 * @return
	 */
	public Integer getIsUseIp(HttpServletRequest request,String ip) {
//		String ip = request.getLocalAddr();
		String loginTime = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());//获取当前时间
		List<TLoginIp> loginIpList = loginIpDao.getLoginIp(ip,loginTime);
		
		if(EmptyUtils.isNotEmpty(loginIpList) && loginIpList.size() == 0){
			return 1;//今天未登录
		}else if(EmptyUtils.isNotEmpty(loginIpList) && loginIpList.size() > 0){
			return 2;//今天已经登录
		}
		return 1;
	}
	
	/**
	 * 功能描述:清除所有记录
	 * @author wsp  2017-1-20 下午01:44:21
	 */
	public void delete(){
		loginIpDao.delete();
	}

}
