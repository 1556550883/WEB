package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.util.MD5;

import net.sf.json.JSONObject;

public class MZDQChannel extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(MZDQChannel.class);
	private static final String ChannelNum = "48737";
	private static final String ChannelKey = "d4ed5ab834164b97665c158e0e81eedf";
	
	public static AppCommonModel paiChong(String domain, String adid, String idfa)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		String sign = adid+"|"+ChannelNum+"|"+ChannelKey;
		sign = MD5.MD5Encode(sign);
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&channel=").append(ChannelNum)
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
	
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String adverName) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		String sign = adid+"|"+ChannelNum+"|"+ChannelKey;
		sign = MD5.MD5Encode(sign);
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&channel=").append(ChannelNum)
				.append("&ip=").append(ip)
				.append("&keywords=").append(adverName)
				.append("&sign=").append(sign)
				.append("&callbackurl=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("code");
			if(code == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if(code == 0){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	/**
	 * 激活上报
	 */
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		if(domain == null || domain.isEmpty()) 
		{
			model.setResult(1);
			model.setMsg("0：已完成！");
			return model;
		}
		
		String sign = adid+"|"+ChannelNum+"|"+ChannelKey;
		sign = MD5.MD5Encode(sign);
		
		StringBuilder url = new StringBuilder(domain)
					.append("?adid=").append(adid)
					.append("&idfa=").append(idfa)
					.append("&channel=").append(ChannelNum)
					.append("&sign=").append(sign);

		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("未完成。原因：调用第三方平台出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("code");
			
			if(code == null){
				model.setResult(-1);
				model.setMsg("渠道未返回状态，未完成！");
			}else if(code == 0 || code == 1){
				JSONObject resultObject = (JSONObject)jsonObject.get("result");
				boolean result = (Boolean)resultObject.get(idfa);
				if(result) 
				{
					model.setResult(1);
					model.setMsg(code + "：已完成！");
				}
				else
				{
					model.setResult(-1);
					model.setMsg("渠道提示，未完成！");
				}
			}else{
				model.setResult(-1);
				model.setMsg("渠道提示，未完成！");
			}
		}
		
		return model;
	}
}
