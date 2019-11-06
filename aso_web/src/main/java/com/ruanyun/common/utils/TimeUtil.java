package com.ruanyun.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtil {

	/**
	 * 功能描述: 格式化日期为 当前日期的最后一秒
	 *
	 * @author yangliu  2016年1月21日 下午9:10:42
	 * 
	 * @param date 时间
	 * @return
	 */
	public static Date getDateMaxHour(Date date){
		String dateStr=doFormatDate(date, "yyyy-MM-dd");
		return doFormatDate(dateStr+" 23:59:59", "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 根据 <code>weekDay</code> 返回那一天的日期
	 * @param weekDay 星期几 1 - 7
	 * @param basisDate 基准的时间,基准参数所在的星期
	 * @return Date日期
	 */
	public static Date getDateForWeekDay(int weekDay,Date basisDate) {
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTime(basisDate);
		int basisWeekDay = currentCalendar.get(Calendar.DAY_OF_WEEK);
		currentCalendar.add(Calendar.DATE, weekDay - basisWeekDay);
		return currentCalendar.getTime();
	}
	
	/**
	 * 自定义格式化日期输出
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String doFormatDate(Date date, String format) {
		return (new SimpleDateFormat(format)).format(date);
	}
	
	/**
	 * 自定义格式化日期输出  
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date doFormatDate(String date, String format) {
		try {
			return (new SimpleDateFormat(format)).parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	
	public static void main(String[] args) {
		System.err.println(doFormatDate(doFormatDate(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss").toString());
		
	}
	
	/**
	 * 功能描述:获取当前时间的小时分钟 格式为：hh:mm
	 *
	 * @author yangliu  2013-7-25 上午09:22:41
	 * 
	 * @return
	 */
	public static String getCurrentHoursMin(){
		return doFormatDate(new Date(),"HH:mm");
	}
	
	/**
	 * 功能描述: 获取当前日期
	 *
	 * @author yangliu  2013-7-25 下午07:43:42
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDay(String format){
		return doFormatDate(new Date(), format);
	}
	
	/**
	 * 功能描述:获取两个日期之间相差多少小时(firstDate要大于secondDate)
	 *
	 * @author L H T  2014-5-21 下午03:25:30
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static long getBetweenHour(Date firstDate,Date secondDate){
		if (EmptyUtils.isEmpty(firstDate) || EmptyUtils.isEmpty(secondDate)) {
			return 0;
		}
		long hour=0;
		//获得两个时间的毫秒时间差异
		long betweenNs = secondDate.getTime() - firstDate.getTime();
		
		hour = betweenNs%(1000*24*60*60)/(1000*60*60);//计算差多少小时
		
		//输出结果
		return hour;
	}
	
	/**
	 * 功能描述:获取两个日期之间相差多少小时(firstDate要大于secondDate)
	 *
	 * @author L H T  2014-5-21 下午03:25:30
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static int getBetweenSecond(Date firstDate,Date secondDate){
		if (EmptyUtils.isEmpty(firstDate) || EmptyUtils.isEmpty(secondDate)) {
			return 0;
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long second=0L;
		//获得两个时间的毫秒时间差异
		Long betweenNs = secondDate.getTime() - firstDate.getTime();
		
		second = betweenNs/(1000);//计算差多少秒
		
		//输出结果
		System.out.println("时间相差："+second+"秒");
		return second.intValue();
	}
	
	/**
	 * 功能描述: 时间相差天数
	 *
	 * @author yangliu  2016年1月21日 下午8:21:44
	 * 
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public static int getBetweenDay(Date firstDate,Date secondDate){
		if (EmptyUtils.isEmpty(firstDate) || EmptyUtils.isEmpty(secondDate)) {
			return 0;
		}
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long day=0L;
		long dayTime=1000*60*60*24;
		//获得两个时间的毫秒时间差异
		long betweenNs =secondDate.getTime()- firstDate.getTime() ;
		
		day = betweenNs/(1000*60*60*24);//计算差多少小时
		
		//输出结果
		System.out.println("时间相差："+day+"天");
		return day.intValue();
	}
	
	  public static int daysBetween(Date date1,Date date2)  
	    {  
	        Calendar cal = Calendar.getInstance();  
	        cal.setTime(date1);  
	        long time1 = cal.get(Calendar.DAY_OF_YEAR);          
	        cal.setTime(date2);  
	        long time2 = cal.get(Calendar.DAY_OF_YEAR);      
	        Long between_days=(time2-time1);  
	          
	       return between_days.intValue();         
	    }  

	//测试方法
	public static int getTest(Date firstDate,Date secondDate){
		if (EmptyUtils.isEmpty(firstDate) || EmptyUtils.isEmpty(secondDate)) {
			return 0;
		}
		SimpleDateFormat sdf=new SimpleDateFormat();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long diff;
		try {
		//获得两个时间的毫秒时间差异
		diff = sd.parse("2014-05-22 00:00:00").getTime() - sd.parse(sd.format(secondDate)).getTime();
		long day = diff/nd;//计算差多少天
		long hour = diff%nd/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		long sec = diff%nd%nh%nm/ns;//计算差多少秒
		//输出结果
		System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
		} catch (ParseException e) {
		e.printStackTrace();
		}
		return 0;
	}
	
	
	public static long getTimestamp() 
	{
		 String _timeZone = "GMT+8:00";
	     TimeZone timeZone = null;  
	     timeZone = TimeZone.getTimeZone(_timeZone);  
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	     sdf.setTimeZone(timeZone);  
	     
		String stridddg = sdf.format(new Date());
		Date d = null;
		try 
		{
			d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(stridddg);
		} 
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		long t1 = d.getTime();
		return t1 = t1/1000;
	}
	
	
	//上月
	@SuppressWarnings("static-access")
	public static String GetYestMonthDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(calendar.MONTH,-1);
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}
	//获取昨天的日期
	@SuppressWarnings("static-access")
	public static String GetYestdayDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(calendar.DATE,-1);
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}
	
	//获取当月
	public static String GetMonthDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}
	
	//获取今日的日期
	public static String GetdayDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		String date = simpleDateFormat.format(calendar.getTime());
		
		return date;
	}

	public static String beforeHourToNowDate(int i) 
	{
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - i);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(calendar.getTime());
	}
	
	/**
	 * 功能描述:获取当前时间-1970年的秒数
	 *
	 * @author L H T  2014-5-5 下午04:26:36
	 *
	 * @return
	 */
	public static Long getCurrentSeconds(){
		return System.currentTimeMillis()/1000;
	}
}
