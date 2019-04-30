package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class LanChongChannel extends BaseChannel {
	
	private static final Log log = LogFactory.getLog(LanChongChannel.class);
	private static final String channelid = "12";
	private static final String platform = "happyzhuan";
		public static AppCommonModel isLanChongChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
				String adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
		{
			//调用第三方排重接口 
			AppCommonModel model = paiChong(adverInfo.getFlag2(), adverInfo.getAdverAdid(), adid, idfa, ip, phoneModel,phoneVersion,adverName);
			if(model.getResult() != -1)
			{
				//调用第三方点击接口
				model = dianJi(adverInfo.getFlag3(),adverInfo, idfa, phoneModel,phoneVersion, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId),
						userNum);
			}
			
			return model;
		}
		
		public static AppCommonModel paiChong(String domain, String appid, String adid, String idfa, String ip, String phoneModel,String phoneVersion,String adverName)
		{
			AppCommonModel model = new AppCommonModel(-1, "出错！");
			//调用第三方排重接口
			StringBuilder url = new StringBuilder(domain)
					.append("?appleid=").append(appid)
					.append("&idfa=").append(idfa)
					.append("&channelid=").append(channelid)
					.append("&taskid=").append(adid)
					.append("&devicemodel=").append(phoneModel)
					.append("&ip=").append(ip)
					.append("&searchkeyword=").append(adverName)
					.append("&osversion=").append(phoneVersion)
					.append("&platform=").append(platform);
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
				JSONObject result = (JSONObject) jsonObject.get("result");
				String status = (String)result.get("used");
				if(status == null)
				{
					model.setResult(-1);
					model.setMsg("领取任务失败。原因：系统出错！");
				}
				else if(status.equals("0"))
				{
					model.setResult(1);
					model.setMsg("未重复，可以领取任务！");
				}
				else if(status.equals("1"))
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
					.append("?appleid=").append(adverInfo.getAdverAdid())
					.append("&idfa=").append(idfa)
					.append("&channelid=").append(channelid)
					.append("&taskid=").append(adverInfo.getAdid())
					.append("&devicemodel=").append(phoneModel)
					.append("&ip=").append(ip)
					.append("&searchkeyword=").append(adverInfo.getAdverName())
					.append("&osversion=").append(phoneVersion)
					.append("&platform=").append(platform)
					.append("&callback=").append(getCallbackUrl(adverInfo.getAdid(), idfa, userAppId, adverId, userNum));
			JSONObject jsonObject = httpGet(url.toString(), false);
			
			if(jsonObject == null){
				log.error("request url：" + url + "。response：null");
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}else{
				log.error("request url：" + url + "。response：" + jsonObject.toString());
				JSONObject result = (JSONObject) jsonObject.get("result");
				String status = (String)result.get("click");
				if(status.equals("1")){
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
					.append("?appleid=").append(adverInfo.getAdverAdid())
					.append("&idfa=").append(idfa)
					.append("&channelid=").append(channelid)
					.append("&taskid=").append(adverInfo.getAdid())
					.append("&devicemodel=").append(deviceType)
					.append("&ip=").append(ip)
					.append("&searchkeyword=").append(adverInfo.getAdverName())
					.append("&osversion=").append(osVersion)
					.append("&platform=").append(platform);
			JSONObject jsonObject = httpGet(url.toString(), false);
			
			if(jsonObject == null){
				log.error("request url：" + url + "。response：null");
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}else{
				log.error("request url：" + url + "。response：" + jsonObject.toString());
				JSONObject result = (JSONObject) jsonObject.get("result");
				String status = (String)result.get("active");
				if(status.equals("1")){
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
