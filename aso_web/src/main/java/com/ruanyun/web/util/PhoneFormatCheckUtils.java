package com.ruanyun.web.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneFormatCheckUtils 
{
    public static boolean isPhoneLegal(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }
    /**
     * 正则表达式：验证手机号
     */
    //public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
    
    public static void main(String[] args) {
    	boolean s = isPhoneLegal("18121111135");
    	if(s) {
    		System.err.println("sss");
    	}else {
    		System.err.println("sssssss");
    	}
	}
}
