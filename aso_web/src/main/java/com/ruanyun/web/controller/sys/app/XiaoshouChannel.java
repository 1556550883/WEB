package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;

import net.sf.json.JSONObject;

public class XiaoshouChannel  extends BaseChannel 
{
	private static final Log log = LogFactory.getLog(XiaoshouChannel.class);
	
	//我们的渠道号
	private static final String chid = "2";
	
	//排重+点击+云聚
	public static AppCommonModel isXiaoshouChannel(TChannelAdverInfo adverInfo, String adid, String idfa, String ip,
			String userAppId, String adverId, String userNum, String  phoneVersion, String  phoneModel, String udid) 
					throws NumberFormatException, UnsupportedEncodingException
	{
		//云聚
		//调用第三方排重接口
		AppCommonModel model = paiChong(adverInfo.getFlag2(), adid, idfa,phoneVersion,phoneModel,adverInfo.getAdverName(),ip, udid);
		
		if(model.getResult() != -1)
		{
			//调用第三方点击接口
			model = dianJi(adverInfo.getFlag3(),adid, idfa, ip, Integer.valueOf(userAppId), Integer.valueOf(adverId), userNum, phoneVersion, phoneModel,adverInfo.getAdverName(),udid);
		}
		
		return model;
	}
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String sysver, String phonemodel,
			String adverName, String ip,String udid) throws UnsupportedEncodingException
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		phonemodel = "iPhone" + phonemodel.substring(6);
		//调用第三方排重接口
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&chid=").append(chid)
				.append("&system=").append(sysver)
				.append("&model=").append(phonemodel)
				.append("&user_ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&idfa=").append(idfa);
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
			Integer status = (Integer)jsonObject.get(idfa);
		    if(status != null && status == 0)
			{
				model.setResult(1);
				model.setMsg("未重复，可以领取任务！");
			}
			else if(status != null && status == 1)
			{
				model.setResult(-1);
				model.setMsg(jsonObject.toString());
			}
			else
			{
				Integer code = (Integer)jsonObject.get("code");
				if(code == null) {code = 0;}
				
				switch (code) {
				case 20001:
					model.setMsg(code + ":排重接口重复请求！");
					break;
				case 20002:
					model.setMsg(code+ ":点击接口重复请求！");
					break;
				case 30001:
				case 30002:
				case 30003:
					model.setMsg(code + ":提交参数验证错误");
					break;
				case 50001:
				case 50002:
					model.setMsg(code + ":任务不存在或关键词提交有误！");
					break;
				default:			
					model.setMsg(jsonObject.toString());
					break;
				}
				
				model.setResult(-1);
				
			}
		}
		
		return model;
	}
	
	public static void main(String[] args) {
		String phonemodel = "iphone9,3";
		phonemodel = "iPhone" + phonemodel.substring(6);
		System.out.print(phonemodel);
	}
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum, String sysver, String phonemodel,String adverName, String udid) throws UnsupportedEncodingException {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		phonemodel = "iPhone" + phonemodel.substring(6);
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&chid=").append(chid)
				.append("&system=").append(sysver)
				.append("&model=").append(phonemodel)
				.append("&user_ip=").append(ip)
				.append("&udid=").append(udid)
				.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
				.append("&idfa=").append(idfa)
				.append("&cb_url=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(adid.equals("c0b2ed6f73a93c2e5013625d3fe7997a")) {
			model.setResult(1);
			model.setMsg("领取任务成功！");
			return model;
		}
		
		if(jsonObject == null){
			log.error("request url：" + url + "。response：null");
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：系统出错！");
		}else{
			log.error("request url：" + url + "。response：" + jsonObject.toString());
			Integer code = (Integer)jsonObject.get("code");
			if(code == null) {code = -1;}
			if(code == 0){
				model.setResult(1);
				model.setMsg("领取任务成功！");
			}else{
				switch (code) {
				case 20001:
					model.setMsg(code+ ":排重接口请求异常！");
					break;
				case 30001:
					model.setMsg(code+ ":渠道标识参数有误！");
					break;
				case 30002:
					model.setMsg(code+ ":应用标识参数有误！");
					break;
				case 40001:
					model.setMsg(code+ ":任务不存在或关键词错误！");
					break;
				case 50001:
				case 50002:
				case 50003:
					model.setMsg(code+ ":提交参数验证错误！");
					break;
				case 70001:
				case 70002:
				case 70003:
				case 70004:
				case 70005:
					model.setMsg(code+ ":ip参数验证错误！");
					break;
				default:
					model.setMsg(jsonObject.toString());
					break;
				}
				
				model.setResult(-1);
				
			}
		}
		
		return model;
	}
	
	
	/**
	 * 激活上报
	 */
	public static AppCommonModel activate(String domain, String adid, String adverName, String idfa, String ip,
			String sysver, String phonemodel, String udid) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		phonemodel = "iPhone" + phonemodel.substring(6);
		StringBuilder url;
		try {
			url = new StringBuilder(domain)
					.append("?adid=").append(adid)
					.append("&chid=").append(chid)
					.append("&system=").append(sysver)
					.append("&model=").append(phonemodel)
					.append("&user_ip=").append(ip)
					.append("&udid=").append(udid)
					.append("&keyword=").append(URLEncoder.encode(adverName, "utf-8"))
					.append("&idfa=").append(idfa);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			model.setResult(-1);
			model.setMsg("未完成。原因：系统出错！");
			return model;
		}
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
			}else if(code == 0){
				model.setResult(1);
				model.setMsg(code + "：已完成！");
			}else{
				model.setResult(-1);
				model.setMsg((String)jsonObject.get("msg"));
			}
		}
		
		return model;
	}
}
