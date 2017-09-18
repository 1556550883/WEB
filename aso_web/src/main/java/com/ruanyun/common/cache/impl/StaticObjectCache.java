package com.ruanyun.common.cache.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ruanyun.common.cache.SystemCacheService;
import com.ruanyun.common.utils.ReflectUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.util.ConstantAuth;
import com.ruanyun.web.util.Constants;

/**
 * 
 *  #(c) ruanyun  <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 静态变量缓存到map中
 * 
 *  <br/>创建说明: 2013-10-15 下午01:57:53 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Service("staticObjectCache")
public class StaticObjectCache implements SystemCacheService{
	public static Map<String,Object> constantsMap=new HashMap<String, Object>();
	public static Map<String,Object> authMap=new HashMap<String, Object>();

	@Override
	public String getCacheName() {
		
		return "加载Constants,ConstantAuth结束时间为:"+TimeUtil.getCurrentDay(SysCode.DATE_FORMAT_NUM_L);
	}

	@Override
	public void run() {
		update();
	}
	@Override
	public void update() {
		constantsMap.clear();
		constantsMap=ReflectUtils.setObjectToMap(Constants.class);
		authMap.clear();
		authMap=ReflectUtils.setObjectToMap(ConstantAuth.class);
	}
	
}
