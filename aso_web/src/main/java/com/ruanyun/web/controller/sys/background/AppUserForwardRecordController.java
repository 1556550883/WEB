package com.ruanyun.web.controller.sys.background;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.alipay.DefaultAlipayClientFactory;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserScoreInfo;
import com.ruanyun.web.producer.QueueProducer;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserScoreInfoService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
@RequestMapping("/userAppForwardRecord")
public class AppUserForwardRecordController extends BaseController
{
	@Autowired
	private UserScoreInfoService userScoreInfoService;
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private UserScoreService userScoreService;
	
	@RequestMapping("list")
	public String getUserForwardList(Page<TUserScoreInfo> page,TUserScoreInfo info,Model model)
	{
		page.setNumPerPage(20);
		//page.setOrder("scoreTime esc");
		Page<TUserScoreInfo> userScoreInfos = userScoreInfoService.queryPage(page, info);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonObject = JSONArray.fromObject(userScoreInfos.getResult(), jsonConfig); 
		addModel(model, "userScoreInfos", jsonObject);
		addModel(model, "pageList", userScoreInfos);
		
		return "pc/userForwardRecord/list";
	}
	
	@RequestMapping("verify")
	public void forwardVerify(HttpServletRequest request, HttpServletResponse response)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		String userScoreInfoId = request.getParameter("userScoreInfoId");
		String status = request.getParameter("status");

		TUserScoreInfo userScoreinfo = userScoreInfoService.get(TUserScoreInfo.class, Integer.parseInt(userScoreInfoId));
		
		TUserApp userApp = userAppService.getUserAppByNum(userScoreinfo.getUserAppNum());
		//如果 已经审核通过就不需要第二次打款
		if(userApp == null ||( userScoreinfo.getStatus() != null && userScoreinfo.getStatus() == 1)) {
			super.writeJsonDataApp(response, model);
			return;
		}
		
		if( userScoreinfo.getStatus() == -1)
		{
			super.writeJsonDataApp(response, model);
			return;
		}
		//移除体现状态
		userScoreService.updatePutforwardStatus(userApp.getUserNum(), 0);
		if(status.equals("-1") && userScoreinfo.getStatus() == 0) {
			//驳回操作
			TUserScore score = new TUserScore();
			score.setType(5);
			score.setUserNum(userApp.getUserNum());//师傅num
			score.setRankingNum("");//用来表示第十个徒弟num。如果不为空
			score.setScore(userScoreinfo.getScore());
			try {
				QueueProducer.getQueueProducer().sendMessage(score, "socre");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			model.setResult(-2);
			userScoreinfo.setSubMsg("审核未通过！");
			userScoreinfo.setStatus(-1);
			userScoreInfoService.update(userScoreinfo);
			super.writeJsonDataApp(response, model);
			return;
		}
		
		if(status.equals("1") && userScoreinfo.getStatus() == 0) {
			AlipayClient alipayClient = DefaultAlipayClientFactory.getAlipayClient();
			AlipayFundTransToaccountTransferRequest alipayRequest = new AlipayFundTransToaccountTransferRequest();
			AlipayFundTransToaccountTransferModel vo = new AlipayFundTransToaccountTransferModel();
			long timestamp = System.currentTimeMillis();
			 vo.setOutBizNo(timestamp+"");
			 vo.setPayeeType("ALIPAY_LOGONID");
			 Float count = userScoreinfo.getScore();
			 if(count < 50) {
				 count = count * 0.95f;
			 }
			 vo.setAmount(count + "");
			 vo.setPayeeAccount(userApp.getZhifubao());
			 vo.setPayeeRealName(userApp.getUserNick());
			 vo.setRemark("Happy赚提现");
			 alipayRequest.setBizModel(vo);
			AlipayFundTransToaccountTransferResponse alipayResponse;
			try {
				alipayResponse = alipayClient.execute(alipayRequest);
				System.out.println(alipayResponse.getBody());	
				if(alipayResponse.isSuccess())
				{
					model.setResult(1);
					model.setMsg("success");
					userScoreinfo.setStatus(Integer.parseInt(status));
					userScoreinfo.setOutBizNo(timestamp+"");
					userScoreinfo.setSubCode("10000");
					userScoreinfo.setSubMsg("转账成功！");
					userScoreinfo.setOrderId(alipayResponse.getOrderId());
				} 
				else
				{
					model.setResult(-1);
					model.setMsg("failed");
					userScoreinfo.setStatus(Integer.parseInt(status));
					userScoreinfo.setOutBizNo(timestamp+"");
					userScoreinfo.setSubCode(alipayResponse.getSubCode());
					userScoreinfo.setSubMsg(alipayResponse.getSubMsg());
					userScoreinfo.setOrderId(alipayResponse.getOrderId());
				}
				
				userScoreInfoService.update(userScoreinfo);
				model.setObj(userScoreinfo);
				
			} catch (AlipayApiException e) {
				e.printStackTrace();
				model.setResult(-1);
				model.setMsg("出现异常");
			}
		}
		
		super.writeJsonDataApp(response, model);
	}
}
