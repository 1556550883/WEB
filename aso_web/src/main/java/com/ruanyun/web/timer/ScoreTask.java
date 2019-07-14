package com.ruanyun.web.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ruanyun.web.producer.ArrayBlockQueueProducer;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.LoginIpService;
import com.ruanyun.web.service.background.UserScoreService;

/**
 * 
 *  #(c) ruanyun freetopapp <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明:  清除用户今日的分数
 *
 */
@Component
public class ScoreTask 
{
	@Autowired	
	private UserScoreService userScoreService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
//	@Autowired
//	private AppChannelAdverInfoService appChannelAdverInfoService;
//	@Autowired
//	private UserappidAdveridService userappidAdveridService;
//	@Autowired
//	private RedPackageLotteryService redPackageLotteryService;
	@Autowired
	private LoginIpService loginIpService;
	
	@Scheduled(cron="0 00 00 ? * * ")   //每天00点00分执行清除用户当天数据  
    public void clearUserScore()
	{  
		userScoreService.clearUserScoreDay();
    }  
	
	@Scheduled(cron="0 00 01 ? * * ")   //每天05点00分执行清除用户当天数据  
    public void stopTask()
	{  
		channelAdverInfoService.updateAdverStatusAll(2);
		ArrayBlockQueueProducer.removeAdverList.addAll(ArrayBlockQueueProducer.adverList);
    }  
	
	//@Scheduled(cron="0 00 06 ? * FRI")   //每个星期五执行操作 6点
	@Scheduled(cron="0 27 01 ? * MON") //每月1号九点
    public void bakAdverInfoTable()
	{  
		channelAdverInfoService.adverInfoTableBak();
    }  
	
//	@Scheduled(cron="0/5 * *  * * ? ")//每5s执行一次
//	public void myTest()
//	{  
//	    System.out.println("进入测试"); 
//	    TChannelAdverInfo tChannelAdverInfo = new TChannelAdverInfo();
//		tChannelAdverInfo.setAdverStatusEnd(2);
//		List<TChannelAdverInfo> adverInfoList = appChannelAdverInfoService.getByCondition(tChannelAdverInfo);
//		BigDecimal scoreSum = new BigDecimal("0.0");
//		if(adverInfoList != null)
//		{
//			for(TChannelAdverInfo item : adverInfoList)
//			{
//				TUserappidAdverid info = new TUserappidAdverid();
//				info.setUserAppId(Integer.valueOf(userAppId));
//				info.setAdverId(item.getAdverId());
//				info.setStatus("2");
//				Integer completeCount = userappidAdveridService.queryMissionCount(info);
//				
//				Map<String,Object> adverInfo = new HashMap<String,Object>();
//				adverInfo.put("adid", item.getAdid());
//				adverInfo.put("adverName", item.getAdverName());
//				adverInfo.put("adverDayStart", item.getAdverDayStart());
//				adverInfo.put("adverDayEnd", item.getAdverDayEnd());
//				adverInfo.put("adverCount", item.getAdverCount());
//				adverInfo.put("adverPrice", item.getAdverPrice());
//				adverInfo.put("completeCount", completeCount);
//				BigDecimal score = new BigDecimal(completeCount*item.getAdverPrice());
//				scoreSum = scoreSum.add(score);
//				adverInfo.put("score", score.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
//			}
//		}
//	}  
	
	@Scheduled(cron="0 00 03 ? * * ")   //每天23点59分执行清除IP登录记录
	public void clearLoginIp()
	{  
		loginIpService.delete();
	}  

//	@Scheduled(cron="0 30 23 ? * * ")   //每天23点30分开奖
//    public void openLottery(){  
//		String lotteryNum=TimeUtil.getCurrentDay("yyyyMMdd");
//		redPackageLotteryService.openLottery(lotteryNum);
//    }  
}
