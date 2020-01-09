package com.ruanyun.web.controller.sys.background;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.rabbitmq.client.Channel;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TUserappidAdverid;
import com.ruanyun.web.producer.AdverProducer;
import com.ruanyun.web.producer.AdverQueueConsumer;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.AdverEffectiveInfoService;
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
	@Autowired
	private AdverEffectiveInfoService adverEffectiveInfoService;
	
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
	
	@RequestMapping("queryidfa")
	public String queryidfa(Page<TUserappidAdverid> page,TUserappidAdverid info,Model model)
	{
		page.setNumPerPage(Integer.MAX_VALUE);
		addModel(model, "pageList", adverEffectiveInfoService.getTaskList(page, info,TimeUtil.GetdayDate()));
		addModel(model, "bean", info);
		return "app/h5adver/adverEffectList";
	}
	
	@RequestMapping("appAdverList")
	public String getChannelappAdverInfoList(Page<TChannelAdverInfo> page,TChannelAdverInfo info,Model model)
	{
		Page<TChannelAdverInfo> queryAdver  = channelAdverInfoService.queryAdverList(page, info, TimeUtil.GetdayDate(),"");
		for(TChannelAdverInfo adverInfo : queryAdver.getResult()) {
			//第二天一点之后算完结任务

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String str=sdf.format(adverInfo.getAdverCreatetime());
			String yesday = TimeUtil.GetdayDate(-1);
			if(str.compareTo(yesday) < 0)
			{
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
*/
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
				String endPointName = adverInfo.getAdverId() + "_" + adverInfo.getAdverName();
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
					//获取任务剩余的量先生成任务
					//需要获取任务已存在的完成数量和正在进行中的数量
					String tableName = "t_adver_"+ adverInfo.getChannelNum() + "_" + adverInfo.getAdid();
					//正在进行的数量和完成数量
					int adverNum = channelAdverInfoService.getadverStartAndCompleteCount(adverId, tableName);
					//剩余的数量
					int remainadverNum =  adverInfo.getAdverCount() - adverNum;
					if(remainadverNum > 0)
					{
						//创建任务队列
						//生成队列
						AdverProducer ap = new AdverProducer(endPointName);
						for(int i = 1; i <= remainadverNum; i++) 
						{
							//更新剩余有效产品数量
							String data = UUID.randomUUID().toString();
							System.out.println("Put:" + data);
							ap.sendMessage(data, endPointName);
						}
						//关闭此通道
						ap.close();
					}
				}
				else
				{
					//清理队列中所有生成的任务
					AdverQueueConsumer sume = new AdverQueueConsumer(endPointName);
					Channel channel = sume.getChannel();
					//清楚消息
					//channel.queuePurge(endPointName);
					//删除队列
					channel.queueDelete(endPointName);
					sume.close();
				}
			}
			
			super.writeJsonData(response, 1);
		}
		catch (Exception e)
		{
			super.writeJsonData(response, -1);
		}
	}

	
	
	/**
	 * 
	 * 功能描述：修改
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	@RequestMapping("edit")
	public void updatea(TChannelAdverInfo info, HttpServletResponse response, MultipartFile file,
			MultipartFile fileAdverImg, HttpServletRequest request) throws IOException, TimeoutException
	{
		TChannelAdverInfo oldAdverInfo = channelAdverInfoService.getInfoById(info.getAdverId());
		boolean isNameChange = false;
		if(!info.getAdverName().equals(oldAdverInfo.getAdverName())) 
		{
			isNameChange = true;
		}
		
		try 
		{
			//广告有效期
			//TChannelAdverInfo oldAdverInfo = channelAdverInfoService.getInfoById(info.getAdverId());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			info.setAdverDayStart(simpleDateFormat.parse(info.getAdverTimeStart()));
			info.setAdverDayEnd(simpleDateFormat.parse(info.getAdverTimeEnd()));
			//任务类型
			if(info.getChannelNum().equals("3") && !info.getTaskType().equals("2"))
			{
				response.sendRedirect("applist");
				return;
			}
			
			//增加任务数量
			int changenum = info.getAdverCount() - oldAdverInfo.getAdverCount();
			String oldPointName = oldAdverInfo.getAdverId() + "_" + oldAdverInfo.getAdverName() ;
			String newPointName = info.getAdverId() + "_" + info.getAdverName() ;
			//判断是否更改了关键词
			if(!isNameChange) 
			{
				if(oldAdverInfo.getAdverStatus() == 1) 
				{
					if(changenum > 0) 
					{
						AdverProducer ap = new AdverProducer(newPointName);
						for(int i = 1; i <= changenum; i++) 
						{
							//更新剩余有效产品数量
							String data = UUID.randomUUID().toString();
							System.out.println("Put:" + data);
							ap.sendMessage(data, newPointName);
						}
						//关闭此通道
						ap.close();
					}
					else if (changenum < 0) 
					{
						for(int i = 0; i > changenum; i--) 
						{
							AdverQueueConsumer ss = new AdverQueueConsumer(newPointName);
							ss.getMessage(newPointName);
						}
					}
				}
			}
			else
			{
				//清理队列中所有生成的任务 被修改的关键词应该清除
				AdverQueueConsumer sume = new AdverQueueConsumer(oldPointName);
				Channel channel = sume.getChannel();
				//清楚消息
				//channel.queuePurge(endPointName);
				//删除队列
				channel.queueDelete(oldPointName);
				sume.close();
			}
			
			boolean iscreatetable = false;
			if(!info.getAdid().equals(oldAdverInfo.getAdid()))
			{
				iscreatetable = true;
			}
			
			channelAdverInfoService.saveOrUpd(info,file, request, fileAdverImg, oldAdverInfo);
			
			if(iscreatetable)
			{
				//生成对应的数据库表
				channelAdverInfoService.createAdverTable(info.getAdid(),oldAdverInfo.getChannelNum());
			}
			response.sendRedirect("applist");
		} 
		catch(SQLGrammarException c) 
		{
			response.sendRedirect("applist");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(isNameChange && oldAdverInfo.getAdverStatus() == 1) 
			{
				String newPointName = oldAdverInfo.getAdverId() + "_" + info.getAdverName() ;
				
				//获取任务剩余的量先生成任务
				//需要获取任务已存在的完成数量和正在进行中的数量
				String tableName = "t_adver_"+ oldAdverInfo.getChannelNum() + "_" + oldAdverInfo.getAdid();
				//正在进行的数量和完成数量
				int adverNum = channelAdverInfoService.getadverStartAndCompleteCount(oldAdverInfo.getAdverId()+"", tableName);
				//剩余的数量
				int remainadverNum =  info.getAdverCount() - adverNum;
				if(remainadverNum > 0)
				{
					//创建任务队列
					//生成队列
					AdverProducer ap = new AdverProducer(newPointName);
					for(int i = 1; i <= remainadverNum; i++) 
					{
						//更新剩余有效产品数量
						String data = UUID.randomUUID().toString();
						System.out.println("Put:" + data);
						ap.sendMessage(data, newPointName);
					}
					//关闭此通道
					ap.close();
				}
			}
			
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
	 * @throws IOException 
	 */
	@RequestMapping("appadversave")
	public void appadversave(TChannelAdverInfo info, HttpServletResponse response,  MultipartFile file,
			MultipartFile fileAdverImg, HttpServletRequest request) throws IOException
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
				response.sendRedirect("applist");
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
				channelAdverInfoService.saveOrUpd(info, file, request,fileAdverImg, null);
			}
			
			//生成对应的数据库表
			channelAdverInfoService.createAdverTable(info.getAdid(),info.getChannelNum());
			
			response.sendRedirect("applist");
		}
		catch(SQLGrammarException c) 
		{
			response.sendRedirect("applist");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
}
