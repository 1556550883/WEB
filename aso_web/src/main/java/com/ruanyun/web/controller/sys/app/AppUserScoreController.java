/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.controller.sys.app;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserScoreInfo;
import com.ruanyun.web.producer.QueueProducer;
import com.ruanyun.web.service.app.AppUserService;
import com.ruanyun.web.service.background.UserScoreInfoService;
import com.ruanyun.web.service.background.UserScoreService;

@Controller
@RequestMapping("app/userScore")
public class AppUserScoreController extends BaseController
{
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private AppUserService appUserService;
	@Autowired
	private UserScoreInfoService userScoreInfoService;
	/**
	 * 
	 * 手机端接口:判断当前用户是否可以兑换该商品
	 * @param session
	 * @param response
	 * @param tUserLogin
	 * @param goodNum
	 */
	
	@RequestMapping("getScore")
	public void getScore(HttpSession session, HttpServletResponse response, String userNum)
	{
		AppCommonModel model = new AppCommonModel();
		
		if(!StringUtils.hasText(userNum))
		{
			model.setResult(-1);
			model.setMsg("userNum不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		//查询个人信息
		TUserScore userScore = userScoreService.getScore(userNum);
		model.setObj(userScore);
		
		super.writeJsonDataApp(response, model);
	}
	
	@RequestMapping("putForward")
	public void putForward(HttpSession session, HttpServletResponse response, String userNum, Float forward) throws IOException, TimeoutException
	{
		AppCommonModel model = new AppCommonModel();
		
		if(!StringUtils.hasText(userNum))
		{
			model.setResult(-1);
			model.setMsg("userNum不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//List<TUserScoreInfo> getScoreInfoListByUserNums(String userNums)
		List<TUserScoreInfo> sTUserScoreInfos = userScoreInfoService.getScoreInfoListByUserNums(userNum);
		if(sTUserScoreInfos  != null && sTUserScoreInfos.size() > 0) 
		{
			//获取最新的提现记录
			TUserScoreInfo info = sTUserScoreInfos.get(0);
			if(info.getStatus() == 0) {
				model.setResult(2);
				model.setMsg("已有提现正在审核中！");
				super.writeJsonDataApp(response, model);
				return;
			}
			
			//如果最新的一条是被驳回的记录，那么在48小时之后才允许继续提现
			if(info.getStatus() == -1) {
				long nd = 1000 * 24 * 60 * 60;
			    long nh = 1000 * 60 * 60;
			    long nm = 1000 * 60;
				Date date = info.getScoreTime();
				Date now = new Date();
				long diff = now.getTime() - date.getTime();
				long day = diff / nd;   // 计算差多少天
				long hour = diff % nd / nh;
				long min = diff % nd % nh / nm;  // 计算差多少分钟
				//小时差
				hour = 48 - (day*24 + hour);
				String timeStr = "";
				if(hour >= 1) {
					timeStr = "由于上次被驳回，请在" + hour + "小时后重新申请";
					model.setResult(2);
					model.setMsg(timeStr);
					super.writeJsonDataApp(response, model);
					return;
				}else if(hour == 0){
					timeStr = "由于上次被驳回，请在" + min + "分钟后重新申请";
					model.setResult(2);
					model.setMsg(timeStr);
					super.writeJsonDataApp(response, model);
					return;
				}
			}
		}
		
		TUserApp userApp = appUserService.getUserByUserNum(userNum);
		if ("0".equals(userApp.getLoginControl())) 
		{
			model.setResult(-1);
			model.setMsg("该用户被禁止登录.");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TUserScore userScore = userScoreService.getScore(userNum);
		if(userScore.getScore() >= forward)
		{
			//model.setResult(userScoreService.addPutForward(userNum, forward));
			
			//减去提现的金额
			TUserScore score = new TUserScore();
			score.setType(2);
			score.setUserNum(userNum);
			score.setScore(forward);
			model.setResult(1);
			QueueProducer ap  = new QueueProducer();
			ap.sendMessage(score, "socre");
			ap.close();
		}else 
		{
			model.setResult(-1);
		}
		
		super.writeJsonDataApp(response, model);
	}

	@RequestMapping("getForwardScore")
	public void getForwardScore(HttpSession session, HttpServletResponse response, String userNum, Page<TUserScoreInfo> page)
	{
		AppCommonModel model = new AppCommonModel();
		
		if(!StringUtils.hasText(userNum))
		{
			model.setResult(-1);
			model.setMsg("userNum不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		//查询个人信息
		List<TUserScoreInfo> userScoreInfoList = userScoreService.getScoreInfoListByUserNums(userNum);
		page.setResult(userScoreInfoList);
		model.setObj(page);
		model.setResult(1);
		super.writeJsonDataApp(response, model);
	}
	/**
	 * 
	 * 手机端接口:获取收徒排行榜
	 * @param page
	 * @param info
	 * type 1/2  收益/收徒数量
	 * @return
	 *@author feiyang
	 *@date 2016-1-20
	 */
//	@RequestMapping("getRankingList")
//	public void getRankingList(HttpSession session,HttpServletResponse response,Page<TUserScore>page,TUserScore info,Integer type,String userNum,String sign){
//		AppCommonModel model=new AppCommonModel();		
//		try
//		{
//			model=appUserScoreService.getRankingList(page, info,type);
//		} 
//		catch (Exception e)
//		{
//			model=new AppCommonModel(-1,"数据异常");
//		}		
//		super.writeJsonDataApp(response, model);
//	}
}
