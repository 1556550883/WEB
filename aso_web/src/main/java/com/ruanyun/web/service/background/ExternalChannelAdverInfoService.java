package com.ruanyun.web.service.background;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.dao.sys.background.ExternalChannelAdverInfoDao;
import com.ruanyun.web.model.TExternalChannelAdverInfo;
import com.ruanyun.web.model.TExternalChannelInfo;
import com.ruanyun.web.util.ExcelUtils;

@Service
public class ExternalChannelAdverInfoService extends BaseServiceImpl<TExternalChannelAdverInfo> 
{
	@Autowired
	@Qualifier("externalChannelAdverInfoDao")
	private ExternalChannelAdverInfoDao externalChannelAdverInfoDao;
	
	public Page<TExternalChannelAdverInfo> queryPageList(Page<TExternalChannelAdverInfo> page, TExternalChannelAdverInfo t) 
	{
		return externalChannelAdverInfoDao.PageSql3(page, t.getExternalChannelNum());
	}
	
	public TExternalChannelAdverInfo getInfoById(Integer id) 
	{
		TExternalChannelAdverInfo info = super.get(TExternalChannelAdverInfo.class, id);
		
		return info;
	}
	
	public TExternalChannelAdverInfo getInfoByAdidAndKey(String cpChannelKey, String adid) 
	{
		return externalChannelAdverInfoDao.getTExternalChannelAdverInfo(cpChannelKey, adid);
	}
	
	/**
	 * 功能描述：增加或者修改类型
	 * 
	 * @param t
	 */
	public Integer saveOrupdate(TExternalChannelAdverInfo info, TExternalChannelInfo tExternalChannelInfo) 
	{
		if (info != null) 
		{
			if (EmptyUtils.isNotEmpty(info.getExternalAdverId()) && info.getExternalAdverId() != 0) 
			{
				TExternalChannelAdverInfo n = super.get(TExternalChannelAdverInfo.class, info.getExternalAdverId());
				BeanUtils.copyProperties(info, n, new String[] { "externalChannelAdverName","externalChannelAdverDesc","externalChannelNum","externalAdverCreatetime"
						,"externalAdverTimeStart","externalAdverTimeEnd"});
				externalChannelAdverInfoDao.update(n);
			}
			else 
			{
				info.setExternalAdverCreatetime(new Date());
				externalChannelAdverInfoDao.save(info);
				int result = createAdverTable(info.getAdid(), tExternalChannelInfo.getExternalChannelKey());
				if(result == -1) 
				{
					return -1;
				}
			}
		}
		
		return 1;
	}
	
	//创建对应的广告表：adid+key
	public int createAdverTable(String adid, String key) 
	{
		return externalChannelAdverInfoDao.createAdverTable(adid, key);
	}
	
	public TExternalChannelAdverInfo getExternalChannelAdverInfo(Integer externalAdverId) 
	{
		return super.get(TExternalChannelAdverInfo.class, "externalAdverId", externalAdverId);
	}
	
	
	
	public void exprotIDFA(HttpServletResponse response, String  adverIds, TExternalChannelAdverInfo t, TExternalChannelInfo externalChannelInfo)
	{
		List list = externalChannelAdverInfoDao.exportExcel(adverIds, t,externalChannelInfo);
		String fileName = "IDFA";
		//ip,idfa,keywords
		String[] columns = {"ip","idfa","keywords","complete_time"};
		String[] headers = {"IP","IDFA","关键词","完成时间"};
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
