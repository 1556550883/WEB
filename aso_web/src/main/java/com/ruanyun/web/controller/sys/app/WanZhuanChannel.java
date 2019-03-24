package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class WanZhuanChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(WanZhuanChannel.class);
	private static final String channelid = "8086e6975b91962d514435167b2184c5";
	
	//排重+点击
	public static AppCommonModel isWanZhuanChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum, String  phoneVersion, String  phoneModel) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa,phoneVersion,phoneModel,adverInfo.getAdverName(),ip);
		
		return model;
	}
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String sysver, String phonemodel,String adverName, String ip)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?channelid=").append(channelid)
				.append("&appid=").append(adid)
				.append("&keyword=").append(adverName)
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
			Integer status = (Integer)jsonObject.get("success");
			if(status == null)
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
			else if(status == 1)
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else if(status == 0)
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
	public static AppCommonModel activate(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String sysver, String phonemodel,String adverName) throws UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?channelid=").append(channelid)
				.append("&idfa=").append(idfa)
				.append("&appid=").append(adid)
				.append("&keyword=").append(adverName)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("success");
			if(code == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if(code == 1){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
}