package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.util.MD5;

import net.sf.json.JSONObject;

public class AiPuYouChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(AiPuYouChannel.class);
	private static final String key = "8086e6975b91962d514435167b2184c5";
	
	public static AppCommonModel paiChong(String domain, String adid, String idfa)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");

		if(domain == null || domain.isEmpty()) 
		{
			model.setResult(1);
			model.setMsg("未重复，可以领取任务！");
			return model;
		}
		
		String[] adids = adid.split("-");
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adids[0])
				.append("&idfa=").append(idfa)
				.append("&adid=").append(adids[1]);
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
			String status = (String)jsonObject.get(idfa);
			if(status == null)
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
			else if(status.equals("1"))
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else if(status.equals("0"))
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：已领取过任务，不能重复领取！");
			}
			else
			{
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}
		}
		
		return model;
	}
	
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String adverName, TUserApp userApp) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");

		if(domain == null || domain.isEmpty()) 
		{
			model.setResult(1);
			model.setMsg("不需要点击接口！");
			return model;
		}
		
		String[] adids = adid.split("-");
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
		t1 = t1/1000;
		String sign = t1 + key;
		sign = MD5.MD5Encode(sign);
		
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adids[0])
				.append("&idfa=").append(idfa)
				.append("&adid=").append(adids[1])
				.append("&ip=").append(ip)
				.append("&isbreak=").append(0)
				.append("&sign=").append(sign)
				.append("&timestamp=").append(t1)
				.append("&device=").append(userApp.getFlag3())
				.append("&os=").append(userApp.getFlag4())
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			String code = (String)jsonObject.get("code");
			if(code == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if(code.equals("0")){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		if(domain == null || domain.isEmpty()) 
		{
			model.setResult(1);
			model.setMsg("0：已完成！");
			return model;
		}
		
		String[] adids = adid.split("-");
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
		t1 = t1/1000;
		String sign = t1 + key;
		sign = MD5.MD5Encode(sign);
		
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adids[0])
				.append("&idfa=").append(idfa)
				.append("&adid=").append(adids[1])
				.append("&sign=").append(sign)
				.append("&timestamp=").append(t1);

		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("未完成。原因：调用第三方平台出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			String code = (String)jsonObject.get("code");
			
			if(code == null){
				model.setResult(-1);
				model.setMsg("渠道未返回状态，未完成！");
			}else if(code.equals("0")){
				model.setResult(1);
				model.setMsg(code + "：已完成！");
			}else{
				model.setResult(-1);
				model.setMsg("渠道提示，未完成！");
			}
		}
		
		return model;
	}
	
	 public static String getFormatedDateString()
	 {  
		 String _timeZone = "GMT+8:00";
	     TimeZone timeZone = null;  
	     timeZone = TimeZone.getTimeZone(_timeZone);  
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     sdf.setTimeZone(timeZone);  
	     return sdf.format(new Date());  
	}  
}
