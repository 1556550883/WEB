package com.ruanyun.common.utils;

import java.security.MessageDigest;

public class MD5Util {

	/**
	 * 功能描述:字符串转换为MD5
	 *
	 * @author houkun  2013-7-18 上午08:52:29
	 * 
	 * @param str字符串
	 * @return
	 */
	public static String encoderByMd5(String str){
		if(str==null || "".equals(str))
			return "";
		byte[] unencodedPassword = str.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return str;
		}
		md.reset();
		md.update(unencodedPassword);
		byte[] encodedPassword = md.digest();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}
		return buf.toString();
	}
}
