/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-27
 */
package com.ruanyun.web.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.ruanyun.common.cache.impl.PublicCache;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.sys.TDictionary;


/**
 *@author feiyang
 *@date 2016-1-27
 */
@Service
public class AppGlobalParameterService {

	
	
	
	/**
	 * 
	 * 功能描述:全局参数
	 * @param type 1/2 1安卓 2IOS
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
	public AppCommonModel getGlobalList(Integer type){

		AppCommonModel model=new AppCommonModel(1);
		List<TDictionary> list=PublicCache.getItemList("GLOBAL_PARAMETER");
		int size=list.size();
		if (type==2) {
			size=list.size()-1;
		}
		Map<String, String> map=new HashMap<String, String>();
		for (int i = 0; i < size; i++) {
			map.put(list.get(i).getItemName(), list.get(i).getItemCode());			
		}
		model.setObj(map);
		return model;
	}
}
