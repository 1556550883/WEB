package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

/**
 * 利得基金
 * @author 向轴
 */
public class LiDeJiJin extends BaseChannel
{
	
	private static final Log log = LogFactory.getLog(LiDeJiJin.class);
	
	//我们的渠道号
	private static final String CH = "0d9183ef359ceba4";
	
	//mac
	private static final String MAC = "02:00:00:00:00:00";
	
	
	//利德基金渠道+排重+点击
	public static  AppCommonModel isLDJJChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
			String adverId, String userNum) 
			throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum);
		
		return model;
	}
	
	
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum) throws UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?app=").append(CH)
				.append("&ad=").append(adid)
				.append("&mac=").append(MAC)
				.append("&idfa=").append(idfa)
				.append("&clientip=").append(ip)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		
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
				model.setMsg("领取任务失败。原因：调用点击接口出错！");
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
				model.setMsg("领取任务失败。原因：调用点击接口出错！");
			}
		}
		
		return model;
	}

	public static AppCommonModel activate(String domain, String adid, String idfa, String ip) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		StringBuilder url =  new StringBuilder(domain)
		.append("?app=").append(CH)
		.append("&adid=").append(adid)
		.append("&idfa=").append(idfa)
		.append("&ip=").append(ip);
		
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
			String msg = (String) jsonObject.get("msg");
			if(code == null)
			{
				model.setResult(-1);
				model.setMsg("未完成！");
			}
			else if(code == 0)
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
}
