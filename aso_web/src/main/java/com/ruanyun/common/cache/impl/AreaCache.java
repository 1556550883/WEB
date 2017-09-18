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
import com.ruanyun.web.model.TAreas;
import com.ruanyun.web.model.sys.TCity;
import com.ruanyun.web.model.sys.TProvince;
@Service("areaCache")
public class AreaCache implements SystemCacheService {
	private static Map<String, List<TCity>> mapCities = new HashMap<String, List<TCity>>();
	private static List<TProvince> provincesList = new ArrayList<TProvince>();
	private static Map<String, List<TAreas>> mapAreas = new HashMap<String, List<TAreas>>();
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Override
	public String getCacheName() {
		return "加载省份城市 地区结束时间为:"+TimeUtil.getCurrentDay(SysCode.DATE_FORMAT_NUM_L);
	}

	@Override
	public void run() {
		update();
	}
	
	 
 
	
	@Override
	public void update() {
		provincesList.clear();
		provincesList=getAllProvinces();

		mapCities.clear();
		mapCities=getMapCities(getAllCities());
		mapAreas.clear();
		mapAreas=getMapAreas(getAllAreas());
	}
	
	/**
	 * 功能描述:转换城市
	 *
	 * @author yangliu  2013-11-30 下午04:19:25
	 * 
	 * @param allList
	 * @return
	 */
	private  Map<String,List<TCity>> getMapCities(List<TCity> allList){
		Map<String,List<TCity>> map = new HashMap<String, List<TCity>>();
		List<TCity> childList=null;
		for(TCity dic : allList){
			childList=map.get(String.valueOf(dic.getProvinceCode()));
			if(childList==null){
				childList=new ArrayList<TCity>();
				map.put(String.valueOf(dic.getProvinceCode()), childList);
			}
			childList.add(dic);
		}
		return map;
	}
	
	/**
	 * 
	 * 功能描述：转换地区
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param allList
	 * @return
	 */
	private  Map<String,List<TAreas>> getMapAreas(List<TAreas> allList){
		Map<String,List<TAreas>> map = new HashMap<String, List<TAreas>>();
		List<TAreas> childList=null;
		for(TAreas dic : allList){
			childList=map.get(String.valueOf(dic.getCityid()));
			if(childList==null){
				childList=new ArrayList<TAreas>();
				map.put(String.valueOf(dic.getCityid()), childList);
			}
			childList.add(dic);
		}
		return map;
	}
	/**
	 * 功能描述:获取城市
	 *
	 * @author yangliu  2013-11-30 下午04:19:41
	 * 
	 * @return
	 */
	private List<TCity> getAllCities(){
		return	jdbcTemplate.query("select * from t_city", new BeanPropertyRowMapper<TCity>(TCity.class));
	}
	
 
	
	/**
	 * 功能描述:获取省份
	 *
	 * @author yangliu  2013-11-30 下午04:20:02
	 * 
	 * @return
	 */
	private List<TProvince> getAllProvinces(){
		return	jdbcTemplate.query("select * from T_Province", new BeanPropertyRowMapper<TProvince>(TProvince.class));
	}

	/**
	 * 
	 * 功能描述：获得地区
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @return
	 */
	private List<TAreas> getAllAreas(){
		return	jdbcTemplate.query("select * from t_areas", new BeanPropertyRowMapper<TAreas>(TAreas.class));
	}
	/**
	 * 功能描述:获取省份下的城市
	 *
	 * @author yangliu  2013-11-30 下午04:33:08
	 * 
	 * @param provinceId
	 * @return
	 */
	public static List<TCity> getCitiesByProvinces(String provinceId){
		return mapCities.get(provinceId);
	}
	
	/**
	 * 
	 * 功能描述：获得城市下的地区
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param cityid
	 * @return
	 */
	public static List<TAreas> getAreasByCity(String cityid){
		return mapAreas.get(cityid);
	}
	/**
	 * 功能描述: 获取城市名称
	 *
	 * @author yangliu  2013-11-30 下午04:33:24
	 * 
	 * @param provinceId
	 * @param cityId
	 * @return
	 */
	public static String getCityName(String provinceId,String cityId){
		if(EmptyUtils.isNotEmpty(provinceId) && EmptyUtils.isNotEmpty(cityId)){
			List<TCity> list = mapCities.get(provinceId);
			if(EmptyUtils.isEmpty(list))
				return null;
			for(TCity dic : list){
				if(dic.getCityCode().equals(cityId))
					return dic.getCityName();
			}
		}
		return null;
	}
	
 
	
 
	
	/**
	 * 功能描述:获取省份列表
	 *
	 * @author yangliu  2013-11-30 下午04:36:44
	 * @return
	 */
	public static List<TProvince> getProvinces(){
		return provincesList;
	}
	
	/**
	 * 功能描述:获取省份名称
	 *
	 * @author yangliu  2013-11-30 下午04:36:57
	 * 
	 * @param provinceId
	 * @return
	 */
	public static String getProvinceName(String provinceId){
		for(TProvince tp : provincesList){
			if(tp.getProvinceCode().equals(provinceId))
				return tp.getProvinceName();
		}
		return null;
	}
	
}
