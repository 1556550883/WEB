/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-16
 */
package com.ruanyun.web.service.background;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.orm.ICommonSqlDao;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.UserScoreInfoDao;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserScoreInfo;

/**
 *@author feiyang
 *@date 2016-1-16
 */
@Service
public class UserScoreInfoService extends BaseServiceImpl<TUserScoreInfo>{

	@Autowired
	@Qualifier("userScoreInfoDao")
	private UserScoreInfoDao userScoreInfoDao;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	@Qualifier("commonSqlExpandDao")
	protected ICommonSqlDao sqlDao;
	
	/**
	 * 功能描述:保存得分记录
	 *
	 * @author yangliu  2016年1月16日 下午6:41:51
	 * 
	 * @param userAppNum 用户编号
	 * @param scoreName 得分名称
	 * @param score 分数
	 * @param scoreType 分数类型
	 * @param userType 用户类型
	 */
	public void saveUserScoreInfo(String userAppNum,String scoreName,Float score,Integer scoreType,Integer userType)
	{
		TUserScoreInfo userScoreInfo = new TUserScoreInfo();
		userScoreInfo.setScore(score);
		userScoreInfo.setScoreName(scoreName);
		userScoreInfo.setScoreTime(new Date());
		userScoreInfo.setScoreType(scoreType);
		userScoreInfo.setUserAppNum(userAppNum);
		userScoreInfo.setStatus(0);
		userScoreInfo.setUserType(userType);
		save(userScoreInfo);
		userScoreInfo.setUserScoreInfoNum(getNewScoreInfoNum(userScoreInfo.getUserScoreInfoId()));
	}
	
	public int updateScoreInfoStatus(String userScoreInfoId)
	{
		StringBuffer sql = new StringBuffer(" UPDATE t_user_score_info set status = ? WHERE user_score_info_id='"+userScoreInfoId+"'");
		Object[] params = new Object[1];
		params[0] = 1;
		return sqlDao.update(params, sql.toString());
	}
	
	@Transactional
	public void deleteScoreInfo(String userScoreInfoNum,String userAppNum){
		TUserScoreInfo userScoreInfo=get(TUserScoreInfo.class,"userScoreInfoNum", userScoreInfoNum);
		TUserScore usreScore=userScoreService.getScore(userAppNum);
		userScoreService.updateScore(usreScore, -userScoreInfo.getScore(), usreScore.getType());
		super.delete(userScoreInfo);
	}
	
	
	public String getNewScoreInfoNum(int id)
	{
		return "USI_"+String.format("%08d", id);
	}
	
	public List<TUserScoreInfo> getScoreInfoListByUserNums(String userNums)
	{
		StringBuffer sql=new StringBuffer(" SELECT * FROM t_user_score_info WHERE user_app_num='"+userNums+"'");
		return sqlDao.getAll(TUserScoreInfo.class, sql.toString());
	}
	/**
	 * 
	 * 功能描述:后台手机用户获取用户的积分明细
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
			public Page<TUserScoreInfo> pageSql(Page<TUserScoreInfo>page,TUserScoreInfo info){
				Integer type=0;
				return userScoreInfoDao.pageSql(page, info, type);
			}
}
