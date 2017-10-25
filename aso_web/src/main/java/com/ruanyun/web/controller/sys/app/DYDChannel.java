package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.ruanyun.web.model.AppCommonModel;

public class DYDChannel extends BaseChannel
{
	private static final String CLICK_ID = "qisu";
	private static final Log log = LogFactory.getLog(DYDChannel.class);
	
	public static AppCommonModel clickDYD(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum) throws UnsupportedEncodingException 
	{
		
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		StringBuilder url = new StringBuilder(domain)
				.append("?clickid=").append(CLICK_ID)
				.append("&idfa=").append(idfa)
				.append("&ip=").append(ip)
				.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		
		int code = httpGet(url.toString());
		
		if(code == 200)
		{
			log.error("request url：" + url + "。response：" + code);
			model.setResult(1);
			model.setMsg("通过渠道验证！");
		}
		else
		{
			log.error("request url：" + url + "。response：" + code);
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：点击渠道未通过！");
		}
		
		return model;
	}
}
