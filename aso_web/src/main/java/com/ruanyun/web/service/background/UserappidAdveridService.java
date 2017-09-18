package com.ruanyun.web.service.background;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.service.impl.BaseServiceImpl;
import com.ruanyun.web.dao.sys.background.UserappidAdveridDao;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserappidAdverid;

/**
 *任务service
 */
@Service
public class UserappidAdveridService extends BaseServiceImpl<TUserappidAdverid> {
	
	private static final Log log = LogFactory.getLog(UserappidAdveridService.class);
	
	private static Map<String,Map<String,String>> appleIdMap;
	
	@Autowired
	private UserappidAdveridDao userappidAdveridDao;
	
	public Integer queryMissionCount(TUserappidAdverid info) {
		return userappidAdveridDao.queryMissionCount(info);
	}
	
	public Page<TUserappidAdverid> queryMission(Page<TUserappidAdverid> page, TUserappidAdverid info) {
		return userappidAdveridDao.PageSql(page, info);
	}
	
	public Page<TUserappidAdverid> queryMissionDistinct(Page<TUserappidAdverid> page, TUserappidAdverid info) {
		return userappidAdveridDao.PageSqlDistinct(page, info);
	}
	
	public int updateStatus2OpenApp(TUserappidAdverid info) {
		return userappidAdveridDao.updateStatus2OpenApp(info);
	}
	
	public int updateStatus2Complete(TUserappidAdverid info) {
		return userappidAdveridDao.updateStatus2Complete(info);
	}
	
	/**
	 * 更新超时未完成任务的状态，并返回更新行数
	 */
	public int updateStatus2Invalid(TChannelAdverInfo adverInfo) {
		return userappidAdveridDao.updateStatus2Invalid(adverInfo);
	}
	
	public Page<TUserappidAdverid> getTasks(String adid, String idfa, String ip) {
		return userappidAdveridDao.getTasks(adid, idfa, ip);
	}
	
	/**
	 * 检查appleId是否已经使用
	 */
	public boolean checkAppleIdIsUsed(String adid, String appleId) {
		if(!StringUtils.hasText(adid) || !StringUtils.hasText(appleId)){
			return false;
		}
		
		if(appleIdMap == null)
		{
			synchronized(this)
			{
				if(appleIdMap == null)
				{
					appleIdMap = userappidAdveridDao.getAppleIdMap();
				}
			}
		}
		
		if(System.currentTimeMillis()/1000%100 == 0){
			log.error("缓存数量=" + appleIdMap.size());
			for(Map.Entry<String, Map<String,String>> entry:appleIdMap.entrySet()){
				log.error("缓存key=" + entry.getKey()+"，value数量=" + entry.getValue().size());
			}
		}
		
		Map<String,String> subMap = appleIdMap.get(adid);
		if(subMap == null){
			return false;
		}else{
			return subMap.containsKey(appleId);
		}
	}
	
	/**
	 * 更新appleIdMap
	 */
	public void renewAppleIdMap(String adid, String appleId) {
		if(!StringUtils.hasText(adid) || !StringUtils.hasText(appleId)){
			return;
		}
		
		if(appleIdMap == null)
		{
			synchronized(this)
			{
				if(appleIdMap == null)
				{
					appleIdMap = userappidAdveridDao.getAppleIdMap();
				}
			}
		}
		
		Map<String,String> subMap = appleIdMap.get(adid);
		if(subMap != null)
		{
			subMap.put(appleId, "");
		}
		else
		{
			subMap = new ConcurrentHashMap<String,String>(4096);
			subMap.put(appleId, "");
			appleIdMap.put(adid, subMap);
		}
	}
	
	/**
	 * 清空appleIdMap
	 */
	public void clearAppleIdMap() {
		appleIdMap = null;
	}
	
}
