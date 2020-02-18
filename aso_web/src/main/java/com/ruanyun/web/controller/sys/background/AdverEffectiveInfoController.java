package com.ruanyun.web.controller.sys.background;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
		queryAdver.setNumPerPage(Integer.MAX_VALUE);
		List<TUserappidAdverid> adverCompleteList = new ArrayList<TUserappidAdverid>();
		TChannelAdverInfo adverinfo = new TChannelAdverInfo();
		adverinfo.setAdid(adid);
		adverinfo.setAdverName(advername);
		//符合条件的所有今日任务
		if(EmptyUtils.isNotEmpty(idfa) || EmptyUtils.isNotEmpty(ip) || EmptyUtils.isNotEmpty(adid) || EmptyUtils.isNotEmpty(advername)) 
		{
			queryAdver = channelAdverInfoService.queryAdverList(queryAdver, adverinfo, TimeUtil.GetdayDate(-7), "");
			
			for(TChannelAdverInfo info : queryAdver.getResult()) 
			{
				String tablename = "t_adver_"+ info.getChannelNum() + "_" + info.getAdid();	
				TUserappidAdverid tas = new TUserappidAdverid();
				tas.setAdverId(info.getAdverId());
				tas.setIdfa(idfa);
				tas.setIp(ip);
				//每个任务每个idfa只能有一个任务 queryMission
				page = userappidAdveridService.queryMission(page,tas, tablename, TimeUtil.GetdayDate(-7));
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
		addModel(model, "pageList", adverEffectiveInfoService.getTaskList(page, info, TimeUtil.GetdayDate(-7)));
		addModel(model, "bean", info);
		return "pc/adverEffectiveInfo/list";
	}
	
	/**
	 * 员工idfa统计
	 */
	@RequestMapping("employeeIdfaStatistics")
	public String employeeIdfaStatistics(Page<TUserappidAdverid> page, Integer userAppId, Model model, Date endtime)
	{
		String queryAdverStr = TimeUtil.GetdayDate();
		String endtimeStr = "";
		Page<TChannelAdverInfo> queryAdver = new Page<TChannelAdverInfo>();
		List<TUserappidAdverid> adverCompleteList = new ArrayList<TUserappidAdverid>();
		int completeTotal = 0;
		int total = 0;
		queryAdver.setNumPerPage(Integer.MAX_VALUE);
		TChannelAdverInfo adverinfo = new TChannelAdverInfo();
		TUserappidAdverid tas = new TUserappidAdverid();
		tas.setUserAppId(userAppId);
		
		//符合条件的所有今日任务
		if(endtime != null) 
		{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			//System.out.println(formatter.format(endtime));
			//TimeUtil.GetdayDate(endtime, 1);
			queryAdverStr = formatter.format(endtime);
			endtimeStr = TimeUtil.GetdayDate(endtime, 1);
		}
				
		
	    queryAdver = channelAdverInfoService.queryAdverList(queryAdver, adverinfo, queryAdverStr,endtimeStr);
	    for(TChannelAdverInfo info : queryAdver.getResult()) 
		{
			String tablename = "t_adver_"+ info.getChannelNum() + "_" + info.getAdid();	
			tas.setAdverId(info.getAdverId());
			//每个任务每个idfa只能有一个任务 queryMission
			page.setNumPerPage(Integer.MAX_VALUE);
			page = userappidAdveridService.queryMission(page,tas,tablename, queryAdverStr);
			for(TUserappidAdverid ss :page.getResult()) 
			{
				total++;
				if(ss.getStatus().equals("2")) 
				{
					completeTotal++;
				}
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
	    addModel(model, "endtime", endtime);
	    addModel(model, "userAppId", userAppId);
	    addModel(model, "pageList", page);
	    addModel(model, "total", total);
	    addModel(model, "completeTotal", completeTotal);
		return "pc/employeeIdfa/list";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) 
	{
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
}
