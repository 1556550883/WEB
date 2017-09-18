/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-14
 */
package com.ruanyun.web.service.app;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.ChannelAdverStepUserDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverStepUser;


/**
 *@author feiyang
 *@date 2016-1-14
 */
@Service
public class AppChannelAdverStepUserService extends BaseServiceImpl<TChannelAdverStepUser>{

	@Autowired
	@Qualifier("channelAdverStepUserDao")
	private ChannelAdverStepUserDao channelAdverStepUserDao;
	/**
	 * 
	 * 功能描述:保存用户渠道下载的任务
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-14
	 */
	public AppCommonModel addChannelAdverUser(TChannelAdverStepUser info,String userNum){
		AppCommonModel model=new AppCommonModel(1,"保存成功");		
		info.setAdverUserStepStatus(-1);
		info.setUserAppNum(userNum);
		save(info);
		return model;
	}
	
}
