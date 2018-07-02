package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.TExternalChannelTask;
import com.ruanyun.web.service.app.ExternalAppService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/interface")
public class ExternalAppController extends BaseController
{
	@Autowired
	private ExternalAppService externalAppService;
	
	@RequestMapping("happy_distinct")
	public void distinctInterface(HttpServletResponse response, HttpServletRequest request, String adid, String key, String idfa)
	{
		TExternalChannelTask tExternalChannelTask = new TExternalChannelTask();
		tExternalChannelTask.setIdfa(idfa);
		tExternalChannelTask.setStatus("1");
		JSONObject obj = new JSONObject();
		
		try 
		{
			TExternalChannelTask taskInfo = externalAppService.getExternalAdverInfo(tExternalChannelTask, adid, key);
			
			if(taskInfo != null && taskInfo.getIdfa() != null) 
			{
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

	@RequestMapping("happy_click")
	public void clickInterface(HttpServletResponse response, HttpServletRequest request, String adid, String key, String idfa, String keywords, String ip, String 
			callbackurl)
	{
		TExternalChannelTask tExternalChannelTask = new TExternalChannelTask();
		tExternalChannelTask.setIdfa(idfa);
		tExternalChannelTask.setStatus("2");
		tExternalChannelTask.setIp(ip);
		tExternalChannelTask.setKeywords(keywords);
		tExternalChannelTask.setCallback(callbackurl);
		JSONObject obj = new JSONObject();
		try 
		{
			TExternalChannelTask taskInfo = externalAppService.getExternalAdverInfo(tExternalChannelTask, adid, key);
			if(taskInfo == null) 
			{
				obj.element("code", -1);
				obj.element("result", "未知task!");
				super.writeJsonDataApp(response, obj);
				return;
			}
			
			if(taskInfo.getStatus().equals("2")) 
			{
				obj.element("code", 0);
				obj.element("result", "ok");
				super.writeJsonDataApp(response, obj);
				return;
			}
			
			if(taskInfo.getStatus().equals("1")) 
			{
				int result = externalAppService.update(tExternalChannelTask, adid, key);
				if(result > 0) 
				{
					obj.element("code", 0);
					obj.element("result", "ok");
					super.writeJsonDataApp(response, obj);
				}
				else
				{
					obj.element("code", -1);
					obj.element("result", "数据更新错误！");
					super.writeJsonDataApp(response, obj);
				}
			}
			else if(taskInfo.getStatus().equals("3"))
			{
				obj.element("code", -1);
				obj.element("result", "idfa已重复！");
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

	@RequestMapping("happy_active")
	public void activeInterface(HttpServletResponse response, HttpServletRequest request, String adid, String key, String idfa)
	{
		TExternalChannelTask tExternalChannelTask = new TExternalChannelTask();
		tExternalChannelTask.setIdfa(idfa);
		tExternalChannelTask.setStatus("3");
		JSONObject obj = new JSONObject();
		
		try 
		{
			TExternalChannelTask taskInfo = externalAppService.getExternalAdverInfo(tExternalChannelTask, adid, key);
			if(taskInfo == null) 
			{
				obj.element("code", -1);
				obj.element("result", "未知task！");
				super.writeJsonDataApp(response, obj);
				return;
			}
			
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
