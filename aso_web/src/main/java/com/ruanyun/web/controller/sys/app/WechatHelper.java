package com.ruanyun.web.controller.sys.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.omg.CosNaming.NamingContextExtPackage.StringNameHelper;

import net.sf.json.JSONObject;

public class WechatHelper {
	private static final Log log = LogFactory.getLog(WechatHelper.class);
	static String appId  = "wx5a7696604cae2200";
	static  String appSecret  = "f0efa83767830bc532cdc962f29abf31";
	//获取accessToken
	private static String getAccessToken(){
	    String accessTokenUrl= "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	    String requestUrl = accessTokenUrl.replace("APPID",appId).replace("APPSECRET",appSecret);
	    JSONObject result = httpGet(requestUrl,false);
		String accessToken = (String)result.get("access_token");
	    return accessToken ;
	}
	
	public static void main(String[] args) {
		 Map<String, String> sMap = makeWXTicket("http://192.168.0.101:8080/invite?id=505");
		 System.err.println(sMap.get("signature"));
	}
	
	//获取ticket
	public static String  getJsApiTicket(){
	    String apiTicketUrl= "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
	    String requestUrl = apiTicketUrl.replace("ACCESS_TOKEN", getAccessToken());
	    JSONObject result = httpGet(requestUrl,false);
	    String ticket = (String)result.get("ticket");
	    return ticket;
	}

	//生成微信权限验证的参数
	public static  Map<String, String> makeWXTicket(String url) {
		String jsApiTicket = getJsApiTicket();
	    Map<String, String> ret = new HashMap<String, String>();
	    String nonceStr = createNonceStr();
	    String timestamp = createTimestamp();
	    String string1;
	    String signature = "";

	    //注意这里参数名必须全部小写，且必须有序
	    string1 = "jsapi_ticket=" + jsApiTicket +
	            "&noncestr=" + nonceStr +
	            "&timestamp=" + timestamp +
	            "&url=" + url;
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(string1.getBytes("UTF-8"));
	        signature = byteToHex(crypt.digest());
	    }
	    catch (NoSuchAlgorithmException e)
	    {
	        log.error("WeChatController.makeWXTicket=====Start");
	        log.error(e.getMessage(),e);
	        log.error("WeChatController.makeWXTicket=====End");
	    }
	    catch (UnsupportedEncodingException e)
	    {
	        log.error("WeChatController.makeWXTicket=====Start");
	        log.error(e.getMessage(),e);
	        log.error("WeChatController.makeWXTicket=====End");
	    }

	    ret.put("url", url);
	    ret.put("jsapi_ticket", jsApiTicket);
	    ret.put("nonceStr", nonceStr);
	    ret.put("timestamp", timestamp);
	    ret.put("signature", signature);
	    ret.put("appid", appId);

	    return ret;
	}
	//字节数组转换为十六进制字符串
	private static String byteToHex(final byte[] hash) {
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	//生成随机字符串
	private static String createNonceStr() {
	    return UUID.randomUUID().toString();
	}
	//生成时间戳
	private static String createTimestamp() {
	    return Long.toString(System.currentTimeMillis() / 1000);
	}
	
	
	public static  JSONObject httpGet(String url, boolean noNeedResponse)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpGet httpGet = new HttpGet(url);
        try 
        {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) 
            {
                String str = "";
                try
                {
                    str = EntityUtils.toString(httpResponse.getEntity());
                    if (noNeedResponse)
                    {
                        return null;
                    }
                    
                    jsonResult = JSONObject.fromObject(str);
                } catch (Exception e) 
                {
                    //logger.error("get请求提交失败:" + url, e);
                	System.out.println("get请求提交失败:" + url);
                	System.out.println(e);
                }
            }
        }
        catch (IOException e)
        {
        	//logger.error("get请求提交失败:" + url, e);
        	System.out.println("get请求提交失败:" + url);
        	System.out.println(e);
        }
        
        return jsonResult;
    }
}
