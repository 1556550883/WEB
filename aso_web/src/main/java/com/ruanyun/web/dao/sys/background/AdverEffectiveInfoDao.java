/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.dao.sys.background;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SQLUtils;
import com.ruanyun.web.model.TAdverEffectiveInfo;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Repository("adverEffectiveInfoDao")
public class AdverEffectiveInfoDao extends BaseDaoImpl<TAdverEffectiveInfo> {
	@Override
	protected String queryPageSql(TAdverEffectiveInfo t,
			Map<String, Object> params) {
		StringBuffer sql = new StringBuffer(
				" from TAdverEffectiveInfo where 1=1 ");
		if (EmptyUtils.isNotEmpty(t.getAdverNum())) {
			sql.append(" and adverNum='"+t.getAdverNum()+"'");
		}
		if (EmptyUtils.isNotEmpty(t.getAdverName())) {
			sql.append(" and adverName LIKE '%"+t.getAdverName()+"%'");
		}
		if (EmptyUtils.isNotEmpty(t.getChannelName())) {
			sql.append(" and channelName LIKE '%"+t.getChannelName()+"%'");
		}
		if (EmptyUtils.isNotEmpty(t.getUserNum())) {
			sql.append(" and user_num ='" +t.getUserNum()+ "'");
		}
		if(EmptyUtils.isNotEmpty(t.getCreatetime())){
			sql.append(SQLUtils.popuHqlMinDate("createtime", t.getCreatetime()));
			sql.append(SQLUtils.popuHqlMaxDate("createtime", t.getEndtime()));
		}
		return sql.toString();
	}

	/**
	 * 
	 * 手机端接口:获取列表
	 * 
	 * @param page
	 * @param tUserLogin
	 * @return
	 *@author feiyang
	 *@date 2016-1-14
	 */
	public Page<TAdverEffectiveInfo> pageSql(Page<TAdverEffectiveInfo> page, String userNum) 
	{
		StringBuffer sql = new StringBuffer(" SELECT * from t_adver_effective_info WHERE 1=1");
		
		if (EmptyUtils.isNotEmpty(userNum)) 
		{
			sql.append(" and user_num ='" + userNum + "'");
		}
		
		sql.append(" order by createtime desc");
		
		return sqlDao.queryPage(page, TAdverEffectiveInfo.class, sql.toString());
	}
	
	/**
	 * 
	 * 手机端接口:获取最热列表
	 * @return
	 *@author feiyang
	 *@date 2016-1-21
	 */
	public List<TAdverEffectiveInfo> getHotList(){
		StringBuffer sql = new StringBuffer("select taei.adver_num adverNum,sum(1)downSum ,tcai.adver_name adverName,tcai.adver_desc adverDesc,tcai.adver_img adverImg");
		sql.append(" from t_adver_effective_info taei, t_channel_adver_info tcai");
		sql.append(" WHERE taei.adver_num=tcai.adver_num");
		sql.append(SQLUtils.popuHqlMinDate("taei.createtime", new Date()));
		sql.append(SQLUtils.popuHqlMaxDate("taei.createtime", new Date()));
		sql.append(" group by taei.adver_num desc");
		
		return sqlDao.getAll(sql.toString());
	}
	
	/**
	 * 功能描述: 根据条件查询调试
	 *
	 * @author yangliu  2016年1月26日 下午10:42:52
	 * 
	 * @param info 对象
	 * @return
	 */
	public Integer getCountByInfo(TAdverEffectiveInfo info){
		StringBuffer sql = new StringBuffer("select count(1) from t_adver_effective_info where 1=1");
		if(EmptyUtils.isNotEmpty(info)){
			sql.append(SQLUtils.popuHqlEq("channel_num", info.getChannelNum()));
			sql.append(SQLUtils.popuHqlEq("adver_num", info.getAdverNum()));
			sql.append(SQLUtils.popuHqlEq("unique_primary_key", info.getUniquePrimaryKey()));
			sql.append(SQLUtils.popuHqlEq("ip", info.getIp()));
			sql.append(SQLUtils.popuHqlEq("phone_serial_number", info.getPhoneSerialNumber()));
			sql.append(SQLUtils.popuHqlEq("channel_Num", info.getChannelNum()));
		}
		return sqlDao.getCount(sql.toString());
	}
}
