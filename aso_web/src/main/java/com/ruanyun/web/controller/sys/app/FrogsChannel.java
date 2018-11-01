package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;

import net.sf.json.JSONObject;

public class FrogsChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(FrogsChannel.class);
	
	//我们的渠道号
	private static final String channel = "frog";
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?appid=").append(adid)
				.append("&channel=").append(channel)
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
			String statusStr = (String)jsonObject.get(idfa);
			Integer status = Integer.parseInt(statusStr);
			if(status == 0)
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
	
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum) throws UnsupportedEncodingException 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(88)
				.append("&promoter=").append(channel)
				.append("&idfa=").append(idfa)
				.append("&rtjson=1")
				.append("&ip=").append(ip)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			boolean code = false;
			code = (Boolean)jsonObject.get("success");
			if(code)
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
	
	/**
	 * 激活上报
	 */
	public static AppCommonModel activate() 
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		model.setResult(1);
		model.setMsg("完成,等待回调！");
		return model;
	}
}
