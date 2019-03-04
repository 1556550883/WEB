package com.ruanyun.web.dao.sys.background;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TChannelAdverUser;
@Repository
public class ChannelAdverUserDao extends BaseDaoImpl<TChannelAdverUser> {
	
	/**
	 * 功能描述: 获取最新的一条数据
	 *
	 * @author yangliu  2016年1月21日 下午12:29:13
	 * 
	 * @param adverNum 广告编号
	 * @param userNum 用户编号
	 * @param phoneSerialNumber 手机序列号
	 * @return
	 */
	public TChannelAdverUser getChannelAdverUser(String adverNum,String userNum,String phoneSerialNumber){
		StringBuffer sql = new StringBuffer("SELECT  * FROM t_channel_adver_user where 1=1 ");
		sql.append(SQLUtils.popuHqlEq("adver_num", adverNum));
		sql.append(SQLUtils.popuHqlEq("user_Num", userNum));
		sql.append(SQLUtils.popuHqlEq("phone_Serial_Number", phoneSerialNumber));
		sql.append(" order by adver_user_id desc limit 0,1");
		return sqlDao.get(TChannelAdverUser.class, sql.toString());
	}

}
