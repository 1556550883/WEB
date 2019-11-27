package com.ruanyun.web.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class AddressUtils {  
	
public static String getAddressByIP(String strIP) {
		try {
			URL url = new URL("https://restapi.amap.com/v3/ip?key=3fca8ce814f1c197cb03fdebfba3e253&ip="+strIP);
			URLConnection conn = url.openConnection();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line = null;
			StringBuffer result = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			String ipAddr = result.toString();
			try {
				JSONObject obj1= JSONObject.fromObject(ipAddr);
				if("1".equals(obj1.get("status").toString())){
				//JSONObject obj2= JSONObject.fromObject(obj1.get("province").toString());
				//JSONObject obj3= JSONObject.fromObject(obj1.get("city").toString());
				return obj1.get("province").toString() + "," + obj1.get("city").toString();
				}else{
					return "读取失败";
				}
			} catch (JSONException e) {
				e.printStackTrace();
				return "读取失败";
			}
			
		} catch (IOException e) {
			return "读取失败";
		}
	}

	public static void main(String[] args) {
		System.out.println(getAddressByIP("59.63.210.139"));
	}
}  