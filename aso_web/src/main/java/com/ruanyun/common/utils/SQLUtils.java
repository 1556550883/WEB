package com.ruanyun.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 
 *  #(c) IFlytek baseweb <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 
 * 
 *  <br/>创建说明: 2013-7-20 下午12:08:34 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class SQLUtils {
	
	/**
	 * 组装like hql
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuHqlLike(String column, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like '%").append(
					param.trim()).append("%' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装like hql
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuPsqlLike(String column,String columnKey, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append(" like '%").append(
					param.trim()).append("%' ");
		}
		return sb.toString();
	}
	
	
	public static String popuHqlLike(String column, String param,Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like :").append(column.trim()).append(" ");
			map.put(column, "%"+param+"%");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2013-9-11 下午06:30:15
	 * 
	 * @param column
	 * @param paramKeyName
	 * @param param
	 * @param map
	 * @return
	 */
	public static String popuHqlLike(String column, String paramKeyName,String param,Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like :").append(paramKeyName).append(" ");
			map.put(paramKeyName, "%"+param+"%");
		}
		return sb.toString();
	}
	
	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuHqlRLike(String column, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like '").append(
					param.trim()).append("%' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuPsqlRLike(String column,String columnKey, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append(" like '").append(
					param.trim()).append("%' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuHqlRLike(String column, String param,Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like :").append(column.trim()).append(" ");
			map.put(column,param+"%");
		}
		return sb.toString();
	}
	
	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 *@param paramKeyName
	 *			map key 
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuHqlRLike(String column,String paramKeyName, String param,Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like :").append(paramKeyName).append(" ");
			map.put(paramKeyName,param+"%");
		}
		return sb.toString();
	}
	
	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuHqlLLike(String column, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like '%").append(
					param.trim()).append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuPsqlLLike(String column,String columnKey, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append(" like '%").append(
					param.trim()).append("%' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuHqlLLike(String column, String param,Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like :").append(column.trim()).append(" ");
			map.put(column,"%"+param);
		}
		return sb.toString();
	}

	/**
	 * 组装 like 'xxxx%'
	 * 
	 * @param column
	 *            列名
	 * @ param paramKeyName
	 * 				map key
	 * @param param
	 *            查询值
	 * @return hql
	 */
	public static String popuHqlLLike(String column,String paramKeyName, String param,Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append(" like :").append(paramKeyName).append(" ");
			map.put(paramKeyName,"%"+param);
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col='param'
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuHqlEq(String column, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append("='").append(param.trim())
					.append("' ");
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param column
	 * @param columnKey
	 * @param param
	 * @return
	 */
	public static String popuPsqlEq(String column,String columnKey, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append("='").append(param.trim())
			.append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col=param
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuHqlEq(String column, int param) {
		StringBuilder sb = new StringBuilder();
		if (param!=0) {
			sb.append(" and ").append(column).append("=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col=param
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuPsqlEq(String column,String columnKey, int param) {
		StringBuilder sb = new StringBuilder();
		if (param!=0) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append("::INTEGER=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col=param
	 * 
	 * @param column
	 *            列名
	 * @param param 参数
	 *            
	 * @return
	 */
	public static String popuHqlEq(String column, long param) {
		StringBuilder sb = new StringBuilder();
		if (param!=0) {
			sb.append(" and ").append(column).append("=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 组装equals hql eg:col=param
	 * 
	 * @param column 列名
	 *            
	 * @param param 参数
	 *            
	 * @param params 参数类型
	 * @return
	 */
	public static String popuHqlEq(String column, Object param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (param!=null && EmptyUtils.isNotEmpty(column)) {
			sb.append(" and ").append(column).append("=:").append(column.trim()).append(" ");
			params.put(column, param);
		}
		
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col=param
	 * 
	 * @param column 列名
	 *            
	 * @param param 参数
	 * 
	 * @param paramKeyName  map key
	 *            
	 * @param params 参数类型
	 * @return
	 */
	public static String popuHqlEq(String column,String paramKeyName, Object param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (param!=null && EmptyUtils.isNotEmpty(column)) {
			sb.append(" and ").append(column).append("=:").append(paramKeyName).append(" ");
			params.put(paramKeyName, param);
		}
		
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col!='param'
	 * @param column
	 * @param param
	 * @return
	 */
	public static String popuHqlNe(String column, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append(column).append("!='").append(param.trim())
					.append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col!='param'
	 * @param column
	 * @param param
	 * @return
	 */
	public static String popuPsqlNe(String column,String columnKey, String param) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(param)) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append("!='").append(param.trim())
					.append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col!='param'
	 * @param column
	 * @param param
	 * @return
	 */
	public static String popuHqlNe(String column, Object param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(column) && param !=null) {
			sb.append(" and ").append(column).append("!=:").append(column.trim()).append(" ");
			params.put(column, param);
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql eg:col!='param'
	 * @param column
	  * @param paramKey map key
	 * @param param
	 * @return
	 */
	public static String popuHqlNe(String column,String paramKeyName, Object param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotBlank(column) && param !=null) {
			sb.append(" and ").append(column).append("!=:").append(paramKeyName).append(" ");
			params.put(paramKeyName, param);
		}
		return sb.toString();
	}

	/**
	 * 组装equals hql
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuHqlEq(String column, Integer param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuPsqlEq(String column,String columnKey, Integer param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append("::INTEGER=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 组装equals hql
	 * 
	 * @param column
	 *            列名
	 * @param param
	 *            参数
	 * @return
	 */
	public static String popuHqlEq(String column, Long param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:44:37
	 * 
	 * @param column 列名称
	 * @param param 参数值
	 * @return
	 */
	public static String popuHqlMin(String column, Long param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:45:07
	 * 
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuHqlMax(String column, Long param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:44:37
	 * 
	 * @param column 列名称
	 * @param param 参数值
	 * @return
	 */
	public static String popuHqlMin(String column, Integer param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:45:07
	 * 
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuHqlMax(String column, Integer param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:44:37
	 * 
	 * @param column 列名称
	 * @param param 参数值
	 * @return
	 */
	public static String popuHqlMin(String column, Double param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:44:37
	 * 
	 * @param column 列名称
	 * @param param 参数值
	 * @return
	 */
	public static String popuPsqlMin(String column,String columnKey, Double param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append("::FLOAT>=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:45:07
	 * 
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuHqlMax(String column, Double param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:45:07
	 * 
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuPsqlMax(String column,String columnKey, Double param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append("::FLOAT<=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:44:37
	 * 
	 * @param column 列名称
	 * @param param 参数值
	 * @return
	 */
	public static String popuHqlMin(String column, Date param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">=:").append(column+"_start")
					.append(" ");
			params.put(column+"_start", param);
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:44:37
	 * 
	 * @param column 列名称
	 * @param paramKeyName  参数key
	 * @param param 参数值
	 * @param params 参数列表
	 * @return
	 */
	public static String popuHqlMin(String column,String paramKeyName, Date param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">=:").append(paramKeyName)
					.append(" ");
			params.put(paramKeyName, param);
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:45:07
	 * 
	 * @param column 列
	 * @param param 参数
	 * @param params 参数map
	 * @return
	 */
	public static String popuHqlMax(String column,String paramKeyName, Date param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<=:").append(paramKeyName)
					.append(" ");
			params.put(paramKeyName, param);
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author yangliu  2013-7-24 下午09:45:07
	 * 
	 * @param column 列
	 * @param param 参数
	 * @param params 参数map
	 * @return
	 */
	public static String popuHqlMax(String column, Date param,Map<String, Object> params) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<=:").append(column+"_end")
					.append(" ");
			params.put(column+"_end", param);
		}
		return sb.toString();
	}
	

	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author ding  2013-7-24 下午09:44:37
	 * 
	 * @param column 列名称
	 * @param param 参数值
	 * @return
	 */
	public static String popuHqlMin(String column, Float param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author ding  2013-7-24 下午09:45:07  
	 * 
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuHqlMax(String column, Float param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author ding  2013-7-24 下午09:45:07  
	 * 
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuPsqlMax(String column,String columnKey, Float param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append("("+column+"->>'"+columnKey+"')").append("::Float<=").append(param)
					.append(" ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段大于给定的值-适用于sql查时间
	 *
	 * @author L H T  2014-5-15 下午01:58:21
	 * 
	 * @param column 参数
	 * @param param 值
	 * @return
	 */
	public static String popuHqlMin(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd"))
					.append("' ");
		}
		return sb.toString();
	}

	public static String popuHqlMin2(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd HH:mm"))
					.append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 功能描述:字段小于给定的值-适用于sql查时间
	 *
	 * @author L H T  2014-5-15 下午01:58:21
	 * 
	 * @param column 参数
	 * @param param 值
	 * @return
	 */
	public static String popuHqlMax(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd"))
					.append("' ");
		}
		return sb.toString();
	}
	
	public static String popuHqlMax2(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd HH:mm"))
					.append("' ");
		}
		return sb.toString();
	}

	/**
	 * 功能描述:字段大于给定的值
	 *
	 * @author xqSun  2015-7-17 下午
	 * 
	 * @param column 列名称
	 * @param param 参数值
	 * @return
	 */
	public static String popuHqlMin(String column, String param) {
		StringBuilder sb = new StringBuilder();
		if (EmptyUtils.isNotEmpty(param)) {
			sb.append(" and ").append(column).append(">='").append(param)
					.append("' ");
		}
		return sb.toString();
	}

	/**
	 * 功能描述:字段小于给定的值
	 *
	 * @author  xqSun  2015-7-17 下午
	 * 
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuHqlMax(String column, String param) {
		StringBuilder sb = new StringBuilder();
		if (EmptyUtils.isNotEmpty(param)) {
			sb.append(" and ").append(column).append("<='").append(param)
					.append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 字段in的查询
	 * @param column 列
	 * @param param 参数
	 * @return
	 */
	public static String popuHqlIn(String column,String param){
		StringBuilder sb = new StringBuilder();
		if (EmptyUtils.isNotEmpty(param)) {
			sb.append(" and ").append(column).append(" in (").append(param)
					.append(") ");
		}
		return sb.toString();
	}
	
	
	/**
	 * 功能描述:字段大于给定的值-适用于sql查时间
	 *
	 * @author L H T  2014-5-15 下午01:58:21
	 * 
	 * @param column 参数
	 * @param param 值
	 * @return
	 */
	public static String popuHqlMinDate(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append(">='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 00:00:00"))
					.append("' ");
		}
		return sb.toString();
	}
		

	// 获得本周一0点时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return cal.getTime();
	}
	/**
	 * 
	 * 功能描述:获取本周的周一0点时间
	 * @param column
	 * @param param
	 * @return
	 *@author feiyang
	 *@date 2015-12-19
	 */
	public static String popuHqlMinWeek(String column, Date param) {
		StringBuilder sb = new StringBuilder();	
			param=getTimesWeekmorning();
		if (param != null) {
			sb.append(" and ").append(column).append(">='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 00:00:00"))
					.append("' ");
		}
		return sb.toString();
	}
	
	
	/**
	 * 
	 * 功能描述:获取本周的周日的24点时间
	 * @param column
	 * @param param
	 * @return
	 *@author feiyang
	 *@date 2015-12-21
	 */
	public static String popuHqlMaxWeek(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		cal.setTime(getTimesWeekmorning());
		cal.add(Calendar.DAY_OF_WEEK, 7);
			param=cal.getTime();
		if (param != null) {
			sb.append(" and ").append(column).append("<='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 00:00:00"))
					.append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * 功能描述:获取本月1号0点时间
	 * @param column
	 * @param param
	 * @return
	 *@author feiyang
	 *@date 2015-12-21
	 */
	public static String popuHqlMinMonth(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			param=cal.getTime();
		if (param != null) {
			sb.append(" and ").append(column).append(">='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 00:00:00"))
					.append("' ");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * 功能描述:获取本月1号0点时间
	 * @param column
	 * @param param
	 * @return
	 *@author feiyang
	 *@date 2015-12-21
	 */
	public static String popuHqlMaxMonth(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal
				.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		cal.set(Calendar.DAY_OF_MONTH, cal
				.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 24);
			param=cal.getTime();
		if (param != null) {
			sb.append(" and ").append(column).append("<='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 00:00:00"))
					.append("' ");
		}
		return sb.toString();
	}
	
	
	
	/**
	 * 功能描述:字段小于给定的值-适用于sql查时间
	 *
	 * @author L H T  2014-5-15 下午01:58:21
	 * 
	 * @param column 参数
	 * @param param 值
	 * @return
	 */
	public static String popuHqlMaxDate(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		if (param != null) {
			sb.append(" and ").append(column).append("<='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 23:59:59"))
					.append("' ");
		}
		return sb.toString();
	}

	/**
	 * 
	 * 功能描述:获取昨天0点到24点
	 * @param column
	 * @param param
	 * @return
	 *@author feiyang
	 *@date 2016-3-3
	 */
	public static String popuHqlYesterday(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,   -1);
		param=cal.getTime();
		if (param != null) {
			sb.append(" and ").append(column).append("<='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 00:00:00"))
					.append("' ");			
		}
		return sb.toString();
	}
	

	/**
	 * 
	 * 功能描述:小于今天0点的时间
	 * @param column
	 * @param param
	 * @return
	 *@author feiyang
	 *@date 2016-3-3
	 */
	public static String popuHqlMaxToDay(String column, Date param) {
		StringBuilder sb = new StringBuilder();
		Calendar cal = Calendar.getInstance();		
		param=cal.getTime();
		if (param != null) {
			sb.append(" and ").append(column).append("<='").append(TimeUtil.doFormatDate(param,"yyyy-MM-dd 00:00:00"))
					.append("' ");
			
		}
		return sb.toString();
	}
	
public static String sqlForIn(String parm) {
		
		if(EmptyUtils.isEmpty(parm)) return parm;
		String[] p = parm.split(",");
		String returnstring = "'";
		for (int i = 0; i < p.length; i++) {
			if (i == 0)
				returnstring = "'" + p[i] + "'";
			else
				returnstring += ",'" + p[i] + "'";
		}
		return returnstring;
	}
}
