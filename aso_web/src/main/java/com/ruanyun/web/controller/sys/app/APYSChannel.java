package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.util.MD5;

import net.sf.json.JSONObject;

public class APYSChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(APYSChannel.class);
	private static final String ChannelSource = "zszs";
	private static final String ChannelKey = "ua7227oetq6rr5p1pa4wlew4bduuvlae";
	
	
	public static AppCommonModel isAPYSChannel(TChannelAdverInfo adverInfo, String adid, String idfa) 
			throws NumberFormatException, UnsupportedEncodingException 
	{
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa);
		
		return model;
	}
	
	public static AppCommonModel paiChong(String domain, String adid, String idfa)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adid)
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
			Integer status = (Integer)jsonObject.get("err_code");
			if(status == null)
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
			else if(status == 0)
			{
				JSONObject resultObject = (JSONObject)jsonObject.get("result");
				Integer result = (Integer)resultObject.get(idfa);
				if(result == 0) 
				{
					model.setResult(1);
					model.setMsg("未重复，可以领取任务！");
				}
				else
				{
					model.setResult(-1);
					model.setMsg("重复任务！");
				}
			}
			else
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
		}
		
		return model;
	}
	
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//source|appid|idfa|sourcekey
		String sign = ChannelSource+"|"+adid +"|" + idfa +"|"+ChannelKey;
		sign = MD5.MD5Encode(sign);
		
		StringBuilder url = new StringBuilder(domain)
					.append("?appid=").append(adid)
					.append("&source=").append(ChannelSource)
					.append("&idfa=").append(idfa)
					.append("&sign=").append(sign)
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
			Integer code = (Integer)jsonObject.get("err_code");
			String msg = (String)jsonObject.get("err_msg");
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
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
}
