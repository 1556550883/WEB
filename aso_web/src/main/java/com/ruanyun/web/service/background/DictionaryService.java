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
	public void updateSystemParameter(String appleIdCheck2, Integer leastTaskTime2)
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