/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-23
 */
package com.ruanyun.web.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserSign;
import com.ruanyun.web.service.UserSignService;

/**
 *@author feiyang
 *@date 2016-1-23
 */
@Service
public class AppUserSignService extends BaseServiceImpl<TUserSign>{
	@Autowired
	private UserSignService userSignService;
	
	/**
	 * 
	 * 功能描述:签到
	 * @param userNum
	 * @param signTime
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public AppCommonModel addTUserSign(String userNum ,String signTime){
		AppCommonModel model=new AppCommonModel(-1,"您今天已经签到过了");
		int re=userSignService.getTodayTUserSign(userNum);
		if (re==1) {			
			int score=userSignService.addUserSign(userNum, signTime);
			model.setResult(1);
			model.setObj(score);
			model.setMsg("签到成功");
		}else {
			model.setObj(0);
			model.setMsg("您今天已经签到过了！");
		}
		return model;
	}
}
