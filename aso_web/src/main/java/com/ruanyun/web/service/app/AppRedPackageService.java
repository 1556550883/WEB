/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.service.app;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TRedPackage;
import com.ruanyun.web.model.TRedPackageLottery;
import com.ruanyun.web.service.background.RedPackageLotteryService;
import com.ruanyun.web.service.background.RedPackageService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.NumUtils;

/**
 *@author feiyang
 *@date 2016-3-2
 */
@Service
public class AppRedPackageService extends BaseServiceImpl<TRedPackage>{
	@Autowired
	private RedPackageService redPackageService;
	
	@Autowired
	private RedPackageLotteryService redPackageLotteryService;
	
	@Autowired
	private UserScoreService userScoreService;
	/**
	 * 
	 * 手机端接口:获取红包列表 （包括是否已猜）
	 * @param userNum 当前用户
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public AppCommonModel getgetListByUser(String  userNum) {
		AppCommonModel model = new AppCommonModel(-1,"参数不全");
		if(EmptyUtils.isNotEmpty(userNum)){	
			TRedPackageLottery  tRedPackageLottery=redPackageLotteryService.getLotteryByluLotteryNum();
			if (EmptyUtils.isEmpty(tRedPackageLottery)) {
				model.setMsg("不存在本期");
				return model;
			}								
			 Map outh = new HashMap();
			 Calendar   cal   =   Calendar.getInstance();	
			  String lotteryTime = new SimpleDateFormat( "MM月dd日  "+NumUtils.DAY_LOTTERY_TIME).format(cal.getTime());
			 outh.put("lotteryTime", lotteryTime); //开奖日期
			 if (tRedPackageLottery.getLotteryScore()<=0f) {
				 tRedPackageLottery.setLotteryScore(NumUtils.LOTTERT_SCORE);
			 }
			 outh.put("lotteryScore", tRedPackageLottery.getLotteryScore()); //开奖金额
			 outh.put("lotteryDayNum", redPackageService.getRedPackageDaySum());//猜奖总次数
			 outh.put("pastLottery", redPackageLotteryService.getPastLottery().getLotteryResult());//往期开奖
			 outh.put("list", redPackageService.getListByUserNum(userNum,NumUtils.getLotteryNum()));
			 outh.put("redPackageCountDay", userScoreService.getScore(userNum).getRedPackageCountDay());

			model.setMsg("查询成功");
			model.setResult(1);
			model.setObj(outh);
		}
		return model;
	}
}
