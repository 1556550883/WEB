package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

/**
 * 掌上互动
 * @author 向轴
 */
public class ZhangShangHuDong extends BaseChannel 
{
	//我们的渠道号
	private static final String APPID = "3235";
	private static final Log log = LogFactory.getLog(ZhangShangHuDong.class);
	
	public static AppCommonModel zhangshanghudong(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum, String  phoneVersion, String  phoneModel,String udid, String adverName) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa, ip, phoneVersion,phoneModel, udid,adverName );
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, adverName,phoneVersion,phoneModel, udid);
		}
		
		return model;
	}
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String ip,String  phoneVersion, String  phoneModel,String udid, String adverName) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//StringBuilder url = new StringBuilder("http://api.adzshd.com/RemoveEcho.ashx")
		//http://api.adzshd.com/SourceSearchISActivate.ashx?cmd=getuserisacctivate&adid=adid&idfa=idfa、
		StringBuilder url = new StringBuilder(domain)
				.append("&adid=").append(adid)
				.append("&appid=").append(APPID)
				.append("&idfa=").append(idfa)
				.append("&udid=").append(udid)
				.append("&KeyWords=").append(adverName)
				.append("&ip=").append(ip)
				.append("&os=").append(phoneModel)
				.append("&osversion=").append(phoneVersion);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：调用第三方排重接口出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			String status = (String)jsonObject.get(idfa);
			if(status == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if("0".equals(status)){
				model.setResult(1);
				model.setMsg("没有重复，可以领取任务！");
			}else if("1".equals(status)){
				model.setResult(-1);
				model.setMsg("抱歉重复任务，请选择其他任务！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum,String KeyWords, String  phoneVersion, String  phoneModel,String udid ) throws UnsupportedEncodingException{
		AppCommonModel model = new AppCommonModel(-1, "领取任务出错！");
		
		//StringBuilder url = new StringBuilder("http://api.adzshd.com/SourceClick.ashx")
		StringBuilder url = new StringBuilder(domain)
			.append("?adid=").append(adid)
			.append("&appid=").append(APPID)
			.append("&idfa=").append(idfa)
			.append("&udid=").append(udid)
			.append("&ip=").append(ip)
			.append("&os=").append(phoneModel)
			.append("&KeyWords=").append(KeyWords)
			.append("&osversion=").append(phoneVersion)
			.append("&mac=02:00:00:00:00:00")
			.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Boolean success = (Boolean)jsonObject.get("success");
			if(success == null){
				model.setResult(-1);
				model.setMsg("领取任务出错！");
			}else if(success){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	public static AppCommonModel externalDianJi(String domain, String adid,String externaladid, String idfa, String ip,
			 String sysver, String phonemodel,String adverName, String key, String udid) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&appid=").append(APPID)
				.append("&idfa=").append(idfa)
				.append("&osversion=").append(sysver)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&os=").append(phonemodel)
				.append("&keyword=").append(adverName)
				.append("&mac=02:00:00:00:00:00")
				.append("&callback=").append(externalCallbackUrl(externaladid, idfa,key));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Boolean success = (Boolean)jsonObject.get("success");
			if(success == null){
				model.setResult(-1);
				model.setMsg("领取任务出错！");
			}else if(success){
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
	public static AppCommonModel activate(String domain, String adid, String idfa, String ip,String KeyWords, String  phoneVersion, String  phoneModel,String udid ) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//StringBuilder url = new StringBuilder("http://api.adzshd.com/submit.ashx")
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&appid=").append(APPID)
				.append("&form=").append(1)
				.append("&idfa=").append(idfa)
				.append("&udid=").append(udid)
				.append("&ip=").append(ip)
				.append("&os=").append(phoneModel)
				.append("&KeyWords=").append(KeyWords)
				.append("&osversion=").append(phoneVersion)
				.append("&mac=02:00:00:00:00:00");
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("response：null！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			String status = (String)jsonObject.get("status");
			if("1".equals(status))
			{
				model.setResult(1);
				model.setMsg("任务已完成！");
			}
			else
			{
				String message = (String)jsonObject.get("message");
				model.setResult(-1);
				model.setMsg(message);
			}
		}
		
		return model;
	}
}
