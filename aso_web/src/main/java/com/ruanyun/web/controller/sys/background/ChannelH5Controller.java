package com.ruanyun.web.controller.sys.background;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.controller.sys.app.ChannelClassification;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class ChannelH5Controller extends BaseController
{
	@Autowired
	private ChannelInfoService channelInfoService;
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	@Autowired
	private AppChannelAdverInfoService appChannelAdverInfoService;
	
//	String base_url = "http://localhost:8080/sjjz/";
	String base_url = "http://moneyzhuan.com/";

	@RequestMapping("applist")
	public String getChannelInfoAppList(Page<TChannelInfo> page, TChannelInfo info, Model model)
	{
		page = channelInfoService.queryPage(page, info);
		List<TChannelInfo> channels = page.getResult();
		for(TChannelInfo t : channels) {
			Date date = new Date();		
			String dateStr = TimeUtil.doFormatDate(date,"yyyy-MM");
			String dateStrday = TimeUtil.doFormatDate(date,"yyyy-MM-dd");
			t.setMonNum(channelInfoService.calculate(t.getChannelNum(), dateStr,null));
			t.setTodayNum(channelInfoService.calculate(t.getChannelNum(), dateStrday,null));
		}
		
		addModel(model, "pageList", page);
		addModel(model, "bean", info);
		
		return "app/h5adver/applist";
	}
	
	@RequestMapping("appAdverList")
	public String getChannelappAdverInfoList(Page<TChannelAdverInfo> page,TChannelAdverInfo info,Model model)
	{
		Page<TChannelAdverInfo> queryAdver  = channelAdverInfoService.queryAdverList(page, info,ChannelClassification.GetYestdayDate());
		for(TChannelAdverInfo adverInfo : queryAdver.getResult()) {
			//第二天一点之后算完结任务
			if(adverInfo.getAdverDayEnd().getDay() < new Date().getDay() && new Date().getHours() > 1) {
				//不是今天的，属于过期任务
				adverInfo.setIsToday(0);
			}else {
				//当天的任务
				adverInfo.setIsToday(1);
			}
		}
		
		addModel(model, "pageList", queryAdver);
		addModel(model, "bean", info);
		
		return "app/h5adver/appAdverList";
	}
	
	/**
	 * 批量审核
	
	@RequestMapping("changeStatus")
	public void changeStatus(String ids, HttpServletResponse response)
	{
		try
		{
			Integer status = 2;
			//启动的时候产生生产者
			String[] adverIds = ids.split(",");  
			for(String adverId : adverIds) 
			{
				TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
				
				if(adverInfo.getAdverStatus() == 1) 
				{
					status = 2;
				}
				else 
				{
					status = 1;
				}
				
				channelAdverInfoService.updateAdverStatus(status, adverId);
				if(status == 1)
				{
					ArrayBlockQueueProducer.addAdverList.add(adverId);
					if(adverInfo.getReceInterTime() > 0 || adverInfo.getSubmitInterTime() > 0) {
						//如果需要任务间隔，就加入间隔队列
						adverInfo.setSubmiTimeZone(0);
						adverInfo.setReceTimeZone(0);
						ArrayBlockQueueProducer.specialXSAdverList.put(adverId,adverInfo);
					}
				}
				else
				{
					ArrayBlockQueueProducer.removeAdverList.add(adverId);
					//任务停止移除间隔任务队列
					ArrayBlockQueueProducer.specialXSAdverList.remove(adverId);
				}
			}
			
			super.writeJsonData(response, 1);
		}
		catch (Exception e)
		{
			super.writeJsonData(response, -1);
		}
	}
	 */
	
	
	/**
	 * 
	 * 功能描述：修改
	 */
	@RequestMapping("edit")
	public void updatea(TChannelAdverInfo info, HttpServletResponse response, MultipartFile file,
			MultipartFile fileAdverImg, HttpServletRequest request)
	{
		try 
		{
			//广告有效期
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			info.setAdverDayStart(simpleDateFormat.parse(info.getAdverTimeStart()));
			info.setAdverDayEnd(simpleDateFormat.parse(info.getAdverTimeEnd()));
			//任务类型
			if(info.getChannelNum().equals("3") && !info.getTaskType().equals("2"))
			{
				response.sendRedirect(base_url + "applist");
				return;
			}
			
			TChannelAdverInfo oldAdverInfo = channelAdverInfoService.getInfoById(info.getAdverId());
			channelAdverInfoService.saveOrUpd(info,file, request, fileAdverImg,oldAdverInfo);
			
			response.sendRedirect(base_url + "applist");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("toappedit")
	public String toappedit(Integer id, Model model,Integer type,String channelNum) throws Exception
	{
		if(EmptyUtils.isNotEmpty(id))
		{	
			TChannelAdverInfo bean = channelAdverInfoService.getInfoById(id);
			addModel(model, "bean", bean);
			addModel(model, "channelNum", channelNum);
			return "app/h5adver/appeditadver";
		}
		else
		{
			TChannelAdverInfo bean = new TChannelAdverInfo();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date now = new Date();
			String nowtime = sdf.format(now);
			String daytime = nowtime.substring(0,10);
			String endtime = daytime + " 23:59:00";
			bean.setAdverDayStart(sdf.parse(nowtime));
			bean.setAdverDayEnd(sdf.parse(endtime));
			addModel(model, "bean", bean);
			addModel(model, "type", type);
			addModel(model, "channelNum", channelNum);
			return "app/h5adver/appAddAdver";
		}
	}
	
	/**
	 * 
	 * 功能描述：增加
	 */
	@RequestMapping("appadversave")
	public void appadversave(TChannelAdverInfo info, HttpServletResponse response,  MultipartFile file,
			MultipartFile fileAdverImg, HttpServletRequest request)
	{
		try 
		{
			//向轴 add
			//广告有效期
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			info.setAdverDayStart(simpleDateFormat.parse(info.getAdverTimeStart()));
			info.setAdverDayEnd(simpleDateFormat.parse(info.getAdverTimeEnd()));
			if(info.getIsRegister() == null) 
			{
				info.setIsRegister(0);//代表任务不是注册任务 1代表为注册任务
			}
			//任务类型
			if(info.getChannelNum().equals("3") && !info.getTaskType().equals("2"))
			{
				response.sendRedirect(base_url + "applist");
				return;
			}
			
			//批量生成
			JSONArray array = JSONArray.fromObject(info.getAdversJson());
			for(int i = 0; i < array.size(); i++)
			{
				info.setAdverId(null);
				info.setAdverCountRemain(null);
				JSONObject jsonObject = array.getJSONObject(i);
				info.setAdverName(jsonObject.getString("adverName"));
				info.setAdverCount(jsonObject.getInt("adverCount"));
				info.setAdverActivationCount(jsonObject.getInt("adverCount"));
				info.setAdverDesc(jsonObject.getString("adverDesc"));
				channelAdverInfoService.saveOrUpd(info, file, request, fileAdverImg,null);
			}
			
			TChannelAdverInfo adverInfo = new TChannelAdverInfo();
			adverInfo.setAdverAdid(info.getAdverAdid());
			adverInfo.setChannelNum(info.getChannelNum());
			List<TChannelAdverInfo> adverInfos = appChannelAdverInfoService.getByCondition(adverInfo);
			if(adverInfos != null && !adverInfos.isEmpty() && adverInfos.size() >= 1) {
				adverInfo = adverInfos.get(0);
				BeanUtils.copyProperties(info, adverInfo, new String[]{"adverId","adverName",
						"adverCount","adverCountRemain","adverCountComplete","adverDayStart","adverDayEnd","adverTimeStart",
						"adverTimeEnd","adverActivationCount","adverCreatetime","adverStatus","channelNum","adverNum","downloadCount"});
				//adverInfo.set
				//已经存在任务的信息
				channelAdverInfoService.update(adverInfo);
			}
			
			response.sendRedirect(base_url + "applist");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
