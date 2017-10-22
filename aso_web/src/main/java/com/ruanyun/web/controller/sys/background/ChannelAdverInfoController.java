/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.controller.sys.background;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ArrayBlockingQueue;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TChannelAdverInfo;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.producer.ArrayBlockQueueProducer;
import com.ruanyun.web.producer.ScoreQueueConsumer;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.service.background.UserappidAdveridService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("channelAdverInfo")
public class ChannelAdverInfoController extends BaseController
{
	@Autowired
	private ChannelAdverInfoService channelAdverInfoService;
	@Autowired
	private AppChannelAdverInfoService appChannelAdverInfoService;
	@Autowired
	private UserappidAdveridService userappidAdveridService;
	@Autowired
	private UserScoreService userScoreService;
	
	private static boolean flag = true;
	/**
	 * 查询广告列表（后台显示）
	 */
	@RequestMapping("list")
	public String getChannelAdverInfoList(Page<TChannelAdverInfo> page,TChannelAdverInfo info,Model model)
	{
		addModel(model, "pageList", channelAdverInfoService.queryAdverList(page, info));
		addModel(model, "bean", info);
		
		return "pc/channelAdverInfo/list";
	}
	
	/**
	 * 
	 * 功能描述：跳转到增加或者修改
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping("toedit")
	public String toedit(Integer id, Model model,Integer type,String channelNum) throws Exception
	{
		if(EmptyUtils.isNotEmpty(id))
		{	
			TChannelAdverInfo bean = channelAdverInfoService.getInfoById(id);
			addModel(model, "bean", bean);
			addModel(model, "channelNum", channelNum);
			return "pc/channelAdverInfo/edit";
		}
		else
		{
			addModel(model, "type", type);
			addModel(model, "channelNum", channelNum);
			return "pc/channelAdverInfo/add";
		}
	}

	/**
	 * 
	 * 功能描述：增加
	 */
	@RequestMapping("add")
	public void save(TChannelAdverInfo info, HttpServletResponse response, HttpSession session, MultipartFile file,
			MultipartFile fileAdverImg, HttpServletRequest request, String[] stepName, String[] stepDesc, Integer[] stepRates, 
			String[] stepTime, Float[] stepScore, Integer[] stepUseTime, String[] stepType, Integer[] stepMinCount)
	{
		try 
		{
			//向轴 add
			//广告有效期
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			info.setAdverDayStart(simpleDateFormat.parse(info.getAdverTimeStart()));
			info.setAdverDayEnd(simpleDateFormat.parse(info.getAdverTimeEnd()));
			//任务类型
			if(info.getChannelNum().equals("3") && !info.getTaskType().equals("2"))
			{
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "自由渠道的广告的任务类型只能是自由任务！", "", "", ""));
				return;
			}
			
			TUser user = HttpSessionUtils.getCurrentUser(session);
			
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
				channelAdverInfoService.saveOrUpd(info, user, file, request, stepName, stepDesc, stepRates, stepTime,
						stepScore, stepUseTime, stepType, stepMinCount, fileAdverImg);
			}
			
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_index2", "channelAdverInfo/list", "closeCurrent"));
		}
		catch (Exception e) 
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	/**
	 * 
	 * 功能描述：修改
	 */
	@RequestMapping("edit")
	public void upd(TChannelAdverInfo info, HttpServletResponse response, HttpSession session, MultipartFile file,
			MultipartFile fileAdverImg, HttpServletRequest request, String[] stepName, String[] stepDesc, Integer[] stepRates,
			String[] stepTime, Float[] stepScore, Integer[] stepUseTime, String[] stepType, Integer[] stepMinCount)
	{
		try 
		{
			//向轴 add
			//广告有效期
			//TChannelAdverInfo oldAdverInfo = channelAdverInfoService.getInfoById(info.getAdverId());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			info.setAdverDayStart(simpleDateFormat.parse(info.getAdverTimeStart()));
			info.setAdverDayEnd(simpleDateFormat.parse(info.getAdverTimeEnd()));
			//info.setAdverActivationCount((info.getAdverCount() - oldAdverInfo.getAdverCount()) + oldAdverInfo.getAdverActivationCount());
			//任务类型
			if(info.getChannelNum().equals("3") && !info.getTaskType().equals("2"))
			{
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "自由渠道的广告的任务类型只能是自由任务！", "", "", ""));
				return;
			}
			
			TUser user = HttpSessionUtils.getCurrentUser(session);
			channelAdverInfoService.saveOrUpd(info, user, file, request, stepName, stepDesc,
					stepRates, stepTime, stepScore, stepUseTime, stepType, stepMinCount, fileAdverImg);
			
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_index2", "channelAdverInfo/list", "closeCurrent"));
		} 
		catch (Exception e)
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	/**
	 * 
	 * 功能描述：删除
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param ids
	 * @param response
	 */
	@RequestMapping("delAll")
	public void delAll(String ids,HttpServletResponse response){
		try {
			channelAdverInfoService.delAll(ids);
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "", "", ""));
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	/**
	 * 批量审核
	 */
	@RequestMapping("updateAdverStatus")
	public void updateAdverStatus(String ids, HttpServletResponse response, Integer status)
	{
		try
		{
			channelAdverInfoService.updateAdverStatus(status, ids);
			//启动的时候产生生产者
				String[] adverIds = ids.split(",");  
				for(String adverId : adverIds) 
				{
					if(status == 1)
					{
						TChannelAdverInfo info = channelAdverInfoService.getInfoById(Integer.parseInt(adverId));
						appChannelAdverInfoService.updateAdverCountRemain(info);
						info.setAdverActivationCount(info.getAdverCountRemain());
						channelAdverInfoService.updateAdverActivationCount(info);
						int count = 150;
						if(info.getAdverActivationCount() < 150) 
						{
							count = info.getAdverCount();
						}
						
						final ArrayBlockingQueue<String> arrayBlockQueue = new ArrayBlockingQueue<String>(count < 5 ? 5 : count);
						ArrayBlockQueueProducer producer = new ArrayBlockQueueProducer(arrayBlockQueue, adverId,
								channelAdverInfoService, appChannelAdverInfoService, userappidAdveridService);
						
						ArrayBlockQueueProducer.pool.execute(producer);
						
						if(flag) 
						{
							ScoreQueueConsumer scoreConsumer = new ScoreQueueConsumer("socre", userScoreService);
							ScoreQueueConsumer.pool.execute(scoreConsumer);
							flag = false;
						}
					}
					else
					{
						if(ArrayBlockQueueProducer.mQueueMap.containsKey(adverId)) 
						{
							ArrayBlockQueueProducer.mQueueMap.remove(adverId);
						}
					}
				}
			
			//super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_index2", "channelAdverInfo/list", "redirect"));
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "", "", ""));
		}
		catch (Exception e)
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	@RequestMapping("freshAdverNum")
	public void freshAdverNum(String ids, HttpServletResponse response)
	{
		try 
		{
			TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(ids));
			
			if(adverInfo != null)
			{
				//更新超时未完成的任务
				userappidAdveridService.updateStatus2Invalid(adverInfo);
				//更新任务数量
				appChannelAdverInfoService.updateAdverCountRemain(adverInfo);
			}
			
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "", "", ""));
		} 
		catch (Exception e) 
		{
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	@InitBinder
	public void initBinders(WebDataBinder binder) 
	{
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
	
	public void getChannelAdverList(HttpServletResponse response,Integer systemType,Integer channelType)
	{

	}
}
