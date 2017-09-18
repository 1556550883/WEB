/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-18
 */
package com.ruanyun.web.dao.sys;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ruanyun.common.dao.impl.BaseDaoImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TChannelAdverStep;

/**
 *@author feiyang
 *@date 2016-1-18
 */
@Repository("channelAdverStep")
public class ChannelAdverStepDao extends BaseDaoImpl<TChannelAdverStep>{

	
	public List<TChannelAdverStep> getStep(){
		StringBuffer sql=new  StringBuffer();
		return sqlDao.getAll(TChannelAdverStep.class, sql.toString());
	}
	
	/**
	 * 功能描述: 获取广告想关联的信息
	 *
	 * @author yangliu  2016年1月25日 下午3:11:06
	 * 
	 * @param adverNum
	 * @param userNum
	 * @return
	 */
	public List<Map<String,Object>> getChannelAdverStepList(String adverNum,String userNum){
		if(EmptyUtils.isEmpty(userNum))
			userNum="US";
		String sql="SELECT cas.`adver_step_count_num`,cas.`adver_step_name` adverStepName,cas.step_desc stepDesc,cas.`price` price,IFNULL(casu.`adver_user_step_status`,-2) adverUserStepStatus FROM t_channel_adver_step cas LEFT JOIN t_channel_adver_step_user casu ON cas.adver_step_num=casu.`adver_step_num`  AND casu.`user_app_num`='"+userNum+"'  WHERE cas.`adver_num`='"+adverNum+"' ORDER BY cas.`adver_step_count_num`";
		return sqlDao.getAll(sql);
	}
	
	
}
