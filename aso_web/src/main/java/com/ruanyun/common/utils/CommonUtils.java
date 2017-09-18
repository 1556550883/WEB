package com.ruanyun.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CommonUtils {

	/**
	 * 功能描述:正数转负数
	 *
	 * @author yanzy 2016-1-16 下午03:59:20
	 * 
	 * @param param
	 * @return
	 */
	public static double plusToMinus(double param) {
		return 0 - param;
	}

	/**
	 * 功能描述:生成订单编号 4位随机数+年月日+6位随机数
	 *
	 * @author yanzy 2016-1-21 上午11:16:44
	 * 
	 * @param flag
	 * @return
	 */
//	public static String getOrderId(String flag) {
//		String begin = String.valueOf((int) (Math.random() * 9000 + 1000));
//		String day = DateUtils.getCurrentDay("yyyyMMdd");
//		String end = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//		String orderId = flag + begin + day + end;
//		return orderId;
//	}

	/**
	 * 功能描述:保留两位小数
	 *
	 * @author yanzy 2016-2-15 上午09:53:42
	 * 
	 * @param flag
	 * @return
	 */
	public static Double toDecimal2(Double flag) {
		DecimalFormat df = new DecimalFormat("#.00");
		return Double.valueOf(df.format(flag));
	}

	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal ret = null;
		if (value != null) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(((Number) value).doubleValue());
			} else {
				throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
						+ " into a BigDecimal.");
			}
		}
		return ret;
	}

	/**
	 * 功能描述: 获取对象中的属性 并组装为 格式 A,B,C,D
	 *
	 * @author yangliu  2016年9月3日 上午10:52:54
	 * 
	 * @param clz 对象的class 【list里面的对象】
	 * @param list 【数组】
	 * @param attributeName 【获取值的属性名称】
	 * @return
	 */
	public static String getAttributeValue(Class clz, List list, String attributeName) {

		StringBuffer result = new StringBuffer();
		String name = "";
		Method methods[] = clz.getMethods();
		Method method = null;
		for (int i = 0; i < methods.length; i++) {
			name = methods[i].getName().toUpperCase();
			if (name.startsWith("GET") && name.substring(3).equalsIgnoreCase(attributeName)) {
				method = methods[i];
				break;
			}
		}
		Iterator it = list.iterator();
		try {
			Object obj=null;
			while (it.hasNext()) {
				obj=method.invoke(it.next());
				if(obj!=null )
					result.append(",").append(obj);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		if (EmptyUtils.isNotEmpty(result.toString())) {
			return result.substring(1);
		}
		return "";
	}
	
	/**
	 * 功能描述: 设置值
	 *
	 * @author yangliu  2016年9月3日 上午11:14:10
	 * 
	 * @param clz class【list对象的class】
	 * @param list 数组
	 * @param map  键值对【根据key，value对应  key是 clz 的一个属性值 value是需要复制给class 另一个对象的值】
	 * @param keyAttributeName 属性【获取值后与map的key对比 然后获取map的value赋值给 valueAttributeName 属性】
	 * @param valueAttributeName 属性值
	 * @return
	 */
	public static void setAttributeValue(Class clz,List list,Map map, String keyAttributeName,String valueAttributeName) {
		String name = "";
		Method methods[] = clz.getMethods();
		Method getMethod = null;
		Method setMethod=null;
		
		for (int i = 0; i < methods.length; i++) {
			name = methods[i].getName().toUpperCase();
			if (name.startsWith("GET") && name.substring(3).equalsIgnoreCase(keyAttributeName)){
				getMethod = methods[i];
			}else if(name.startsWith("SET") && name.substring(3).equalsIgnoreCase(valueAttributeName)){
				setMethod= methods[i];
			}
		}
		Iterator it = list.iterator();
		try {
			Object obj=null;
			Object result=null;
			while (it.hasNext()) {
				obj=it.next();
				Object in = getMethod.invoke(obj);
				if (EmptyUtils.isNotEmpty(in)) {
					result=map.get(in);
					if(result!=null)
						setMethod.invoke(obj,result);
				}
				
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 功能描述:获取服务项目编号 除去最后一个大于零数字后面的所有0
	 *
	 * @author yanzy  2016-10-26 上午10:55:46
	 * 
	 * @param projectNum
	 * @return
	 */
	public static String getProjectNum(String projectNum){
		//String projectNum = "005002000000000";
    	int index=0;
    	for (int i = 0; i < projectNum.length(); i++) {
			int a = Integer.parseInt(projectNum.substring(i, i+1));
			System.out.println(a);
			if (a > 0) {
				index = i+1;
			}
		}
    	String result = projectNum.substring(0, index);
    	System.out.println("----"+result);
    	return result;
	}
	
	/**
	 * 功能描述:判断支付串为数字
	 *
	 * @author yanzy  2016-12-3 下午01:52:42
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}
}
