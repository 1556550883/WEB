/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.service.background;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.RedPackageDao;
import com.ruanyun.web.model.TRedPackage;

/**
 *@author feiyang
 *@date 2016-3-2
 */
@Service
public class RedPackageService extends BaseServiceImpl<TRedPackage>{
	@Autowired
	private RedPackageDao redPackageDao;
	
	/**
	 * 
	 * 功能描述:获取全部红包
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public List<TRedPackage> getAllRedPackage(){
		return redPackageDao.getAllRedPackage();
	}
	
	/**
	 * 
	 * 手机端接口:获取红包列表 （包括是否已猜）
	 * @param userNum 当前用户
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public List<Map> getListByUserNum(String userNum,String lotteryNum){
		return redPackageDao.getListByUserNum(userNum, lotteryNum);
	}
	
	
	/**
	 * 
	 * 功能描述:根据红包编号获取红包
	 * @param redPackageNum
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public TRedPackage getRedPackageByRedPackageNum(String redPackageNum){
		return redPackageDao.getRedPackageByRedPackageNum(redPackageNum);
	}
	
	
	/**
	 * 
	 * 功能描述:获取本期猜将总数
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	public int getRedPackageDaySum(){	
		return redPackageDao.getRedPackageDaySum();
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2016年4月7日 上午11:36:35
	 * 
	 * @return
	 */
	public int clearDay(){
		return redPackageDao.clearDay();
	}
}
