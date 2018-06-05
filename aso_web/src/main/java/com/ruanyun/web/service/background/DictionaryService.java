package com.ruanyun.web.service.background;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.dao.sys.background.DictionaryDao;
import com.ruanyun.web.model.sys.TDictionary;

@Service("dictionaryService")
public class DictionaryService extends BaseServiceImpl<TDictionary>
{
	@Autowired
	private DictionaryDao dictionaryDao;
	
	private static String appleIdCheck = "-1";
	
	private static Integer leastTaskTime = -1;
	
	private static Integer leastForward = 20;
	
	private static String notice = "目前没消息！";
	
	private static String downloadUrl = "none";
	
	private static Integer vestorLevel = 6;
	/**
	 * 功能描述:查询字典表 父级
	 * @author wsp  2016-10-20 下午05:48:44
	 * @return
	 */
	public List<TDictionary> getList()
	{
		return dictionaryDao.getList();
	}
	
	/**
	 * 修改系统参数
	 */
	public void updateSystemParameter(String appleIdCheck2, Integer leastTaskTime2, Integer leastForward2, String notice2, 
			String downloadUrl2, Integer vestorLevel2)
	{
		if(StringUtils.hasText(appleIdCheck2))
		{
			TDictionary oldInfo = super.get(TDictionary.class, "parentCode", "APPLE_ID_CHECK");
			oldInfo.setItemCode(appleIdCheck2);
			super.update(oldInfo);
			appleIdCheck = appleIdCheck2;
		}
		
		if(leastTaskTime2 != null && leastTaskTime2 > 0)
		{
			TDictionary oldInfo = super.get(TDictionary.class, "parentCode", "LEAST_TASK_TIME");
			oldInfo.setItemCode(String.valueOf(leastTaskTime2));
			super.update(oldInfo);
			leastTaskTime= leastTaskTime2;
		}
		
		if(leastForward2 != null && leastForward2 > 0)
		{
			TDictionary oldInfo = super.get(TDictionary.class, "parentCode", "LEAST_FORWARD");
			oldInfo.setItemCode(String.valueOf(leastForward2));
			super.update(oldInfo);
			leastForward= leastForward2;
		}
		
		if(StringUtils.hasText(notice2))
		{
			TDictionary oldInfo = super.get(TDictionary.class, "parentCode", "NOTICE");
			oldInfo.setItemCode(notice2);
			super.update(oldInfo);
			notice = notice2;
		}
		
		if(StringUtils.hasText(downloadUrl2))
		{
			TDictionary oldInfo = super.get(TDictionary.class, "parentCode", "DOWNLOAD_URL");
			oldInfo.setItemCode(downloadUrl2);
			super.update(oldInfo);
			downloadUrl = downloadUrl2;
		}
		
		
		if(vestorLevel2 != null && vestorLevel2 > 0)
		{
			TDictionary oldInfo = super.get(TDictionary.class, "parentCode", "VESTOR_LEVEL");
			oldInfo.setItemCode(String.valueOf(vestorLevel2));
			super.update(oldInfo);
			vestorLevel= leastTaskTime2;
		}
	}
	
	/**
	 * 查询appleId排重开关
	 */
	public String getAppleIdCheck()
	{
		if(appleIdCheck.equals("-1")) 
		{
			TDictionary dictionary = super.get(TDictionary.class, "parentCode", "APPLE_ID_CHECK");
			if (dictionary == null) 
			{
				appleIdCheck = "0";
			}
			else 
			{
				appleIdCheck = dictionary.getItemCode();
			}
		}
		
		return appleIdCheck;
	}
	
	/**
	 * 查询app最少体验时间
	 */
	public Integer getLeastTaskTime()
	{
		if(leastTaskTime == -1)
		{
			TDictionary dictionary = super.get(TDictionary.class, "parentCode", "LEAST_TASK_TIME");
			if (dictionary == null)
			{
				leastTaskTime = 180;//默认180秒
			}
			else
			{
				leastTaskTime = Integer.valueOf(dictionary.getItemCode());
			}
		}
		
		return leastTaskTime;
	}

	public Integer getLeastForward()
	{
		TDictionary dictionary = super.get(TDictionary.class, "parentCode", "LEAST_FORWARD");
		if (dictionary == null)
		{
			leastForward = 20;//默认20元
		}
		else
		{
			leastForward = Integer.valueOf(dictionary.getItemCode());
		}
		
		return leastForward;
	}
	
	public String getNotice()
	{
		TDictionary dictionary = super.get(TDictionary.class, "parentCode", "NOTICE");
		if (dictionary == null)
		{
			notice = "没消息！";
		}
		else
		{
			notice = dictionary.getItemCode();
		}
		
		return notice;
	}
	
	
	public String getDownloadUrl()
	{
		TDictionary dictionary = super.get(TDictionary.class, "parentCode", "DOWNLOAD_URL");
		if (dictionary == null)
		{
			downloadUrl = "未获取到下载地址！";
		}
		else
		{
			downloadUrl = dictionary.getItemCode();
		}
		
		return downloadUrl;
	}
	
	public Integer getVestorLevel()
	{
		if(leastTaskTime == -1)
		{
			TDictionary dictionary = super.get(TDictionary.class, "parentCode", "VESTOR_LEVEL");
			if (dictionary == null)
			{
				vestorLevel = 6;//默认180秒
			}
			else
			{
				vestorLevel = Integer.valueOf(dictionary.getItemCode());
			}
		}
		
		return vestorLevel;
	}
	/**
	 * 功能描述:按条件查询
	 * @author wsp  2016-11-24 上午09:12:31
	 * @param dictionary
	 * @return
	 */
	public List<TDictionary> getDictionaryList(TDictionary dictionary)
	{
		return dictionaryDao.getList(dictionary);
	}
	
	/**
	 * 功能描述:将 TDictionary 封装到  map中  key itemCode   value TDictionary
	 * @author wsp  2016-12-10 下午07:33:47
	 * @param parentCode
	 * @param itemCodes
	 * @return
	 */
	public Map<Integer, TDictionary> getDictionaryByitemCodes(String parentCode,String itemCodes)
	{
		return dictionaryDao.getDictionaryByitemCodes(parentCode,itemCodes);
	}

	/**
	 * 功能描述:获取最大排序值
	 * @author wsp  2016-12-22 下午05:26:45
	 * @param dictionary
	 * @return
	 */
	public int getOrderby(TDictionary dictionary)
	{
		if(EmptyUtils.isEmpty(dictionary.getParentCode()))
			return 1;
		return dictionaryDao.getOrderby(dictionary);
	}
	
}