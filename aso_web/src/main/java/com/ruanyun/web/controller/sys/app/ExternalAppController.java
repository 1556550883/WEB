package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TExternalChannelAdverInfo;
import com.ruanyun.web.model.TExternalChannelTask;
import com.ruanyun.web.service.app.ExternalAppService;
import com.ruanyun.web.service.background.ExternalChannelAdverInfoService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/interface")
public class ExternalAppController extends BaseController
{
	@Autowired
	private ExternalAppService externalAppService;
	@Autowired
	private ExternalChannelAdverInfoService externalChannelAdverInfoService;
	
	@RequestMapping("happy_distinct")
	public void distinctInterface(HttpServletResponse response, HttpServletRequest request, String adid, String key,
			String idfa,String sysver, String model,String keyword,String ip, String udid)
	{
		TExternalChannelAdverInfo info = externalChannelAdverInfoService.getInfoByAdidAndKey(key, adid);
		JSONObject obj = new JSONObject();
		if(info == null) {
			obj.element("code", -1);
			obj.element("msg", "task not exist");
			super.writeJsonDataApp(response, obj);
			return;
		}
		
		TExternalChannelTask tExternalChannelTask = new TExternalChannelTask();
		tExternalChannelTask.setIdfa(idfa);
		if(udid != null) {
			tExternalChannelTask.setUdid(udid);
		}else {
			tExternalChannelTask.setUdid("0");
		}
		tExternalChannelTask.setSysver(sysver);
		tExternalChannelTask.setModel(model);
		tExternalChannelTask.setIp(ip);
		tExternalChannelTask.setKeywords(keyword);
		tExternalChannelTask.setStatus("1");
		
		//对接云聚
		if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("happyzhuan")) {
			AppCommonModel models = YunJu.paiChong(info.getCpchannelDistinct(), info.getChannelAdverAdid(), idfa, sysver, model, keyword, ip);
			if(models.getResult() == 1) {
				//设置0代表为重复
				obj.element(idfa, 0);
				try {
					//如果抛出异常重复添加了
					externalAppService.save(tExternalChannelTask, adid, key);
				} catch (Exception e) {
					obj.element(idfa, 1);
				}
			}else {
				obj.element(idfa, 1);
			}
			//保存
			super.writeJsonDataApp(response, obj);
			return;
		}
		else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("qingwa")) {
			try {
				
				//(adverInfo.getFlag2(), adid, idfa, ip, adverName,deviceType, osVersion);
				AppCommonModel models = SanhuFrogsChannel.paiChong(info.getCpchannelDistinct(), info.getChannelAdverAdid(), idfa,ip,keyword, model,sysver, udid);
				if(models.getResult() == 1) {
					//设置0代表为重复
					obj.element(idfa, 0);
					try {
						//如果抛出异常重复添加了
						externalAppService.save(tExternalChannelTask, adid, key);
					} catch (Exception e) {
						obj.element(idfa, 1);
					}
				}else {
					obj.element(idfa, 1);
				}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//保存
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("youbang")) {
				AppCommonModel models = YouZhuanChannel.paiChong(info.getCpchannelDistinct(), info.getChannelAdverAdid(), idfa, sysver, model, keyword, ip, udid);
				if(models.getResult() == 1) {
					//设置0代表为重复
					obj.element(idfa, 0);
					try {
						//如果抛出异常重复添加了
						externalAppService.save(tExternalChannelTask, adid, key);
					} catch (Exception e) {
						obj.element(idfa, 1);
					}
				}else {
					obj.element(idfa, 1);
				}
				//保存
				super.writeJsonDataApp(response, obj);
				return;
			}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("fenqianba")) {
				AppCommonModel models = FenQianChannel.paiChong(info.getCpchannelDistinct(), info.getChannelAdverAdid(), idfa);
				if(models.getResult() == 1) {
					//设置0代表为重复
					obj.element(idfa, 0);
					try {
						//如果抛出异常重复添加了
						externalAppService.save(tExternalChannelTask, adid, key);
					} catch (Exception e) {
						obj.element(idfa, 1);
					}
				}else {
					obj.element(idfa, 1);
				}
				//保存
				super.writeJsonDataApp(response, obj);
							return;
				}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("zhangshanghudong")) {
					if(!model.contains(",")) {
						obj.element("code", -1);
						obj.element("msg", "Invalid Model value");
						super.writeJsonDataApp(response, obj);
						return;
					}
					
					udid = ChannelClassification.getPhoneUdid(model);
					tExternalChannelTask.setUdid(udid);
					AppCommonModel models = ZhangShangHuDong.paiChong(info.getCpchannelDistinct(), info.getChannelAdverAdid(), idfa,ip,sysver, model,udid,keyword);
					if(models.getResult() == 1) {
						//设置0代表为重复
						obj.element(idfa, 0);
						try {
							//如果抛出异常重复添加了
							externalAppService.save(tExternalChannelTask, adid, key);
						} catch (Exception e) {
							obj.element(idfa, 1);
						}
					}else {
						obj.element(idfa, 1);
					}
					//保存
					super.writeJsonDataApp(response, obj);
					return;
				}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("mifeng")) {
					if(!model.contains(",")) {
						obj.element("code", -1);
						obj.element("msg", "Invalid Model value");
						super.writeJsonDataApp(response, obj);
						return;
					}
					
					udid = ChannelClassification.getPhoneUdid(model);
					tExternalChannelTask.setUdid(udid);
					//exterPaiChong(String domain,String adid,String keyword,  String idfa, String ip, 
					//		String deviceType, String osVersion, String udid) 
					
					AppCommonModel models = BeeChannel.exterPaiChong(info.getCpchannelDistinct(), info.getChannelAdverAdid(),keyword, idfa,ip,model,sysver,udid);
					if(models.getResult() == 1) {
						//设置0代表为重复
						obj.element(idfa, 0);
						try {
							//如果抛出异常重复添加了
							externalAppService.save(tExternalChannelTask, adid, key);
						} catch (Exception e) {
							obj.element(idfa, 1);
						}
					}else {
						obj.element(idfa, 1);
					}
					//保存
					super.writeJsonDataApp(response, obj);
					return;
				}

		try 
		{
			TExternalChannelTask taskInfo = externalAppService.geTExternalTaskInfo(tExternalChannelTask, adid, key);
			
			if(taskInfo != null && taskInfo.getIdfa() != null) 
			{
				//3是任务完成
				if(taskInfo.getStatus().equals("3"))
				{
					obj.element(idfa, 1);
				}
				else 
				{
					obj.element(idfa, 0);
				}
			}
			else
			{
				externalAppService.save(tExternalChannelTask, adid, key);
				obj.element(idfa, 0);
			}
			
			super.writeJsonDataApp(response, obj);
		} 
		catch (Exception e) 
		{
			obj.element(idfa, 1);
			super.writeJsonDataApp(response, obj);
		}
	}

	@RequestMapping("callback")
	public void callback(String adid, String idfa, String key) {
		TExternalChannelTask tExternalChannelTask = new TExternalChannelTask();
		tExternalChannelTask.setIdfa(idfa);
		tExternalChannelTask.setStatus("3");
		TExternalChannelTask taskInfo = externalAppService.geTExternalTaskInfo(tExternalChannelTask, adid, key);
		String externalCallback = taskInfo.getCallback();
		BaseChannel.httpGet(externalCallback.toString(), false);
		externalAppService.updateStatus(tExternalChannelTask, adid, taskInfo.getChannelKey());
	}
	
	@RequestMapping("happy_click")
	public void clickInterface(HttpServletResponse response, HttpServletRequest request, 
			String adid, String key, String idfa, String keyword, String ip, 
			String model, String sysver, String callbackurl ,String udid) throws UnsupportedEncodingException
	{
		TExternalChannelAdverInfo info = externalChannelAdverInfoService.getInfoByAdidAndKey(key, adid);
		JSONObject obj = new JSONObject();
		if(info == null) {
			obj.element("code", -1);
			obj.element("msg", "task not exist");
			super.writeJsonDataApp(response, obj);
			return;
		}
		
		TExternalChannelTask tExternalChannelTask = new TExternalChannelTask();
		tExternalChannelTask.setIdfa(idfa);
		tExternalChannelTask.setStatus("2");
		tExternalChannelTask.setIp(ip);
		tExternalChannelTask.setKeywords(keyword);
		tExternalChannelTask.setModel(model);
		tExternalChannelTask.setSysver(sysver);
		tExternalChannelTask.setCallback(callbackurl);

		//对接云聚 激活
		if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("happyzhuan")) {
			AppCommonModel appmodel = YunJu.externalDianJi(info.getCpchannelClick(),info.getChannelAdverAdid(),adid, idfa, ip, sysver, model,keyword,key);
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("msg", "ok");
				externalAppService.update(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("msg", "click failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("qingwa")) {
			
			AppCommonModel appmodel = SanhuFrogsChannel.externalDianJi(info.getCpchannelClick(),info.getChannelAdverAdid(),adid, idfa, ip, sysver, model,keyword,key, udid);
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("msg", "ok");
				externalAppService.update(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("msg", "click failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("youbang")) {
				AppCommonModel appmodel = YouZhuanChannel.externalDianJi(info.getCpchannelClick(),info.getChannelAdverAdid(),adid, idfa, ip, sysver, model,keyword,key, udid);
				if(appmodel.getResult() == 1) {
					obj.element("code", 0);
					obj.element("msg", "ok");
					externalAppService.update(tExternalChannelTask, adid, key);
				}else {
					obj.element("code", -1);
					obj.element("msg", "click failed");
				}
				
				super.writeJsonDataApp(response, obj);
				return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("fenqianba")) {
			//不需要点击接口
			obj.element("code", 0);
			obj.element("msg", "ok");
			externalAppService.update(tExternalChannelTask, adid, key);
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("zhangshanghudong")) {
			TExternalChannelTask taskInfo = externalAppService.geTExternalTaskInfo(tExternalChannelTask, adid, key);
			AppCommonModel appmodel = ZhangShangHuDong.externalDianJi(info.getCpchannelClick(),info.getChannelAdverAdid(),adid, idfa, ip, sysver, model,keyword,key, taskInfo.getUdid());
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("msg", "ok");
				externalAppService.update(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("msg", "click failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("mifeng")) {
			TExternalChannelTask taskInfo = externalAppService.geTExternalTaskInfo(tExternalChannelTask, adid, key);
			AppCommonModel appmodel = BeeChannel.externalDianJi(info.getCpchannelClick(),info.getChannelAdverAdid(),adid, idfa, ip, sysver, model,keyword,key, taskInfo.getUdid());
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("msg", "ok");
				externalAppService.update(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("msg", "click failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}


		try 
		{
			TExternalChannelTask taskInfo = externalAppService.geTExternalTaskInfo(tExternalChannelTask, adid, key);
			if(taskInfo == null) 
			{
				obj.element("code", -1);
				obj.element("msg", "未知task!");
				super.writeJsonDataApp(response, obj);
				return;
			}
			
			if(taskInfo.getStatus().equals("2")) 
			{
				obj.element("code", 0);
				obj.element("msg", "ok");
				super.writeJsonDataApp(response, obj);
				return;
			}
			
			if(taskInfo.getStatus().equals("1")) 
			{
				int result = externalAppService.update(tExternalChannelTask, adid, key);
				if(result > 0) 
				{
					obj.element("code", 0);
					obj.element("msg", "ok");
					super.writeJsonDataApp(response, obj);
				}
				else
				{
					obj.element("code", -1);
					obj.element("msg", "数据更新错误！");
					super.writeJsonDataApp(response, obj);
				}
			}
			else if(taskInfo.getStatus().equals("3"))
			{
				obj.element("code", -1);
				obj.element("msg", "idfa已重复！");
				super.writeJsonDataApp(response, obj);
			}
		}
		catch (Exception e) 
		{
			obj.element("code", -1);
			obj.element("msg", "系统错误");
			super.writeJsonDataApp(response, obj);
		}
	}

	@RequestMapping("happy_active")
	public void activeInterface(HttpServletResponse response, HttpServletRequest request, String adid, String key, String idfa,
			String ip,String sysver,String model,String keyword, String udid)
	{
		
		TExternalChannelAdverInfo info = externalChannelAdverInfoService.getInfoByAdidAndKey(key, adid);
		JSONObject obj = new JSONObject();
		if(info == null) {
			obj.element("code", -1);
			obj.element("msg", "task not exist");
			super.writeJsonDataApp(response, obj);
			return;
		}
		
		if(info.getExternalTaskType().equals("1")) {
			obj.element("code", -1);
			obj.element("result", "callback task");

			return;
		}
		
		TExternalChannelTask tExternalChannelTask = new TExternalChannelTask();
		tExternalChannelTask.setIdfa(idfa);
		tExternalChannelTask.setStatus("3");
		TExternalChannelTask taskInfo = externalAppService.geTExternalTaskInfo(tExternalChannelTask, adid, key);
		if(taskInfo == null) 
		{
			obj.element("code", -1);
			obj.element("result", "未知task！");
			super.writeJsonDataApp(response, obj);
			return;
		}
		
		//回调任务不需要请求激活接口
		if(info.getExternalTaskType().equals("1")) {
			obj.element("code", 0);
			obj.element("msg", "request success");
			super.writeJsonDataApp(response, obj);
			return;
		}
		
		//对接云聚
		if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("happyzhuan")) {
			AppCommonModel appmodel = YunJu.activate(info.getCpchannelActive(),info.getChannelAdverAdid(),keyword, idfa, ip, sysver, model);
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("result", "ok");
				try {
					externalAppService.updateStatus(tExternalChannelTask, adid, key);
				} catch (Exception e) {
				}
			}else {
				obj.element("code", -1);
				obj.element("result", "active failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("qingwa")){
			
			//(String domain, String adid, String idfa, String ip, String keyword, 
			//String deviceType, String osVersion)
			AppCommonModel appmodel = SanhuFrogsChannel.activate(info.getCpchannelActive(),info.getChannelAdverAdid(),idfa,ip,keyword, model,sysver, udid);
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("result", "ok");
				externalAppService.updateStatus(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("result", "active failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("youbang")) {
			AppCommonModel appmodel = YouZhuanChannel.activate(info.getCpchannelActive(),info.getChannelAdverAdid(),keyword, idfa, ip, sysver, model, udid);
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("result", "ok");
				externalAppService.updateStatus(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("result", "active failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("fenqianba")) {
			AppCommonModel appmodel = FenQianChannel.activate(info.getCpchannelActive(),info.getChannelAdverAdid(), idfa,keyword,sysver, model);
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("result", "ok");
				externalAppService.updateStatus(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("result", "active failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("zhangshanghudong")) {
			//不存在激活接口
			AppCommonModel appmodel = ZhangShangHuDong.activate(info.getCpchannelActive(), info.getChannelAdverAdid(), idfa, ip, keyword, sysver, model, taskInfo.getUdid());
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("result", "ok");
				externalAppService.updateStatus(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("result", appmodel.getMsg());
			}
			
			super.writeJsonDataApp(response, obj);
			return;
		}else if(info.getCpChannelKey() != null && info.getCpChannelKey().equalsIgnoreCase("mifeng")) {
			//exterActivate(String domain, String adid,String idfa, String ip, 
					//String deviceType, String osVersion,String keyword, String udid) 
			//TExternalChannelTask taskInfo = externalAppService.geTExternalTaskInfo(tExternalChannelTask, adid, key);
			
			AppCommonModel appmodel = BeeChannel.exterActivate(info.getCpchannelActive(),info.getChannelAdverAdid(), idfa,ip, model,sysver,keyword,taskInfo.getUdid());
			if(appmodel.getResult() == 1) {
				obj.element("code", 0);
				obj.element("result", "ok");
				externalAppService.updateStatus(tExternalChannelTask, adid, key);
			}else {
				obj.element("code", -1);
				obj.element("result", "active failed");
			}
			
			super.writeJsonDataApp(response, obj);
			return;

		}
		
		try 
		{
			if(taskInfo.getStatus().equals("3"))
			{
				obj.element("code", 0);
				obj.element("result", "ok");
				super.writeJsonDataApp(response, obj);
				return;
			}
			
			if(taskInfo.getStatus().equals("2")) 
			{
				int restult = externalAppService.updateStatus(tExternalChannelTask, adid, key);
				if(restult > 0) 
				{
					obj.element("code", 0);
					obj.element("result", "ok");
					super.writeJsonDataApp(response, obj);
					return;
				}
				else
				{
					obj.element("code", -1);
					obj.element("result", "数据更新错误！");
					super.writeJsonDataApp(response, obj);
				}
			}
			else
			{
				obj.element("code", -1);
				obj.element("result", "未存在已被激活的task！");
				super.writeJsonDataApp(response, obj);
			}
		}
		catch (Exception e) 
		{
			obj.element("code", -1);
			obj.element("result", "系统错误");
			super.writeJsonDataApp(response, obj);
		}
	}
}
