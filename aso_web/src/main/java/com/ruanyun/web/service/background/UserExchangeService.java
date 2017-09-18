/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-12
 */
package com.ruanyun.web.service.background;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.UserExchangeDao;
import com.ruanyun.web.model.TUserExchange;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.Constants;




/**
 *@author feiyang
 *@date 2016-1-12
 */
@Service
public class UserExchangeService extends BaseServiceImpl<TUserExchange> {

	
	@Autowired
	private UserExchangeDao userExchangeDao;
	
	@Autowired
	private UserScoreService userScoreService;
	/**
	 * 
	 * 功能描述:分页查询
	 * 
	 * @param page
	 * @param t
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */

	public Page<TUserExchange> queryPage(Page<TUserExchange> page,
			TUserExchange t,TUser tUser) {
		return userExchangeDao.queryPageSql(page, t, tUser);
	}
	
	public int updateStatus(String exchangeNum,int status) {
		TUserExchange tUserExchange = get(TUserExchange.class,"exchangeNum", exchangeNum);
		if(status==1){
			tUserExchange.setExchangeStatus(1);
		}else if(status==-1){
			tUserExchange.setExchangeStatus(-1);
			userScoreService.addScoreSum(tUserExchange.getExchangeUserNum(), "退还积分", Constants.USER_SCORE_INFO_TYPE_6, tUserExchange.getExchangeScore(), 1);
		}
		update(tUserExchange);
		return 1;
	}
	/**
	 * 
	 * 功能描述:兑换审核
	 * 
	 * @param info
	 * @param isType 1 手动 2自动 3无需兑换码 4 拒绝
	 * @return
	 *@author feiyang
	 *@date 2016-1-12
	 */
	public int updateStatus(Integer exchhangeId, Integer isType,String exchangeCode) {
		TUserExchange tUserExchange = get(TUserExchange.class, exchhangeId);
		if (EmptyUtils.isNotEmpty(tUserExchange)) {
			
			if (isType == 1) {
				tUserExchange.setExchangeCode(exchangeCode);
				tUserExchange.setExchangeStatus(1);
			}else if (isType==2) {
				UUID uuid = UUID.randomUUID();
				tUserExchange.setExchangeCode(uuid.toString());
				tUserExchange.setExchangeStatus(1);
			}else if (isType==3) {
				tUserExchange.setExchangeCode("");
				tUserExchange.setExchangeStatus(1);
			}else if (isType==4) {
				tUserExchange.setExchangeStatus(-1);
				userScoreService.addScoreSum(tUserExchange.getExchangeUserNum(), "退还积分", Constants.USER_SCORE_INFO_TYPE_6, tUserExchange.getExchangeScore(), 1);
			}
			if (isType==1||isType==2) {//审核成功
				if(EmptyUtils.isNotEmpty(tUserExchange.getExchangeShopNum())){					
					userScoreService.addScoreSum(tUserExchange.getExchangeShopNum(), "增加店铺积分", Constants.USER_SCORE_INFO_TYPE_7, tUserExchange.getExchangeScore(), 1);
				}
			}
			update(tUserExchange);
			return 1;
		}
		return 0;
	}
	
	/**
	 * 
	 * 功能描述:验证兑换码
	 * @param info
	 *@author feiyang
	 *@date 2016-1-12
	 */
	public String useExchangeCode(TUserExchange info,TUser tUser){
		String msg=null;
		info.setExchangeStatus(1);//审核成功
		TUserExchange tUserExchange1=userExchangeDao.getExchangeByCode(info,tUser);
		info.setExchangeStatus(2);//已消费
		TUserExchange tUserExchange2=userExchangeDao.getExchangeByCode(info,tUser);
		if(EmptyUtils.isNotEmpty(tUserExchange1)){
			tUserExchange1.setExchangeStatus(2);
			update(tUserExchange1);			
			msg="兑换成功";			
		}else if(EmptyUtils.isNotEmpty(tUserExchange2)){
			msg="该兑换码已经被使用过";			
		}else {
			msg="兑换失败";		
		}
		return msg;
	}
}
