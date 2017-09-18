/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.service.background;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserLevelDao;
import com.ruanyun.web.model.TUserLevel;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.NumUtils;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Service
public class UserLevelService extends BaseServiceImpl<TUserLevel>{

	@Autowired
	@Qualifier("userLevelDao")
	private UserLevelDao userLevelDao;
	
	
	@Override
	public Page<TUserLevel> queryPage(Page<TUserLevel> page, TUserLevel t) {
		return userLevelDao.queryPage(page, t);
	}
	
	
	/**
	 * 
	 * 功能描述:增加或者修改类型
	 * @param info
	 * @param request
	 * @param user
	 * @return
	 *@author feiyang
	 *@date 2016-1-7
	 */
	public Integer saveOrupdate(TUserLevel info,HttpServletRequest request,TUser user) {
			if(info != null){
				if(EmptyUtils.isNotEmpty(info.getLevelId())&&info.getLevelId()!=0){
					TUserLevel n=super.get(TUserLevel.class, info.getLevelId());
					BeanUtils.copyProperties(info, n, new String[]{});
					userLevelDao.update(n);
				}else{								
					userLevelDao.save(info);
					info.setLevelNum(NumUtils.getCommondNum(NumUtils.USER_LEVELE, info.getLevelId()));
				}
			}	
		return 1;
	}
	
	public TUserLevel getUserLevelByProportionCount(Integer proportionCount){
		if(EmptyUtils.isEmpty(proportionCount))
			proportionCount=0;
		return userLevelDao.getUserLevel(proportionCount);
	}
	
}
