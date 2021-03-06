/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-13
 */
package com.ruanyun.web.service.app;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.dao.sys.ChannelAdverStepDao;
import com.ruanyun.web.dao.sys.background.ChannelAdverInfoDao;
import com.ruanyun.web.dao.sys.background.UserAppDao;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserappidAdverid;
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
	
//	@Autowired
//	private ChannelInfoService channelInfoService;

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
	
	public List<TChannelAdverInfo>  queryAllStartAdvers()
	{
		return channelAdverInfoDao.queryAllStartAdvers();
	}

	public List<TChannelAdverInfo>  queryAllStartAdversGroup()
	{
		return channelAdverInfoDao.queryAllStartAdversGroup();
	}
	/**
	 * 获取广告列表
	 */
	public AppCommonModel getAdverInfoByChannelNum2(Page<TChannelAdverInfo> page, 
			String channelType,String systemType,String phoneType,Integer userAppId, String osversion)
	{
		AppCommonModel model = new AppCommonModel(1, "查询成功");
		TUserApp tUserApp = userAppDao.get(TUserApp.class, "userAppId", userAppId);
		Page<TChannelAdverInfo> page2 = channelAdverInfoDao.PageSql2(page, channelType, systemType, phoneType, tUserApp.getLevel(), osversion, tUserApp.getUserApppType());
		//此处应该放入缓存
		//List<TChannelInfo> channels =  channelInfoService.getAll(TChannelInfo.class);
		
		Page<TUserappidAdverid> taskList = null;
		//保存正在进行的任务
		List<TChannelAdverInfo> advering =  new ArrayList<>();
		if(tUserApp.getUserApppType() == 2) 
		{
			//获取散户正在进行中的任务
			//taskList = userappidAdveridService.getTasking(tUserApp.getIdfa());
		}
		if(page2 != null && page2.getResult() != null)
		{
			Iterator<TChannelAdverInfo> iterator = page2.getResult().iterator();
			while(iterator.hasNext())
			{
				TChannelAdverInfo adver = iterator.next();
				adver.setUserStatus(0);
				
				if((tUserApp.getUserApppType() <= 1 && adver.getAdverStatus() == 2) || (adver.getAdverStatus() == 0))
				{
					iterator.remove();
					continue;
				}
				
				adver.setRemark(adver.getChannelNum()+"-" + adver.getRemark());
				
//				for(TChannelInfo ss :channels) 
//				{
//					if(ss.getChannelNum().equals(adver.getChannelNum())) 
//					{
//						adver.setRemark(ss.getChannelName().substring(0,1)+"-" + adver.getRemark());
//					}
//				}
//				if(adver.getChannelNum().equals("25"))
//				{
//					if(adver.getRemark() != null )
//					{
//						adver.setRemark("*" + adver.getRemark());
//					}
//					else
//					{
//						adver.setRemark("*");
//					}
//				}
				
				if(tUserApp.getUserApppType() <= 1)
				{
					float s = ArithUtil.subf(adver.getAdverPrice(), adver.getPriceDiff());
					adver.setAdverPrice(s);
				}
				
				if (tUserApp.getUserApppType() == 2 && taskList != null && taskList.getResult() != null) {
					for(TUserappidAdverid adverid:taskList.getResult()) 
					{
						if(adverid.getAdverId().equals(adver.getAdverId())) {
							if(adverid.getStatus().equals("1")) {
								//进行中
								adver.setUserStatus(1);
								//保存正在进行的任务
								iterator.remove();
								advering.add(adver);
							}else if(adverid.getStatus().equals("1.5")) {
								//进行中
								adver.setUserStatus(2);
								//等待回调中
								if(adver.getTaskType().equals("1")) {
									adver.setUserStatus(3);
								}
								//保存正在进行的任务
								iterator.remove();
								advering.add(adver);
							}
						}
					}
				}
			}
			
			page2.getResult().addAll(0, advering);
			page2.setTotalCount(page2.getResult().size());
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());
		page2.setFlag(date);
		//放置一个10000任务来检测idfa是否正常
		TChannelAdverInfo spadver = new TChannelAdverInfo();
		spadver.setAddTask(0);
		spadver.setAddTaskLimit(0);
		spadver.setAdverId(10000);
		spadver.setAdid("10000");
		spadver.setAdverActivationCount(1000);
		spadver.setAdverAdid("10000");
		spadver.setAdverCount(1000);
		spadver.setAdverCountComplete(0);
		spadver.setAdverCountRemain(1000);
		spadver.setAdverCreatetime(TimeUtil.bHourToNowDate(1));
		spadver.setAdverDayStart(TimeUtil.bHourToNowDate(1));
		spadver.setAdverDayEnd(TimeUtil.pHourToNowDate(1));
		spadver.setAdverDesc("1");
		spadver.setAdverInter(0);
		spadver.setAdverName("特殊任务");
		spadver.setAdverNum("ADVER_0000001000");
		spadver.setAdverPrice(0f);
		
		spadver.setAdverSort(0);
		
		spadver.setAdverStatus(1);
		
		spadver.setAdverStatusEnd(0);
		spadver.setAdverStepCount(0);
		spadver.setAdverTimeEnd(TimeUtil.beforeHourToNowDate(-1));
		spadver.setAdverTimeStart(TimeUtil.beforeHourToNowDate(1));
		
		spadver.setChannelNum("25");
		
		spadver.setDownloadCount(0);
		spadver.setDownloadType(0);
		
		spadver.setIosVersion(12);
		
		spadver.setIsAdverInter(0);
		spadver.setIsAuth(3);
		
		spadver.setIsIpLimitEnabled(0);
		
		spadver.setIsMock(0);
		spadver.setIsOpen(1);
		spadver.setIsRegister(0);
		spadver.setIsToday(0);
		spadver.setIsTrue(0);
		spadver.setLevel(1);
		
		spadver.setOpenTime(11);
		spadver.setPhoneModelPercent(0);
		spadver.setPhoneType(8);	
		spadver.setPriceDiff(0f);
		spadver.setRandom(1f);
		spadver.setReceInterTime(0);
		spadver.setRemark("查询");
		spadver.setSubmitInterTime(0);
		
		spadver.setTaskType("0");
		//spadver.set
		
		spadver.setAdverActivationCount(1000);
		
		spadver.setAdverCountRemain(1000);
		
		spadver.setTaskInterval(0);
		
		
		spadver.setBundleId("10000");
		spadver.setTimeLimit(0f);
		spadver.setUserStatus(0);
		
		page2.getResult().add(0,spadver);
		
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
	
	/**
	 * 广告完成数量增加一
	 */
	public int updateAdverDownloadCountAdd1(TChannelAdverInfo adverInfo)
	{
		return channelAdverInfoDao.updateAdverDownloadCountAdd1(adverInfo);
	}
	
	public int updateAdverActivationRemainMinus1(TChannelAdverInfo adverInfo)
	{
		return channelAdverInfoDao.updateAdverActivationRemainMinus1(adverInfo);
	}
	/**
	 * 广告剩余数量更新
	 */
	public int updateAdverCountAndRemain(String tablename , TChannelAdverInfo adverInfo,int addTask) 
	{
		return channelAdverInfoDao.updateAdverCountAndRemain(tablename,adverInfo,addTask);
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
		String[] adverids = adverIds.split(",");
		List list =  new ArrayList<>();
		for(String i:adverids) 
		{
			TChannelAdverInfo adverInfo = get(TChannelAdverInfo.class, "adverId", Integer.valueOf(i));
			String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();	
			list.addAll(channelAdverInfoDao.exportExcel(tablename, i));
		}
		
		String fileName = "IDFA";
		String[] columns = {"idfa","ip","ip_localtion","complete_time","adver_name"};
		String[] headers = {"IDFA","IP","地区","结束时间","关键词"};
		try {
			ExcelUtils.exportExcel(response, fileName, list, columns, headers,
			SysCode.DATE_FORMAT_STR_L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//CSVUtils.exportCsv(fileName, headers, columns, list);
	}
	
	  public static List removeDuplicationByHashSet(List<String> list) {
	      HashSet set = new HashSet(list);
	      //把List集合所有元素清空
	      list.clear();
	      //把HashSet对象添加至List集合
	      list.addAll(set);
	      return list;
	  }
	  
	public void exprotIDFA1(HttpServletResponse response,String date1,String date2)
	{
		List<TChannelAdverInfo> adverInfos = channelAdverInfoDao.getadverlists();
		List list =  new ArrayList<>();
		for(TChannelAdverInfo adverInfo : adverInfos) 
		{
			try {
				String tablename = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();
				list.addAll(channelAdverInfoDao.exportExcel1(tablename,date1,date2));
			}catch(Exception e) 
			{
				//e.printStackTrace();
			}
		}
		
		list = removeDuplicationByHashSet(list);
		String fileName = "IDFA";
		String[] columns = {"idfa"};
		String[] headers = {"IDFA"};
		try {
			ExcelUtils.exportExcel(response, fileName, list, columns, headers,
			SysCode.DATE_FORMAT_STR_L);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//CSVUtils.exportCsv(fileName, headers, columns, list);
	}
	
	public void exprotAdver(HttpServletResponse response, String channelNum, String day)
	{
		List list = channelAdverInfoDao.exportAdverInfoExcel(channelNum, day);
		String fileName = "task";
		String[] columns = {"adver_name","adver_count","download_count"};
		String[] headers = {"关键词","任务数量","完成数量"};
		try {
			ExcelUtils.exportExcel(response, fileName, list, columns, headers,
			SysCode.DATE_FORMAT_STR_L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportMonthAdver(HttpServletResponse response, String channelNum, String month)
	{
		List list = channelAdverInfoDao.exportMonthAdver(channelNum, month);
		String fileName = "adverDetail";
		String[] columns = {"adid","adver_name","adver_count","download_count","adver_createtime"};
		String[] headers = {"adid","关键词","任务数量","完成数量","任务创建时间"};
		try {
			ExcelUtils.exportExcel(response, fileName, list, columns, headers,
			SysCode.DATE_FORMAT_STR_L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void releaseIp(String channelNum) 
	{
		channelAdverInfoDao.releaseIp(channelNum);
	}
}
