package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import net.sf.json.JSONObject;

public class MkChannel extends BaseChannel 
{
	static Log log = LogFactory.getLog(MkChannel.class);
	//我们的渠道号
	
	//排重+点击+云聚 
	public static AppCommonModel isMkChannel(TChannelAdverInfo adverInfo,String idfa, String ip,
			String userAppId,String userNum, String  phoneVersion, String  phoneModel, String udid) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo, idfa,ip,phoneModel,phoneVersion, udid);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = dianJi(adverInfo, idfa, ip, Integer.valueOf(userAppId), userNum, phoneModel, phoneVersion,udid);
		}
		
		return model;
	}
	
	/**
	 * 排重
	 * @throws UnsupportedEncodingException 
	 */
	public static AppCommonModel paiChong(TChannelAdverInfo adverInfo,  String idfa, String ip,
			String devemodel, String os_version, String udid) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//调用第三方排重接口
		String domain = adverInfo.getFlag2();
		if(domain == null || domain == "") 
		{
			model.setResult(-1);
			model.setMsg("未填写排重接口！");
			return model;
		}
		
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adverInfo.getAdverAdid())
				.append("&ip=").append(ip)
				.append("&idfa=").append(idfa)
				.append("&udid=").append(udid)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&model=").append(devemodel)
				.append("&os_version=").append(os_version);
		
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
				model.setMsg("success");				
			}
			else
			{
				model.setResult(-1);
				model.setMsg("fail");
			}
		}
		
		return model;
	}
	
	/**
	 * 
	 */
	public static AppCommonModel dianJi(TChannelAdverInfo adverInfo, String idfa, String ip,
			Integer userAppId, String userNum, String devemodel, String os_version, String udid) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		StringBuilder url = new StringBuilder(adverInfo.getFlag3())
				.append("?appid=").append(adverInfo.getAdverAdid())
				.append("&ip=").append(ip)
				.append("&idfa=").append(idfa)
				.append("&udid=").append(udid)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&model=").append(devemodel)
				.append("&os_version=").append(os_version)
				.append("&callback=").append(getCallbackUrl(adverInfo.getAdid(), idfa, userAppId, adverInfo.getAdverId(), userNum));
		
		JSONObject jsonObject = httpGet(url.toString(), false);
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("code");
			String msg = (String)jsonObject.get("msg");
			if(status == 0)
			{
				model.setResult(1);
				model.setMsg(msg);				
			}
			else
			{
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
	
	/**
	 * 激活上报
	 */
	public static AppCommonModel activate(TChannelAdverInfo adverInfo, String idfa, String ip, 
			String devemodel, String os_version,String udid)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(adverInfo.getFlag4())
				.append("?appid=").append(adverInfo.getAdverAdid())
				.append("&ip=").append(ip)
				.append("&idfa=").append(idfa)
				.append("&udid=").append(udid)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&model=").append(devemodel)
				.append("&os_version=").append(os_version);
		
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
			String msg = (String)jsonObject.get("msg");
			if(status == 0)
			{
				model.setResult(1);
				model.setMsg("success");				
			}
			else
			{
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
}
