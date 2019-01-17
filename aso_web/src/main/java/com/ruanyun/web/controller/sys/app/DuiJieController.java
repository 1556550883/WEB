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
	
	private static java.util.Random random = new java.util.Random();
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
	
	
	public String getPhoneModel() 
	{	
		String phonemodel_sim = "iPhone7,1";
		int result = random.nextInt(18);
		switch (result)
		{
		case 0:
			phonemodel_sim = "iPhone10,6";
			break;
		case 1:
			phonemodel_sim = "iPhone8,1";
			break;
		case 2:
			phonemodel_sim = "iPhone8,2";
			break;
		case 3:
			phonemodel_sim = "iPhone8,4";
			break;
		case 4:
			phonemodel_sim = "iPhone9,1";
			break;
		case 5:
			phonemodel_sim = "iPhone9,2";
			break;
		case 6:
			phonemodel_sim = "iPhone9,3";
			break;
		case 7:
			phonemodel_sim = "iPhone9,4";
			break;
		case 8:
			phonemodel_sim = "iPhone9,5";
			break;
		case 9:
			phonemodel_sim = "iPhone9,6";
			break;
		case 10:
			phonemodel_sim = "iPhone10,1";
			break;
		case 11:
			phonemodel_sim = "iPhone10,4";
			break;	
		case 12:
			phonemodel_sim = "iPhone10,2";
			break;
		case 13:
			phonemodel_sim = "iPhone10,5";
			break;
		case 14:
			phonemodel_sim = "iPhone11,2";
			break;
		case 15:
			phonemodel_sim = "iPhone11,4";
			break;
		case 16:
			phonemodel_sim = "iPhone11,6";
			break;
		case 17:
			phonemodel_sim = "iPhone11,8";
			break;
		default:
			phonemodel_sim = "iPhone8,1";
			break;
		}
		
		return phonemodel_sim;
	}
	
	public String  getPhoneVersion()
	{
		String phoneVersion = "12.1.2";
		int result = random.nextInt(4);
		switch (result)
		{
			case 0:
				phoneVersion = "12.0";
				break;
			case 1:
				phoneVersion = "12.1";
				break;
			case 2:
				phoneVersion = "12.1.1";
				break;	
			case 3:
				phoneVersion = "12.1.2";
				break;	
			default:
				phoneVersion = "12.1.2";
				break;
		}
				
		return phoneVersion;
	}
	
//	public String getPhoneName() throws UnsupportedEncodingException 
//	{	
//		String phonemodel_sim = "iPhone6Plus";
//		int result = random.nextInt(11);
//		switch (result)
//		{
//		case 0:
//			phonemodel_sim = "iPhone6";
//			break;
//		case 1:
//			phonemodel_sim = "iPhone6s";
//			break;
//		case 2:
//			phonemodel_sim = "iPhone6sPlus";
//			break;
//		case 3:
//			phonemodel_sim = "iPhoneSE";
//			break;
//		case 4:
//			phonemodel_sim = "iPhone7";
//			break;
//		case 5:
//			phonemodel_sim = "iPhone7";
//			break;
//		case 6:
//			phonemodel_sim = "iPhone7";
//			break;
//		case 7:
//			phonemodel_sim = "iPhone7Plus";
//			break;
//		case 8:
//			phonemodel_sim = "iPhone8";
//			break;
//		case 9:
//			phonemodel_sim = "iPhone8Plus";
//			break;	
//		case 10:
//			phonemodel_sim = "iPhone6Plus";
//			break;
//		default:
//			phonemodel_sim = "iPhone6Plus";
//			break;
//		}
//		
////		phonemodel_sim =  URLEncoder.encode(phonemodel_sim,"UTF-8");
////		phonemodel_sim = phonemodel_sim.replaceAll("\\+","%20");
//		return phonemodel_sim;
//	}
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
		
		String adid = request.getParameter("adid");//广告id（第三方提供）
		String idfa = request.getParameter("idfa");//手机广告标识符
		String ip = request.getRemoteAddr();//手机ip
		String userAppId = request.getParameter("userAppId");//用户Id
		String adverId = request.getParameter("adverId");//广告id（我们系统提供）
		String appleId = request.getParameter("appleId");//苹果账号
		String userNum = request.getParameter("userNum");
		String phoneModel_real = request.getParameter("phoneModel") + "-";
		String phoneVersion_real = request.getParameter("phoneVersion") + "-";
		String phoneModel = getPhoneModel();
		String phoneVersion = request.getParameter("phoneVersion");
		if(phoneModel.compareTo("iPhone10,1") >= 0) 
		{
			phoneVersion = getPhoneVersion();
		}
		
		if(!StringUtils.hasText(adid) || !StringUtils.hasText(idfa) || !StringUtils.hasText(ip)
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
		
		Page<TUserappidAdverid> taskList = userappidAdveridService.getTasksByIdfaOrIP(idfa, ip);
		
		if (taskList != null && !taskList.getResult().isEmpty())
		{
			//判断idfa是否重复
			AppCommonModel checkIdfaModel = checkIDFADuplicated(adid, taskList, idfa, adverId);
			if(checkIdfaModel.getResult() != 2) 
			{
				super.writeJsonDataApp(response, checkIdfaModel);
				return;
			}
			
			//判断数据库里IP是否重复
			AppCommonModel checkIpModel = checkIPDuplicated(adid, taskList, ip);
			if(checkIpModel.getResult() != 2) 
			{
				super.writeJsonDataApp(response, checkIpModel);
				return;
			}
		}
		
		//检测apple是否可以使用
		AppCommonModel checkAppleIDModel = checkAppleIDDuplicated(userAppId, appleId, adid);
		if(checkAppleIDModel.getResult() != 2)
		{
			super.writeJsonDataApp(response, checkAppleIDModel);
			return;
		}
		
		//判断是否有任务在进行中
		AppCommonModel checkAdverInProcessModel = checkAdverInProcess(taskList);
		if(checkAdverInProcessModel.getResult() != 2)
		{
			super.writeJsonDataApp(response, checkAdverInProcessModel);
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
		
		AppCommonModel checkChannelInfoModel = checkChannelInfo(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverInfo.getAdverName(), phoneModel, phoneVersion);
		
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
	
	private AppCommonModel checkAdverInProcess(Page<TUserappidAdverid> taskList) 
	{
		AppCommonModel model = new AppCommonModel(2, "通过！");
		
		for (TUserappidAdverid item : taskList.getResult()) 
		{
			if(item.getStatus().compareTo("1.6") < 0) 
			{
				//model.setResult(-1);
				//model.setMsg("此类广告已经领取过，请勿重复领取！");
			}
		}
		
		return model;
	}
	
	private AppCommonModel checkAppleIDDuplicated(String userAppId, String appleId, String adid) 
	{
		AppCommonModel model = new AppCommonModel(2, "通过！");
		TUserApp tUserApp = userAppService.getUserAppById(Integer.valueOf(userAppId));
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
	
	private AppCommonModel checkIDFADuplicated(String adid, Page<TUserappidAdverid> taskList, String idfa, String adverId) 
	{
		AppCommonModel model = new AppCommonModel(2, "没有符合条件！");
		
		for (TUserappidAdverid item : taskList.getResult())
		{
			//model.setObj(item);
			if(item.getIdfa().equals(idfa))
			{
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
		
		return model;
	}
	
	//判断ip是否重复
	private AppCommonModel checkIPDuplicated(String adid, Page<TUserappidAdverid> taskList, String ip)
	{
		AppCommonModel model = new AppCommonModel(2, "通过！");
		//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		for (TUserappidAdverid item : taskList.getResult())
		{
//			if (simpleDateFormat.format(item.getReceiveTime()).equals(simpleDateFormat.format(new Date())) 
//					&& !idfa.equals(item.getIdfa()) 
//					&& adid.equals(item.getAdid())) 
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
		String taskType = request.getParameter("taskType");
		
		if(!StringUtils.hasText(idfa) || !StringUtils.hasText(adverId)
				|| !StringUtils.hasText(taskType))
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
		
		//状态改为已完成
		TUserappidAdverid tUserappidAdverid = getTask(adverId, idfa);
		tUserappidAdverid.setStatus("2");
		tUserappidAdverid.setCompleteTime(new Date());
		if(tUserappidAdverid != null && tUserappidAdverid.getStatus().equals("1.6"))
		{
			model.setResult(-1);
			model.setMsg("未完成。原因：任务已经超时！");
			userappidAdveridService.updateStatus2Complete(tUserappidAdverid);
			super.writeJsonDataApp(response, model);
			return;
		}
		
		int rowCount = userappidAdveridService.updateStatus2Complete(tUserappidAdverid);
		if(rowCount == 0)
		{
			model.setResult(-1);
			model.setMsg("未完成。原因：回调更改任务状态失败！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		TUserApp tUserApp = userAppService.getUserAppById(Integer.valueOf(userAppId));
		userNum = tUserApp.getUserNum();
		
		//更新金额
		TUserScore score = new TUserScore();
		score.setUserNum(userNum);
		score.setUserNick(tUserappidAdverid.getIdfa());
		score.setType(0);
		if(tUserApp.getUserApppType() == 1) //工作室
		{
			//float sco = (float) (adverInfo.getAdverPrice() - adverInfo.getPriceDiff());
			float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
			System.out.println(tUserappidAdverid.getIdfa() +  "this is a tip 2");
			System.out.println(sco);
			score.setScore(sco);
		}
		else
		{
			score.setScore(adverInfo.getAdverPrice());
		}
		QueueProducer.getQueueProducer().sendMessage(score, "socre");
		masterWork(tUserApp);
		
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
			throws Exception
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String adid = request.getParameter("adid");//广告id（第三方提供）
		String idfa = request.getParameter("idfa");//手机广告标识符
		//String ip = request.getRemoteAddr();//手机ip
		String adverId = request.getParameter("adverId");//广告id（我们系统提供）
		String userNum = request.getParameter("userNum");
		
		if(!StringUtils.hasText(adid) || !StringUtils.hasText(idfa)
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
		userNum = tUserApp.getUserNum();
		
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
			model.setMsg("未完成。原因：自由任务需要先下载并打开app！");
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
			switch (num)
			{
				case 1:
					//云聚
					//调用第三方激活上报接口
					model = YunJu.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip);
					break;
				case 2:
					//掌上互动
					//调用第三方激活上报接口
					model = ZhangShangHuDong.activate(adverInfo.getFlag4(), adid, idfa, ip);
					break;
				case 4:
					//云聚
					//调用第三方激活上报接口
					model = LiDeJiJin.activate(adverInfo.getFlag4(), adid, idfa, ip);
					break;
				case 6:
					model = MZDQChannel.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip);
					break;
				case 7:
					model = APYSChannel.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip);
					break;
				case 8:
					model = JvZhangChannel.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip);
					break;
				case 9:
					model = AiyinliChannel.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip);
					break;
				case 10:
					model = HappyChannel.activate(adverInfo.getFlag4(), adid, idfa);
					break;
				case 11:
					model = Huizhuan.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip, arr1[1], arr2[1]);
					break;
				case 12:
					model = BeeChannel.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip);
					break;
				case 13:
					model = FrogsChannel.activate();
					break;
				case 14:
					model = FrogsTChannel.activate(adverInfo.getFlag4(), adid, idfa, ip);
					break;
				case 15:
					model = FrogsTTChannel.activate(adverInfo.getFlag4(), adid, idfa, ip, adverInfo.getAdverName(),arr1[1], arr2[1]);
					break;
				default:
					model.setResult(-1);
					model.setMsg("未完成。原因：渠道未在后台配置！");
					break;
			}
			
			if(model.getResult() == -1)
			{
				super.writeJsonDataApp(response, model);
				return;
			}	
			
			//状态改为已完成
			TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
			tUserappidAdverid.setIdfa(idfa);
			tUserappidAdverid.setAdverId(Integer.valueOf(adverId));
			tUserappidAdverid.setStatus("2");
			tUserappidAdverid.setCompleteTime(new Date());
			int rowCount = userappidAdveridService.updateStatus2Complete(tUserappidAdverid);
			if(rowCount == 0)
			{
				model.setResult(-1);
				model.setMsg("状态错误，请稍后请求！");
				super.writeJsonDataApp(response, model);
				return;
			}
			//把单子发送到队列
			TUserScore score = new TUserScore();
			score.setUserNick(tUserappidAdverid.getIdfa());
			score.setUserNum(userNum);
			score.setType(0);
			if(tUserApp.getUserApppType() == 1) //工作室
			{
				float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
				//float sco = (float) (adverInfo.getAdverPrice() - adverInfo.getPriceDiff());
				System.out.println(tUserappidAdverid.getIdfa() +  "this is a tip 1");
				System.out.println(rowCount  + "this is rowcount");
				score.setScore(sco);
			}
			else
			{
				score.setScore(adverInfo.getAdverPrice());
			}
			
			QueueProducer.getQueueProducer().sendMessage(score, "socre");
			masterWork(tUserApp);
		}
		else if("1".equals(adverInfo.getTaskType()))
		{
			//回调任务
			Integer leastTaskTime = dictionaryService.getLeastTaskTime();
			if(new Date().getTime() - task.getOpenAppTime().getTime() <= leastTaskTime * 1000)
			{
				model.setResult(-1);
				model.setMsg("未完成。原因：自由任务打开app必须达到" + leastTaskTime + "秒！");
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
			Integer leastTaskTime = dictionaryService.getLeastTaskTime();
			if(new Date().getTime() - task.getOpenAppTime().getTime() <= leastTaskTime*1000)
			{
				model.setResult(-1);
				model.setMsg("未完成。原因：自由任务打开app必须达到" + leastTaskTime + "秒！");
				super.writeJsonDataApp(response, model);
				return;
			}
			//状态改为已完成
			TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
			tUserappidAdverid.setIdfa(idfa);
			tUserappidAdverid.setAdverId(Integer.valueOf(adverId));
			tUserappidAdverid.setStatus("2");
			tUserappidAdverid.setCompleteTime(new Date());
			int rowCount = userappidAdveridService.updateStatus2Complete(tUserappidAdverid);
			if(rowCount == 0)
			{
				model.setResult(-1);
				model.setMsg("状态错误，请稍后请求！");
				super.writeJsonDataApp(response, model);
				return;
			}
			//更改金额
			TUserScore score = new TUserScore();
			score.setUserNick(tUserappidAdverid.getIdfa());
			score.setUserNum(userNum);
			score.setType(0);
			if(tUserApp.getUserApppType() == 1) //工作室
			{
				//float sco = (float) (adverInfo.getAdverPrice() - adverInfo.getPriceDiff());
				float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
				System.out.println(tUserappidAdverid.getIdfa() +  "this is a tip 3");
				System.out.println(rowCount  + "this is rowcount");
				System.out.println(sco);
				score.setScore(sco);
			}
			else
			{
				score.setScore(adverInfo.getAdverPrice());
			}
			QueueProducer.getQueueProducer().sendMessage(score, "socre");
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
		model.setMsg("已完成！");
		super.writeJsonDataApp(response, model);
	}

	//计算师傅获取到的利润
	private void masterWork(TUserApp tUserApp) throws IOException, TimeoutException
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
				QueueProducer.getQueueProducer().sendMessage(score, "socre");
			}
			
			//updateLimitTime
			Integer ltime = tUserApp.getLimitTime() - 1;
			userAppService.updateLimitTime(masterUserApp, ltime);
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
			//float sco = (float)ArithUtil.sub(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
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
    
	//检测任务信息
	private AppCommonModel checkChannelInfo(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, 
			String userAppId, String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(1, "任务领取成功！");
		
		//分渠道调用排重接口、点击接口
		TChannelInfo channelInfo = channelInfoService.getInfoByNum(adverInfo.getChannelNum());
		if(channelInfo == null)
		{
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：渠道不存在！");
		}
		
		int num = Integer.parseInt(channelInfo.getChannelNum());
		switch (num) {
		case 1:
			//云聚
			model = isYunJvChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			break;
		case 2:
			//云聚
			model = zhangshanghudong(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			break;
		case 3:
			//自由渠道
			model.setResult(1);
			break;
		case 4:
			//利得基金
			model = isLDJJChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			break;
		case 5:
			model = isDYDChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			break;
		case 6:
			model = isMZDQChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName);
			break;
		case 7:
			model = isAPYSChannel(adverInfo, adid, idfa);
			break;
		case 8:
			model = isJZChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName);
			break;
		case 9:
			model = isAiYLChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName);
			break;
		case 10:
			model =  isHappyChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName);
			break;
		case 11:
			model =  isHZChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 12:
			model =  isBeeChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 13:
			model =  isFrogsChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 14:
			model =  isFrogsTChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		case 15:
			model =  isFrogsTTChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum, adverName, phoneModel, phoneVersion);
			break;
		default:
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：渠道未在后台配置！");
			break;
		}
		return model;
	}
	
	private AppCommonModel zhangshanghudong(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = ZhangShangHuDong.paiChong(adverInfo.getFlag2(), adid, idfa, ip);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = ZhangShangHuDong.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum);
		}
		
		return model;
	}
	
	//排重+点击+云聚
	private AppCommonModel isYunJvChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = YunJu.paiChong(adverInfo.getFlag2(), adid, idfa);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = YunJu.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum);
		}
		
		return model;
	}
	
	//利德基金渠道+排重+点击
	private AppCommonModel isLDJJChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum) 
			throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = LiDeJiJin.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum);
		
		return model;
	}
	
	private AppCommonModel isDYDChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum) 
			throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = DYDChannel.clickDYD(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum);
		
		return model;
	}
	
	private AppCommonModel isMZDQChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName) 
			throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = MZDQChannel.paiChong(adverInfo.getFlag2(), adid, idfa);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = MZDQChannel.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, adverName);
		}
		
		return model;
	}
	
	private AppCommonModel isAPYSChannel(TChannelAdverInfo adverInfo, String adid, String idfa) 
			throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = APYSChannel.paiChong(adverInfo.getFlag2(), adid, idfa);
		
		return model;
	}
	
	private AppCommonModel isJZChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName) throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = JvZhangChannel.paiChong(adverInfo.getFlag2(), adid, idfa);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = JvZhangChannel.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, adverName);
		}
		
		return model;
	}
	
	private AppCommonModel isAiYLChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName) throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = AiyinliChannel.paiChong(adverInfo.getFlag2(), adid, idfa);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			TUserApp userApp = userAppService.getUserAppById(Integer.parseInt(userAppId));
			model = AiyinliChannel.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, adverName, userApp);
		}
		
		return model;
	}
	
	private AppCommonModel isHappyChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName) throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = HappyChannel.paiChong(adverInfo.getFlag2(), adid, idfa);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = HappyChannel.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, adverName);
		}
		
		return model;
	}
	
	private AppCommonModel isHZChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		//会赚
		//调用第三方排重接口
		AppCommonModel model = Huizhuan.paiChong(adverInfo.getFlag2(), adid, idfa, phoneModel, phoneVersion, adverName, ip);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = Huizhuan.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, phoneModel, phoneVersion, adverName);
		}
		
		return model;
	}
	
	private AppCommonModel isBeeChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		//会赚
		//调用第三方排重接口
		AppCommonModel model = BeeChannel.paiChong(adverInfo.getFlag2(), adid, idfa, ip);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = BeeChannel.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, adverName ,phoneModel, phoneVersion);
		}
		
		return model;
	}
	
	
	private AppCommonModel isFrogsChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		//会赚
		//调用第三方排重接口
		AppCommonModel model = FrogsChannel.paiChong(adverInfo.getFlag2(), adid, idfa);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = FrogsChannel.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum);
		}
		
		return model;
	}
	
	private AppCommonModel isFrogsTChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		//调用第三方排重接口
		AppCommonModel model = FrogsTChannel.paiChong(adverInfo.getFlag2(), adid, idfa, ip);
		
		return model;
	}
	
	//TT frogs - 15
	private AppCommonModel isFrogsTTChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName, String deviceType, String osVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		//调用第三方排重接口 
		AppCommonModel model = FrogsTTChannel.paiChong(adverInfo.getFlag2(), adid, idfa, ip, adverName,deviceType, osVersion);
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = FrogsTTChannel.dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId),
					userNum, deviceType, osVersion, adverName);
		}
		
		return model;
	}
	
	//设置任务时间超时
	@RequestMapping("setTaskTimeout")
	public void setTaskTimeout(HttpServletResponse response,HttpServletRequest request) 
			throws Exception
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String idfa = request.getParameter("idfa");
		String adverId = request.getParameter("adverId");
	
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
		if(tUserApp.getUserApppType() == 2) 
		{
			TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
			tUserappidAdverid.setIdfa(idfa);
			tUserappidAdverid.setAdverId(Integer.valueOf(adverId));
			tUserappidAdverid.setStatus("1.7");
			userappidAdveridService.updateTaskStatus(tUserappidAdverid);
		}
		
		model.setResult(1);
		model.setMsg("成功！");
		super.writeJsonDataApp(response, model);
	}
}
