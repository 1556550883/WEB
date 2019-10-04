package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.util.MD5;

import net.sf.json.JSONObject;

public class BeeChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(BeeChannel.class);
	private static final String ChannelSource = "zszs";
	private static final String ChannelKey = "ua7227oetq6rr5p1pa4wlew4bduuvlae";
	
	

	public static AppCommonModel isBeeChannel(TChannelAdverInfo adverInfo, String idfa, String ip, String userAppId,
			String adverId, String userNum, String phoneModel, String phoneVersion, String udid) throws NumberFormatException, UnsupportedEncodingException 
	{
		//会赚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo, idfa, ip,phoneModel, phoneVersion, udid);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = dianJi(adverInfo, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum,phoneModel, phoneVersion, udid);
		}
		
		return model;
	}
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(TChannelAdverInfo adverInfo, String idfa, String ip, 
			String deviceType, String osVersion, String udid) 
	{
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adverInfo.getAdid() +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);

		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(adverInfo.getFlag2())
				.append("?adid=").append(adverInfo.getAdid())
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&device_type=").append(deviceType)
				.append("&os_version=").append(osVersion)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign);
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
	 * 排重
	 */
	public static AppCommonModel exterPaiChong(String domain,String adid,String keyword,  String idfa, String ip, 
			String deviceType, String osVersion, String udid) 
	{
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+ adid+"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);

		AppCommonModel model = new AppCommonModel(-1, "出错！");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(keyword)
				.append("&device_type=").append(deviceType)
				.append("&os_version=").append(osVersion)
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
	public static AppCommonModel dianJi(TChannelAdverInfo adverInfo, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String phoneModel, String phoneVersion,String udid) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adverInfo.getAdid() +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);
		//String keyword =  URLEncoder.encode(adverName, "utf-8");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(adverInfo.getFlag3())
				.append("?adid=").append(adverInfo.getAdid())
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&os_version=").append(phoneVersion)
				.append("&device_type=").append(phoneModel)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign)
				.append("&callback=").append(getCallbackUrl(adverInfo.getAdid(), idfa, userAppId, adverId, userNum));
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
	
	public static AppCommonModel externalDianJi(String domain,String adid,String externaladid, String idfa, String ip,
			 String sysver, String phonemodel,String adverName, String key, String udid) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adid +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);
		//String keyword =  URLEncoder.encode(adverName, "utf-8");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(adverName)
				.append("&os_version=").append(sysver)
				.append("&device_type=").append(phonemodel)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign)
				.append("&callback=").append(externalCallbackUrl(externaladid, idfa,key));
	
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
	public static AppCommonModel activate(TChannelAdverInfo adverInfo, String idfa, String ip, 
			String deviceType, String osVersion, String udid) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		if(adverInfo.getFlag4() == null || adverInfo.getFlag4().isEmpty()) 
		{
			model.setResult(1);
			model.setMsg("任务完成！");
			return model;
		}
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adverInfo.getAdid() +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);

		StringBuilder url = new StringBuilder(adverInfo.getFlag4())
				.append("?adid=").append(adverInfo.getAdid())
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(adverInfo.getAdverName())
				.append("&device_type=").append(deviceType)
				.append("&os_version=").append(osVersion)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("原因：系统出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("err_code");
			if(status == null)
			{
				model.setResult(-1);
				model.setMsg("原因：系统出错！");
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
				model.setMsg("原因：系统出错！");
			}
		}
		
		return model;
	}
	
	
	/**
	 * 激活上报
	 */
	public static AppCommonModel exterActivate(String domain, String adid,String idfa, String ip, 
			String deviceType, String osVersion,String keyword, String udid) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		if(domain == null || domain.isEmpty()) 
		{
			model.setResult(1);
			model.setMsg("任务完成！");
			return model;
		}
		
		long timestamp = System.currentTimeMillis()/1000;
		String sign = ChannelSource+"|"+adid +"|" + idfa +"|"+ChannelKey + "|" + timestamp;
		sign = MD5.MD5Encode(sign);

		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(keyword)
				.append("&device_type=").append(deviceType)
				.append("&os_version=").append(osVersion)
				.append("&source=").append(ChannelSource)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null)
		{
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("原因：系统出错！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer status = (Integer)jsonObject.get("err_code");
			if(status == null)
			{
				model.setResult(-1);
				model.setMsg("原因：系统出错！");
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
				model.setMsg("原因：系统出错！");
			}
		}
		
		return model;
	}
}
