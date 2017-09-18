/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-20
 */
package com.ruanyun.web.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserScoreInfoDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserScoreInfo;

/**
 *@author feiyang
 *@date 2016-1-20
 */
@Service
public class AppUserScoreInfoService extends BaseServiceImpl<TUserScoreInfo>{

	@Autowired
	@Qualifier("userScoreInfoDao")
	private UserScoreInfoDao userScoreInfoDao;
	
	/**
	 * 
	 * 功能描述:获取个人的积分明细
	 * @param page
	 * @param info
	 * type 1/2 今日/全部
	 * @return
	 *@author feiyang
	 *@date 2016-1-20
	 */
	public AppCommonModel getMyScoreList(Page<TUserScoreInfo>page,String userNum,Integer type){
		AppCommonModel model =new AppCommonModel();
		TUserScoreInfo info=new TUserScoreInfo();
		if(EmptyUtils.isNotEmpty(userNum)){
			info.setUserAppNum(userNum);
		}
		model.setResult(1);		
		model.setObj(userScoreInfoDao.pageSql(page, info,type));
		return model;
		
	}
}
