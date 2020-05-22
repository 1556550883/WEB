package com.ruanyun.web.controller.sys.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
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
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.producer.AdverQueueConsumer;
import com.ruanyun.web.producer.QueueProducer;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UdidService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserappidAdveridService;
import com.ruanyun.web.util.AddressUtils;
import com.ruanyun.web.util.ArithUtil;
import com.ruanyun.web.util.NumUtils;

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
	private UdidService udidService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	
	
	@RequestMapping("test_idfa")
	public void test(HttpServletResponse response,String udid,String idfa,String ip,String phoneModel,String phoneVersion,String adverId) 
	{
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		try {
			AppCommonModel checkChannelInfoModel = ChannelClassification.checkChannelInfo(adverInfo, adverInfo.getAdid(), idfa, ip, "52", adverId, "UAN_0000000052", adverInfo.getAdverName(), phoneModel, phoneVersion, udid);
		
			super.writeJsonDataApp(response, checkChannelInfoModel);
		} catch (NumberFormatException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("bulang")
	public void bulang() throws UnsupportedEncodingException
	{
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
	 * 打开app
	 */
	@RequestMapping("openFrontApp")
	public void openFrontApp(HttpServletResponse response,HttpServletRequest request,String idfa,String phoneModel,String phoneVersion) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		String udid = "0";
		String udidtablename = "idfa_udid_xiaoshou";
		String time = TimeUtil.doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		TPhoneUdidWithIdfa result = udidService.getUdidByIdfa(idfa, udidtablename);
		
		if(result == null) 
		{
			//如果是空就说明需要获取新的udid
			String pModel = phoneModel;
			if(phoneModel.toLowerCase().equals("iphone9,3"))
			{
				pModel = "iphone9,1";
			}
			
			udid = ChannelClassification.getPhoneUdid(pModel.toLowerCase(),1);
			
			//获取新的udid之后需要保存idfa和udid
			if(!udid.equals("0")) 
			{
				TPhoneUdidWithIdfa p = new TPhoneUdidWithIdfa(idfa,udid,phoneModel,phoneVersion, time);
				udidService.savePhoneInfo(p, udidtablename);
			}
			else 
			{
				model.setResult(-1);
				model.setMsg(phoneModel + " udid被消耗完");
				super.writeJsonDataApp(response, model);
				return;
			}
		}
		else
		{
			udid = result.getUdid();
		}
		
		model.setResult(1);
		model.setMsg(udid);
		super.writeJsonDataApp(response, model);
		return;
	}
	
	private int testIdfaWork(String idfa)
	{
		try
		{
			URL url = new URL("http://xiaoshouzhuanqian.com/test/checkZdIdfa?idfa="+idfa);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) 
			{
				result.append(line);
			}
			reader.close();
			String ipAddr = result.toString();

			String str = ipAddr.substring(ipAddr.indexOf("[\"sysver\"]")-6,ipAddr.indexOf("[\"sysver\"]")-5);
			
			return Integer.parseInt(str);

		} catch (IOException e) {
			return 0;
		}
	}
	
	
	/**
	 * 领取任务
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 * @throws IOException 
	 * @throws ConsumerCancelledException 
	 * @throws ShutdownSignalException 
	 * @throws ParseException 
	 */
	@RequestMapping("lingQuRenWu")
	public void lingQuRenWu(HttpServletResponse response, HttpServletRequest request) 
			throws NumberFormatException, InterruptedException, ShutdownSignalException, ConsumerCancelledException, IOException, TimeoutException, ParseException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		String udid = request.getParameter("udid");
		String adverId = request.getParameter("adverId");
		String ip = request.getRemoteAddr();//手机ip
		String idfa = "";//手机广告标识符
		String userAppId = "";//用户Id
		String appleId = "";//苹果账号
		String userNum = "";
		String phoneModel_real = "";
		String phoneVersion_real = "";
		String phoneModel = "";
		String phoneVersion = "";
		int userAppType = 1;
		idfa = request.getParameter("idfa");//手机广告标识符
		
		if(adverId.equalsIgnoreCase("10000"))
		{
			int resu = testIdfaWork(idfa);
			if(resu != 1) 
			{
				model.setMsg("idfa不合格！");
			}
			else 
			{
				model.setMsg("idfa符合要求！");
			}
			
			model.setResult(-1);
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
		
		if(adverInfo.getAdverCountRemain() <= 0) 
		{
			model.setResult(-1);
			model.setMsg("1000：任务已被抢光！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();	
		//自定义通过率
		double ran = Math.random();
		if(ran >= adverInfo.getRandom()) 
		{
			model.setResult(-1);
			model.setMsg("抱歉，重复任务！");
			super.writeJsonDataApp(response, model);
			return;
		}

		//等于1就说说明需要去检测ip次数限制
		if(adverInfo.getIsIpLimitEnabled() > 0) 
		{
			int ipcount = userappidAdveridService.getIPLimitCount(ip,2,tablename);
			if(ipcount > adverInfo.getIsIpLimitEnabled()) 
			{
				model.setResult(-1);
				model.setMsg("此ip段前两位已经超出限制，请更换ip！");
				super.writeJsonDataApp(response, model);
				return;
			}
			else
			{
				ipcount = userappidAdveridService.getIPLimitCount(ip,3,tablename);
				if(ipcount > 3) 
				{
					model.setResult(-1);
					model.setMsg("此ip段前三位已经超出限制，请更换ip！");
					super.writeJsonDataApp(response, model);
					return;
				}
			}
		}
		
		String iplocaltion = AddressUtils.getAddressByIP(ip);
		
		//限制连云港港的ip
		if(adverInfo.getChannelNum().equals("25")
				||adverInfo.getChannelNum().equals("27")
				||adverInfo.getChannelNum().equals("16")
				||adverInfo.getChannelNum().equals("12"))
		{
			if(iplocaltion.contains("连云港市")) 
			{
				model.setResult(-1);
				model.setMsg("此ip在限制范围内，请更换ip！");
				super.writeJsonDataApp(response, model);
				return;
			}
		}
		
		//检测地区ip限制
		if(adverInfo.getIsIpLimitEnabled() > 0) 
		{
			int count  = userappidAdveridService.getIPlocalLimitCount(iplocaltion, tablename);
			int limit = (adverInfo.getAdverCount() * 4)/100;
			if(limit == 0) {limit = 1;}
			if(limit < count) 
			{
				model.setResult(-1);
				model.setMsg("此地区ip使用超出限制，请更换ip！");
				super.writeJsonDataApp(response, model);
				return;
			}
		}
		
		 //工作室
		
		 userAppId = request.getParameter("userAppId");//用户Id
		// appleId = request.getParameter("appleId");//苹果账号
		 userNum = request.getParameter("userNum");
		 phoneModel_real = request.getParameter("phoneModel") + "-";
		 phoneVersion_real = request.getParameter("phoneVersion") + "-";
		 //随机出机型
		 //phoneModel = ChannelClassification.getPhoneModel(userAppId);
		 phoneModel = request.getParameter("phoneModel");
		 phoneVersion = request.getParameter("phoneVersion");
			 
			 //测试代码
//			 phoneModel = "iphone9,1";
//			 phoneVersion = "13.1.1";
		 
		 if(!phoneModel.contains(",")) 
		 {
			model.setResult(-1);
			model.setMsg("请使用最新的安装包！");
			super.writeJsonDataApp(response, model);
			return;
		 }
			 
		 if(udid == null) 
		 {
			model.setResult(-1);
			model.setMsg("udid不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		 }
		 
		if(adverInfo.getAdverAdid().contains("-11"))
		{
			phoneVersion = ChannelClassification.get11PhoneVersion();
		}

		//判断当天领取到的任务
		String adid = adverInfo.getAdid();
		if(userAppType == 1)
		{
			//加上时间索引 提高效率 减轻rds压力
			Page<TUserappidAdverid> taskList = userappidAdveridService.getTasksByIdfaOrIP(idfa, ip,adverInfo.getChannelNum(),adverInfo.getAdid());
			
			if (taskList != null && !taskList.getResult().isEmpty())
			{
				//判断idfa是否重复 判断是否有任务在进行 
				AppCommonModel checkIdfaModel = checkDuplicated(userAppType,taskList, idfa, adverId,ip);
				if(checkIdfaModel.getResult() != 2) 
				{
					super.writeJsonDataApp(response, checkIdfaModel);
					return;
				}
			}
		}
		else 
		{
			//散户验证是否存在任务
			Page<TUserappidAdverid> taskList = userappidAdveridService.getTasksByIdfa(idfa,tablename);
			if (taskList != null && !taskList.getResult().isEmpty()) 
			{
				for (TUserappidAdverid item : taskList.getResult()) 
				{
					TChannelAdverInfo adverInfoss = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", item.getAdverId());
					if(adverInfoss.getTaskType().equals("1")) 
					{
						continue;
					}
					
					if(item.getStatus().compareTo("1.5") < 0) 
					{
						item.setReceiveTime(new Date());
						userappidAdveridService.updateReceiveTime(item,tablename);
						model.setResult(1);
						model.setObj(item);
						model.setMsg("请先完成正在进行的任务...");
						super.writeJsonDataApp(response, model);
						return;
					}
					else if(item.getStatus().compareTo("1.6") == 0 && item.getAdverId().equals(Integer.valueOf(adverId)))
					{
						model.setResult(-1);
						model.setMsg("任务已重复！");
						super.writeJsonDataApp(response, model);
						return;
					}
					else if((item.getStatus().compareTo("1.5") == 0 && item.getAdverId().equals(Integer.valueOf(adverId)))) 
					{
						model.setResult(1);
						model.setObj(item);
						super.writeJsonDataApp(response, model);
						return;
					}
				}
			}
		}

		//先进行排重，如果通过了就判断是否还有任务
		AppCommonModel checkChannelInfoModel = ChannelClassification.checkChannelInfo(adverInfo, adid, idfa, ip, userAppId, adverId,
				userNum, adverInfo.getAdverName(), phoneModel, phoneVersion, udid);
		
		if(checkChannelInfoModel.getResult() == -1)
		{
			super.writeJsonDataApp(response, checkChannelInfoModel);
			return;
		}

		//正式通过排重 检测是否还有剩余任务//先去判断是否还剩余任务，否则直接跳出
		String endPointName =  adverInfo.getAdverId() + "_" + adverInfo.getAdverName();
		AdverQueueConsumer ss = new AdverQueueConsumer(endPointName);
		if(ss.getMessageCount() <= 0) 
		{
			//正在进行的数量和完成数量
			int adverNum = channelAdverInfoService.getadverStartAndCompleteCount(adverId, tablename);
			//剩余的数量
			int remainadverNum =  adverInfo.getAdverCount() - adverNum;
			if(remainadverNum > 0)
			{
				//创建任务队列
				//生成队列
				for(int i = 1; i <= remainadverNum; i++) 
				{
					//更新剩余有效产品数量
					String data = UUID.randomUUID().toString();
					System.out.println("Put:" + data);
					ss.sendMessage(data, endPointName);
				}
			}
		}
		
		boolean success = ss.getMessage(endPointName);
		//false 代表队列中不存在任务了，需要去判断是否还有剩下任务
		if(!success) 
		{
			model.setResult(-1);
			model.setMsg("10001：任务已经被抢光!");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//保存任务
		TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
		tUserappidAdverid.setUserAppId(Integer.valueOf(userAppId));
		tUserappidAdverid.setIp(ip);
		tUserappidAdverid.setUserUdid(udid);
		tUserappidAdverid.setIdfa(idfa);
		tUserappidAdverid.setAppleId(appleId);
		tUserappidAdverid.setAdid(adid);
		tUserappidAdverid.setIpLocaltion(iplocaltion);
		tUserappidAdverid.setAdverId(Integer.valueOf(adverId));
		tUserappidAdverid.setPhoneModel(phoneModel_real + phoneModel);
		tUserappidAdverid.setPhoneVersion(phoneVersion_real + phoneVersion);
		//得到任务，保存任务
		tUserappidAdverid.setStatus("1");
		//如果保存出现异常一律按照重复任务处理
		
		userappidAdveridService.saveTask(tablename,tUserappidAdverid);
		
		model.setResult(1);
		model.setMsg("领取任务成功，请在规定时间内完成任务，否则不计分！");
		super.writeJsonDataApp(response, model);
		return;
	}
	
	private AppCommonModel checkDuplicated(int userType, Page<TUserappidAdverid> taskList, String idfa, String adverId, String ip) 
	{
		AppCommonModel model = new AppCommonModel(2, "没有符合条件！");
		
		for (TUserappidAdverid item : taskList.getResult())
		{
			if(item.getIdfa().equalsIgnoreCase(idfa))
			{
				if (item.getStatus().compareTo("2") >= 0) 
				{
					model.setResult(-1);
					model.setMsg("领取任务失败。原因：任务已完成，不能重复领取！");
					break;
				} 
				else if (item.getStatus().compareTo("1.6") == 0) 
				{
					model.setResult(-1);
					model.setMsg("领取任务失败。原因：任务没有在规定时间内完成，导致任务已失效！");
					break;
				}
				else if (!item.getAdverId().equals(Integer.valueOf(adverId)))  
				{
					model.setResult(-1);
					model.setMsg("领取任务失败。原因：已领取过此类任务，不能重复领取！");
					break;
				}
				else if (item.getStatus().compareTo("1.6") < 0)  
				{
					if(!ip.equals(item.getIp())) 
					{
						model.setResult(-1);
						model.setMsg("ip已经被更改，请重新领取任务！");
					}
					
					model.setResult(3);
					//model.setObj(item); 此处的msg不能改，由于app用这个判断 需要修改
					model.setMsg("你已成功领取任务，不需重复领取！");
					break;
				}
			}
			else if(ip.equals(item.getIp()))
			{
				model.setResult(-1);
				model.setMsg("ip已被使用,请直接更换ip！");
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
		
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		if(adverInfo == null) {
			model.setResult(-1);
			model.setMsg("任务不存在！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();	
		//得到唯一任务
		TUserappidAdverid task = userappidAdveridService.getTask(tablename,idfa,adverId);
		if(task == null)
		{
			model.setResult(-1);
			model.setMsg("请先领取任务！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		if(task.getStatus().compareTo("1.6") == 0)
		{
			model.setResult(-1);
			model.setMsg("没有在规定时间内完成任务，任务已失效！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//状态改为打开app
		task.setStatus("1.5");
		task.setOpenAppTime(new Date());
		userappidAdveridService.updateStatus2OpenApp(tablename,task);
		
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
		
		userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, Integer.valueOf(userAppId));
		String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();	
		//更新金额
		TUserScore score = new TUserScore();
		score.setUserNick(idfa);//标记任务的idfa
		score.setUserNum(userNum);
		score.setLevelName(tablename);//标记表名
		score.setUserScoreId(Integer.valueOf(adverId));//标记得分的 任务
		score.setType(-1);
		
		//只有工作室
		float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
		score.setScore(sco);
//		if(tUserApp.getUserApppType() == 1) //工作室
//		{
//			float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
//			score.setScore(sco);
//		}
//		else
//		{
//			score.setScore(adverInfo.getAdverPrice());
//		}
//		
		QueueProducer ap  = new QueueProducer();
		ap.sendMessage(score, "socre");
		ap.close();
//		if(tUserApp.getUserApppType() == 2)
//		{
//			masterWork(tUserApp);
//		}
		
		model.setResult(1);
		model.setMsg("success!");
		model.setSuccess(true);
		model.setMessage("ok");
		super.writeJsonDataApp(response, model);
	}
	
	/**
	 * 查看结果
	 */
	@RequestMapping("queryOneMission")
	public void queryOneMission(HttpServletResponse response, HttpServletRequest request)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String idfa = request.getParameter("idfa");//手机广告标识符l
		String remoteIp = request.getRemoteAddr();//手机ip
		String adverId = request.getParameter("adverId");//广告id（我们系统提供）
		String udid = request.getParameter("udid");
		//工作室
		if(udid != null && !udid.isEmpty())
		{
			TUserApp userApp = userAppService.getUserAppByUserName(udid);
			idfa = userApp.getIdfa();
			if(!userApp.getLoginControl().equals("1"))
			{
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
		
		TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
		//查询广告信息
		if(adverInfo == null)
		{
			model.setResult(-1);
			model.setMsg("广告不存在！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		if(adverInfo.getAdverStatus() == 2)
		{
			model.setResult(-1);
			model.setMsg("任务已被停止！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//判断是否领取任务
		String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();	
		//得到唯一任务
		TUserappidAdverid task = userappidAdveridService.getTask(tablename,idfa,adverId);
		if(task == null)
		{
			model.setResult(-1);
			model.setMsg("请先领取任务！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		String ip = task.getIp();
		if(!remoteIp.equals(ip) 
				&& !adverInfo.getChannelNum().equals("3")
				&& !adverInfo.getChannelNum().equals("12")
				&& !adverInfo.getChannelNum().equals("15")) 
		{
			model.setResult(-1);
			model.setMsg("ip不统一，任务已失效！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		//判断任务状态
		if(task.getStatus().compareTo("2.1") >= 0)
		{
			model.setResult(1);
			model.setMsg("进入自提交，请稍后查看结果！");
			super.writeJsonDataApp(response, model);
			return;
		}
		else if(task.getStatus().compareTo("2") >= 0)
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
		
		//String userNum = NumUtils.getCommondNum(NumUtils.USER_APP_NUM, task.getUserAppId());
		Integer leastTaskTime = adverInfo.getOpenTime();
		if(new Date().getTime() - task.getOpenAppTime().getTime() <= leastTaskTime * 1000)
		{
			model.setResult(-1);
			model.setMsg("未完成。原因：打开app必须达到" + leastTaskTime + "秒！");
			super.writeJsonDataApp(response, model);
			return;
		}

		//回调任务
		if("1".equals(adverInfo.getTaskType()))
		{
			model.setResult(1);
			model.setMsg("任务已达到条件，等待回调，是否退回继续做任务！");
			super.writeJsonDataApp(response, model);
			return;
		}
		else
		{	
			//快速任务和自由任务
			//分渠道调用激活上报接口
			//任务需要提交的时间间隔，任务就需要后台自动去判断进行提交，这里就不需要真正的提交,更改任务状态为2.1
			//(int)(1+Math.random()*(10-1+1)) 此处定义任务真实的完成过时间，随机完成
			int ranMin = (int)(1+Math.random()*adverInfo.getSubmitInterTime());
			int ranSec = (int)(1+Math.random()*55);
			
			//如果任务不需要自提交就直接去提交任务
			if(adverInfo.getSubmitInterTime() == 0) 
			{
				ranMin = 0;
				ranSec= 0;
			}
			
			String commpleteTime = ChannelClassification.GetRandomFuntrueTime(ranMin, ranSec);
			userappidAdveridService.updateSpecialComplete(tablename,"2.1", commpleteTime,adverId,idfa);
			model.setResult(1);
			model.setMsg("进入自提交，请稍后查看结果!");
			super.writeJsonDataApp(response, model);
			return;
		}
		
//			if(userType == 1) //工作室
//			{
//				//float sco = (float) (adverInfo.getAdverPrice() - adverInfo.getPriceDiff());
//				float sco = ArithUtil.subf(adverInfo.getAdverPrice(), adverInfo.getPriceDiff());
//				score.setScore(sco);
//			}
//			else
//			{
//				score.setScore(adverInfo.getAdverPrice());
//				//计算师傅获取到的利润
//				TUserApp tUserApp  = userAppService.getUserAppById(task.getUserAppId());
//				masterWork(tUserApp);
//			}
			
	}
	
	//计算师傅获取到的利润 这个应该放在计算之后有时间更改
//	private void masterWork(TUserApp tUserApp)
//	{
//		if(tUserApp.getMasterID() != null && tUserApp.getLimitTime() != null && tUserApp.getLimitTime() > 0)
//		{
//			TUserApp masterUserApp  = userAppService.getUserAppById(Integer.valueOf(tUserApp.getMasterID()));
//			if(masterUserApp != null)
//			{
//				TUserScore score = new TUserScore();
//				score.setType(1);
//				score.setUserNum(masterUserApp.getUserNum());//师傅num
//				score.setRankingNum(tUserApp.getUserNum());//用来表示徒弟num
//				score.setScore((float) 0.5);
//				try {
//					QueueProducer.getQueueProducer().sendMessage(score, "socre");
//				} catch (IOException e) {
//					e.printStackTrace();
//				} catch (TimeoutException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
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
		
		//获取所有正在进行中的任务种类
		List<TUserappidAdverid> adverCompleteList = new ArrayList<TUserappidAdverid>();
		List<TChannelAdverInfo> advers = appChannelAdverInfoService.queryAllStartAdversGroup();
		for(TChannelAdverInfo info : advers) 
		{
			String tablename = "t_adver_"+ info.getChannelNum() + "_" + info.getAdid();	
			//每个任务每个idfa只能有一个任务
			TUserappidAdverid task = userappidAdveridService.getTaskDetailByIdfa(idfa, tablename);
			if(task != null) 
			{
				task.setAdverName(info.getAdverName());
				task.setAdverPrice(ArithUtil.subf(info.getAdverPrice(), info.getPriceDiff()));
				adverCompleteList.add(task);
			}
		}
		
		Collections.sort(adverCompleteList, new Comparator<Object>() {
		     @Override
		     public int compare(Object o1, Object o2) {
		    	 TUserappidAdverid e1 = (TUserappidAdverid) o1;
		    	 TUserappidAdverid e2 = (TUserappidAdverid) o2;
		         return e2.getReceiveTime().compareTo(e1.getReceiveTime());
		     }
		 });
		
		page.setResult(adverCompleteList);
		model.setObj(page);
		model.setResult(1);
		model.setMsg("查询成功！");
		super.writeJsonDataApp(response, model);
	}
	
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
		String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();	
		Page<TUserappidAdverid> page2 = userappidAdveridService.queryMission(page, info, tablename, TimeUtil.GetdayDate());
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
		int userType = 1;
		//工作室不需要放弃功能
		if(userType == 1) 
		{
			model.setResult(1);
			model.setMsg("成功！");
			super.writeJsonDataApp(response, model);
			return;
		}

		if(udid != null && !udid.isEmpty()) {
			TUserApp userApp = userAppService.getUserAppByUserName(udid);
			idfa = userApp.getIdfa();
			//外放
			userType = 2;
		}
		
		if(!StringUtils.hasText(idfa) || !StringUtils.hasText(adverId))
		{
			model.setResult(-1);
			model.setMsg("idfa、adverId不能为空！");
			super.writeJsonDataApp(response, model);
			return;
		}
		
		
		//判断是否领取任务
		//String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();	
		//得到唯一任务
//		TUserappidAdverid task = userappidAdveridService.getTask(tablename,adverId, idfa);
//		if(task != null && task.getStatus().compareTo("2") == 0)
//		{
//			model.setResult(-1);
//			model.setMsg("任务已经完成，不需要放弃！");
//			super.writeJsonDataApp(response, model);
//			return;
//		}
//		
//		//查询个人信息
//		//外放人员
//		if(userType == 2) 
//		{
//			TUserappidAdverid tUserappidAdverid = new TUserappidAdverid();
//			tUserappidAdverid.setIdfa(idfa);
//			tUserappidAdverid.setAdverId(Integer.valueOf(adverId));
//			//1.7代表放弃的状态
//			tUserappidAdverid.setStatus("1.7");
//			userappidAdveridService.updateTaskStatus(tUserappidAdverid);
//		}
//		
		model.setResult(1);
		model.setMsg("成功！");
		super.writeJsonDataApp(response, model);
	}
}
