package com.ruanyun.web.util;

import net.sf.json.JSONObject;

public class SendSms 
{
	//post请求验证码
	private static String batchSend(String url, String un, String pw, String phone, String msg) 
			throws Exception 
	{
		JSONObject paramJS = new JSONObject();
		paramJS.put("account", un);
		paramJS.put("password", pw);
		paramJS.put("phone", phone);
		paramJS.put("msg", msg);
		String result = HttpRequestUtil.post(url, paramJS);
		
		return  result;
	}
	
	public static void sendMessage(String phone, String verifyCode)
	{
		String url = "http://smssh1.253.com/msg/send/json";// 应用地址
		String un = "N0255047";// 账号
		String pw = "kixZv2UW5Jd279";// 密码
		//phone = "18762672247";// 手机号码，多个号码使用","分割  "【253 云通讯】您的验证码是：253
		//Happy赚
		String msg = "【253云通讯】您的验证码是" + verifyCode;// 短信内容
		//String rd = "1";// 是否需要状态报告，需要1，不需要0
		//String ex = null;// 扩展码
		try 
		{
			batchSend(url, un, pw, phone, msg);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) 
	{
		sendMessage("18762672247", "135791");
	}
}
