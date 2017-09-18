package com.ruanyun.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ReflectUtils {
	protected static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ReflectUtils.class);
	/**
	 * 功能描述:把对象的属性封装到map中
	 *
	 * @author yangliu  2013-10-15 下午01:36:42
	 * 
	 * @param <T>
	 * @param clz
	 * @return
	 */
	public static <T> Map<String, Object> setObjectToMap(Class<T> clz) {
		Field[] fields = clz.getFields();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for (Field f : fields) {
				map.put(f.getName(), f.get(null));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"加载错误，消息为"+e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"加载错误，消息为"+e.getMessage());
		}
		return map;
	}
	
	/**
	 * 功能描述:把对象的属性封装到map中
	 *
	 * @author yangliu  2013-10-15 下午01:36:42
	 * 
	 * @param <T>
	 * @param obj  对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> setObjectToMap(T obj) {
		Class<T> clz=(Class<T>) obj.getClass();
		Field[] fields = clz.getFields();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			for (Field f : fields) {
				map.put(f.getName(), f.get(obj));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"加载错误，消息为"+e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"加载错误，消息为"+e.getMessage());
		}
		return map;
	}
	
		/**
		 * 更具属性名称回去在对象中的值
		 * @param fieldName 属性名称
		 * @param o 对象
		 * @return 值
		 */
	   public static Object getFieldValueByName(String fieldName, Object o) {  
	       try {    
	           String firstLetter = fieldName.substring(0, 1).toUpperCase();    
	           String getter = "get" + firstLetter + fieldName.substring(1);    
	           Method method = o.getClass().getMethod(getter, new Class[] {});    
	           Object value = method.invoke(o, new Object[] {});    
	           return value;    
	       } catch (Exception e) {    
	           return null;    
	       }    
	   }   
	   
	 /**
	  * 更具属性名称回去在对象中的值
	  * @param fieldNames 属性名称列表
	  * @param o 对象
	  * @return list对象值
	  */
	   @SuppressWarnings("unchecked")
	public static List<Object> getFieldValuesByNames(String[] fieldNames, Object o) {  
		   List<Object> list= new ArrayList<Object>();
	       try {    
	    	   Class<?> clz=o.getClass();
	    	   if (o instanceof HashMap) {
	    		   Map<String ,String> map=new HashMap<String, String>();
	    		   map=(Map<String, String>) clz.cast(o);
	    		   for(String fieldName : fieldNames){
		    		  String value= String.valueOf(map.get(fieldName));
			          list.add(value);
		    	   }
			   }else {
	    		   for(String fieldName : fieldNames){
		    		   String firstLetter = fieldName.substring(0, 1).toUpperCase();    
			           String getter = "get" + firstLetter + fieldName.substring(1);    
			           Method method = clz.getMethod(getter, new Class[] {});  
			           Object value = method.invoke(o, new Object[] {}); 
			           list.add(value);
		    	   }
			}
	           return list;    
	       } catch (Exception e) {   
	    	   e.printStackTrace();
	           return null;    
	       }    
	   }   
//	   /**
//		  * 更具属性名称回去在对象中的值
//		  * @param fieldNames 属性名称列表
//		  * @param o map对象
//		  * @return list对象值
//		  */
//		   public static List<Object> getMapValuesByName(String[] fieldNames, List<HashMap<String, Object>> o) {  
//			   List<Object> list= new ArrayList<Object>();
//		       try {    
//		    	   Class<?> clz=o.getClass();
//		    	  
//		    	   System.out.println("clz===="+clz+"====="+ clz.cast(o));
//		    	   System.out.println("object  类型-==="+clz.getName());
//		    	   System.out.println("-=-=-=-="+clz.getSimpleName());
//		    	  
//		    	   String objectType=clz.getName();
//		    	    
//		    	   if (o instanceof HashMap) {
//		    		   for(String fieldName : fieldNames){
//		    			   Map<String ,String> map=new HashMap<String, String>();
//			    		   map=(Map<String, String>) clz.cast(o);
//			    		  String value= map.get(fieldName);
//				          list.add(value);
//			    	   }
//					
//				   }
//		    	  
//		           return null;    
//		       } catch (Exception e) {    
//		           return null;    
//		       }    
//		   }   
		   
		  
}
