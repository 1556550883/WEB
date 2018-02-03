package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ruanyun.web.model.AppCommonModel;
import net.sf.json.JSONObject;

public class JvZhangChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(JvZhangChannel.class);
	private static  String mac = "02:00:00:00:00:00";
	private static  String sourceid = "11029";
	
	public static AppCommonModel paiChong(String domain, String adid, String idfa) 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&sourceid=").append(sourceid)
				.append("&idfa=").append(idfa)
				.append("&mac=").append(mac);
		
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
				model.setMsg("领取任务失败。原因：系统出错！");
			}
			else if(status == 0)
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else if(status == 1)
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
			Integer userAppId, Integer adverId, String userNum, String adverName) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&sourceid=").append(sourceid)
				.append("&idfa=").append(idfa)
				.append("&keywords=").append(adverName)
				.append("&ip=").append(ip)
				.append("&mac=").append(mac)
				.append("&callbackurl=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
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
			Integer code = (Integer)jsonObject.get("State");
			if(code == null)
			{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
			else if(code == 100)
			{
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}
			else
			{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
					.append("?adid=").append(adid)
					.append("&sourceid=").append(sourceid)
					.append("&idfa=").append(idfa)
					.append("&ip=").append(ip)
					.append("&mac=").append(mac)
					.append("&keywords=").append(adverName);

		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("未完成。原因：调用第三方平台出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("State");
			
			if(code == null)
			{
				model.setResult(-1);
				model.setMsg("渠道未返回状态，未完成！");
			}
			else if(code == 100)
			{
				model.setResult(1);
				model.setMsg(code + "：已完成！");
			}
			else
			{
				String msg = (String)jsonObject.get("Remark"); 
				model.setResult(-1);
				model.setMsg(msg);
			}
		}
		
		return model;
	}
}

