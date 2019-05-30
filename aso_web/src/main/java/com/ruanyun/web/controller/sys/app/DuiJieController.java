package com.ruanyun.web.controller.sys.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.ShutdownSignalException;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.producer.AdverQueueConsumer;
import com.ruanyun.web.producer.QueueProducer;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelInfoService;
import com.ruanyun.web.service.background.DictionaryService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserappidAdveridService;
import com.ruanyun.web.util.ArithUtil;

@Controller
@RequestMapping("app/duijie")
public class DuiJieController extends BaseController
{
	@Autowired
	private UserAppService userAppService;
	@Autowired
	private UserappidAdveridService userappidAdveridService;
	@Autowired
	private AppChannelAdverInfoService appChannelAdverInfoService;
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	
	/**
	 * 查询系统参数
	 */
	@RequestMapping("getSystemParameter")
	public void getSystemParameter(HttpServletResponse response) 
	{
		AppCommonModel model = new AppCommonModel();
		
		try
		{
			Map<String,Object> map = new HashMap<String,Object>(4);
			map.put("appleIdCheck", dictionaryService.getAppleIdCheck());
			map.put("leastTaskTime", dictionaryService.getLeastTaskTime());
			map.put("leastForward", dictionaryService.getLeastForward());
			map.put("notice", dictionaryService.getNotice());
			map.put("downloadUrl", dictionaryService.getDownloadUrl());
			map.put("appVersion", dictionaryService.getAppVersion());
			map.put("idfaCheck", dictionaryService.getIdfaCheck());
			model.setObj(map);
			model.setResult(1);
			model.setMsg("成功！");
		}
		catch (Exception e) 
		{
			model.setResult(-1);
			model.setMsg("出错！");
		}
		
		super.writeJsonDataApp(response, model);
	}
	
	
	@RequestMapping("bulang")
	public void bulang() throws UnsupportedEncodingException {
		//SELECT * FROM `t_userappid_adverid` WHERE adid = '0ebd032e98aed1f40cce13abad1af4a7'  AND STATUS = 2
		//UNION 
		//SELECT * FROM `t_userappid_adverid_2019_5_27` WHERE adid = '0ebd032e98aed1f40cce13abad1af4a7'  AND STATUS = 2
		Page<TUserappidAdverid> taskList = userappidAdveridService.getTasks();
		for(TUserappidAdverid tas :taskList.getResult() ) {
			System.out.println("-------------------------------------------------------");
			XiaoshouChannel.paiChong("https://www.xiaoshouzhuanqian.com/openapi/upstream/idfaQueryApi"
					, "0ebd032e98aed1f40cce13abad1af4a7", tas.getIdfa(),tas.getPhoneVersion(),tas.getPhoneModel(),"聊天交友",tas.getIp(), "");
		}
	}
	
	/**
	 * 领取任务
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 * @throws IOException 
	 * @throws ConsumerCancelledException 
	 * @throws ShutdownSignalException 
	 */
	@RequestMapping("lingQuRenWu")
	public void lingQuRenWu(HttpServletResponse response, HttpServletRequest request) 
			throws NumberFormatException, InterruptedException, ShutdownSignalException, ConsumerCancelledException, IOException, TimeoutException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		String udid = request.getParameter("udid");
		String adverId = request.getParameter("adverId");
		String ip = request.getRemoteAddr();//手机ip
		//String adid = "";//广告id（第三方提供）
		String idfa = "";//手机广告标识符
		String userAppId = "";//用户Id
		String appleId = "";//苹果账号
		String userNum = "";
		String phoneModel_real = "";
		String phoneVersion_real = "";
		String phoneModel = "";
		String phoneVersion = "";
		TUserApp userApp;
		if(udid != null && !udid.isEmpty()) 
		{
			userApp = userAppService.getUserAppByUserName(udid);
			if(!userApp.getLoginControl().equals("1")) {
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：账号已被禁止登录！");
				super.writeJsonDataApp(response, model);
				return;
			}
			
			if(userApp.getPhoneNum() == null || userApp.getZhifubao() == null || userApp.getOpenID() == null ) {
				model.setResult(0);
				model.setMsg("请先完善个人信息！");
				super.writeJsonDataApp(response, model);
				return;
			}
			
			idfa = userApp.getIdfa();
			userAppId = userApp.getUserAppId() + "";
			userNum = userApp.getUserNum();
			phoneModel_real = userApp.getPhoneModel() + "-";
			phoneVersion_real = userApp.getPhoneVersion() + "-";
			phoneModel = userApp.getPhoneModel();
			phoneVersion = userApp.getPhoneVersion();
		}
		else 
		{
			 //工作室
			 idfa = request.getParameter("idfa");//手机广告标识符
			 userAppId = request.getParameter("userAppId");//用户Id
			 appleId = request.getParameter("appleId");//苹果账号
			 userNum = request.getParameter("userNum");
			 phoneModel_real = request.getParameter("phoneModel") + "-";
			 phoneVersion_real = request.getParameter("phoneVersion") + "-";
			 phoneModel = ChannelClassification.getPhoneModel(userAppId);
			 phoneVersion = request.getParameter("phoneVersion");
			 //模拟用户的udid
			 udid = ChannelClassification.getPhoneUdid(phoneModel);
			if(phoneModel.compareTo("iPhone10,1") >= 0 && phoneModel.compareTo("iPhone8,1") < 0) 
			{
				phoneVersion = ChannelClassification.getPhoneVersion();
			}
			
			if(userAppId.equals("197") || userAppId.equals("798")) {
				phoneModel = request.getParameter("phoneModel");
				phoneVersion = request.getParameter("phoneVersion");
			}
			
			userApp = userAppService.getUserAppById(Integer.valueOf(userAppId));
		}
		
		if(!StringUtils.hasText(idfa) || !StringUtils.hasText(ip)
				|| !StringUtils.hasText(userAppId) || !StringUtils.hasText(adverId))
		{
			model.setResult(-1);
			model.setMsg("adid、idfa、ip、userAppId、adverId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		
		//判断广告是否存在
		if(adverInfo == null)
		{
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：广告不存在！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		if(adverInfo.getAdverStatus() != 1) 
		{
			model.setResult(-1);
			model.setMsg("任务已完结或已下线！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		if(adverInfo.getIsMock() != 0) 
		{
			model.setResult(-1);
			model.setMsg("抢任务人数过多，请稍后再试！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//判断当天领取到的任务
		//Page<TUserappidAdverid> taskList = userappidAdveridService.getTasks(adid, idfa, ip);
		String adid = adverInfo.getAdid();
		if(userApp.getUserApppType() == 1) {
			Page<TUserappidAdverid> taskList = userappidAdveridService.getTasksByIdfaOrIP(idfa, ip);
			
			if (taskList != null && !taskList.getResult().isEmpty())
			{
				//判断idfa是否重复 判断是否有任务在进行
				AppCommonModel checkIdfaModel = checkIDFADuplicated(userApp, adid, taskList, idfa, adverId);
				if(checkIdfaModel.getResult() != 2) 
				{
					super.writeJsonDataApp(response, checkIdfaModel);
					return;
				}
				
				AppCommonModel checkIpModel = checkIPDuplicated(adid, taskList, ip);
				if(checkIpModel.getResult() != 2) 
				{
					super.writeJsonDataApp(response, checkIpModel);
					return;
				}
			}
		}else {
			//散户验证是否存在任务
			Page<TUserappidAdverid> taskList = userappidAdveridService.getTasksByIdfa(idfa);
			if (taskList != null && !taskList.getResult().isEmpty()) {
				for (TUserappidAdverid item : taskList.getResult()) {
					
					TChannelAdverInfo adverInfoss = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", item.getAdverId());
					if(adverInfoss.getTaskType().equals("1")) 
					{
						continue;
					}
					
					if(item.getStatus().compareTo("1.5") < 0) {
						item.setReceiveTime(new Date());
						userappidAdveridService.updateReceiveTime(item);
						model.setResult(1);
						model.setObj(item);
						model.setMsg("请先完成正在进行的任务...");
						super.writeJsonDataApp(response, model);
						return;
					}else if(item.getStatus().compareTo("1.6") == 0 && item.getAdverId().equals(Integer.valueOf(adverId))){
						model.setResult(-1);
						model.setMsg("抱歉，此任务今日已超时，请明日在来！");
						super.writeJsonDataApp(response, model);
						return;
					}else if((item.getStatus().compareTo("1.5") == 0 && item.getAdverId().equals(Integer.valueOf(adverId)))) 
					{
						model.setResult(1);
						model.setObj(item);
						super.writeJsonDataApp(response, model);
						return;
					}
				}
			}
		}
		
		//检测apple是否可以使用
		AppCommonModel checkAppleIDModel = checkAppleIDDuplicated(userApp, appleId, adid);
		if(checkAppleIDModel.getResult() != 2)
		{
			super.writeJsonDataApp(response, checkAppleIDModel);
			return;
		}
		
		if(adverInfo.getAdverCountRemain() <= 0) 
		{
			model.setResult(-1);
			model.setMsg("任务被抢光啦!");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		String endPointName = adverInfo.getAdverName() + "_" + adverInfo.getAdverId();
		if(AdverQueueConsumer.consumerMap.get(endPointName) == null) 
		{
			model.setResult(-1);
			model.setMsg("任务需要管理员重新启动!");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		boolean success = AdverQueueConsumer.getMessage(endPointName);
		if(!success) 
		{
			TChannelAdverInfo lastAdverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
			if(lastAdverInfo.getAdverCountRemain() != 0) 
			{
				int countComplete = channelAdverInfoService.getCountComplete(adverId);
				lastAdverInfo.setDownloadCount(countComplete);//用这个来记录完成数量
				lastAdverInfo.setAdverActivationCount(lastAdverInfo.getAdverCountRemain());
				channelAdverInfoService.updateAdverActivationCount(lastAdverInfo);
			}
			
			model.setResult(-1);
			model.setMsg("真遗憾，你未抢到任务!");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//排重
		TChannelInfo channelInfo = channelInfoService.getInfoByNum(adverInfo.getChannelNum());
		AppCommonModel checkChannelInfoModel = ChannelClassification.checkChannelInfo(channelInfo, adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverInfo.getAdverName(), phoneModel, phoneVersion, udid);
		
		if(checkChannelInfoModel.getResult() == -1)
		{
			super.writeJsonDataApp(response, checkChannelInfoModel);
			return;
		}
		else
		{
			appChannelAdverInfoService.updateAdverCountRemainMinus1(adverInfo);
		}
		
		//保存任务
		TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
		tUserappidAdverid.setUserAppId(Integer.valueOf(userAppId));
		tUserappidAdverid.setIp(ip);
		tUserappidAdverid.setUserUdid(udid);
		tUserappidAdverid.setIdfa(idfa);
		tUserappidAdverid.setAppleId(appleId);
		tUserappidAdverid.setAdid(adid);
		tUserappidAdverid.setAdverId(Integer.valueOf(adverId));
		tUserappidAdverid.setStatus("1");
		tUserappidAdverid.setReceiveTime(new Date());
		tUserappidAdverid.setPhoneModel(phoneModel_real + phoneModel);
		tUserappidAdverid.setPhoneVersion(phoneVersion_real + phoneVersion);
		tUserappidAdverid = userappidAdveridService.save(tUserappidAdverid);
		
		if(tUserappidAdverid == null)
		{
			model.setResult(-1);
			model.setMsg("领取任务失败，请修改ip地址重新尝试！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		
		
		//model.setObj(tUserappidAdverid);
		model.setResult(1);
		model.setMsg("领取任务成功，请在规定时间内完成任务，否则不计分！");
		super.writeJsonDataApp(response, model);
		return;
	}
	
	private AppCommonModel checkAppleIDDuplicated(TUserApp tUserApp, String appleId, String adid) 
	{
		AppCommonModel model = new AppCommonModel(2, "通过！");
		//TUserApp tUserApp = userAppService.getUserAppById(Integer.valueOf(userAppId));
		//appleId排重开关开启且为普通用户时，判断appleId是否重复(此排重要放在idfa排重和ip排重之后)
		if(1 == tUserApp.getUserApppType() && "1".equals(dictionaryService.getAppleIdCheck()))
		{
			if(!StringUtils.hasText(appleId))
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：苹果账号不能为空！");
			}
			else if(userappidAdveridService.checkAppleIdIsUsed(adid, appleId))
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：该苹果账号完成这一类广告！");
			}
		}
		
		return model;
	}
	
	private AppCommonModel checkIDFADuplicated(TUserApp userApp, 
			String adid, Page<TUserappidAdverid> taskList, String idfa, String adverId) 
	{
		AppCommonModel model = new AppCommonModel(2, "没有符合条件！");
		
		for (TUserappidAdverid item : taskList.getResult())
		{
			//model.setObj(item);
			if(item.getIdfa().equals(idfa))
			{
				if(userApp.getUserApppType() == 2)
				{
					//真实用户
					if (item.getStatus().compareTo("2") >= 0 && adid.equals(item.getAdid())) 
					{
						model.setResult(-1);
						model.setMsg("领取任务失败。原因：任务已完成，不能重复领取！");
						break;
					}
					else if(item.getStatus().compareTo("1.6") < 0)
					{
						//更新 未完成 的 任务开始时间，真实用户需要更新
						item.setReceiveTime(new Date());
						userappidAdveridService.updateReceiveTime(item);
						model.setResult(1);
						model.setObj(item);
						model.setMsg("请先完成正在进行的任务...");
						break;
					}
					else if(item.getStatus().compareTo("1.6") == 0 && item.getAdverId().equals(Integer.valueOf(adverId)))
					{
						model.setResult(-1);
						model.setMsg("抱歉，此任务今日已超时，请明日在来！");
						break;
					}
				}
				else
				{
					//工作室准备
					if (item.getStatus().compareTo("2") >= 0 && adid.equals(item.getAdid())) 
					{
						model.setResult(-1);
						model.setMsg("领取任务失败。原因：任务已完成，不能重复领取！");
						break;
					} 
					else if (item.getStatus().compareTo("1.6") == 0 && adid.equals(item.getAdid())) 
					{
						model.setResult(-1);
						model.setMsg("领取任务失败。原因：任务没有在规定时间内完成，导致任务已失效！");
						break;
					}
					else if (!item.getAdverId().equals(Integer.valueOf(adverId)) && adid.equals(item.getAdid()))  
					{
						model.setResult(-1);
						model.setMsg("领取任务失败。原因：已领取过此类任务，不能重复领取！");
						break;
					}
					else if (item.getStatus().compareTo("1.6") < 0  && adid.equals(item.getAdid()))  
					{
						model.setResult(1);
						//model.setObj(item);
						model.setMsg("你已成功领取任务，不需重复领取！");
						break;
					}
					else if (item.getStatus().compareTo("1.6") < 0  && !adid.equals(item.getAdid()))  
					{
						//撤除检测是否已经存在任务
						model.setResult(2);
						//model.setResult(-1);
						model.setMsg("你已存在未完成的任务，请先去完成！");
					}
				}
				
			}
		}
		
		return model;
	}
	
	//判断ip是否重复
	private AppCommonModel checkIPDuplicated(String adid, Page<TUserappidAdverid> taskList, String ip)
	{
		AppCommonModel model = new AppCommonModel(2, "通过！");
		for (TUserappidAdverid item : taskList.getResult())
		{
			if (ip.equals(item.getIp()) && adid.equals(item.getAdid())) 
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：IP重复！");
				break;
			}
		}
		
		return model;
	}
	
	/**
	 * 打开app
	 */
	@RequestMapping("openApp")
	public void openApp(HttpServletResponse response,HttpServletRequest request) 
			throws Exception
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String idfa = request.getParameter("idfa");
		String adverId = request.getParameter("adverId");
		//String taskType = request.getParameter("taskType");
		
		if(!StringUtils.hasText(idfa) || !StringUtils.hasText(adverId))
		{
			model.setResult(-1);
			model.setMsg("idfa、adverId、taskType不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TUserappidAdverid task = getTask(adverId, idfa);
		
		if(task == null)
		{
			model.setResult(-1);
			model.setMsg("请先领取任务！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		if(task != null && task.getStatus().compareTo("1.6") == 0)
		{
			model.setResult(-1);
			model.setMsg("没有在规定时间内完成任务，任务已失效！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//状态改为打开app
		task.setStatus("1.5");
		task.setOpenAppTime(new Date());
		userappidAdveridService.updateStatus2OpenApp(task);
		
		model.setResult(1);
		model.setMsg("成功！");
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 回调（由第三方调用）
	 * {"success":true,"message":'ok'}
	 */
	@RequestMapping("callback")
	public void callback(HttpServletResponse response,HttpServletRequest request) 
			throws Exception
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String adid = request.getParameter("adid");
		String idfa = request.getParameter("idfa");
		String userAppId = request.getParameter("userAppId");
		String adverId = request.getParameter("adverId");
		String userNum = request.getParameter("userNum");

		if(!StringUtils.hasText(adid) || !StringUtils.hasText(idfa) || !StringUtils.hasText(userAppId)
				|| !StringUtils.hasText(adverId))
		{
			model.setResult(-1);
			model.setMsg("adid、idfa、userAppId、adverId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		//快速任务不需要回调
		if(adverInfo == null || "0".equals(adverInfo.getTaskType())) 
		{
			model.setResult(-1);
			model.setMsg("回调失败，未找到任务或者任务为快速任务");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TUserApp tUserApp = userAppService.getUserAppById(Integer.valueOf(userAppId));
		userNum = tUserApp.getUserNum();
		
		//更新金额
		TUserScore score = new TUserScore();
		score.setUserNick(idfa);//标记任务的idfa
		score.setUserNum(userNum);
		score.setUserScoreId(Integer.valueOf(adverId));//标记得分的 任务
		score.setType(-1);
		if(tUserApp.getUserApppType() == 1) //工作室
		{
			float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
			score.setScore(sco);
		}
		else
		{
			score.setScore(adverInfo.getAdverPrice());
		}
		
		QueueProducer.getQueueProducer().sendMessage(score, "socre");
		
		if(tUserApp.getUserApppType() == 2) {
			masterWork(tUserApp);
		}
		
		model.setResult(1);
		model.setMsg("success!");
		model.setSuccess(true);
		model.setMessage("ok");
		super.writeJsonDataApp(response, model);
	}
	
	//根据adverid和idfa获取领取到的任务
	private TUserappidAdverid getTask(String adverId, String idfa) 
	{
		TUserappidAdverid task = null;
		
		String[] propertyNames = new String[2];
		propertyNames[0] = "adverId";
		propertyNames[1] = "idfa";
		Object[] values = new Object[2];
		values[0] = Integer.valueOf(adverId);
		values[1] = idfa;
		task = userappidAdveridService.get(TUserappidAdverid.class, propertyNames, values);
		
		return task;
	}
	
	/**
	 * 查看结果
	 */
	@RequestMapping("queryOneMission")
	public void queryOneMission(HttpServletResponse response, HttpServletRequest request)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//String adid = request.getParameter("adid");//广告id（第三方提供）
		String idfa = request.getParameter("idfa");//手机广告标识符l
		//String ip = request.getRemoteAddr();//手机ip
		String adverId = request.getParameter("adverId");//广告id（我们系统提供）
		String udid = request.getParameter("udid");
		if(udid != null && !udid.isEmpty()) {
			TUserApp userApp = userAppService.getUserAppByUserName(udid);
			idfa = userApp.getIdfa();
			if(!userApp.getLoginControl().equals("1")) {
				model.setResult(-1);
				model.setMsg("查询失败。原因：账号已被禁止登录！");
				super.writeJsonDataApp(response, model);
				return;
			}
		}
		
		if(!StringUtils.hasText(idfa)
				|| !StringUtils.hasText(adverId))
		{
			model.setResult(-1);
			model.setMsg("adid、idfa、ip、adverId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//判断是否领取任务
		TUserappidAdverid task = getTask(adverId, idfa);
		if(task == null)
		{
			model.setResult(-1);
			model.setMsg("请先领取任务！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		String ip = task.getIp();
		TUserApp tUserApp  = userAppService.getUserAppById(Integer.valueOf(task.getUserAppId()));
		String userNum = tUserApp.getUserNum();
		
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		
		//判断任务状态
		if(task.getStatus().compareTo("2") >= 0)
		{
			model.setResult(1);
			model.setMsg("已完成！");
			super.writeJsonDataApp(response, model);
			return;
		}
		else if(task.getStatus().compareTo("1.6") == 0)
		{
			model.setResult(-1);
			model.setMsg("未完成。原因：任务没有在规定时间内完成，导致任务已失效！");
			super.writeJsonDataApp(response, model);
			return;
		}
		else if(task.getStatus().compareTo("1.5") < 0)
		{
			model.setResult(-1);
			model.setMsg("未完成。原因：需要先下载并打开app！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//查询广告信息
		if(adverInfo == null)
		{
			model.setResult(-1);
			model.setMsg("广告不存在！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//查询任务完成情况
		if("0".equals(adverInfo.getTaskType()))
		{	//快速任务
			//分渠道调用激活上报接口
			Integer leastTaskTime = adverInfo.getOpenTime();
			if(new Date().getTime() - task.getOpenAppTime().getTime() <= leastTaskTime * 1000)
			{
				model.setResult(-1);
				model.setMsg("未完成。原因：打开app必须达到" + leastTaskTime + "秒！");
				super.writeJsonDataApp(response, model);
				return;
			}
			
			TChannelInfo channelInfo = channelInfoService.getInfoByNum(adverInfo.getChannelNum());
			if(channelInfo == null)
			{
				model.setResult(-1);
				model.setMsg("未完成。原因：渠道不存在！");
				super.writeJsonDataApp(response, model);
				return;
			}
			
			int num = Integer.parseInt(channelInfo.getChannelNum());
			String phoneModel = task.getPhoneModel();
			String phoneOs = task.getPhoneVersion();
			String [] arr1=phoneModel.split("-");
			String [] arr2=phoneOs.split("-");
			model = ChannelClassification.channelActive(model,adverInfo,num, idfa, ip, arr1, arr2, task.getUserUdid());
			if(model.getResult() == -1)
			{
				super.writeJsonDataApp(response, model);
				return;
			}	
			
			//把单子发送到队列
			TUserScore score = new TUserScore();
			score.setUserNick(idfa);//标记任务的idfa
			score.setUserNum(userNum);
			score.setUserScoreId(Integer.valueOf(adverId));//标记得分的 任务
			score.setType(0);
			if(tUserApp.getUserApppType() == 1) //工作室
			{
				float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
				//float sco = (float) (adverInfo.getAdverPrice() - adverInfo.getPriceDiff());
				score.setScore(sco);
			}
			else
			{
				score.setScore(adverInfo.getAdverPrice());
			}
			
			try {
				QueueProducer.getQueueProducer().sendMessage(score, "socre");
			} catch (Exception e) {
				e.printStackTrace();
				model.setResult(-1);
				model.setMsg("发送给到结算队列失败，请稍后尝试！");
				super.writeJsonDataApp(response, model);
			}
			
			masterWork(tUserApp);
		}
		else if("1".equals(adverInfo.getTaskType()))
		{
			//回调任务
			Integer leastTaskTime = adverInfo.getOpenTime();
			if(new Date().getTime() - task.getOpenAppTime().getTime() <= leastTaskTime * 1000)
			{
				model.setResult(-1);
				model.setMsg("未完成。原因：打开app必须达到" + leastTaskTime + "秒！");
				super.writeJsonDataApp(response, model);
				return;
			}
			else
			{
				model.setResult(1);
				model.setMsg("任务已达到条件，等待回调，是否退回继续做任务！");
				super.writeJsonDataApp(response, model);
				return;
			}
		}
		else if("2".equals(adverInfo.getTaskType())) //自由任务
		{
			//判断打开app的时间是否足够
			//Integer leastTaskTime = dictionaryService.getLeastTaskTime();
			Integer leastTaskTime = adverInfo.getOpenTime();
			if(new Date().getTime() - task.getOpenAppTime().getTime() <= leastTaskTime * 1000)
			{
				model.setResult(-1);
				model.setMsg("未完成。原因：打开app必须达到" + leastTaskTime + "秒！");
				super.writeJsonDataApp(response, model);
				return;
			}
			//更改金额
			TUserScore score = new TUserScore();
			score.setUserNick(idfa);//标记任务的idfa
			score.setUserNum(userNum);
			score.setUserScoreId(Integer.valueOf(adverId));//标记得分的 任务
			score.setType(0);
			
			if(tUserApp.getUserApppType() == 1) //工作室
			{
				//float sco = (float) (adverInfo.getAdverPrice() - adverInfo.getPriceDiff());
				float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
				score.setScore(sco);
			}
			else
			{
				score.setScore(adverInfo.getAdverPrice());
			}
			
			try {
				QueueProducer.getQueueProducer().sendMessage(score, "socre");
				
			} catch (Exception e) {
				e.printStackTrace();
				model.setResult(-1);
				model.setMsg("发送给到结算队列失败，请稍后尝试！");
				super.writeJsonDataApp(response, model);
			}
			//计算师傅获取到的利润
			masterWork(tUserApp);
		}
		else
		{//异常情况
			model.setResult(-1);
			model.setMsg("未完成。原因：任务类型错误！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		model.setResult(1);
		model.setMsg("成功，进入结算！");
		super.writeJsonDataApp(response, model);
	}

	//计算师傅获取到的利润 这个应该放在计算之后有时间更改
	private void masterWork(TUserApp tUserApp)
	{
		if(tUserApp.getMasterID() != null && tUserApp.getLimitTime() != null && tUserApp.getLimitTime() > 0)
		{
			TUserApp masterUserApp  = userAppService.getUserAppById(Integer.valueOf(tUserApp.getMasterID()));
			if(masterUserApp != null)
			{
				TUserScore score = new TUserScore();
				score.setType(1);
				score.setUserNum(masterUserApp.getUserNum());//师傅num
				score.setRankingNum(tUserApp.getUserNum());//用来表示徒弟num
				score.setScore((float) 0.5);
				try {
					QueueProducer.getQueueProducer().sendMessage(score, "socre");
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 我的已完成任务--明细
	 */
	@RequestMapping("queryTaskSum")
	public void queryTaskSum(HttpServletResponse response, HttpServletRequest request, Page<TUserappidAdverid> page)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		page.setNumPerPage(Integer.MAX_VALUE);
		String idfa = request.getParameter("idfa");
		
		if(!StringUtils.hasText(idfa))
		{
			model.setResult(-1);
			model.setMsg("idfa不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//查询任务完成情况
		String[] propertyNames = new String[1];
		propertyNames[0] = "idfa";
		Object[] values = new Object[1];
		values[0] = idfa;
		List<TUserappidAdverid> adverCompleteList = userappidAdveridService.getAllByCondition(TUserappidAdverid.class, propertyNames, values);
		for(TUserappidAdverid adverid : adverCompleteList) 
		{
			TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverid.getAdverId()));
			adverid.setAdverName(adverInfo.getAdverName());
			adverid.setAdverPrice(ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff()));
		}
		
		page.setResult(adverCompleteList);
		model.setObj(page);
		model.setResult(1);
		model.setMsg("查询成功！");
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 任务是否完成
	 */
	@RequestMapping("queryTaskDetail")
	public void queryTaskDetail(HttpServletResponse response, HttpServletRequest request, Page<TUserappidAdverid> page)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		page.setNumPerPage(Integer.MAX_VALUE);
		
		String userAppId = request.getParameter("userAppId");
		String adverId = request.getParameter("adverId");
		
		if(!StringUtils.hasText(userAppId) || !StringUtils.hasText(adverId)){
			model.setResult(-1);
			model.setMsg("userAppId、adverId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//查询个人信息
		TUserApp tUserApp = userAppService.get(TUserApp.class, "userAppId", Integer.valueOf(userAppId));
		if(tUserApp == null)
		{
			model.setResult(-1);
			model.setMsg("用户不存在！");
			super.writeJsonDataApp(response, model);
			return;
		}
		//查询广告信息
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		if(adverInfo == null)
		{
			model.setResult(-1);
			model.setMsg("广告不存在！");
			super.writeJsonDataApp(response, model);
			return;
		}
		//查询任务完成情况
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		TUserappidAdverid info = new TUserappidAdverid();
		info.setUserAppId(Integer.valueOf(userAppId));
		info.setAdverId(Integer.valueOf(adverId));
		info.setStatus("2");
		Page<TUserappidAdverid> page2 = userappidAdveridService.queryMission(page, info);
		if(page2 != null && page2.getResult() != null)
		{
			for(TUserappidAdverid item:page2.getResult())
			{
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("loginName", tUserApp.getLoginName());
				map.put("adverName", adverInfo.getAdverName());
				map.put("completeTime", item.getCompleteTime());
				list.add(map);
			}
		}
		
		model.setObj(list);
		model.setResult(1);
		model.setMsg("查询成功！");
		super.writeJsonDataApp(response, model);
	}
    
	//设置任务时间超时
	@RequestMapping("setTaskTimeout")
	public void setTaskTimeout(HttpServletResponse response,HttpServletRequest request) 
			throws Exception
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String idfa = request.getParameter("idfa");
		String adverId = request.getParameter("adverId");
		String udid = request.getParameter("udid");
		if(udid != null && !udid.isEmpty()) {
			TUserApp userApp = userAppService.getUserAppByUserName(udid);
			idfa = userApp.getIdfa();
		}
		
		if(!StringUtils.hasText(idfa) || !StringUtils.hasText(adverId))
		{
			model.setResult(-1);
			model.setMsg("idfa、adverId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TUserappidAdverid task = getTask(adverId, idfa);
		if(task != null && task.getStatus().compareTo("2") == 0)
		{
			model.setResult(-1);
			model.setMsg("任务已经完成，不需要放弃！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//查询个人信息
		TUserApp tUserApp = userAppService.get(TUserApp.class, "userAppId", Integer.valueOf(task.getUserAppId()));
		//外放人员
		if(tUserApp != null && tUserApp.getUserApppType() == 2) 
		{
			TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
			tUserappidAdverid.setIdfa(idfa);
			tUserappidAdverid.setAdverId(Integer.valueOf(adverId));
			//1.7代表放弃的状态
			tUserappidAdverid.setStatus("1.7");
			userappidAdveridService.updateTaskStatus(tUserappidAdverid);
		}
		
		model.setResult(1);
		model.setMsg("成功！");
		super.writeJsonDataApp(response, model);
	}
}
