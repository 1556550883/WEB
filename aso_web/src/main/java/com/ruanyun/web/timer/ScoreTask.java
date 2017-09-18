package com.ruanyun.web.timer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.service.background.LoginIpService;
import com.ruanyun.web.service.background.RedPackageLotteryService;
import com.ruanyun.web.service.background.RedPackageService;
import com.ruanyun.web.service.background.UserScoreService;

/**
 * 
 *  #(c) ruanyun freetopapp <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明:  清除用户今日的分数
 * 
 *  <br/>创建说明: 2016年1月29日 下午9:26:00 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Component
public  class ScoreTask {
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private RedPackageLotteryService redPackageLotteryService;
	@Autowired
	private LoginIpService loginIpService;
	
	@Scheduled(cron="0 59 23 ? * * ")   //每天23点59分执行清除用户当天数据  
    public void clearUserScore(){  
          userScoreService.clearUserScoreDay();
    }  
	
	@Scheduled(cron="0 59 23 ? * * ")   //每天23点59分执行清除IP登录记录
	public void clearLoginIp(){  
		loginIpService.delete();
	}  
	
	@Scheduled(cron="0 30 23 ? * * ")   //每天23点30分开奖
    public void openLottery(){  
		String lotteryNum=TimeUtil.getCurrentDay("yyyyMMdd");
		redPackageLotteryService.openLottery(lotteryNum);
    }  

	
	
	

}
