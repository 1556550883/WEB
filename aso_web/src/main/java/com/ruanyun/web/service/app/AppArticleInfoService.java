/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.ArticleInfoDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TArticleInfo;
import com.ruanyun.web.service.background.ArticleInfoService;


/**
 *@author feiyang
 *@date 2016-4-16
 */
@Service
public class AppArticleInfoService extends BaseServiceImpl<TArticleInfo>{
	
	@Autowired
	private ArticleInfoDao articleInfoDao; 
	@Autowired
	private ArticleInfoService articleInfoService; 
	

	/**
	 * 
	 * 功能描述:根据用户编号获取文章列表
	 * @param page
	 * @param info
	 * @param userNum
	 * @return
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public AppCommonModel getTArticleInfoByUserNum(Page<TArticleInfo> page,String userNum,Integer isAuth) {
		AppCommonModel model = new AppCommonModel(-1, "查询失败");
		if (EmptyUtils.isNotEmpty(userNum)) {		
				model.setMsg("查询成功");
				model.setResult(1);
				model.setObj(articleInfoDao.appPageSql(page, userNum,isAuth));
		}else {
			model.setMsg("参数不全");	
		}
		return model;
	}

	/**
	 * 	功能描述:文章详情
	 * @author wsp  2017-1-10 下午03:08:41
	 * @param articleNum
	 * @return
	 */
	public AppCommonModel getTArticleInfoDetail(String articleNum) {
		AppCommonModel model = new AppCommonModel(-1, "查询失败");
		if (EmptyUtils.isNotEmpty(articleNum)) {		
			model.setMsg("查询成功");
			model.setResult(1);
			model.setObj(articleInfoDao.get(TArticleInfo.class, "articleNum",articleNum));
	}else {
		model.setMsg("参数不全");	
	}
		
		return model;
	}
}







