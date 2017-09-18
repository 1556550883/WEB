package com.ruanyun.common.cache.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.ruanyun.common.cache.SystemCacheService;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.sys.TDictionary;

@Service("publicCache")
public class PublicCache  implements SystemCacheService {
	private static Map<String, List<TDictionary>> map = new HashMap<String, List<TDictionary>>();
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	public String getCacheName() {
	
		return "加载T_Dictionary结束时间为:"+TimeUtil.getCurrentDay(SysCode.DATE_FORMAT_NUM_L);
	}

	public void run() {
		update();
	}

	public void update() {
		map.clear();
		map=getMap(getAllDic());
	}
	
	/**
	 * 功能描述:加载字典表中所有数据
	 *
	 * @author yangliu  2013-9-14 下午04:01:04
	 * 
	 * @return
	 */
	private List<TDictionary> getAllDic(){
		return	jdbcTemplate.query("select * from t_dictionary where status=1 order by orderby ", new BeanPropertyRowMapper<TDictionary>(TDictionary.class));
	}
	
	/**
	 * 功能描述: 根据父类信息把 封装到map中去
	 *
	 * @author yangliu  2013-9-14 下午04:11:28
	 * 
	 * @param allList 所有列表
	 * @return
	 */
	private  Map<String,List<TDictionary>> getMap(List<TDictionary> allList){
		Map<String,List<TDictionary>> map = new HashMap<String, List<TDictionary>>();
		List<TDictionary> childList=null;
		for(TDictionary dic : allList){
			childList=map.get(dic.getParentCode());
			if(childList==null){
				childList=new ArrayList<TDictionary>();
				map.put(dic.getParentCode(), childList);
			}
			childList.add(dic);
		}
		return map;
	}
	
	/**
	 * 功能描述: 获取 值
	 *
	 * @author yangliu  2013-9-16 上午08:59:47
	 * 
	 * @param parentCode 父类型
	 * @param itemCode 子类型
	 * @return
	 */
	public static String getItemName(String parentCode,String itemCode){
		if(EmptyUtils.isNotEmpty(parentCode) && EmptyUtils.isNotEmpty(itemCode)){
			List<TDictionary> list = map.get(parentCode);
			if(EmptyUtils.isEmpty(list))
				return null;
			for(TDictionary dic : list){
				if(dic.getItemCode().equals(itemCode))
					return dic.getItemName();
			}
		}
		return null;
	}
	
	/**
	 * 功能描述: 获取 值
	 *
	 * @author yangliu  2013-9-16 上午08:59:47
	 * 
	 * @param parentCode 父类型
	 * @param itemCode 子类型
	 * @return
	 */
	public static String getItemShortName(String parentCode,String itemCode){
		if(EmptyUtils.isNotEmpty(parentCode) && EmptyUtils.isNotEmpty(itemCode)){
			List<TDictionary> list = map.get(parentCode);
			if(EmptyUtils.isEmpty(list))
				return null;
			for(TDictionary dic : list){
				if(dic.getItemCode().equals(itemCode))
					return dic.getFlag1();
			}
		}
		return null;
	}
	
	/**
	 * 功能描述: 积分接口类型  ---通过flag1获取itemCode
	 *
	 * @author L H T  2014-6-5 下午03:38:26
	 * 
	 * @param parentCode
	 * @param flag
	 * @return
	 */
	public static String getItemCodeByFlag(String parentCode,String flag){
		if(EmptyUtils.isNotEmpty(parentCode) && EmptyUtils.isNotEmpty(flag)){
			List<TDictionary> list = map.get(parentCode);
			if(EmptyUtils.isNotEmpty(list)){
				for(TDictionary dic : list){
					if(flag.equals(dic.getFlag1()))
						return dic.getItemCode();
				}
			}
		}
		return null;
	}
	
	/**
	 * 功能描述: 获取 子类列表
	 *
	 * @author yangliu  2013-9-16 上午09:01:21
	 * 
	 * @param parentCode 父Code
	 * @return
	 */
	public static List<TDictionary> getItemList(String parentCode){
		return map.get(parentCode);
	}

}
