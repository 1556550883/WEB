/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.service.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserRedPackage;
import com.ruanyun.web.service.background.UserRedPackageService;


/**
 *@author feiyang
 *@date 2016-3-2
 */
@Service
public class AppUserRedPackageService extends BaseServiceImpl<TUserRedPackage>{


	
	@Autowired
	private UserRedPackageService userRedPackageService;
	
	
	/**
	 * 
	 * 手机端接口:用户猜红包
	 * @param tUserRedPackage
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public AppCommonModel addUserRedPackage(TUserRedPackage tUserRedPackage){
		AppCommonModel model =new AppCommonModel(-1,"猜红包失败");
		if (EmptyUtils.isNotEmpty(tUserRedPackage.getUserNum())||EmptyUtils.isNotEmpty(tUserRedPackage.getUserPackageNum())) {
		int re=	userRedPackageService.addUserRedPackage(tUserRedPackage.getUserNum(), tUserRedPackage.getUserPackageNum());
		if (re==1) {
			model.setMsg("猜红包成功");
			model.setResult(1);
		}else if (re==-1) {
			model.setMsg("今日可以抢红包数量不足");
		}
		}		
		return model;
		
	}
	

	/**
	 * 
	 * 手机端接口:用户的猜红包的历史记录
	 * @param page
	 * @param tUserRedPackage
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public AppCommonModel getUserRedPackageList(Page<TUserRedPackage> page,TUserRedPackage tUserRedPackage){
		AppCommonModel model =new AppCommonModel(-1,"参数不全");
		if (EmptyUtils.isNotEmpty(tUserRedPackage.getUserNum())) {
			Page<TUserRedPackage> pageList=userRedPackageService.pageSql(page, tUserRedPackage);
			model.setResult(1);
			model.setMsg("查询成功");
			if (pageList.getResult().size()>0) {
				model.setObj(pageList);				
			}else {
				model.setObj("{}");
			}
		}		
		return model;
		
	}
	
}
