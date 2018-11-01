package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;

import net.sf.json.JSONObject;

public class FrogsTChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(FrogsTChannel.class);
	
	//我们的渠道号
	private static final String channel = "04fbf67352dfa19a34c4e28952a4c824";
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String ip)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&channel=").append(channel)
				.append("&ip=").append(ip)
				.append("&idfa=").append(idfa);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("code");
			if(status == 0)
			{
				JSONObject resultObject = (JSONObject)jsonObject.get("data");
				Integer result = (Integer)resultObject.get(idfa);
				if(result == 0) 
				{
					model.setResult(1);
					model.setMsg("未重复，可以领取任务！");
				}
				else
				{
					model.setResult(-1);
					model.setMsg("重复任务！");
				}
			}
			else if(status == 1)
			{
				String msg = (String)jsonObject.get("msg");
				model.setResult(-1);
				model.setMsg(msg);
			}
			else
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
		}
		
		return model;
	}
	
	/**
	 * 点击
	 */
	public static AppCommonModel activate(String domain, String adid, String idfa, String ip) throws UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&channel=").append(channel);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}
		else
		{
			Integer status = (Integer)jsonObject.get("code");
			if(status == 0)
			{
				JSONObject resultObject = (JSONObject)jsonObject.get("data");
				Integer result = (Integer)resultObject.get("status");
				if(result == 1) 
				{
					model.setResult(1);
					model.setMsg("任务完成！");
				}
				else
				{
					model.setResult(-1);
					model.setMsg("任务上报失败！");
				}
			}
			else if(status == 1)
			{
				String msg = (String)jsonObject.get("msg");
				model.setResult(-1);
				model.setMsg(msg);
			}
			else
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
		}
		
		return model;
	}
}
