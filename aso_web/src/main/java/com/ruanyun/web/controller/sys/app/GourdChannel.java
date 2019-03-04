package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class GourdChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(GourdChannel.class);
	private static final String channel = "rfqjdwl2019";
	
	public static AppCommonModel paiChong(String domain, String appid, String adid, String idfa)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(appid)
				.append("&idfa=").append(idfa)
				.append("&channel=").append(channel)
				.append("&adid=").append(adid);
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
	public static AppCommonModel dianJi(String domain, TChannelAdverInfo adverInfo, String idfa ,String phoneModel,String phoneVersion,
			String ip, Integer userAppId, Integer adverId, String userNum) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adverInfo.getAdid())
				.append("&appid=").append(adverInfo.getAdverAdid())
				.append("&idfa=").append(idfa)
				.append("&model=").append(phoneModel)
				.append("&version=").append(phoneVersion)
				.append("&ip=").append(ip)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&channel=").append(channel)
				.append("&callback=").append(getCallbackUrl(adverInfo.getAdid(), idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Boolean result = (Boolean)jsonObject.get("success");
			if(result){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	public static AppCommonModel activate(TChannelAdverInfo adverInfo, String idfa, String ip, 
			String deviceType, String osVersion)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		StringBuilder url = new StringBuilder(adverInfo.getFlag4())
				.append("?adid=").append(adverInfo.getAdid())
				.append("&appid=").append(adverInfo.getAdverAdid())
				.append("&idfa=").append(idfa)
				.append("&model=").append(deviceType)
				.append("&version=").append(osVersion)
				.append("&ip=").append(ip)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&channel=").append(channel);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Boolean result = (Boolean)jsonObject.get("success");
			if(result){
				model.setResult(1);
				model.setMsg("任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("任务失败！");
			}
		}
		
		return model;
	}
}
