/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-20
 */
package com.ruanyun.web.service.app;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.UserApprenticeDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserApprentice;

@Service
public class AppUserApprenticeService extends BaseServiceImpl<TUserApprentice>
{
	@Autowired
	@Qualifier("userApprenticeDao")
	private UserApprenticeDao userApprenticeDao;
	
	
	/**
	 * 
	 * 手机端接口:获取收徒明细
	 * @param page
	 * @param info
	 * @param type 1/2 今日/全部
	 * @return
	 */
	public AppCommonModel getMyApprenticeList(Page<TUserApprentice>page,TUserApprentice info,Integer type,Integer userApprenticeType){
		AppCommonModel model=new AppCommonModel();
		Page<TUserApprentice> list=userApprenticeDao.pageSql(page, info,2,userApprenticeType);
		model.setResult(1);
		model.setObj(list);
		return model;
	}
	
	public AppCommonModel getMyApprenticeList(Page<TUserApprentice>page,String masterNum)
	{
		AppCommonModel model=new AppCommonModel();
		Page<TUserApprentice> list=userApprenticeDao.pageSql(page, masterNum);
		model.setResult(1);
		model.setObj(list);
		return model;
	}
	
	public Page<TUserApprentice> getMyApprentices(Page<TUserApprentice>page,String masterNum)
	{
		 return userApprenticeDao.pageSql(page, masterNum);
	}
	
	public void addMyApprenticeScore(String masterNum, String ApprenticeNum, Float score, int type) 
	{
		TUserApprentice userApprentice = new TUserApprentice();
		userApprentice.setUserNum(masterNum);
		userApprentice.setApprenticeUserNum(ApprenticeNum);
		userApprentice.setScore(score);
		userApprentice.setUserApprenticeType(type);
		userApprentice.setApprenticeTime(new Date());
		super.save(userApprentice);
	}
}
