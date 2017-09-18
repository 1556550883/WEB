package com.ruanyun.common.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
	
	/**
	 * 功能描述:根据头部信息判断是否为手机访问
	 *
	 * @author yangliu  2013-9-17 下午03:44:56
	 * 
	 * @param userAgent 头部信息
	 * @return
	 */
	public static boolean isMobileRequest(String userAgent) {  
		userAgent=userAgent.toUpperCase();
	    if (userAgent.indexOf("ANDROID") >-1 || userAgent.indexOf("IPAD")>-1||
	    		userAgent.indexOf("NOKI") > -1 || // Nokia phones and emulators     
	             userAgent.indexOf("ERIC") > -1 || // Ericsson WAP phones and emulators     
	             userAgent.indexOf("WAPI") > -1 || // Ericsson WapIDE 2.0     
	             userAgent.indexOf("MC21") > -1 || // Ericsson MC218     
	             userAgent.indexOf("AUR") > -1  || // Ericsson R320     
	             userAgent.indexOf("R380") > -1 || // Ericsson R380     
	             userAgent.indexOf("UP.B") > -1 || // UP.Browser     
	             userAgent.indexOf("WINW") > -1 || // WinWAP browser     
	             userAgent.indexOf("UPG1") > -1 || // UP.SDK 4.0     
	             userAgent.indexOf("UPSI") > -1 || //another kind of UP.Browser     
	             userAgent.indexOf("QWAP") > -1 || // unknown QWAPPER browser     
	             userAgent.indexOf("JIGS") > -1 || // unknown JigSaw browser     
	             userAgent.indexOf("JAVA") > -1 || // unknown Java based browser     
	             userAgent.indexOf("ALCA") > -1 || // unknown Alcatel-BE3 browser (UP based)     
	             userAgent.indexOf("MITS") > -1 || // unknown Mitsubishi browser     
	             userAgent.indexOf("MOT-") > -1 || // unknown browser (UP based)     
	             userAgent.indexOf("My S") > -1 ||//  unknown Ericsson devkit browser      
	             userAgent.indexOf("WAPJ") > -1 ||//Virtual WAPJAG www.wapjag.de     
	             userAgent.indexOf("FETC") > -1 ||//fetchpage.cgi Perl script from www.wapcab.de     
	             userAgent.indexOf("ALAV") > -1 || //yet another unknown UP based browser     
	             userAgent.indexOf("WAPA") > -1 || //another unknown browser (Web based "Wapalyzer")    
	             userAgent.indexOf("UCWEB") > -1 || //another unknown browser (Web based "Wapalyzer")    
	             userAgent.indexOf("BlackBerry") > -1 || //another unknown browser (Web based "Wapalyzer")                     
	             userAgent.indexOf("J2ME") > -1 || //another unknown browser (Web based "Wapalyzer")              
	             userAgent.indexOf("OPER") > -1 
	             ) 
	             {
	         return true;     
	     } else {     
	         return false;     
	     }     
	}    
	
	/**
	 * 功能描述:根据头部信息判断是否为手机访问
	 *
	 * @author yangliu  2013-9-17 下午03:44:56
	 * 
	 * @param request 请求信息
	 * @return
	 */
	public static boolean isMobileRequest(HttpServletRequest request){
		String agent = request.getHeader("User-Agent") == null ? ""
				: request.getHeader("User-Agent");
		return isMobileRequest(agent);
	}
	
}
