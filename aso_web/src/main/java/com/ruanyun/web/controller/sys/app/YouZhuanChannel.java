package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.util.MD5;

import net.sf.json.JSONObject;

public class YouZhuanChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(YouZhuanChannel.class);
	
	//我们的渠道号
	private static final String channel = "10307";
	
	

	//排重+点击+云聚
	public static AppCommonModel isYouZhuanChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum, String  phoneVersion, String  phoneModel, String udid) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa,phoneVersion,phoneModel,adverInfo.getAdverName(),ip,udid);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, phoneVersion, phoneModel,adverInfo.getAdverName(), udid);
		}
		
		return model;
	}
	
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String sysver, String phonemodel,String adverName, String ip, String udid)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&type=").append("CheckIDFA")
				.append("&channel=").append(channel)
				.append("&idfa=").append(idfa)
				.append("&udid=").append(udid);
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
	
	
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String sysver, String phonemodel,String adverName, String udid) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//udid = MD5.MD5Encode(udid.toLowerCase());
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&type=").append("ClickSync")
				.append("&channel=").append(channel)
				.append("&idfa=").append(idfa)
				.append("&udid=").append(udid)
				.append("&os=").append(sysver)
				.append("&device=").append(phonemodel)
				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&ip=").append(ip)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("resultCode");
			if(code == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if(code == 1000){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("错误码：" +  code);
			}
		}
		
		return model;
	}
	
	
	public static AppCommonModel externalDianJi(String domain, String adid,String externaladid, String idfa, String ip,
			 String sysver, String phonemodel,String adverName, String key, String udid) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		
		//udid = MD5.MD5Encode(udid.toLowerCase());
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&type=").append("ClickSync")
				.append("&channel=").append(channel)
				.append("&idfa=").append(idfa)
				.append("&os=").append(sysver)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&device=").append(phonemodel)
				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&callback=").append(externalCallbackUrl(externaladid, idfa,key));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("resultCode");
			if(code == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if(code == 1000){
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
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip, String sysver, String phonemodel,String udid) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url;
		try {
			url = new StringBuilder(domain)
					.append("?adid=").append(adid)
					.append("&type=").append("Activation")
					.append("&channel=").append(channel)
					.append("&idfa=").append(idfa)
					.append("&os=").append(sysver)
					.append("&device=").append(phonemodel)
					.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
					.append("&udid=").append(udid)
					.append("&ip=").append(ip);
		} catch (UnsupportedEncodingException e) {
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
			Integer code = (Integer)jsonObject.get("resultCode");
			if(code == null){
				model.setResult(-1);
				model.setMsg("渠道未返回状态，未完成！");
			}else if(code == 1000){
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
