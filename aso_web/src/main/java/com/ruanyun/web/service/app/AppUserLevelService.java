/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-25
 */
package com.ruanyun.web.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserLevelDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserLevel;
import com.ruanyun.web.service.background.UserLevelRateService;

/**
 *@author feiyang
 *@date 2016-1-25
 */
@Service
public class AppUserLevelService extends BaseServiceImpl<TUserLevel> {
	@Autowired
	@Qualifier("userLevelDao")
	private UserLevelDao userLevelDao;

	@Autowired
	private UserLevelRateService userLevelRateService;

	/**
	 * 
	 * 手机端接口:获取等级说明
	 * 
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	public AppCommonModel getUserLevelList(Page<TUserLevel> page) {
		AppCommonModel model = new AppCommonModel(1, "获取成功");
		model.setObj(userLevelDao.pageSql(page));
		return model;
	}

	
	
	
	
	/**
	 * 
	 * 手机端接口:根据等级编号获取该等级的得分比例
	 * @param levelNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	public AppCommonModel getUserLevelDetail(String levelNum) {
		AppCommonModel model = new AppCommonModel(-1, "获取失败");
		if (EmptyUtils.isNotEmpty(levelNum)) {
			model.setMsg("获取成功");
			model.setResult(1);
			model.setObj(userLevelRateService.getListByLevelNum(levelNum));
		}
		return model;
	}
	
	/**
	 * 
	 * 功能描述:收徒等级
	 * @param page
	 * @return
	 *@author feiyang
	 *@date 2016-3-22
	 */
	public AppCommonModel getApprenticeLevelList(Page<TUserLevel> page) {
		AppCommonModel model = new AppCommonModel(1, "获取成功");
		model.setObj(userLevelDao.pageSql1(page));
		return model;
	}
}
