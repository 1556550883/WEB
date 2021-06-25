package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class NBAchannelController extends BaseChannel 
{

private static final Log log = LogFactory.getLog(NBAchannelController.class);
	
	//我们的渠道号
	private static final String api_token = "42ed9ee0eaecd86a5f9aad1debf7ba37a1f47bcd";
	//排重+点击+云聚
	public static AppCommonModel isNBAchannelController(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
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
				.append("&api_token=").append(api_token)
				.append("&os_ver=").append(sysver)
				.append("&model=").append(phonemodel)
				.append("&ip=").append(ip)
				//.append("&udid=").append(udid)
				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
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
			Integer status = (Integer)jsonObject.get("err_code");
		    if(status != null && status == 0)
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else
			{
				String msg = (String)jsonObject.get("err_msg");
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
				.append("?api_token=").append(api_token)
				.append("&idfa=").append(idfa)
				.append("&os_ver=").append(sysver)
				.append("&model=").append(phonemodel)
				.append("&ip=").append(ip)
				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&appid=").append(adid)
				//.append("&udid=").append(udid)
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
			Integer code = (Integer)jsonObject.get("err_code");
			if(code == null) {code = -1;}
			if(code == 0)
			{
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}
			else
			{
				String msg = (String)jsonObject.get("err_msg");
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
	
	
	public static AppCommonModel externalDianJi(String domain, String adid,String externaladid, String idfa, String ip,
			 String sysver, String phonemodel,String adverName, String key) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adid)
				.append("&api_token=").append(api_token)
				.append("&os_ver=").append(sysver)
				.append("&model=").append(phonemodel)
				.append("&ip=").append(ip)
				//.append("&udid=").append(udid)
				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&idfa=").append(idfa)
				.append("&callback=").append(externalCallbackUrl(externaladid, idfa,key));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("err_code");
			if(code == null) {code = -1;}
			if(code == 0)
			{
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}
			else
			{
				String msg = (String)jsonObject.get("err_msg");
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
					.append("?api_token=").append(api_token)
					//.append("&udid=").append(udid)
					.append("&idfa=").append(idfa)
					.append("&os_ver=").append(sysver)
					.append("&model=").append(phonemodel)
					.append("&ip=").append(ip)
					.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
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
			Integer code = (Integer)jsonObject.get("err_code");
			if(code == null) 
			{
				model.setResult(-1);
				model.setMsg("渠道未返回状态，未完成！");
			}
			if(code == 0)
			{
				model.setResult(1);
				model.setMsg(code + "：已完成！");
			}
			else
			{
				String msg = (String)jsonObject.get("err_msg");
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
}
