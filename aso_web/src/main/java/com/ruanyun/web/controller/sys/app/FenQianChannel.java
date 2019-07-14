package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class FenQianChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(FenQianChannel.class);
	
	public static AppCommonModel isFenQianChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
	{
		//会赚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa);
		
		if(model.getResult() != -1)
		{
			model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, phoneVersion, phoneModel,adverInfo.getAdverName());
		}
		
		return model;
	}
	
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		long timestamp = System.currentTimeMillis();
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?task_id=").append(adid)
				.append("&timestamp=").append(timestamp)
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
			Integer status = (Integer)jsonObject.get(idfa);
			if(status == 0)
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else if(status == 1)
			{
				model.setResult(-1);
				model.setMsg("抱歉重复任务，请选择其他任务！");
			}
			else
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
		}
		
		return model;
	}
	
	
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String sysver, String phonemodel,String adverName) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		if(domain == null || domain.isEmpty()) {
			model.setResult(1);
			model.setMsg("success！");
			return model;
		}
		
		long timestamp = System.currentTimeMillis();
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?task_id=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&timestamp=").append(timestamp)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("原因：系统出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("code");
			String msg = (String)jsonObject.get("msg");
			if(status == 1)
			{
				model.setResult(1);
				model.setMsg(status + "：success！");
			}
			else if(status == 0)
			{
				model.setResult(-1);
				model.setMsg(msg);
			}
			else
			{
				model.setResult(-1);
				model.setMsg("原因：系统出错！");
			}
		}
		
		
		return model;
	}
	
	public static AppCommonModel activate(String domain, String adid, String idfa,String adverName,String sysver, String phonemodel) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?task_id=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&keywords=").append(adverName)
				.append("&devicemodel=").append(phonemodel)
				.append("&systemversion=").append(sysver);
		JSONObject jsonObject = httpGet(url.toString(), false);
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("原因：系统出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("code");
			String msg = (String)jsonObject.get("msg");
			if(status == 1)
			{
				model.setResult(1);
				model.setMsg(status + "：已完成！");
			}
			else if(status == 0)
			{
				model.setResult(-1);
				model.setMsg(msg);
			}
			else
			{
				model.setResult(-1);
				model.setMsg("原因：系统出错！");
			}
		}
		
		
		return model;
	}
	
}
