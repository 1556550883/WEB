package com.ruanyun.web.service.background;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ShopInfoDao;
import com.ruanyun.web.model.TShopsInfo;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.sys.UserRoleService;
import com.ruanyun.web.service.sys.UserService;
import com.ruanyun.web.util.NumUtils;

@Service
public class ShopInfoService extends BaseServiceImpl<TShopsInfo> {
	@Autowired
	private ShopInfoDao shopInfoDao;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserScoreService userScoreService;
	@Override
	public Page<TShopsInfo> queryPage(Page<TShopsInfo> page, TShopsInfo t) {
		// TODO Auto-generated method stub
		return shopInfoDao.queryPage(page, t);
	}
	
	/**
	 * 
	 * 功能描述：增加或者修改
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param info
	 */
	public void saveOrUpd(TShopsInfo info,TUser user){
		if(EmptyUtils.isNotEmpty(info.getShopId())){
			TShopsInfo shopsInfo=getInfoById(info.getShopId());
			BeanUtils.copyProperties(info, shopsInfo, new String[]{"lotteryNum"});
			update(shopsInfo);
		}else{
			save(info);
			info.setShopNum(NumUtils.getCommondNum(NumUtils.SHOP_INFO, info.getShopId()));
			userService.saveUser(info.getLoginName(),info.getShopNum(), UserRoleService.ROLE_TYPE_9,user);
			userScoreService.addNewUserScore(info.getShopNum(), 2);
		}
	}

	/**
	 * 
	 * 功能描述：删除
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param ids
	 */
	public void delAll(String ids){
		shopInfoDao.delAll(ids);
	}
	/**
	 * 
	 * 功能描述：根据Id获得详细信息
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param id
	 * @return
	 */
	public TShopsInfo getInfoById(Integer id){
		TShopsInfo shopsInfo=super.get(TShopsInfo.class, id);
		TUser user=userService.getInfoByUserNum(shopsInfo.getShopNum());
		if(EmptyUtils.isNotEmpty(user))
		shopsInfo.setLoginName(user.getLoginName());
		return shopsInfo;
	}
	/**
	 * 
	 * 功能描述:获取所有店铺的列表
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	public List<TShopsInfo> getAllShopList(){
	
		return shopInfoDao.getAllShopList();
	}
}
