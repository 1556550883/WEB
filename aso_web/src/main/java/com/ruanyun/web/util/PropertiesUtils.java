package com.ruanyun.web.util;

import com.ruanyun.common.utils.PropertiesManager;

public class PropertiesUtils {
	
	public static PropertiesManager URLPage=new PropertiesManager(Constants.URL_PATH);
	public static PropertiesManager EMAIL_CONFIG=new PropertiesManager(Constants.EMAILCONFIG_PATH);
	public static PropertiesManager PAGE_MESSAGE_CONFIG=new PropertiesManager(Constants.PAGECONFIG_PATH);
	public static PropertiesManager SMS_MESSAGE_CONFIG=new PropertiesManager(Constants.SMS_PATH);
}
