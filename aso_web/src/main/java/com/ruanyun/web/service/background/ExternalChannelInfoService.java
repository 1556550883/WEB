package com.ruanyun.web.service.background;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.ExternalChannelAdverInfoDao;
import com.ruanyun.web.dao.sys.background.ExternalChannelInfoDao;
import com.ruanyun.web.model.TExternalChannelAdverInfo;
import com.ruanyun.web.model.TExternalChannelAdverTaskInfo;
import com.ruanyun.web.model.TExternalChannelInfo;
import com.ruanyun.web.model.TExternalChannelTask;
import com.ruanyun.web.util.NumUtils;

@Service
public class ExternalChannelInfoService extends BaseServiceImpl<TExternalChannelInfo> 
{
	@Autowired
	@Qualifier("externalChannelInfoDao")
	private ExternalChannelInfoDao externalChannelInfoDao;
	@Autowired
	@Qualifier("externalChannelAdverInfoDao")
	private ExternalChannelAdverInfoDao externalChannelAdverInfoDao;
	@Override
	public Page<TExternalChannelInfo> queryPage(Page<TExternalChannelInfo> page, TExternalChannelInfo t) 
	{
		return externalChannelInfoDao.queryPage(page, t);
	}
	
	/**
	 * 
	 * 功能描述:更改启动状态
	 * @param id
	 * @param isEnable 0/1 未启用/启用
	 * @return
	 */
	public int updateIsEnable(Integer id,Integer isEnable)
	{
		TExternalChannelInfo oldChannelInfo = get(TExternalChannelInfo.class, id);
		if (EmptyUtils.isNotEmpty(oldChannelInfo)) 
		{
			oldChannelInfo.setIsEnable(isEnable);
			update(oldChannelInfo);
			return 1;
		}
		
		return 0;
	}
	
	

	/**
	 * 
	 * 功能描述:根据Id获得详细信息
	 * @param id
	 * @return
	 */
	public TExternalChannelInfo getInfoById(Integer id) 
	{
		TExternalChannelInfo info = super.get(TExternalChannelInfo.class, id);
		
		return info;
	}
	
	/**
	 * 
	 * 功能描述:根据Id获得详细信息
	 * @param id
	 * @return
	 */
	public TExternalChannelInfo getInfoByChannelNum(String externalChannelNum) 
	{
		TExternalChannelInfo info = super.get(TExternalChannelInfo.class, "externalChannelNum", externalChannelNum);
		
		return info;
	}
	
	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TExternalChannelInfo info) 
	{
		if (info != null) 
		{
			if (EmptyUtils.isNotEmpty(info.getExternalChannelId()) && info.getExternalChannelId() != 0) 
			{
				TExternalChannelInfo n = super.get(TExternalChannelInfo.class, info.getExternalChannelId());
				BeanUtils.copyProperties(info, n, new String[] { "externalChannelName","externalChannelDesc"});
				externalChannelInfoDao.update(n);
			}
			else 
			{
				info.setCreateDate(new Date());
				info.setIsEnable(0);//默认不启用
				info.setExternalChannelKey(getRandomString(15));
				externalChannelInfoDao.save(info);
				info.setExternalChannelNum(NumUtils.getCommondNum(NumUtils.CHANNEL_INFO, info.getExternalChannelId()));
			}
		}
		
		return 1;
	}
	
	public TExternalChannelTask getExternalChannelTask(String adverId) 
	{
		return super.get(TExternalChannelTask.class, "adverId", adverId);
	}
	
	/**
	 * 广告完成情况
	 */
	public Page<TExternalChannelAdverTaskInfo> completeListInfo(Page<TExternalChannelAdverTaskInfo> page, TExternalChannelInfo externalChannelInfo, TExternalChannelAdverInfo t)
	{
		return	externalChannelAdverInfoDao.completeListInfo(page, externalChannelInfo, t);
	}
	
	/**
	 * 广告完成详情
	 */
	public Page<TExternalChannelTask> adverCompleteInfo(Page<TExternalChannelTask> page, TExternalChannelInfo externalChannelInfo, TExternalChannelAdverInfo t)
	{
		return	externalChannelAdverInfoDao.adverCompleteInfo(page, externalChannelInfo, t);
	}
	
	
	 public static String getRandomString(int length){
	     String str="abcdefghijklmnopqrstuvwxyz0123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(36);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
}
