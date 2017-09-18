/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.service.app;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.PicDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TPic;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Service
public class AppPicService extends BaseServiceImpl<TPic> {
	@Autowired
	@Qualifier("picDao")
	private PicDao picDao;

	/**
	 * 
	 * 功能描述:查询位置图片
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-18
	 */
	public AppCommonModel getPiclListByPosition(TPic info) {
		AppCommonModel model = new AppCommonModel(-1,"参数不全");
		if(EmptyUtils.isNotEmpty(info.getPicPosition())){			
			List<TPic> list=picDao.getPiclListByPosition(info.getPicPosition());
			model.setMsg("查询成功");
			model.setResult(1);
			model.setObj(list);
		}
		return model;
	}
}
