/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.service.app;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.ChannelAdverStepDao;
import com.ruanyun.web.dao.sys.background.ChannelAdverInfoDao;
import com.ruanyun.web.dao.sys.background.UserAppDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserApp;

/**
 *@author feiyang
 *@date 2016-1-13
 */
@Service
public class AppChannelAdverInfoService extends
		BaseServiceImpl<TChannelAdverInfo> {
	
	@Autowired
	@Qualifier("channelAdverInfoDao")
	private ChannelAdverInfoDao channelAdverInfoDao;
	@Autowired
	private ChannelAdverStepDao channelAdverStepDao;
	@Autowired
	private UserAppDao userAppDao;

	/**
	 * 
	 * 功能描述:根据广告类型和渠道编号获取广告列表
	 * 
	 * @param page
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	public AppCommonModel getAdverInfoByChannelNum(Page<TChannelAdverInfo> page, TChannelAdverInfo info) {
		AppCommonModel model = new AppCommonModel(-1, "无查询结果");
		if (EmptyUtils.isNotEmpty(info)) {
			if (EmptyUtils.isNotEmpty(info.getChannelNum())) {
				Page<TChannelAdverInfo> page2 = channelAdverInfoDao.PageSql(page, info);
				model.setMsg("查询成功");
				model.setResult(1);
				model.setObj(page2);
			}
		}
		return model;
	}
	
	/**
	 * 获取广告列表
	 */
	public AppCommonModel getAdverInfoByChannelNum2(Page<TChannelAdverInfo> page, 
			String channelType,String systemType,String phoneType,Integer userAppId) {
		AppCommonModel model = new AppCommonModel(1, "查询成功");
		
		Page<TChannelAdverInfo> page2 = channelAdverInfoDao.PageSql2(page, channelType, systemType, phoneType);
		TUserApp tUserApp = userAppDao.get(TUserApp.class, "userAppId", userAppId);
		
		if(page2 != null && page2.getResult() != null)
		{
			Iterator<TChannelAdverInfo> iterator = page2.getResult().iterator();
			while(iterator.hasNext())
			{
				TChannelAdverInfo adver = iterator.next();
				if(StringUtils.hasText(tUserApp.getExcludeAdverId()) && tUserApp.getExcludeAdverId().indexOf(String.valueOf(adver.getAdverId())) >= 0)
				{
					iterator.remove();
				}
			}
			page2.setTotalCount(page2.getResult().size());
		}
		
		model.setObj(page2);
		return model;
	}

	/**
	 * 
	 * 功能描述:根据编号获取详情
	 * @param info
	 * @return
	 *@author feiyang
	 *@date 2016-1-13
	 */
	public AppCommonModel getDetailByAdverNum(String adverNum,String userNum) {
		AppCommonModel model = new AppCommonModel(-1, "查询失败");
		if (EmptyUtils.isNotEmpty(adverNum)) {
				model.setMsg("查询成功");
				model.setResult(1);
				TChannelAdverInfo adverInfo=channelAdverInfoDao.getDetailByAdverNum(adverNum);
				adverInfo.setAdverStepList(channelAdverStepDao.getChannelAdverStepList(adverNum,userNum));
				model.setObj(adverInfo);
		}
		return model;
	}
	
	/**
	 * 广告剩余数量减1
	 */
	public int updateAdverCountRemainMinus1(TChannelAdverInfo adverInfo) {
		return channelAdverInfoDao.updateAdverCountRemainMinus1(adverInfo);
	}
	
	/**
	 * 广告剩余数量更新
	 */
	public int updateAdverCountRemain(TChannelAdverInfo adverInfo) {
		return channelAdverInfoDao.updateAdverCountRemain(adverInfo);
	}
	
	/**
	 * 通用查询
	 */
	public List<TChannelAdverInfo> getByCondition(TChannelAdverInfo adverInfo) {
		return channelAdverInfoDao.getByCondition(adverInfo);
	}
	
}
