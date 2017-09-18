/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-27
 */
package com.ruanyun.web.service.background;


import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.ViewDao;
import com.ruanyun.web.model.TView;

/**
 *@author feiyang
 *@date 2016-1-27
 */
@Service
public class ViewService extends BaseServiceImpl<TView>{
	
	@Autowired
	private ViewDao viewDao;
	/**
	 * 
	 * 功能描述:关于和帮助
	 * @param viewType 1/2  关于帮助
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
	public TView getView(String viewType){
		return viewDao.getView(viewType);
	}
	
	/**
	 * 
	 * 功能描述:增加或者修改
	 * @param info
	 * @param request
	 * @return
	 *@author feiyang
	 *@date 2016-1-27
	 */
	public Integer saveOrupdate(TView info) {
		if (info != null) {
			if (EmptyUtils.isNotEmpty(info.getViewId())
					&& info.getViewId() != 0) {
				TView n =get(TView.class, info.getViewId());
				BeanUtils.copyProperties(info, n, new String[] {"viewType","viewName"});
				update(n);
				return 1;
			} 
		}
		return 0;
	}
}
