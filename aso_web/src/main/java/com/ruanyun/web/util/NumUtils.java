package com.ruanyun.web.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class NumUtils {
	
	/**
	 * 商铺编号前缀
	 */
	public final static String SHOP_INFO="SH";
	
	
	/**
	 * 渠道编号前缀
	 */
	public final static String CHANNEL_INFO="CH";
	
	/**
	 * 商品编号前缀
	 */
	public final static String GOODS_NUM="SG";
	/**
	 *兑换编号前缀
	 */
	public  final static String EXCHANGE_NUM="EN";
	
	/**
	 *广告编号前缀
	 */
	public  final static String CHANNEL_ADVER_INFO="AD";
	
	/**
	 *广告步骤编号前缀
	 */
	public  final static String CHANNEL_ADVER_STEP_INFO="ADSP";
	
	/**
	 *广告步骤编号前缀
	 */
	public  final static String CHANNEL_ADVER_USER="CAU";
	
	/**
	 * 广告图片编号前缀
	 */
	public  final static String CHANNEL_ADVER_PIC="CAP";
	
	/**
	 *等级编号前缀 
	 */
	public final static String USER_LEVELE="UL";
	
	/**
	 * 班级编号前缀
	 */
	public final static String ADVER_INFO="ADVER";
	
	/**
	 * 公共用户编号前缀
	 */
	public final static String PIX_COMON_USER="UC";
	
	/**
	 * 学生编号前缀
	 */
	public final static String PIX_STU_USER="US";
	
	/**
	 * 家长编号前缀
	 */
	public final static String PIX_PARENT_USER="UP";
	/**
	 * 老师编号前缀
	 */
	public final static String PIX_TEACHER_USER="UT";
	
    /**
     * 签到编号前缀
     */
    public static String USER_SIGN_NUM="USM";
	
	/**
	 * 第三方用户登陆编号
	 */
	public final static String DSF_USER="DU";
	
	/**
	 * 用户编号
	 */
	public final static String USER_NUM="UN";
	
	/**
	 * 用户编号
	 */
	public final static String EFFECTIVE_NUM="EN";
	
	/**
	 * 用户猜红包编号
	 */
	public final static String USER_RED_PACKAGE_NUM="URP";
	
	/**
	 * 文章编号
	 */
	public final static String ARTICLEINFO_NUM="ARN";
	
	
	/**
	 * 每日开奖日期
	 */
	public final static String DAY_LOTTERY_TIME="16:00";
	
	
	/**
	 * 开奖金额默认值
	 */
	public final static Float LOTTERT_SCORE=50f;
	
	/**
	 * 用户编号
	 */
	public final static String USER_APP_NUM = "UAN";
	
	/**
	 * 登录IP
	 */
	public final static String PIX_LOGIN_IP="LI";
	
	/**
	 * 功能描述:机构编码
	 *
	 * @author yangliu  2015年11月26日 下午4:41:43
	 * 
	 * @param cityBh 城市编号
	 * @param id 主键
	 * @return
	 */
	public static String getOrgNum(String cityBh,int id){
		return "O_"+cityBh.substring(0,6)+String.format("%06d", id);
	}
	
	/**
	 * 功能描述: 获取编号
	 *
	 * @author yangliu  2015年11月26日 下午5:15:32
	 * 
	 * @param pix 前缀
	 * @param id 主键
	 * @return
	 */
	public static String getCommondNum(String pix,int id){
		return pix+"_"+String.format("%010d", id);
	}
	
	public static String getCommondUserNum(int id){
		return String.format("%06d", id);
	}
	
	public static String getLotteryNum() {
		  Calendar   cal   =   Calendar.getInstance();	
		  String lotteryNum = new SimpleDateFormat( "yyyyMMdd").format(cal.getTime());
		  return lotteryNum;
	}
	
	
	/**
	 * 
	 * 功能描述:随机产生（字母+数字）字符串
	 * @param length  位数
	 * @return
	 *@author feiyang
	 *@date 2016-3-14
	 */
	 public static String getStringRandom(int length) {  
         
	        String val = "";  
	        Random random = new Random();  
	          
	        //参数length，表示生成几位随机数  
	        for(int i = 0; i < length; i++) {  
	              
	            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
	            //输出字母还是数字  
	            if( "char".equalsIgnoreCase(charOrNum) ) {  
	                //输出是大写字母还是小写字母  
	                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
	                val += (char)(random.nextInt(26) + temp);  
	            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
	                val += String.valueOf(random.nextInt(10));  
	            }  
	        }  
	        return val;  
	    } 
	 
	 
	 
	 public static String getInvitationCode(Integer aa){
		 String  str=aa.toString();
		 int zeroSize=8-str.length();
		 String a="";
		 for (int i = 0; i < zeroSize; i++) {
			  a+= "0";			
		}
		 return a+str;
	 }

}
