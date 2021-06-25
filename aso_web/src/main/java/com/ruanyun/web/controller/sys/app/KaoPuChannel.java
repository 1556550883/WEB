package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.util.MD5;

import net.sf.json.JSONObject;

public class KaoPuChannel extends BaseChannel 
{

private static final Log log = LogFactory.getLog(KaoPuChannel.class);
	
	//我们的渠道号
	private static final String custId  = "1905";
	private static final String key  = "jg2ZLSBbti3csWwr";
	
	//排重+点击+云聚
	public static AppCommonModel isKaoPuChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum, String  phoneVersion, String  phoneModel, String udid) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa,phoneVersion,phoneModel,adverInfo.getAdverName(),ip, udid, userAppId,adverId,userNum,adverInfo.getAdverAdid());
		
		return model;
	}
	
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String sysver, String phonemodel,
			String adverName, String ip,String udid, String userid,String adverid ,String usernum,String appadid) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = timestamp+ custId + appadid + key;
		sign = MD5.MD5Encode(sign);
		
		phonemodel = "iPhone" + phonemodel.substring(6);
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appId=").append(appadid)
				.append("&reqTime=").append(timestamp)
				.append("&custId=").append(custId)
				.append("&api=").append(adid)
				.append("&word=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&os=").append(sysver)
				.append("&sign=").append(sign)
				.append("&dev=").append(phonemodel)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&idfa=").append(idfa)
				.append("&callback=").append(getCallbackUrl(adid, idfa, Integer.valueOf(userid), Integer.valueOf(adverid), usernum));
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
		    if(status != null && status == 0)
			{
				model.setResult(1);
				model.setMsg("领取任务！");
			}
			else
			{
				String msg = (String)jsonObject.get("msg");
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
	
	/**
	 * 点击
	 */
//	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
//			Integer userAppId, Integer adverId, String userNum, String sysver, String phonemodel,String adverName, String udid) throws UnsupportedEncodingException {
//		AppCommonModel model = new AppCommonModel(-1, "出错！");
//		
//		phonemodel = "iPhone" + phonemodel.substring(6);
//		StringBuilder url = new StringBuilder(domain)
//				.append("?api_token=").append(api_token)
//				.append("&idfa=").append(idfa)
//				.append("&os_ver=").append(sysver)
//				.append("&model=").append(phonemodel)
//				.append("&ip=").append(ip)
//				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
//				.append("&appid=").append(adid)
//				.append("&udid=").append(udid)
//				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
//		JSONObject jsonObject = httpGet(url.toString(), false);
//		
//		if(adid.equals("c0b2ed6f73a93c2e5013625d3fe7997a")) {
//			model.setResult(1);
//			model.setMsg("领取任务成功！");
//			return model;
//		}
//		
//		if(jsonObject == null)
//		{
//			log.error("request url：" + url + "。response：null");
//			model.setResult(-1);
//			model.setMsg("领取任务失败。原因：系统出错！");
//		}
//		else
//		{
//			log.error("request url：" + url + "。response：" + jsonObject.toString());
//			Integer code = (Integer)jsonObject.get("err_code");
//			if(code == null) {code = -1;}
//			if(code == 0)
//			{
//				model.setResult(1);
//				model.setMsg("领取任务成功！");
//			}
//			else
//			{
//				String msg = (String)jsonObject.get("err_msg");
//				model.setResult(-1);
//				model.setMsg(msg);
//			}
//		}
//		
//		return model;
//	}
//	
	
	public static AppCommonModel externalDianJi(String domain, String adid,String externaladid, String idfa, String ip,
			 String sysver, String phonemodel,String adverName, String keys,String udid, String appadid) throws UnsupportedEncodingException {
		
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = timestamp+ custId + appadid + key;
		sign = MD5.MD5Encode(sign);
		
		phonemodel = "iPhone" + phonemodel.substring(6);
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appId=").append(appadid)
				.append("&reqTime=").append(timestamp)
				.append("&custId=").append(custId)
				.append("&api=").append(adid)
				.append("&word=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&os=").append(sysver)
				.append("&sign=").append(sign)
				.append("&dev=").append(phonemodel)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&idfa=").append(idfa)
				.append("&callback=").append(externalCallbackUrl(externaladid, idfa,keys));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("code");
			if(code == null) {code = -1;}
			if(code == 0)
			{
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}
			else
			{
				String msg = (String)jsonObject.get("msg");
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
			String sysver, String phonemodel, String udid,String appadid) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		phonemodel = "iPhone" + phonemodel.substring(6);
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = timestamp+ custId + appadid + key;
		sign = MD5.MD5Encode(sign);
		
		StringBuilder url;
		try {
			url = new StringBuilder(domain)
					.append("?reqTime=").append(timestamp)
					.append("&custId=").append(custId)
					.append("&appId=").append(appadid)
					.append("&api=").append(adid)
					.append("&sign=").append(sign)
					.append("&udid=").append(udid)
					.append("&idfa=").append(idfa)
					.append("&os=").append(sysver)
					.append("&dev=").append(phonemodel)
					.append("&ip=").append(ip)
					.append("&word=").append(URLEncoder.encode(adverName, "utf-8"));
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
			if(code == null) 
			{
				model.setResult(-1);
				model.setMsg("渠道未返回状态，未完成！");
			}
			else if(code == 0)
			{
				model.setResult(1);
				model.setMsg(code + "：已完成！");
			}
			else
			{
				String msg = (String)jsonObject.get("msg");
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
}
