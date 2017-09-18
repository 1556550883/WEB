/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TChannelAdverStepUser;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Repository("channelAdverStepUserDao")
public class ChannelAdverStepUserDao extends BaseDaoImpl<TChannelAdverStepUser>{

	@Override
	protected String queryPageSql(TChannelAdverStepUser t, Map<String, Object> params) {
		StringBuffer sql=new StringBuffer(" from TChannelAdverStepUser where 1=1 ");
		if(EmptyUtils.isNotEmpty(t)){
			sql.append(SQLUtils.popuHqlEq("idfa", t.getIdfa()));
			sql.append(SQLUtils.popuHqlEq("ip", t.getIp()));
		}
		sql.append(" order by adverUserStepId desc ");
		return sql.toString();
	}
	
	/**
	 * 获取最新的任务记录
	 *
	 * @author yangliu  
	 * 
	 * @param adverNum 广告编号
	 * @param userNum 用户编号
	 * @return
	 */
	public TChannelAdverStepUser getAdverStepUser(TChannelAdverStepUser adverStepUser){
		StringBuffer sql = new StringBuffer("select * from t_channel_adver_step_user where 1=1");
		sql.append(SQLUtils.popuHqlEq("adver_num", adverStepUser.getAdverNum()));
		sql.append(SQLUtils.popuHqlEq("user_app_num", adverStepUser.getUserAppNum()));
		sql.append(SQLUtils.popuHqlEq("phone_serial_number", adverStepUser.getPhoneSerialNumber())); 
		sql.append(SQLUtils.popuHqlEq("adver_step_num", adverStepUser.getAdverStepNum())); 
		sql.append(" order by adver_user_step_id desc limit 0,1;");
		return sqlDao.get(TChannelAdverStepUser.class,sql.toString());
	}
	
	/**
	 * 功能描述:
	 *
	 * @author yangliu  2016年1月21日 下午8:56:26
	 * 
	 * @param page
	 * @param info
	 * @return
	 */
	public Page<TChannelAdverStepUser> pageSql(Page<TChannelAdverStepUser>page,TChannelAdverStepUser info){
		StringBuffer sql=new StringBuffer(" SELECT * from t_channel_adver_step_user WHERE 1=1");
		if (EmptyUtils.isNotEmpty(info.getUserAppNum())) {			
			sql.append("  AND user_app_num='"+info.getUserAppNum()+"'");
		}
		sql.append(" ORDER BY createtime DESC");
		return sqlDao.queryPage(page, TChannelAdverStepUser.class, sql.toString());
	}
	
}
