/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-12
 */
package com.ruanyun.web.service.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.UserLoginDao;
import com.ruanyun.web.model.TUserLogin;

/**
 *@author feiyang
 *@date 2016-1-12
 */
@Service
public class UserLoginService extends BaseServiceImpl<TUserLogin> {

	@Autowired
	private UserLoginDao userLoginDao;

	/**
	 * 
	 * 功能描述:根据用户编码获取用户
	 * 
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */
	public TUserLogin getUserByUserNum(String userNum,Integer loginType) {
		return userLoginDao.getUserByUserNum(userNum,loginType);
	}
	
	public TUserLogin getUserLogin(String loginName,Integer loginType){
		return super.get(TUserLogin.class, new String[]{"loginType","loginName"},new Object[]{loginType,loginName});
	}
}
