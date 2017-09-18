package com.ruanyun.web.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.sys.TCity;
import com.ruanyun.web.service.sys.CityService;

@Controller
@RequestMapping("/city")
public class CityController extends BaseController {
	
	@Autowired
	@Qualifier("cityService")
	private CityService cityService;

	@RequestMapping("/listAjax")
	public void listAjax(HttpServletResponse response,String provinceCode){
		List<TCity> cities = cityService.getAllcity(provinceCode);
		super.writeJsonData(response, cities);
	}
}
