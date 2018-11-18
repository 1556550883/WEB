package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;

import net.sf.json.JSONObject;

public class Huizhuan extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(Huizhuan.class);
	//我们的渠道号
	private static final String channel = "ynkalw";
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String appid, String idfa, String phoneModel, String phoneVersion, String keyword)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(appid)
				.append("&idfa=").append(idfa)
				.append("&keyword=").append(keyword)
				.append("&model=").append(phoneModel)
				.append("&sysver=").append(phoneVersion)
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
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("errno");
			if(status == null)
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
			else if(status == 0)
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else if(status == 1)
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：已领取过任务，不能重复领取！");
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
	public static AppCommonModel dianJi(String domain, String appid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String phoneModel, String phoneVersion, String keyword) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(appid)
				.append("&channel=").append(channel)
				.append("&idfa=").append(idfa)
				.append("&sysver=").append(phoneVersion)
				.append("&model=").append(phoneModel)
				.append("&keyword=").append(keyword)
				.append("&ip=").append(ip)
				.append("&callback=").append(getCallbackUrl(appid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("errno");
			if(code == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if(code == 0){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	/**
	 * 激活上报
	 */
	public static AppCommonModel activate(String domain, String appid, String adverName, String idfa, String ip, String phoneModel, String phoneVersion) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url;
		try
		{
			String keyword =  URLEncoder.encode(adverName, "utf-8");
			url = new StringBuilder(domain)
					.append("?appid=").append(appid)
					.append("&channel=").append(channel)
					.append("&idfa=").append(idfa)
					.append("&sysver=").append(phoneVersion)
					.append("&model=").append(phoneModel)
					.append("&keyword=").append(keyword)
					.append("&ip=").append(ip);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			model.setResult(-1);
			model.setMsg("未完成。原因：系统出错！");
			return model;
		}
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("未完成。原因：调用第三方平台出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("errno");
			if(code == null){
				model.setResult(-1);
				model.setMsg("渠道未返回状态，未完成！");
			}else if(code == 0){
				model.setResult(1);
				model.setMsg(code + "：已完成！");
			}else{
				model.setResult(-1);
				model.setMsg("渠道提示，未完成！");
			}
		}
		
		return model;
	}

}
