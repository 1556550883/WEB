package com.ruanyun.web.controller.sys.app;

import java.io.UnsupportedEncodingException;
import com.ruanyun.web.model.AppCommonModel;

import net.sf.json.JSONObject;

/**
 * 掌上互动
 * @author 向轴
 */
public class ZhangShangHuDong extends BaseChannel 
{
	//我们的渠道号
	private static final String CH = "3163";
	
	/**
	 * 排重
	 */
	public static AppCommonModel paiChong(String domain, String adid, String idfa, String ip)
	{
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//StringBuilder url = new StringBuilder("http://api.adzshd.com/RemoveEcho.ashx")
		//http://api.adzshd.com/SourceSearchISActivate.ashx?cmd=getuserisacctivate&adid=adid&idfa=idfa、
		StringBuilder url = new StringBuilder(domain)
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&btype=1")
				.append("&ip=").append(ip);
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			model.setResult(-1);
			model.setMsg("领取任务失败。原因：调用第三方排重接口出错！");
		}else{
			String status = (String)jsonObject.get(idfa);
			if(status == null){
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}else if("0".equals(status)){
				model.setResult(1);
				model.setMsg("没有重复，可以领取任务！");
			}else if("1".equals(status)){
				model.setResult(-1);
				model.setMsg("抱歉重复任务，请选择其他任务！");
			}else{
				model.setResult(-1);
				model.setMsg("领取任务失败！");
			}
		}
		
		return model;
	}
	
	/**
	 * 点击
	 */
	public static AppCommonModel dianJi(String domain, String adid, String idfa, String ip,
			Integer userAppId, Integer adverId, String userNum) throws UnsupportedEncodingException{
		AppCommonModel model = new AppCommonModel(-1, "领取任务出错！");
		
		//StringBuilder url = new StringBuilder("http://api.adzshd.com/SourceClick.ashx")
		StringBuilder url = new StringBuilder(domain)
			.append("?adid=").append(adid)
			.append("&appid=").append(CH)
			.append("&idfa=").append(idfa)
			.append("&ip=").append(ip)
			.append("&mac=02:00:00:00:00:00")
			.append("&callback=").append(getCallbackUrl(adid, idfa, userAppId, adverId, userNum));
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			model.setResult(-1);
			model.setMsg("领取任务出错！");
		}else{
			Boolean success = (Boolean)jsonObject.get("success");
			if(success == null){
				model.setResult(-1);
				model.setMsg("领取任务出错！");
			}else if(success){
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
	public static AppCommonModel activate(String domain, String adid, String idfa, String ip) {
		AppCommonModel model = new AppCommonModel(-1, "出错！");
		
		//StringBuilder url = new StringBuilder("http://api.adzshd.com/submit.ashx")
		StringBuilder url = new StringBuilder(domain)
			.append("?adid=").append(adid)
			.append("&appid=").append(CH)
			.append("&mac=02:00:00:00:00:00")
			.append("&idfa=").append(idfa)
			.append("&ip=").append(ip)
			.append("&osverssion=");
		JSONObject jsonObject = httpGet(url.toString(), false);
		
		if(jsonObject == null){
			model.setResult(-1);
			model.setMsg("任务未完成！");
		}else{
			String status = (String)jsonObject.get("status");
			if("1".equals(status)){
				model.setResult(1);
				model.setMsg("任务已完成！");
			}else{
				model.setResult(-1);
				model.setMsg("任务未完成！");
			}
		}
		
		return model;
	}
}
