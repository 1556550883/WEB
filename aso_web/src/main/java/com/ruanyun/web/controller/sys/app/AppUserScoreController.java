/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-11
 */
package com.ruanyun.web.controller.sys.app;

import java.io.IOException;
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
import com.ruanyun.web.service.background.UserScoreService;

@Controller
@RequestMapping("app/userScore")
public class AppUserScoreController extends BaseController
{
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private AppUserService appUserService;
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
			model.setResult(userScoreService.addPutForward(userNum, forward));
			
			//减去提现的金额
			if(model.getResult() == 1)
			{
				TUserScore score = new TUserScore();
				score.setType(2);
				score.setUserNum(userNum);
				score.setScore(-forward);
				
				QueueProducer.getQueueProducer().sendMessage(score, "socre");
			}
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
