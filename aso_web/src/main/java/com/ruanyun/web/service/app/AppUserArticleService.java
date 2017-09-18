/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.service.app;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.UserArticleDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TArticleInfo;
import com.ruanyun.web.model.TUserArticle;
import com.ruanyun.web.service.background.ArticleInfoService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.Constants;

/**
 *@author feiyang
 *@date 2016-4-16
 */
@Service
public class AppUserArticleService extends BaseServiceImpl<TUserArticle>{
	
	@Autowired
	private UserArticleDao userArticleDao;

	@Autowired
	private  UserScoreService userScoreService;
	
	@Autowired
	private ArticleInfoService articleInfoService;
	
	
	/**
	 * 
	 * 功能描述:用户关联文章
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public AppCommonModel addUserArticle(TUserArticle info) {
		AppCommonModel model = new AppCommonModel(-1, "任务失败");
		if (EmptyUtils.isNotEmpty(info.getUserNum())||EmptyUtils.isNotEmpty(info.getArticleNum())) {
			TUserArticle tUserArticle=userArticleDao.getUserArticleByUseNum(info);
			if (EmptyUtils.isEmpty(tUserArticle)) {	
				TArticleInfo articleInfo=articleInfoService.get(TArticleInfo.class, "articleNum",info.getArticleNum());
				if (EmptyUtils.isNotEmpty(articleInfo)) {
					saveUserArticle(info.getUserNum(), info.getArticleNum(), articleInfo.getRewardQuantity(),articleInfo);	
					model.setResult(1);
					model.setMsg("任务成功");
				}
			}else {
				model.setMsg("您已完成过该文章任务");
			}
		}else {
				model.setMsg("参数不全");
		}
		return model;
	}
	


	/**
	 * 
	 * 功能描述:增加用户关联文章
	 * @param userNum  用户编号
	 * @param articleNum  关联文章编号
	 * @param rewardQuantity 奖励
	 *@author feiyang
	 *@date 2016-4-16
	 */
	public void saveUserArticle(String userNum,String articleNum,Float rewardQuantity,TArticleInfo info){
		TUserArticle tUserArticle=new TUserArticle();
		tUserArticle.setUserNum(userNum);
		tUserArticle.setArticleNum(articleNum);
		tUserArticle.setRewardQuantity(rewardQuantity);
		tUserArticle.setCreateTime(new Date());
		save(tUserArticle);		
		userScoreService.addScoreSum(userNum, "文章任务得分", Constants.USER_SCORE_INFO_TYPE_9, rewardQuantity,Constants.USER_TYPE_APP);
		articleInfoService.updateForwardQuantity(info);//增加转发量量
	}

}

		
