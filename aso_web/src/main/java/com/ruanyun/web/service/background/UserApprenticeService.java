/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.service.background;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.UserApprenticeDao;
import com.ruanyun.web.model.TUserApprentice;

import freemarker.template.EmptyMap;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Service
public class UserApprenticeService extends BaseServiceImpl<TUserApprentice>{

	@Autowired
	@Qualifier("userApprenticeDao")
	private UserApprenticeDao userApprenticeDao;
	
	
	/**
	 * 功能描述: 获取用户的所有上级
	 *
	 * @author yangliu  2016年1月18日 下午3:30:11
	 * 
	 * @param apprenticeUserNum 当前用户的编号
	 * @return
	 */
	public List<TUserApprentice> getListAllByApprenticeUserNum(String apprenticeUserNum){
		return super.getAllByCondition(TUserApprentice.class,"apprenticeUserNum",apprenticeUserNum);
	}
	
	/**
	 * 功能描述: 获取用户的所有徒弟
	 *
	 * @author yangliu  2016年1月18日 下午3:30:11
	 * 
	 * @param apprenticeUserNum 当前用户的编号
	 * @return
	 */
	public List<TUserApprentice> getListAllByUserNum(String userNum){
		return super.getAll(TUserApprentice.class,"userNum",userNum);
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2016年1月18日 下午8:35:55
	 * 
	 * @param list
	 * @param type
	 * @return
	 */
	public String getUserNums(List<TUserApprentice> list,int type){
		if(EmptyUtils.isNotEmpty(list)){
			StringBuffer sb=new StringBuffer();
			for(TUserApprentice userApprentice : list){
				if(type==1){
					sb.append(",'").append(userApprentice.getUserNum()).append("'");
				}
			}
			return sb.substring(1);
		}
		return "";
	}
	
	/**
	 * 功能描述: 保存用户信息
	 *
	 * @author yangliu  2016年1月19日 上午10:50:01
	 * 
	 * @param userNum  用户编号
	 * @param apprenticeUserNum 徒弟编号
	 * @param apprenticeType  徒弟类型
	 */
	public void saveApprentice(String userNum,String apprenticeUserNum,int apprenticeType){
		TUserApprentice userApprentice = new TUserApprentice();
		userApprentice.setApprenticeTime(new Date());
		userApprentice.setApprenticeUserNum(apprenticeUserNum);
		userApprentice.setScore(0f);
		userApprentice.setUserApprenticeType(apprenticeType);
		userApprentice.setUserNum(userNum);
		super.save(userApprentice);
		userApprentice.setUserApprenticeNum(getNewApprenticeNum(userApprentice.getUserApprenticeId()));
	}
	
	public String getNewApprenticeNum(int id){
		return "UA_"+String.format("%08d", id);
	}
}
