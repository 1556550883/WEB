package com.ruanyun.common.utils;

	import java.io.IOException;
	import java.io.InputStream;
	import java.util.Properties;

	public class PropertiesManager {
		private Properties prop;

		public PropertiesManager(String path) {
			super();

			prop = new Properties(); 
		    InputStream in = this.getClass().getResourceAsStream(path); 
		    try { 
		            prop.load(in); 
		            if(in !=null){
			    		 in.close();
			    	 }
		     } catch (IOException e) { 
		            e.printStackTrace(); 
		     }
		}
		
		 /**
	     * 功能描述: 获取属性值
	     *
	     * @author yangliu  2013-8-8 下午02:51:11
	     * 
	     * @param key
	     * @return
	     */
	    public String getValue(String key){
	    	Object obj=prop.get(key);
	    	if(obj!=null)
	    		return obj.toString();
	    	return "";
	    }
		
	}
