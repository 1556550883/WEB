package com.ruanyun.web.controller.sys.app;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.service.app.AppShopGoodsService;
import com.ruanyun.web.service.app.AppUserExchangeService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.duiba.CreditConsumeParams;
import com.ruanyun.web.util.duiba.CreditConsumeResult;
import com.ruanyun.web.util.duiba.CreditNotifyParams;
import com.ruanyun.web.util.duiba.CreditTool;

@Controller
@RequestMapping("app/duiba")
public class DuiBaController extends BaseController{
	@Autowired
	private AppShopGoodsService appShopGoodsService;
	
	static final String APP_KEY=Constants.DUIBA_APP_KEY;
	static final String APP_SECRET=Constants.DUIBA_APP_SECRET;
	static final int PROPORTION=Constants.SCORE_PROPORTION;
	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private AppUserExchangeService appUserExchangeService;
	
	@RequestMapping("login")
	public String login(String userNum){
		TUserScore userScore =userScoreService.getScore(userNum);
		CreditTool tool=new CreditTool(APP_KEY, APP_SECRET);
		Map params=new HashMap();
		params.put("uid",userNum);
		if(EmptyUtils.isEmpty(userScore.getScore()))
			userScore.setScore(0f);
		params.put("credits",String.valueOf(userScore.getScore().intValue()));
		    //redirect是目标页面地址，默认积分商城首页是：http://www.duiba.com.cn/chome/index
		    //此处请设置成一个外部传进来的参数，方便运营灵活配置
		String url=tool.buildUrlWithSign("http://www.duiba.com.cn/autoLogin/autologin?",params);
		return redirect(url);
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2016年3月29日 下午8:02:58
	 * 
	 * @param uid
	 * @return
	 */
	@RequestMapping("consumption")
	public void consumptionScore(HttpServletRequest request,String uid,HttpServletResponse response){
		
		CreditTool tool=new CreditTool(APP_KEY,APP_SECRET);
		
		try {
		    CreditConsumeParams params= tool.parseCreditConsume(request);//利用tool来解析这个请求
		    Long credits=params.getCredits();
		    String type=params.getType();//获取兑换类型
		    String alipay=params.getAlipay();//获取支付宝账号
		    //其他参数参见 params的属性字段

		    String bizId=appShopGoodsService.userScore(params);//todo();//返回开发者系统中的业务订单id
		    CreditConsumeResult result=new CreditConsumeResult(true);
		    TUserScore userScore =userScoreService.getScore(uid);
		    result.setBizId(bizId);
		    result.setCredits(userScore.getScore().longValue());
		    writeText(response, result.toString());
		} catch (Exception e) {
		    e.printStackTrace();
		    TUserScore userScore =userScoreService.getScore(uid);
		    CreditConsumeResult result=new CreditConsumeResult(false);
		    result.setCredits(userScore.getScore().longValue());
		    writeText(response, result.toString());
		}
		
	}
	
	@RequestMapping("consumptionResult")
	public void consumptionScoreResult(HttpServletRequest request,String uid,HttpServletResponse response){
		CreditTool tool=new CreditTool(APP_KEY, APP_SECRET);

		try {
		    CreditNotifyParams params= tool.parseCreditNotify(request);//利用tool来解析这个请求
		    String orderNum=params.getOrderNum();
		    if(params.isSuccess()){
		    	appUserExchangeService.updateStatus(orderNum, "suc");
		        //兑换成功
		    }else{
		    	appUserExchangeService.updateStatus(orderNum, "fail");
		        //兑换失败，根据orderNum，对用户的金币进行返还，回滚操作
		    }
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
}
