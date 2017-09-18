/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-16
 */
package com.ruanyun.web.controller.sys;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.sys.TCity;
import com.ruanyun.web.model.sys.TProvince;
import com.ruanyun.web.service.sys.CityService;
import com.ruanyun.web.service.sys.ProvincService;

/**
 *@author feiyang
 *@date 2016-3-16
 */
@Controller
@RequestMapping("mechInfo")
public class MechanismInfoController extends BaseController{

	@Autowired
	private ProvincService provincService;
	
	@Autowired
	private CityService cityService;
	
	
	
	/**
	 * 
	 * 功能描述:省市区公共类
	 * @param response
	 *@author feiyang
	 *@date 2016-3-16
	 */
	@RequestMapping("getprovince")
	 public void getprovince(HttpServletResponse response){
		List<TProvince> list=provincService.getAllprovice("");
		 super.writeJsonData(response, list);
    }
	
	@RequestMapping("getcity")
    public void getcity(HttpServletResponse response,String provinceId){	
			List<TCity> list=cityService.getAllcity(provinceId);
			 super.writeJsonData(response, list);
    }
	
	
	
	

	/**
	 * 
	 * 功能描述:省市区公共类
	 * @param response
	 *@author feiyang
	 *@date 2016-3-16
	 */
	@RequestMapping("province1")
	 public void getprovince1(HttpServletResponse response){
		List<TProvince> list=provincService.getAllprovice("");
		 super.writeJsonData(response, list);
    }
	
	@RequestMapping("city1")
    public void getcity1(HttpServletResponse response,String provinceId){	
		
		if(provinceId == null){
			super.writeJsonData(response, "");
		}else{
		List<TCity> list=cityService.getAllcity(provinceId);
		 super.writeJsonData(response, list);
		}
    }
	
}
