/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ChannelInfoDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelInfo;

@Service
public class AppChannelInfoService extends BaseServiceImpl<TChannelInfo>{

	
	@Autowired
	@Qualifier("channelInfoDao")
	private ChannelInfoDao channelInfoDao;
	/**
	 * 
	 * 功能描述:根据类型获取渠道列表
	 * @param info
	 * @return
	 */
	public AppCommonModel getChannelByType(Page<TChannelInfo> page,TChannelInfo info){
		AppCommonModel model=new AppCommonModel(-1,"获取失败");
		if(EmptyUtils.isNotEmpty(info)){
			if(EmptyUtils.isNotEmpty(info.getChannelType())){				
				model.setResult(1);
				model.setObj(channelInfoDao.PageSql(page, info));
				model.setMsg("获取成功");
			}
		}
		return model;
	}
	
}
