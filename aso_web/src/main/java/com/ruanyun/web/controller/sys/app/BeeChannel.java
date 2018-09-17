package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.util.MD5;
import net.sf.json.JSONObject;

public class BeeChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(BeeChannel.class);
	private static final String ChannelSource = "zszs";
	private static final String ChannelKey = "ua7227oetq6rr5p1pa4wlew4bduuvlae";
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String ip)
	{
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adid +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);

		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign);
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
	
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String adverName, String phoneModel, String phoneVersion) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adid +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);
		String keyword =  URLEncoder.encode(adverName, "utf-8");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&keyword=").append(keyword)
				.append("&os_version=").append(phoneVersion)
				.append("&device_type=").append(phoneModel)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign);
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
				if(result == 1) 
				{
					model.setResult(1);
					model.setMsg("领取任务成功！");
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
	
	/**
	 * 激活上报
	 */
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adid +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);

		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign);
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
				if(result == 1) 
				{
					model.setResult(1);
					model.setMsg("任务完成！");
				}
				else
				{
					model.setResult(-1);
					model.setMsg("渠道返回任务失败！");
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
}
