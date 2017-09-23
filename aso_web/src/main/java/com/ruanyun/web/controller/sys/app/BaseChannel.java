package com.ruanyun.web.controller.sys.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import net.sf.json.JSONObject;

public abstract class BaseChannel 
{
	
	/**
	 * 获取回调地址
	 */
	public static String getCallbackUrl(String adid, String idfa, Integer userAppId, Integer adverId) throws UnsupportedEncodingException{
		StringBuilder callback = new StringBuilder("http://116.62.103.139:8080/sjjz/app/duijie/callback")
				.append("?adid=").append(adid)
				.append("&idfa=").append(idfa)
				.append("&userAppId=").append(userAppId)
				.append("&adverId=").append(adverId);
		return URLEncoder.encode(callback.toString(), "utf-8");
	}
	
	/**
	 * httpGet
	 */
    public static JSONObject httpGet(String url, boolean noNeedResponse)
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
