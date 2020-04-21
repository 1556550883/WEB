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

public class JYSWChannel extends BaseChannel 
{
	static Log log = LogFactory.getLog(JYSWChannel.class);
	//我们的渠道号
	static String source = "zssw";
	static String source_key = "86469047216";
	
	//排重+点击+云聚
	public static AppCommonModel isJYSWChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum, String  phoneVersion, String  phoneModel, String udid) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa,ip,adverInfo.getAdverName(),phoneModel,phoneVersion, udid);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, phoneModel, phoneVersion,adverInfo.getAdverName(),udid);
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
	
	
	public static void main(String[] args) {
		AppCommonModel model;
		try {
			model = paiChong("http://www.android-banks.com:9100/open/task/identity", "200096",
					"2F14673B-CDAD-4FC3-9D37-3C5C612F87C7","183.166.145.192","翻墙vpn","iPhone9,3","13.2.3","fa9d256d5b5ca012088f92aabd2be290513908e8");
		
			System.out.print(model.getMsg());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 排重
	 * @throws UnsupportedEncodingException 
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String ip, String keyword, 
			String deviceType, String osVersion, String udid) throws UnsupportedEncodingException
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
				.append("&udid=").append(udid)
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
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String deviceType, String osVersion, String keyword,String udid) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		long timestamp = getTimestamp();
		String sign = source + "|" + adid + "|" + idfa + "|" + source_key + "|" + timestamp;
		sign = MD5.MD5Encode(sign);
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&udid=").append(udid)
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
	public static AppCommonModel activate(String domain, String adid, String idfa, String ip, String keyword, 
			String deviceType, String osVersion,String udid)
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
				.append("&udid=").append(udid)
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
