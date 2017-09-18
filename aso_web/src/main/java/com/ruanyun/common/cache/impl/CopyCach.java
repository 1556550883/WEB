package com.ruanyun.common.cache.impl;

import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.cache.SystemCacheService;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.ReflectUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.util.Constants;

/**
 * 
 *  #(c) IFlytek zqb <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 通过字典表中的数据复制给Constants.java中的属性
 * 
 *  <br/>创建说明: 2014-6-5 下午02:56:18 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Service("copyCach")
public class CopyCach implements SystemCacheService{
	protected static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ReflectUtils.class);
	
	@Autowired
	@Qualifier("publicCache")
	private PublicCache publicCache;
	
	

	@Override
	public String getCacheName() {
		return "加载 CopyCach 的时间为："+TimeUtil.getCurrentDay(SysCode.DATE_FORMAT_STR_L);
	}

	@Override
	public void run() {
		update();
	}

	@Override
	public void update() {
		//复制接口
		copyConstantsValue(Constants.class,"SCORE_INTERFACE_TYPE");
		//复制其他限制值
		copyConstantsValue(Constants.class,"OTHER_EXCHANGE_REST");
		//复制百度key值
		copyConstantsValue(Constants.class,"BAIDU_KEY");
		//其他参数变量
		copyConstantsValue(Constants.class,"OTHER_CONTANTS");
	}
	
	/**
	 * 功能描述:给Constants.java中的属性赋值
	 *
	 * @author L H T  2014-6-5 下午04:36:44
	 * 
	 * @param <T>
	 * @param clz Constants.class
	 * @param parentCode 字典表父类型code
	 */
	@SuppressWarnings("static-access")
	public  <T> void copyConstantsValue(Class<T> clz,String parentCode) {
		Field[] fields = clz.getFields();
		
		try {
			for (Field f : fields) {
				String itemCode=publicCache.getItemCodeByFlag(parentCode,f.getName());
				if (EmptyUtils.isNotEmpty(itemCode)) {
					if(f.getType()==String.class){
						f.set(null, itemCode);
					}else if (f.getType() ==Integer.class|| f.getType()==int.class){
						f.set(null, Integer.parseInt(itemCode));
					}else if (f.getType()==Float.class|| f.getType()==float.class){
						f.set(null, Float.parseFloat(itemCode));
					}else if (f.getType()==Double.class || f.getType()==double.class){
						f.set(null, Double.parseDouble(itemCode));
					}
					
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"复制错误，消息为"+e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"复制错误，消息为"+e.getMessage());
		}
	}
	
	
	/**
	 * 功能描述:给Constants.java中的属性赋值（int类型）
	 *
	 * @author L H T  2014-6-5 下午04:36:44
	 * 
	 * @param <T>
	 * @param clz Constants.class
	 * @param parentCode 字典表父类型code
	 */
	@SuppressWarnings("static-access")
	public  <T> void copyConstantsIntValue(Class<T> clz,String parentCode) {
		Field[] fields = clz.getFields();
		
		try {
			for (Field f : fields) {
				String itemCode=publicCache.getItemCodeByFlag(parentCode,f.getName());
				if (EmptyUtils.isNotEmpty(itemCode)) {
					f.set(null, Integer.parseInt(itemCode));
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"复制错误，消息为"+e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error(clz.getName()+"复制错误，消息为"+e.getMessage());
		}
	}
	
}
