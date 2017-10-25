package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.producer.ArrayBlockQueueProducer;
import com.ruanyun.web.producer.ScoreQueueConsumer;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelInfoService;
import com.ruanyun.web.service.background.DictionaryService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserappidAdveridService;

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
	
	/**
	 * 领取任务
	 * @throws InterruptedException 
	 */
	@RequestMapping("lingQuRenWu")
	public void lingQuRenWu(HttpServletResponse response, HttpServletRequest request) 
			throws NumberFormatException, UnsupportedEncodingException, InterruptedException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String adid = request.getParameter("adid");//广告id（第三方提供）
		String idfa = request.getParameter("idfa");//手机广告标识符
		String ip = request.getRemoteAddr();//手机ip
//		String idfa = 9999 + Math.random() * 9000 + "";//手机广告标识符
//		String ip = 1000 + Math.random() * 9000 + "";//手机ip
		String userAppId = request.getParameter("userAppId");//用户Id
		String adverId = request.getParameter("adverId");//广告id（我们系统提供）
		String appleId = request.getParameter("appleId");//苹果账号
		String userNum = request.getParameter("userNum");
		
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
		
		//判断当天领取到的任务
		Page<TUserappidAdverid> taskList = userappidAdveridService.getTasks(adid, idfa, ip);
		
		if (taskList != null && !taskList.getResult().isEmpty())
		{
			//判断idfa是否重复
			AppCommonModel checkIdfaModel = checkIDFADuplicated(taskList, idfa, adverId);
			if(checkIdfaModel.getResult() != 2) 
			{
				super.writeJsonDataApp(response, checkIdfaModel);
				return;
			}
			
			//判断当天IP是否重复
			AppCommonModel checkIpModel = checkIPDuplicated(taskList, idfa);
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
		
		ArrayBlockingQueue<String> arrayBlockQueue = ArrayBlockQueueProducer.mQueueMap.get(adverId);
		
		if(arrayBlockQueue == null)
		{
			model.setResult(-1);
			model.setMsg("此任务需要管理员重新启动!");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		String data = arrayBlockQueue.poll();
		
		if(data != null)
		{
			//分渠道调用排重接口、点击接口
			AppCommonModel checkChannelInfoModel = checkChannelInfo(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
			
			if(checkChannelInfoModel.getResult() == -1)
			{
				super.writeJsonDataApp(response, checkChannelInfoModel);
				return;
			}
			else
			{
				appChannelAdverInfoService.updateAdverCountRemainMinus1(adverInfo);
			}
		}
		else
		{
			model.setResult(-1);
			model.setMsg("真遗憾，你未抢到任务!");
			super.writeJsonDataApp(response, model);
			return;
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
		tUserappidAdverid = userappidAdveridService.save(tUserappidAdverid);
		
		if(tUserappidAdverid == null)
		{
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：保存任务失败！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		model.setResult(1);
		model.setMsg("领取任务成功，请在规定时间内完成任务，否则不计分！");
		super.writeJsonDataApp(response, model);
	}
	
	private AppCommonModel checkAdverInProcess(Page<TUserappidAdverid> taskList) 
	{
		AppCommonModel model = new AppCommonModel(2, "通过！");
		
		for (TUserappidAdverid item : taskList.getResult()) 
		{
			if(item.getStatus().compareTo("1.6") < 0) 
			{
				model.setResult(1);
				model.setMsg("此类广告已经领取过，请勿重复领取！");
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
	
	private AppCommonModel checkIDFADuplicated(Page<TUserappidAdverid> taskList, String idfa, String adverId) 
	{
		AppCommonModel model = new AppCommonModel(2, "没有符合条件！");
		
		for (TUserappidAdverid item : taskList.getResult())
		{
			if(item.getIdfa().equals(idfa))
			{
				if (item.getStatus().compareTo("2") >= 0) 
				{
					model.setResult(-1);
					model.setMsg("领取任务失败。原因：任务已完成，不能重复领取！");
				} 
				else if (item.getStatus().compareTo("1.6") == 0) 
				{
					model.setResult(-1);
					model.setMsg("领取任务失败。原因：任务没有在规定时间内完成，导致任务已失效！");
				}
				else if (!item.getAdverId().equals(Integer.valueOf(adverId))) 
				{
					model.setResult(-1);
					model.setMsg("领取任务失败。原因：已领取过此类任务，不能重复领取！");
				}
				else if (item.getStatus().compareTo("1.6") < 0) 
				{
					model.setResult(1);
					model.setMsg("你已成功领取任务，不需重复领取！");
				}
			}
		}
		
		return model;
	}
	
	//判断ip是否重复
	private AppCommonModel checkIPDuplicated(Page<TUserappidAdverid> taskList, String idfa)
	{
		AppCommonModel model = new AppCommonModel(2, "通过！");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		for (TUserappidAdverid item : taskList.getResult())
		{
			if (simpleDateFormat.format(item.getReceiveTime()).equals(simpleDateFormat.format(new Date())) && !idfa.equals(item.getIdfa())) 
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
		if(adverInfo != null && "0".equals(adverInfo.getTaskType())) 
		{
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
			model.setMsg("未完成。原因：更改任务状态失败！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//更新金额
		TUserScore score = new TUserScore();
		score.setUserNum(userNum);
		score.setScore(adverInfo.getAdverPrice());
		ScoreQueueConsumer.getQueueProducer().sendMessage(score);
		
		model.setResult(1);
		model.setMsg("success！");
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
		String ip = request.getRemoteAddr();//手机ip
		String adverId = request.getParameter("adverId");//广告id（我们系统提供）
		String userNum = request.getParameter("userNum");
		
		if(!StringUtils.hasText(adid) || !StringUtils.hasText(idfa) || !StringUtils.hasText(ip)
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
		
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		
		//判断任务状态
		if(task.getStatus().compareTo("2") >= 0 && "1".equals(adverInfo.getTaskType()))
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
			else if("1".equals(channelInfo.getChannelNum()))
			{
				//云聚
				//调用第三方激活上报接口
				model = YunJu.activate(adverInfo.getFlag4(), adid, adverInfo.getAdverName(), idfa, ip);
				if(model.getResult() == -1)
				{
					super.writeJsonDataApp(response, model);
					return;
				}
			}
			else if("2".equals(channelInfo.getChannelNum()))
			{
				//掌上互动
				//调用第三方激活上报接口
				model = ZhangShangHuDong.activate(adid, idfa, ip);
				if(model.getResult() == -1)
				{
					super.writeJsonDataApp(response, model);
					return;
				}
			}
			else if("4".equals(channelInfo.getChannelNum()))
			{
				//云聚
				//调用第三方激活上报接口
				model = LiDeJiJin.activate(adverInfo.getFlag4(), adid, idfa, ip);
				if(model.getResult() == -1)
				{
					super.writeJsonDataApp(response, model);
					return;
				}
			}
			else
			{
				model.setResult(-1);
				model.setMsg("未完成。原因：渠道未在后台配置！");
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
				model.setResult(1);
				model.setMsg("任务已经完成！");
				super.writeJsonDataApp(response, model);
				return;
			}
			//把单子发送到队列
			TUserScore score = new TUserScore();
			score.setUserNum(userNum);
			score.setScore(adverInfo.getAdverPrice());
			ScoreQueueConsumer.getQueueProducer().sendMessage(score);
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
				model.setResult(1);
				model.setMsg("任务已经完成！");
				super.writeJsonDataApp(response, model);
				return;
			}
			//更改金额
			TUserScore score = new TUserScore();
			score.setUserNum(userNum);
			score.setScore(adverInfo.getAdverPrice());
			ScoreQueueConsumer.getQueueProducer().sendMessage(score);
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

	/**
	 * 我的已完成任务
	 */
	@RequestMapping("queryTaskSum")
	public void queryTaskSum(HttpServletResponse response, HttpServletRequest request, Page<TUserappidAdverid> page)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		page.setNumPerPage(Integer.MAX_VALUE);
		String userAppId = request.getParameter("userAppId");
		
		if(!StringUtils.hasText(userAppId))
		{
			model.setResult(-1);
			model.setMsg("userAppId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//查询个人信息
		TUserApp tUserApp = userAppService.getUserAppById(Integer.valueOf(userAppId));
		if(tUserApp == null)
		{
			model.setResult(-1);
			model.setMsg("用户不存在！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//查询任务完成情况
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		TChannelAdverInfo tChannelAdverInfo = new TChannelAdverInfo();
		tChannelAdverInfo.setAdverStatusEnd(2);
		List<TChannelAdverInfo> adverInfoList = appChannelAdverInfoService.getByCondition(tChannelAdverInfo);
		BigDecimal scoreSum = new BigDecimal("0.0");
		scoreSum.setScale(1, BigDecimal.ROUND_HALF_UP);
		if(adverInfoList != null)
		{
			for(TChannelAdverInfo item:adverInfoList)
			{
				TUserappidAdverid info = new TUserappidAdverid();
				info.setUserAppId(Integer.valueOf(userAppId));
				info.setAdverId(item.getAdverId());
				info.setStatus("2");
				Integer completeCount = userappidAdveridService.queryMissionCount(info);
				
				Map<String,Object> adverInfo = new HashMap<String,Object>();
				adverInfo.put("adid", item.getAdid());
				adverInfo.put("adverName", item.getAdverName());
				adverInfo.put("adverDayStart", item.getAdverDayStart());
				adverInfo.put("adverDayEnd", item.getAdverDayEnd());
				adverInfo.put("adverCount", item.getAdverCount());
				adverInfo.put("adverPrice", item.getAdverPrice());
				adverInfo.put("completeCount", completeCount);
				BigDecimal score = new BigDecimal(completeCount*item.getAdverPrice());
				scoreSum = scoreSum.add(score);
				adverInfo.put("score", score.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue());
				list.add(adverInfo);
			}
		}
		
		TUserScore tUserScore = tUserApp.getUserScore();
		if(tUserScore == null)
		{
			tUserScore = new TUserScore();
		}
		tUserScore.setScore(scoreSum.floatValue());
		tUserApp.setUserScore(tUserScore);
		
		Map<String,Object> map = new HashMap<String,Object>(4);
		map.put("userInfo", tUserApp);
		map.put("adverInfo", list);
		
		model.setObj(map);
		model.setResult(1);
		model.setMsg("查询成功！");
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 我的已完成任务--明细
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
			String userAppId, String adverId, String userNum) throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(1, "任务领取成功！");
		
		//分渠道调用排重接口、点击接口
		TChannelInfo channelInfo = channelInfoService.getInfoByNum(adverInfo.getChannelNum());
		if(channelInfo == null)
		{
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：渠道不存在！");
		}
		else if("1".equals(channelInfo.getChannelNum()))
		{
			//云聚
			model = isYunJvChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
		}
		else if("2".equals(channelInfo.getChannelNum()))
		{
			model.setResult(-1);
			model.setMsg("此渠道尚未对接！");
		}
		else if("3".equals(channelInfo.getChannelNum()))
		{
			//自由渠道
			model.setResult(1);
		}
		else if("4".equals(channelInfo.getChannelNum()))
		{
			//利得基金
			model = isLDJJChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
		}
		else if("5".equals(channelInfo.getChannelNum()))
		{
			//利得基金
			model = isDYDChannel(adverInfo, adid, idfa, ip, userAppId, adverId, userNum);
		}
		else
		{
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：渠道未在后台配置！");
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
		
		model.setResult(1);
		model.setMsg("成功！");
		super.writeJsonDataApp(response, model);
	}
}
