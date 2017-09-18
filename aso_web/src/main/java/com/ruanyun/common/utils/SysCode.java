package com.ruanyun.common.utils;

public interface SysCode {
	
	/**
	 * 英文逗号
	 */
	public static final String COMMA=","; 
	/**
	 * 日期格式化 yyyy-MM-dd
	 */
	static final String DATE_FORMAT_STR_S="yyyy-MM-dd";
	/**
	 * 日期格式化 yyyy-MM-dd HH:mm:ss
	 */
	static final String DATE_FORMAT_STR_L="yyyy-MM-dd HH:mm:ss";
	/**
	 * 日期格式化 yyyyMMdd
	 */
	static final String DATE_FORMAT_NUM_S="yyyyMMdd";
	/**
	 * 日期格式化 yyyyMMddHHmmss
	 */
	static final String DATE_FORMAT_NUM_L="yyyyMMddHHmmss";
	
	
	/**
	 * 电脑访问
	 */
	public static final String REQUEST_TYPE_PC="1";
	
	/**
	 * 手机访问
	 */
	public static final String REQUEST_TYPE_MOBILE="2";
	
	/**
	 * 电脑访问和手机访问 都可以访问
	 */
	public static final String REQUEST_TYPE_ALL="3";
}
