/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.service.background;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserLevelRateDao;
import com.ruanyun.web.model.TUserLevel;
import com.ruanyun.web.model.TUserLevelRate;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.NumUtils;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Service
public class UserLevelRateService extends BaseServiceImpl<TUserLevelRate>{

	
	@Autowired
	@Qualifier("userLevelRateDao")
	private  UserLevelRateDao userLevelRateDao;
	
	
	
	@Override
	public Page<TUserLevelRate> queryPage(Page<TUserLevelRate> page, TUserLevelRate t) {
		return userLevelRateDao.queryPage(page, t);
	}
	
	/**
	 * 功能描述: 得到所有
	 *
	 * @author yangliu  2016年1月18日 下午8:00:07
	 * 
	 * @return
	 */
	public List<TUserLevelRate> getAll(){
		return super.getAll(TUserLevelRate.class);
	}
	
	/**
	 * 功能描述: 獲取用戶等級 與獎勵比例  key levelNum+rateType
	 *
	 * @author yangliu  2016年1月18日 下午8:04:48
	 * 
	 * @return
	 */
	public Map<String,TUserLevelRate> getMapAllLevelRate(){
		Map<String,TUserLevelRate> map = new HashMap<String, TUserLevelRate>();
		List<TUserLevelRate> list =getAll();
		for(TUserLevelRate levelRate : list){
			map.put(levelRate.getLevelNum()+"_"+levelRate.getRateType(), levelRate);
		}
		return map;
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
	public Integer saveOrupdate(TUserLevelRate info,HttpServletRequest request) {
			if(info != null){
				if(EmptyUtils.isNotEmpty(info.getLevelRateId())&&info.getLevelRateId()!=0){
					TUserLevelRate n=super.get(TUserLevelRate.class, info.getLevelRateId());
					BeanUtils.copyProperties(info, n, new String[]{});
					userLevelRateDao.update(n);
				}else{								
					userLevelRateDao.save(info);				
				}
			}	
		return 1;
	}
	
	/**
	 * 
	 * 手机端接口 :根据等级获取得分比例
	 * @param levelNum
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	public List<TUserLevelRate> getListByLevelNum(String levelNum){
		return userLevelRateDao.getListByLevelNum(levelNum);
	}

}
