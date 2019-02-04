/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-14
 */
package com.ruanyun.web.service.app;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserAppDao;
import com.ruanyun.web.model.TUserApp;

@Service
public class AppUserService extends BaseServiceImpl<TUserApp>{

	@Autowired
	@Qualifier("userAppDao")
	private UserAppDao userAppDao;
	
	/**
	 * 
	 * 功能描述:根据NUM获取用户
	 * @param userNum
	 * @return
	 */
	public TUserApp getUserByUserNum(String userNum)
	{		
		return userAppDao.getUserByUserNum(userNum);
	}
	

	//getUserByUserAppid
	
	
	public TUserApp getUserByUserAppid(String appid)
	{		
		return userAppDao.getUserByUserAppid(appid);
	}
	
	public TUserApp getUserByUdid(String udid)
	{		
		return userAppDao.getUserByUdid(udid);
	}
	
	public TUserApp getUserByOpenID(String openID)
	{		
		return userAppDao.getUserByOpenID(openID);
	}
	/**
	 * 
	 * 功能描述:根据序列号获取用户
	 * @param phoneSerialNumber
	 * @return
	 */
	public TUserApp getUserBySerialNum(String phoneSerialNumber){
		return userAppDao.getUserBySerialNum(phoneSerialNumber);
	}

	/**
	 * 功能描述:修改appStore  苹果账号
	 * @author wsp  2017-1-20 下午03:05:47
	 * @param userNum
	 * @param appStore
	 */
	public int updateAppStore(String userNum, String appStore) {
		TUserApp userApp = get(TUserApp.class, "userNum", userNum);
		if(EmptyUtils.isEmpty(userApp)){
			return 2;
		}else if(EmptyUtils.isEmpty(appStore)){
			return 3;
		}
		
		userApp.setAppStore(appStore);
		update(userApp);
		return 1;
	}
	
	public int updateUserByUdid(String udid,String idfa,String phoneModel, String phoneVersion)
	{
		TUserApp userApp = userAppDao.getUserByUdid(udid);
		if(userApp == null) {
			return -1;
		}
	
		userApp.setIdfa(idfa);
		userApp.setPhoneModel(phoneModel);
		userApp.setPhoneVersion(phoneVersion);
		update(userApp);
		return 1;
	}
	
	public int updateUserWeiXin(String udid,String weiXinName,String headImgUrl, String openID)
	{
		TUserApp userApp = getUserByOpenID(openID);
		if(userApp != null) {
			return -1;
		}
		
		userApp = getUserByUdid(udid);
		if(EmptyUtils.isEmpty(userApp))
		{
			return 2;
		}
		
		try
		{
			userApp.setWeixin(URLDecoder.decode(weiXinName, "UTF-8"));
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		//userApp.setWeixin(weiXinName);
		userApp.setFlag5(headImgUrl);
		userApp.setOpenID(openID);
		update(userApp);
		return 1;
	}
	
	//updateUserName
	public int updateUserName(String userNum,String userName) 
	{
		TUserApp userApp = get(TUserApp.class, "userNum", userNum);
		if(EmptyUtils.isEmpty(userApp))
		{
			return 2;
		}
		
		try
		{
			userApp.setUserNick(URLDecoder.decode(userName, "UTF-8"));
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		
		update(userApp);
		return 1;
	}
	
	//updateUserAlipay
	public int updateUserAlipay(String alipay, String udid, String usernick) 
	{
		TUserApp userApp = getUserByUdid(udid);
		if(EmptyUtils.isEmpty(userApp)){
			return -1;
		}
		
		try
		{
			userApp.setUserNick(URLDecoder.decode(usernick, "UTF-8"));
			userApp.setZhifubao(URLDecoder.decode(alipay, "UTF-8"));
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}

		update(userApp);
		return 1;
	}
	
	public int updateUserPhoneNum(String userNum,String phoneNum)
	{
		TUserApp userApp = get(TUserApp.class, "userNum", userNum);
		if(EmptyUtils.isEmpty(userApp)){
			return 2;
		}
		userApp.setPhoneNum(phoneNum);
		update(userApp);
		return 1;
	}
}
