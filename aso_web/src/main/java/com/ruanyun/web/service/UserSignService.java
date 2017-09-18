/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-19
 */
package com.ruanyun.web.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.dao.sys.UserSignDao;
import com.ruanyun.web.model.TUserSign;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.NumUtils;

/**
 *@author feiyang
 *@date 2016-1-19
 */
@Service
public class UserSignService extends BaseServiceImpl<TUserSign> {
	@Autowired
	@Qualifier("userSignDao")
	private UserSignDao userSignDao;
	@Autowired
	private UserScoreService userScoreService;

	/**
	 * 
	 * 功能描述:签到
	 * 
	 * @param userNum
	 * @param signTime
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public int addUserSign(String userNum, String signTime) {
		if (EmptyUtils.isNotEmpty(userNum)) {
			int re = getTodayTUserSign(userNum);
			if (re == 1) {
				String nowStr=TimeUtil.doFormatDate(new Date(), "yyyy-MM-dd");
				Float score=Constants.USER_SIGN_SCORCE;
				int count=userSignDao.getCountByDay(userNum, nowStr);
				if(count>=6){
					score=15F;
				}
				
				TUserSign tUserSign = new TUserSign();
				tUserSign.setUserNum(userNum);
				tUserSign.setSignTime(signTime);
				tUserSign.setCreatetime(new Date());
				tUserSign.setSignScore(score);
				save(tUserSign);
				tUserSign.setSignNum(NumUtils.getCommondNum(
						NumUtils.USER_SIGN_NUM, tUserSign.getSignId()));
				userScoreService.addScoreSum(userNum, "签到得分",
						Constants.USER_SCORE_INFO_TYPE_2,
						score, 1);
				return score.intValue();
			}
		}
		return 0;
	}

	/**
	 * 
	 * 功能描述:当前用户今天是否签到
	 * 
	 * @param sign
	 * 
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public int getTodayTUserSign(String userNum) {
		TUserSign oldSign = userSignDao.getTodayTUserSign(userNum);
		if (EmptyUtils.isEmpty(oldSign)) {
			return 1;
		}
		return 0;
	}
}
