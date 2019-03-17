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
			URL url = new URL("http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip="+strIP);
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
				if("0".equals(obj1.get("status").toString())){
				JSONObject obj2= JSONObject.fromObject(obj1.get("content").toString());
				JSONObject obj3= JSONObject.fromObject(obj2.get("address_detail").toString());
				return obj3.get("province").toString() + "," + obj3.get("city").toString();
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
}  