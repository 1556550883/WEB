/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.service.app;

import java.util.List;
import org.springframework.stereotype.Service;
import com.ruanyun.common.cache.impl.PublicCache;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.sys.TDictionary;

/**
 *@author feiyang
 *@date 2016-1-13
 */
@Service
public class AppDictionaryService extends BaseServiceImpl<TDictionary>{

	
	/**
	 * 
	 * 功能描述:根据parentCode 获取集合
	 * @param parentCode
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	public AppCommonModel getDictionaryList(String parentCode){
		AppCommonModel model=new AppCommonModel();
		List<TDictionary> list=PublicCache.getItemList(parentCode);
		model.setObj(list);
		return model;
	}
}
