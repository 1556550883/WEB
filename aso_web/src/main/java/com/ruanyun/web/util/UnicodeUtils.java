package com.ruanyun.web.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtils {

	public static String decodeUnicode(String str) { 
		try{
        Charset set = Charset.forName("UTF-16");  
        Pattern p = Pattern.compile("\\\\u([0-9a-fA-F]{4})");  
        Matcher m = p.matcher( str );  
        int start = 0 ;  
        int start2 = 0 ;  
        StringBuffer sb = new StringBuffer();  
        while( m.find( start ) ) {  
            start2 = m.start() ;  
            if( start2 > start ){  
                String seg = str.substring(start, start2) ;  
                sb.append( seg );  
            }  
            String code = m.group( 1 );  
            int i = Integer.valueOf( code , 16 );  
            byte[] bb = new byte[ 4 ] ;  
            bb[ 0 ] = (byte) ((i >> 8) & 0xFF );  
            bb[ 1 ] = (byte) ( i & 0xFF ) ;  
            ByteBuffer b = ByteBuffer.wrap(bb);  
            sb.append( String.valueOf( set.decode(b) ).trim() );  
            start = m.end() ;  
        }  
        start2 = str.length() ;  
        if( start2 > start ){  
            String seg = str.substring(start, start2) ;  
            sb.append( seg );  
        }  
        return sb.toString() ;  
		}catch (Exception e) {
			return str;
		}
    }  
	
	/**
	 * 功能描述:将字符串转换成Unicode
	 *
	 * @author houkun  2013-9-11 下午05:03:11
	 * 
	 * @param str 普通字符串
	 * @return unicode
	 */
	public static String convert(String str)
	{
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++)
		{
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>>8); //取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); //取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

	/**
	 * 功能描述:将Unicode转换成普通字符串
	 *
	 * @author houkun  2013-9-11 下午05:03:51
	 * 
	 * @param str unicode字符串
	 * @return 普通字符串
	 */
	public static String revert(String str){
		str = (str == null ? "" : str);
		if (str.indexOf("\\u") == -1)//如果不是unicode码则原样返回
			return str;

		StringBuffer sb = new StringBuffer(1000);
		for (int i = 0; i <= str.length() - 6;)
		{
			String strTemp = str.substring(i, i + 6);
			String value = strTemp.substring(2);
			int c = 0;
			for (int j = 0; j < value.length(); j++)
			{
				char tempChar = value.charAt(j);
				int t = 0;
				switch (tempChar)
				{
				case 'a':
					t = 10;
					break;
				case 'b':
					t = 11;
					break;
				case 'c':
					t = 12;
					break;
				case 'd':
					t = 13;
					break;
				case 'e':
					t = 14;
					break;
				case 'f':
					t = 15;
					break;
				default:
					t = tempChar - 48;
				break;
				}

				c += t * ((int) Math.pow(16, (value.length() - j - 1)));
			}
			sb.append((char) c);
			i = i + 6;
		}
		return sb.toString();
	}
	
	
	 /** 
     * utf-8编码 
     *  
     * @param source 
     * @return 
     */  
    public static String urlEncodeUTF8(String source) {  
        String result = source;  
        try {  
            result = java.net.URLEncoder.encode(source, "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    /** 
     * utf-8编码 解码
     *  
     * @param source 
     * @return 
     */  
    public static String urlDecodeUTF8(String source) {  
        String result = source;  
        try {  
            result = java.net.URLDecoder.decode(source, "UTF-8");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
    
    public static void main(String[] args) {
    	String df=UnicodeUtils.urlEncodeUTF8("美丽说");
    	System.out.println(df);
    	String revert=UnicodeUtils.urlDecodeUTF8("美丽说");
    	System.out.println(revert);
	}
  
	
}
