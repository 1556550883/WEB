package com.ruanyun.web.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * 
 *  #(c) IFlytek zqb <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 有米 -公用类
 * 
 *  <br/>创建说明: 2014-4-11 下午01:42:14 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class PortUtils {
	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 功能描述:有米--签名生成算法
	 *
	 * @author L H T  2014-4-11 下午01:43:24
	 * 
	 * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
     * @param String                 dev_server_secret 开发者在有米后台设置的密钥
     * @return 签名
     * @throws IOException
	 */
    public static String getSignature(HashMap<String, String> params, String dev_server_secret) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
 
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
        basestring.append(dev_server_secret);
        System.out.println("参数拼接："+basestring.toString());
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }
        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }
    
    /***
     * 功能描述:米迪---签名生成算法
     *
     * @author L H T  2014-4-15 下午01:20:32
     * 
     * @param str
     * @return
     * @throws Exception
     */
    public  static String md5(String str){  
        MessageDigest messageDigest = null;  
  
        try {  
            messageDigest = MessageDigest.getInstance("MD5");  
            messageDigest.reset();    
            messageDigest.update(str.getBytes("UTF-8"));  
            byte[] byteArray = messageDigest.digest();  
            StringBuffer md5StrBuff = new StringBuffer();   
            for (int i = 0; i < byteArray.length; i++) {              
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)  
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));  
                else  
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));  
            }   
//            logger.debug("md5 src:{}, dst:{}", str, md5StrBuff.toString());
            return md5StrBuff.toString();  

        } catch (NoSuchAlgorithmException e) {  
//            logger.error("NoSuchAlgorithmException caught!");  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        } 
        
     return "";
  
 } 
    /**
     * 功能描述:安沃---签名生成算法
     *
     * @author L H T  2014-4-15 下午05:42:55
     * 
     * @param plain
     * @return
     */
	public static String adwoMD5(HashMap<String, String> params,String key) {
		StringBuffer buf = new StringBuffer("");
		Map<String, String> sortedParams = new TreeMap<String, String>(params);

		Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder basestring = new StringBuilder();
		for (Map.Entry<String, String> param : entrys) {
			basestring.append(param.getKey()).append("=").append(
					param.getValue());
		}
		basestring.append("key="+key);
		 System.out.println("参数拼接："+basestring.toString());
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(basestring.toString().getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * 功能描述:点入--签名生成算法
	 *
	 * @author L H T  2014-4-11 下午01:43:24
	 * 
	 * @param HashMap<String,String> params 请求参数集，所有参数必须已转换为字符串类型
     * @param String                 dev_server_secret 开发者在有米后台设置的密钥
     * @return 签名
     * @throws IOException
	 */
    public static String dianruMD5(HashMap<String, String> params) throws IOException {
        // 先将参数以其参数名的字典序升序进行排序
        Map<String, String> sortedParams = new TreeMap<String, String>(params);
 
        Set<Map.Entry<String, String>> entrys = sortedParams.entrySet();
        // 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
        StringBuilder basestring = new StringBuilder();
        for (Map.Entry<String, String> param : entrys) {
            basestring.append(param.getKey()).append("=").append(param.getValue());
        }
//        basestring.append(dev_server_secret);
        System.out.println("参数拼接："+basestring.toString());
        // 使用MD5对待签名串求签
        byte[] bytes = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            bytes = md5.digest(basestring.toString().getBytes("UTF-8"));
        } catch (GeneralSecurityException ex) {
            throw new IOException(ex);
        }
        // 将MD5输出的二进制结果转换为小写的十六进制
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex);
        }
        return sign.toString();
    }

}
