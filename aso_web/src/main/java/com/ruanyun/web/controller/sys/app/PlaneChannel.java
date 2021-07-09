package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class PlaneChannel extends BaseChannel 
{	
	private static final Log log = LogFactory.getLog(PlaneChannel.class);
	private static final String channel = "AA5152D8083F720D0C396DC9F5C08D55F3495680E816A900F68FEEAEF419D55B";
	//排重+点击+云聚
	public static AppCommonModel isPlaneCHannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum, String  phoneVersion, String  phoneModel, String udid) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa,phoneVersion,phoneModel,adverInfo.getAdverName(),ip, udid);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, phoneVersion, phoneModel,adverInfo.getAdverName(),udid);
		}
		
		return model;
	}

	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String sysver, String phonemodel,
			String adverName, String ip,String udid) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		phonemodel = "iPhone" + phonemodel.substring(6);
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adid)
				.append("&channel=").append(channel)
				.append("&os=").append(sysver)
				.append("&device=").append(phonemodel)
				.append("&ip=").append(ip)
				.append("&mac=").append("0")
				.append("&udid=").append(udid)
				.append("&word=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&idfa=").append(idfa);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("response：null！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("code");
			Integer installed = (Integer)jsonObject.get("installed");
		    if(status != null && status == 0 && installed == 0)
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else if(installed == 1)
			{
				model.setResult(-1);
				model.setMsg("已重复");
			}
			else 
			{
				String msg = (String)jsonObject.get("message");
				model.setResult(-1);
				model.setMsg(msg);
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
		
		phonemodel = "iPhone" + phonemodel.substring(6);
		StringBuilder url = new StringBuilder(domain)
				.append("?channel=").append(channel)
				.append("&idfa=").append(idfa)
				.append("&os=").append(sysver)
				.append("&device=").append(phonemodel)
				.append("&ip=").append(ip)
				.append("&mac=").append("0")
				.append("&word=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&appid=").append(adid)
				.append("&udid=").append(udid)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(adid.equals("c0b2ed6f73a93c2e5013625d3fe7997a")) {
			model.setResult(1);
			model.setMsg("领取任务成功！");
			return model;
		}
		
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("code");
			if(code!= null && code == 0)
			{
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}
			else
			{
				String msg = (String)jsonObject.get("message");
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
	
	
	public static AppCommonModel externalDianJi(String domain, String adid,String externaladid, String idfa, String ip,
			 String sysver, String phonemodel,String adverName, String key,String udid) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adid)
				.append("&channel=").append(channel)
				.append("&os=").append(sysver)
				.append("&device=").append(phonemodel)
				.append("&ip=").append(ip)
				.append("&mac=").append("0")
				.append("&udid=").append(udid)
				.append("&word=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&idfa=").append(idfa)
				.append("&callback=").append(externalCallbackUrl(externaladid, idfa,key));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("code");
			if(code != null && code == 0)
			{
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}
			else
			{
				String msg = (String)jsonObject.get("message");
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
	/**
	 * 激活上报
	 */
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip,
			String sysver, String phonemodel, String udid) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		phonemodel = "iPhone" + phonemodel.substring(6);
		StringBuilder url;
		try {
			url = new StringBuilder(domain)
					.append("?channel=").append(channel)
					.append("&udid=").append(udid)
					.append("&idfa=").append(idfa)
					.append("&os=").append(sysver)
					.append("&device=").append(phonemodel)
					.append("&mac=").append("0")
					.append("&ip=").append(ip)
					.append("&word=").append(URLEncoder.encode(adverName, "utf-8"))
					.append("&appid=").append(adid);
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
			model.setResult(-1);
			model.setMsg("未完成。原因：系统出错！");
			return model;
		}
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("未完成。原因：调用第三方平台出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("code");
			
			if(code!=null && code == 0)
			{
				model.setResult(1);
				model.setMsg(code + "：已完成！");
			}
			else
			{
				String msg = (String)jsonObject.get("message");
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}

}
