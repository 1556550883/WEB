package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.util.MD5;

import net.sf.json.JSONObject;

public class FrogsTTChannel  extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(FrogsTChannel.class);
	
	//我们的渠道号
	private static final String source = "zuanshi";
	private static final String source_key = "345fcb48f33e6a7dcf5ab8b64c4886b6";
	
	//TT frogs - 15
	public static AppCommonModel isFrogsTTChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
				String adverId, String userNum, String adverName, String deviceType, String osVersion) throws NumberFormatException, UnsupportedEncodingException 
		{
			//调用第三方排重接口 
			AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa, ip, adverName,deviceType, osVersion);
			if(model.getResult() != -1)
			{
				//调用第三方点击接口
				model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId),
						userNum, deviceType, osVersion, adverName);
			}
			
			return model;
		}
	
	private static String getFormatedDateString()
	 {  
		 String _timeZone = "GMT+8:00";
	     TimeZone timeZone = null;  
	     timeZone = TimeZone.getTimeZone(_timeZone);  
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     sdf.setTimeZone(timeZone);  
	     return sdf.format(new Date());  
	}  
	 
	private static long getTimestamp() 
	{
		String stridddg = getFormatedDateString();
		Date d = null;
		try 
		{
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stridddg);
		} 
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		long t1 = d.getTime();
		return t1 = t1/1000;
	}
	
	/**
	 * 排重
	 * @throws UnsupportedEncodingException 
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String ip, String keyword, 
			String deviceType, String osVersion) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		long timestamp = getTimestamp();
		String sign = source + "|" + adid + "|" + idfa + "|" + source_key + "|" + timestamp;
		sign = MD5.MD5Encode(sign);
		//调用第三方排重接口
		if(domain == null || domain == "") 
		{
			model.setResult(1);
			model.setMsg("未重复，可以领取任务！");
			return model;
		}
		
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&ip=").append(ip)
				.append("&idfa=").append(idfa)
				.append("&keyword=").append(keyword)
				.append("&device_type=").append(deviceType)
				.append("&os_version=").append(osVersion)
				.append("&source=").append(source)
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
			Integer status = (Integer)jsonObject.get("code");
			if(status == 0)
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
					model.setMsg("重复任务！-1");
				}
			}
			else if(status == 1)
			{
				String msg = (String)jsonObject.get("msg");
				model.setResult(-1);
				model.setMsg(msg);
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
			Integer userAppId, Integer adverId, String userNum, String deviceType, String osVersion, String keyword) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		long timestamp = getTimestamp();
		String sign = source + "|" + adid + "|" + idfa + "|" + source_key + "|" + timestamp;
		sign = MD5.MD5Encode(sign);
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&keyword=").append(keyword)
				.append("&device_type=").append(deviceType)
				.append("&os_version=").append(osVersion)
				.append("&source=").append(source)
				.append("&timestamp=").append(timestamp)
				.append("&sign=").append(sign)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		
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
			if(status == 0)
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
					model.setMsg("领取任务失败！-1");
				}
			}
			else if(status == 1)
			{
				String msg = (String)jsonObject.get("msg");
				model.setResult(-1);
				model.setMsg(msg);
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
	public static AppCommonModel activate(String domain, String adid, String idfa, String ip, String keyword, 
			String deviceType, String osVersion)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		long timestamp = getTimestamp();
		String sign = source + "|" + adid + "|" + idfa + "|" + source_key + "|" + timestamp;
		sign = MD5.MD5Encode(sign);
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&ip=").append(ip)
				.append("&idfa=").append(idfa)
				.append("&keyword=").append(keyword)
				.append("&device_type=").append(deviceType)
				.append("&os_version=").append(osVersion)
				.append("&source=").append(source)
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
			Integer status = (Integer)jsonObject.get("code");
			if(status == 0)
			{
				JSONObject resultObject = (JSONObject)jsonObject.get("result");
				Integer result = (Integer)resultObject.get(idfa);
				if(result == 1) 
				{
					model.setResult(1);
					model.setMsg("任务成功！");
				}
				else
				{
					model.setResult(-1);
					model.setMsg("任务失败-1！");
				}
			}
			else if(status == 1)
			{
				String msg = (String)jsonObject.get("msg");
				model.setResult(-1);
				model.setMsg(msg);
			}
			else
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错-2！");
			}
		}
		
		return model;
	}

}
