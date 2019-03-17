package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class XinzhuanChannel  extends BaseChannel
{
		//我们的渠道号
		private static final String source = "HappyZhuan";
		private static final Log log = LogFactory.getLog(XinzhuanChannel.class);
		
		
		public static AppCommonModel isXinzhuanChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip, String userAppId,
				String adverId, String userNum, String adverName,String phoneModel,String phoneVersion) throws NumberFormatException, UnsupportedEncodingException 
		{
			AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa,phoneVersion,phoneModel,adverInfo.getAdverName(),ip);
			
			if(model.getResult() != -1 && adverInfo.getTaskType().equals("1"))
			{
				//回调任务需要点击接口，快速不需要此接口
				model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, phoneVersion, phoneModel,adverInfo.getAdverName());
			}
			
			return model;
		}
		
		/**
		 * 排重 String adid, String key,
				String idfa,String sysver, String phonemodel,String keyword,String ip)
		 */
		public static AppCommonModel paiChong(String domain, String adid, String idfa, String sysver, String phonemodel,String adverName, String ip)
		{
			AppCommonModel model = new AppCommonModel(-1, "出错！");
			
			//调用第三方排重接口
			StringBuilder url = new StringBuilder(domain)
					.append("?adid=").append(adid)
					.append("&source=").append(source)
					.append("&idfa=").append(idfa)	
					.append("&os_version=").append(sysver)
					.append("&device=").append(phonemodel)
					.append("&keyword=").append(adverName)	
					.append("&ip=").append(ip);
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
				else if(status.equals("0"))
				{
					model.setResult(1);
					model.setMsg("未重复，可以领取任务！");
				}
				else if(status.equals("1"))
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
		 * 点击String adid, String key, String idfa, String keywords, String ip, 
				String model, String sysver, String callbackurl
		 */
		public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
				Integer userAppId, Integer adverId, String userNum,  String sysver, String phonemodel, String adverName) throws UnsupportedEncodingException {
			AppCommonModel model = new AppCommonModel(-1, "出错！");
			StringBuilder url = new StringBuilder(domain)
					.append("?adid=").append(adid)
					.append("&source=").append(source)
					.append("&idfa=").append(idfa)
					.append("&keyword=").append(adverName)
					.append("&ip=").append(ip)
					.append("&device=").append(phonemodel)
					.append("&os_version=").append(sysver)
					.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
			JSONObject jsonObject = httpGet(url.toString(), false);
			
			if(jsonObject == null){
				log.error("request url：" + url + "。response：null");
				model.setResult(-1);
				model.setMsg("领取任务失败。原因：系统出错！");
			}else{
				log.error("request url：" + url + "。response：" + jsonObject.toString());
				Integer code = (Integer)jsonObject.get("status");
				if(code == null){
					model.setResult(-1);
					model.setMsg("领取任务失败！");
				}else if(code == 1){
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
		 * 激活上报 String adid, String key, String idfa,
				String ip,String sysver,String model,String keyword)
		 */
		public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip, String sysver, String phonemodel) {
			AppCommonModel model = new AppCommonModel(-1, "出错！");
			
			StringBuilder url = new StringBuilder(domain)
					.append("?adid=").append(adid)
					.append("&source=").append(source)
					.append("&idfa=").append(idfa)
					.append("&ip=").append(ip)
					.append("&os_version=").append(sysver)
					.append("&device=").append(phonemodel)
					.append("&keyword=").append(adverName);
			JSONObject jsonObject = httpGet(url.toString(), false);
			
			if(jsonObject == null){
				log.error("request url：" + url + "。response：null");
				model.setResult(-1);
				model.setMsg("未完成。原因：调用第三方平台出错！");
			}else{
				log.error("request url：" + url + "。response：" + jsonObject.toString());
				Integer code = (Integer)jsonObject.get("status");
				if(code == null){
					model.setResult(-1);
					model.setMsg("渠道未返回状态，未完成！");
				}else if(code == 1){
					model.setResult(1);
					model.setMsg(code + "：已完成！");
				}else{
					model.setResult(-1);
					model.setMsg("渠道提示，未完成！");
				}
			}
			
			return model;
		}
}
