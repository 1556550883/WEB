package com.ruanyun.web.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.ExternalAppDao;
import com.ruanyun.web.model.TExternalChannelTask;

@Service
public class ExternalAppService extends BaseServiceImpl<TExternalChannelTask>
{
	@Autowired
	private ExternalAppDao externalAppDao;
	
	public void save(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		externalAppDao.save(tExternalChannelTask, adid, key);
	}
	
	public TExternalChannelTask geTExternalTaskInfo(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		return externalAppDao.getExternalTaskInfo(tExternalChannelTask, adid, key);
	}
	
	public int update(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		return externalAppDao.update(tExternalChannelTask, adid, key);
	}
	
	
	public int updateStatus(TExternalChannelTask tExternalChannelTask, String adid, String key) 
	{
		return externalAppDao.updateStatus(tExternalChannelTask, adid, key);
	}
}
