/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-16
 */
package com.ruanyun.web.service.sys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.ProvinceDao;
import com.ruanyun.web.model.sys.TProvince;

/**
 *@author feiyang
 *@date 2016-3-16
 */
@Service
public class ProvincService extends BaseServiceImpl<TProvince>{
	
		@Autowired
		private ProvinceDao provinceDao;
			
	/**
	 * 
	 * 功能描述:获取省份
	 * @param provinceCode
	 * @return
	 *@author feiyang
	 *@date 2016-3-16
	 */
	public List<TProvince> getAllprovice(String provinceCode ){
		return provinceDao.getAllprovice(provinceCode);
	}
	 
	
	
	public Map<String, Object> getProviceMap(String provinceCode){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("provice", provinceDao.getAllprovice(provinceCode));	
		return map;
	}
}
