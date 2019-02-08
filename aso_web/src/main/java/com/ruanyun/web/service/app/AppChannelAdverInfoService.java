/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.service.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.ChannelAdverStepDao;
import com.ruanyun.web.dao.sys.background.ChannelAdverInfoDao;
import com.ruanyun.web.dao.sys.background.UserAppDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.util.ArithUtil;
import com.ruanyun.web.util.ExcelUtils;

/**
 *@author feiyang
 *@date 2016-1-13
 */
@Service
public class AppChannelAdverInfoService extends BaseServiceImpl<TChannelAdverInfo> 
{
	
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
	 */
	public AppCommonModel getAdverInfoByChannelNum(Page<TChannelAdverInfo> page, TChannelAdverInfo info)
	{
		AppCommonModel model = new AppCommonModel(-1, "无查询结果");
		
		if (EmptyUtils.isNotEmpty(info)) 
		{
			if (EmptyUtils.isNotEmpty(info.getChannelNum()))
			{
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
			String channelType,String systemType,String phoneType,Integer userAppId, String osversion)
	{
		AppCommonModel model = new AppCommonModel(1, "查询成功");
		
		TUserApp tUserApp = userAppDao.get(TUserApp.class, "userAppId", userAppId);
		Page<TChannelAdverInfo> page2 = channelAdverInfoDao.PageSql2(page, channelType, systemType, phoneType, tUserApp.getLevel(), osversion);
		
		if(page2 != null && page2.getResult() != null)
		{
			Iterator<TChannelAdverInfo> iterator = page2.getResult().iterator();
			while(iterator.hasNext())
			{
				TChannelAdverInfo adver = iterator.next();
//				if(StringUtils.hasText(tUserApp.getExcludeAdverId()) && tUserApp.getExcludeAdverId().indexOf(String.valueOf(adver.getAdverId())) >= 0)
//				{
//					iterator.remove();
//				}
				if((tUserApp.getUserApppType() <= 1 && adver.getAdverStatus() == 2) || (adver.getAdverStatus() == 0))
				{
					iterator.remove();
					continue;
				}
				
				if(tUserApp.getUserApppType() <= 1)
				{
					float s = ArithUtil.subf(adver.getAdverPrice(), adver.getPriceDiff());
					adver.setAdverPrice(s);
				}
			}
			page2.setTotalCount(page2.getResult().size());
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		page2.setFlag(date);
		model.setObj(page2);
		return model;
	}

	/**
	 * 
	 * 功能描述:根据编号获取详情
	 * @param info
	 * @return
	 */
	public AppCommonModel getDetailByAdverNum(String adverNum,String userNum) 
	{
		AppCommonModel model = new AppCommonModel(-1, "查询失败");
		if (EmptyUtils.isNotEmpty(adverNum)) 
		{
				model.setMsg("查询成功");
				model.setResult(1);
				TChannelAdverInfo adverInfo = channelAdverInfoDao.getDetailByAdverNum(adverNum);
				adverInfo.setAdverStepList(channelAdverStepDao.getChannelAdverStepList(adverNum,userNum));
				model.setObj(adverInfo);
		}
		
		return model;
	}
	
	/**
	 * 广告剩余数量减1
	 */
	public int updateAdverCountRemainMinus1(TChannelAdverInfo adverInfo)
	{
		return channelAdverInfoDao.updateAdverCountRemainMinus1(adverInfo);
	}
	
	public int updateAdverActivationRemainMinus1(TChannelAdverInfo adverInfo)
	{
		return channelAdverInfoDao.updateAdverActivationRemainMinus1(adverInfo);
	}
	/**
	 * 广告剩余数量更新
	 */
	public int updateAdverCountRemain(TChannelAdverInfo adverInfo) 
	{
		return channelAdverInfoDao.updateAdverCountRemain(adverInfo);
	}
	
	/**
	 * 通用查询
	 */
	public List<TChannelAdverInfo> getByCondition(TChannelAdverInfo adverInfo) 
	{
		return channelAdverInfoDao.getByCondition(adverInfo);
	}
	

	public void exprotIDFA(HttpServletResponse response, String  adverIds)
	{
		List list = channelAdverInfoDao.exportExcel(adverIds);
		String fileName = "IDFA";
		String[] columns = {"idfa","ip","complete_time","adver_name"};
		String[] headers = {"IDFA","IP","结束时间","关键词"};
		try {
			ExcelUtils.exportExcel(response, fileName, list, columns, headers,
			SysCode.DATE_FORMAT_STR_L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//CSVUtils.exportCsv(fileName, headers, columns, list);
	}
}
