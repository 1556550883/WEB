/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.service.background;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.dao.sys.background.RedPackageLotteryDao;
import com.ruanyun.web.model.TRedPackageLottery;
import com.ruanyun.web.model.TUserRedPackage;
import com.ruanyun.web.util.Constants;


/**
 *@author feiyang
 *@date 2016-3-2
 */
@Service()
public class RedPackageLotteryService extends BaseServiceImpl<TRedPackageLottery> {

	@Autowired
	private RedPackageLotteryDao redPackageLotteryDao;
	@Autowired
	private UserRedPackageService userRedPackageService;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private RedPackageService redPackageService;

	/**
	 * 
	 * 功能描述:后台管理红包
	 * 
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public Page<TRedPackageLottery> PageSql(Page<TRedPackageLottery> page,TRedPackageLottery info,Integer type) {
		return redPackageLotteryDao.PageSql(page, info,type);
	}

	/**
	 * 
	 * 功能描述:今日是否已经开奖
	 * 
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public int isDayLottery() {
		return redPackageLotteryDao.isDayLottery();
	}
	
	
	/**
	 * 
	 * 功能描述:增加或者修改
	 * @param info
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public void saveOrUpd(TRedPackageLottery info){
		if(EmptyUtils.isNotEmpty(info.getLotteryId())){
			TRedPackageLottery oldInfo=get(TRedPackageLottery.class, info.getLotteryId());
			BeanUtils.copyProperties(info, oldInfo, new String[]{"lotteryNum","createTime"});
			update(oldInfo);
		}else{
			info.setLotteryStatus(0);//默认为开奖
			info.setCreateTime(new Date());
			save(info);
						
		}
	}
	
	
	/**
	 * 
	 * 功能描述:获取往期的开奖
	 * @return
	 *@author feiyang
	 *@date 2016-3-3
	 */
	public TRedPackageLottery getPastLottery(){
		return	redPackageLotteryDao.getPastLottery();
	}
	
	/**
	 * 
	 * 功能描述:根据开奖编号获取本期开奖红包
	 * @param lotteryNum
	 * @return
	 *@author feiyang
	 *@date 2016-3-3
	 */
	public TRedPackageLottery getLotteryByluLotteryNum(){		
		return redPackageLotteryDao.getLotteryByluLotteryNum();
	}
	
	/**
	 * 功能描述: 定时调用任务
	 *
	 * @author yangliu  2016年4月7日 下午2:41:40
	 * 
	 * @param lotteryNum 编号
	 * @return
	 */
	@Transactional
	public int openLottery(String lotteryNum){
		TRedPackageLottery redPackageLottery=super.get(TRedPackageLottery.class, "lotteryNum", lotteryNum);
		
		if(redPackageLottery==null){
			redPackageLottery=new TRedPackageLottery(lotteryNum, "1,2,3,4,5,6", 50F, 1,new Date());
			save(redPackageLottery);
		}else if(1==redPackageLottery.getLotteryStatus()){
			return 1; 
		}
		String result=","+redPackageLottery.getLotteryResult()+",";
		List<TUserRedPackage> userRedPackageList=userRedPackageService.getUserRedPackageList(lotteryNum);
		Iterator<TUserRedPackage> itUserRedPackage=userRedPackageList.iterator();
		TUserRedPackage userRedPackage=null;
		Map<String,Integer> userLotteryMap = new HashMap<String,Integer>();   //中奖的用户
		String userNum="";
		int count=0;  //中奖个数
		while(itUserRedPackage.hasNext()){
			userRedPackage=itUserRedPackage.next();
			if(result.indexOf(","+userRedPackage.getUserPackageNum()+",")>-1){
				userNum=userRedPackage.getUserNum();
				Integer sum=userLotteryMap.get(userNum);  
				if(sum==null){
					sum=0;
				}
				++sum;
				count++;
				userLotteryMap.put(userNum, sum);
				//userScoreService.addScoreSum(userRedPackage.getUserNum(), lotteryNum+"期中奖号:"+userRedPackage.getUserPackageNum(), Constants.USER_SCORE_INFO_TYPE_8, score, userType);
			}
		}
		if(count>0){  //中奖人数大于0
			Iterator<String> userNumIt=userLotteryMap.keySet().iterator();
			Float score=redPackageLottery.getLotteryScore()/count;
			while(userNumIt.hasNext()){
				userNum=userNumIt.next();
				userScoreService.addScoreSum(userRedPackage.getUserNum(), lotteryNum+"", Constants.USER_SCORE_INFO_TYPE_8, score*userLotteryMap.get(userNum), Constants.USER_TYPE_APP);
			}
		}
		redPackageService.clearDay();
		redPackageLottery.setLotteryStatus(1);
		super.update(redPackageLottery);
		userScoreService.clearUserRedPackageCount();
		return 0;
	}
}
