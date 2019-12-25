package com.ruanyun.web.controller.sys.background;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.controller.sys.app.ChannelClassification;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.service.app.AppUserService;
import com.ruanyun.web.service.background.AdverEffectiveInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserappidAdveridService;
import com.ruanyun.web.util.ArithUtil;

@Controller
@RequestMapping("adverEffectiveInfo")
public class AdverEffectiveInfoController extends BaseController
{
	@Autowired
	private AdverEffectiveInfoService adverEffectiveInfoService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	@Autowired
	private UserappidAdveridService userappidAdveridService;
	@Autowired
	private AppUserService appUserService;
	
	@RequestMapping("list")
	public String getAdverEffectiveInfoList(Page<TUserappidAdverid> page,String ip,String idfa,
			String adid,String advername,Model model)
	{
		//说明针对特定任务进行的查询
		Page<TChannelAdverInfo> queryAdver = new Page<TChannelAdverInfo>();
		List<TUserappidAdverid> adverCompleteList = new ArrayList<TUserappidAdverid>();
		TChannelAdverInfo adverinfo = new TChannelAdverInfo();
		adverinfo.setAdid(adid);
		adverinfo.setAdverName(advername);
		//符合条件的所有今日任务
		if(EmptyUtils.isNotEmpty(adid) || EmptyUtils.isNotEmpty(advername)) 
		{
			queryAdver = channelAdverInfoService.queryAdverList(queryAdver, adverinfo, ChannelClassification.GetYestdayDate());
			
			for(TChannelAdverInfo info : queryAdver.getResult()) 
			{
				String tablename = "t_adver_"+ info.getChannelNum() + "_" + info.getAdid();	
				TUserappidAdverid tas = new TUserappidAdverid();
				tas.setAdverId(info.getAdverId());
				tas.setIdfa(idfa);
				tas.setIp(ip);
				//每个任务每个idfa只能有一个任务 queryMission
				page = userappidAdveridService.queryMission(page,tas, tablename);
				for(TUserappidAdverid ta : page.getResult()) 
				{
					ta.setAdverName(info.getAdverName());
					ta.setAdverPrice(ArithUtil.subf(info.getAdverPrice(), info.getPriceDiff()));
					
					TUserApp userApp = appUserService.get(TUserApp.class, "userAppId", ta.getUserAppId());
					if(userApp != null) 
					{
						ta.setLoginName(userApp.getLoginName());
					}
				}
				
				adverCompleteList.addAll(page.getResult());
			}
		}
		
		Collections.sort(adverCompleteList, new Comparator<Object>()
		{
		     @Override
		     public int compare(Object o1, Object o2) {
		    	 TUserappidAdverid e1 = (TUserappidAdverid) o1;
		    	 TUserappidAdverid e2 = (TUserappidAdverid) o2;
		         return e2.getReceiveTime().compareTo(e1.getReceiveTime());
		     }
		 });
		
		page.setResult(adverCompleteList);
		addModel(model, "pageList", page);
		addModel(model, "ip", ip);
		addModel(model, "idfa", idfa);
		addModel(model, "adid", adid);
		addModel(model, "advername", advername);
		return "pc/adverEffectiveInfo/list";
	}
	
	@RequestMapping("completeList")
	public String completeList(Page<TUserappidAdverid> page,TUserappidAdverid info,Model model)
	{
		page.setNumPerPage(50);
		addModel(model, "pageList", adverEffectiveInfoService.getTaskList(page, info));
		addModel(model, "bean", info);
		return "pc/adverEffectiveInfo/list";
	}
	
	/**
	 * 员工idfa统计
	 */
	@RequestMapping("employeeIdfaStatistics")
	public String employeeIdfaStatistics(Page<TUserappidAdverid> page, Integer userAppId,String completeTime, Model model)
	{
		//符合条件的所有今日任务
		Page<TChannelAdverInfo> queryAdver = new Page<TChannelAdverInfo>();
		List<TUserappidAdverid> adverCompleteList = new ArrayList<TUserappidAdverid>();
		TChannelAdverInfo adverinfo = new TChannelAdverInfo();
		TUserappidAdverid tas = new TUserappidAdverid();
		tas.setUserAppId(userAppId);
	    queryAdver = channelAdverInfoService.queryAdverList(queryAdver, adverinfo, TimeUtil.GetdayDate());
	    for(TChannelAdverInfo info : queryAdver.getResult()) 
		{
			String tablename = "t_adver_"+ info.getChannelNum() + "_" + info.getAdid();	
			tas.setAdverId(info.getAdverId());
			//每个任务每个idfa只能有一个任务 queryMission
			page = userappidAdveridService.queryMission(page,tas,tablename);
			for(TUserappidAdverid ss :page.getResult()) 
			{
				ss.setAdverName(info.getAdverName());
				ss.setAdverPrice(info.getAdverPrice());
			}
			
			adverCompleteList.addAll(page.getResult());
		}
	    Collections.sort(adverCompleteList, new Comparator<Object>()
		{
		     @Override
		     public int compare(Object o1, Object o2) {
		    	 TUserappidAdverid e1 = (TUserappidAdverid) o1;
		    	 TUserappidAdverid e2 = (TUserappidAdverid) o2;
		         return e2.getReceiveTime().compareTo(e1.getReceiveTime());
		     }
		 });
		//pageList
	    page.setResult(adverCompleteList);
	    page.setNumPerPage(Integer.MAX_VALUE);
	    page.setTotalPage(1);
	    page.setTotalCount(adverCompleteList.size());
	    addModel(model, "completeTime", TimeUtil.GetdayDate());
	    addModel(model, "userAppId", userAppId);
	    addModel(model, "pageList", page);
		return "pc/employeeIdfa/list";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
}
