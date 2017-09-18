/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-16
 */
package com.ruanyun.web.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.CityDao;
import com.ruanyun.web.model.sys.TCity;

/**
 *@author feiyang
 *@date 2016-3-16
 */
@Service
public class CityService extends BaseServiceImpl<TCity>{

	@Autowired
	private CityDao cityDao;
	
	/**
	 * 
	 * 功能描述:根据省份CODE 获取所有城市
	 * @param provinceCode
	 * @return
	 *@author feiyang
	 *@date 2016-3-16
	 */
	public List<TCity> getAllcity(String provinceCode ){
		return  cityDao.getAllcity(provinceCode);
	}
	
	/**
	 * 功能描述:将TCity封装到map中  key：cityCode  value ：TCity
	 * @author wsp  2017-1-9 下午06:36:10
	 * @param provinces
	 * @return
	 */
	public Map<String, TCity> getCityByCityCodes(String cityCodes){
		if(EmptyUtils.isEmpty(cityCodes))
			return null;
		return cityDao.getCityByCityCodes(cityCodes);
	}
}
