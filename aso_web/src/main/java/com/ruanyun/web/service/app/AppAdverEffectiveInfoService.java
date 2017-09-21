/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-14
 */
package com.ruanyun.web.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.AdverEffectiveInfoDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TAdverEffectiveInfo;

/**
 *@author feiyang
 *@date 2016-1-14
 */
@Service
public class AppAdverEffectiveInfoService extends BaseServiceImpl<TAdverEffectiveInfo> {
	@Autowired
	@Qualifier("adverEffectiveInfoDao")
	private AdverEffectiveInfoDao adverEffectiveInfoDao;

	
	/**
	 * 
	 * 功能描述:获取分页
	 * @param page
	 * @param tUserLogin
	 * 根据UserNum获取分页信息
	 * @return
	 */
	public AppCommonModel getAllAdverEffective(Page<TAdverEffectiveInfo>page,String userNum)
	{		
		AppCommonModel model=new AppCommonModel(1,"");
		model.setObj(adverEffectiveInfoDao.pageSql(page, userNum));
		return model;
	}
	/**
	 * 
	 * 手机端接口:获取最热列表
	 * @return
	 *@author feiyang
	 *@date 2016-1-21
	 */
	public AppCommonModel getHotList(){
		AppCommonModel model=new AppCommonModel(1,"获取成功");
		List<TAdverEffectiveInfo>list=adverEffectiveInfoDao.getHotList();
		model.setObj(list);
	return model;
	}
}
