package com.ruanyun.web.controller.sys.background;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.cache.impl.AreaCache;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.TAreas;
import com.ruanyun.web.model.sys.TCity;

@Controller
@RequestMapping("area")
public class AreaController extends BaseController {
	
	/**
	 * 
	 * 功能描述：根据省份Id获得地区
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param provinceId
	 * @param response
	 */
	@RequestMapping("citys")
	public void getCityList(String provinceId,HttpServletResponse response){
		List<TCity> cities=AreaCache.getCitiesByProvinces(provinceId);
		super.writeJsonData(response, cities);
	}
	
	/**
	 * 
	 * 功能描述：获得城市下的地区
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param cityid
	 * @param response
	 */
	@RequestMapping("areas")
	public void getAreaList(String cityid,HttpServletResponse response){
		List<TAreas> cities=AreaCache.getAreasByCity(cityid);
		super.writeJsonData(response, cities);
	}
}
