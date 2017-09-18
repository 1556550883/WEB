/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.service.background;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserRedPackageDao;
import com.ruanyun.web.model.TRedPackage;
import com.ruanyun.web.model.TUserRedPackage;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.util.NumUtils;

/**
 *@author feiyang
 *@date 2016-3-2
 */
@Service
public class UserRedPackageService extends BaseServiceImpl<TUserRedPackage>{

	@Autowired
	private UserRedPackageDao userRedPackageDao;
	
	@Autowired
	private UserScoreService userScoreService;
	
	
	@Autowired
	private RedPackageService redPackageService;
	
	/**
	 * 
	 * 功能描述:当前用户的猜红包的历史记录
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public Page<TUserRedPackage> pageSql(Page<TUserRedPackage> page,TUserRedPackage info){
		return userRedPackageDao.pageSql(page, info);
	}
/**
 * 
 * 功能描述:用户猜红包
 * @param userNum 用户编号
 * @param userPackageNum 红包编号
 * @param lotteryNum 开奖期号
 * @return
 *@author feiyang
 *@date 2016-3-2
 */
	public int addUserRedPackage(String userNum,String userPackageNum){
		TUserRedPackage tUserRedPackage=new TUserRedPackage();
		if (EmptyUtils.isNotEmpty(userNum)||EmptyUtils.isNotEmpty(userPackageNum)) {
			TUserScore tUserScore=userScoreService.getScore(userNum);
			if (EmptyUtils.isNotEmpty(tUserScore)) {
				if (tUserScore.getRedPackageCountDay()<=0) {
					return -1;//今日可以抢红包数量不足
				}else {
					tUserRedPackage.setUserNum(userNum);
					tUserRedPackage.setUserPackageNum(userPackageNum);					
					tUserRedPackage.setLotteryNum(NumUtils.getLotteryNum());
					tUserRedPackage.setCreateDate(new Date());
					save(tUserRedPackage);
					tUserScore.setHaveRedPackageDay(tUserScore.getHaveRedPackageDay()+1);//今日已抢红包数量+1
					tUserScore.setRedPackageCountDay(tUserScore.getRedPackageCountDay()-1);//今日可以抢红包数量-1
					userScoreService.update(tUserScore);
					TRedPackage tRedPackage=redPackageService.getRedPackageByRedPackageNum(userPackageNum);
					if (EmptyUtils.isEmpty(tRedPackage)) {
						return 0;//红包不存在
					}
					tRedPackage.setRedPackageCountDay(tRedPackage.getRedPackageCountDay()+1);//今日红包用户猜的数量+1
					redPackageService.update(tRedPackage);
					return 1;//猜红包成功
				}
			}		
		}		
		return 0;
	}
	
	/**
	 * 功能描述: 获取固定期的红包
	 *
	 * @author yangliu  2016年4月7日 上午11:33:27
	 * 
	 * @param lotteryNum
	 * @return
	 */
	public List<TUserRedPackage> getUserRedPackageList(String lotteryNum){
		if(EmptyUtils.isEmpty(lotteryNum))
			return null;
		return super.getAllByCondition(TUserRedPackage.class, "lotteryNum", lotteryNum);
	}
	
}
