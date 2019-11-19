/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.controller.sys.background;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
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
import com.ruanyun.web.producer.ArrayBlockQueueProducer;
import com.ruanyun.web.service.app.AppChannelAdverInfoService;
import com.ruanyun.web.service.background.ChannelAdverInfoService;
import com.ruanyun.web.service.background.UserappidAdveridService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpRequestUtil;

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
	/**
	 * 查询广告列表（后台显示）
	 * 
	 */
	@RequestMapping("list")
	public String getChannelAdverInfoList(Page<TChannelAdverInfo> page,TChannelAdverInfo info,Model model)
	{
		Page<TChannelAdverInfo> queryAdver  = channelAdverInfoService.queryAdverList(page, info);
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
			TChannelAdverInfo bean = new TChannelAdverInfo();
			//yyyy-MM-dd HH:mm:ss
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
			return "pc/channelAdverInfo/add";
		}
	}
	
	/**
	 * 
	 * 功能描述：增加
	 */
	@RequestMapping("add")
	public void save(TChannelAdverInfo info, HttpServletResponse response, MultipartFile file,
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
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "自由渠道的广告的任务类型只能是自由任务！", "", "", ""));
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
				channelAdverInfoService.saveOrUpd(info, file, request,fileAdverImg);
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
	public void upd(TChannelAdverInfo info, HttpServletResponse response, MultipartFile file,
			MultipartFile fileAdverImg, HttpServletRequest request)
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
			
			//TUser user = HttpSessionUtils.getCurrentUser(session);
			channelAdverInfoService.saveOrUpd(info,file, request, fileAdverImg);
			
//			Map<String,TChannelAdverInfo > xsAdverList = ArrayBlockQueueProducer.specialXSAdverList;
//			//修改启动的任务
//			if(info.getReceInterTime() > 0 || info.getSubmitInterTime() > 0) {
//				if(xsAdverList.containsKey(info.getAdverId() + ""))
//				{
//					xsAdverList.get(info.getAdverId() + "").setReceInterTime(info.getReceInterTime());
//					xsAdverList.get(info.getAdverId() + "").setSubmitInterTime(info.getSubmitInterTime());;
//				}
//			}
			
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
	 * @param ids
	 * @param response
	 */
	@RequestMapping("delAll")
	public void delAll(String ids, HttpServletResponse response){
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
			//启动的时候产生生产者
			String[] adverIds = ids.split(",");  
			for(String adverId : adverIds) 
			{
				TChannelAdverInfo adverInfo = appChannelAdverInfoService.get(TChannelAdverInfo.class, "adverId", Integer.valueOf(adverId));
//				if(adverInfo.getAdverCreatetime().getDay() < new Date().getDay()) {
//					continue;
//				}
				
				if(adverInfo.getAdverStatus() == status) {
					continue;
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
	
	@RequestMapping("getKeywordRank")
	public void getKeywordRank(String appStoreID, String keyword, HttpServletResponse response, Model model) {
		String url = "http://backend.cqaso.com/app/"+appStoreID
				+"/asoWord?limit=500&offset=0&word="+keyword+"&fuzzy=1&country=CN";
		JSONObject result = HttpRequestUtil.httpGet(url, false);
		
		JSONArray contents = (JSONArray) result.get("contents");
		
		for (int i = 0; i< contents.size(); i++) {
			 JSONObject obj = (JSONObject) JSONObject.fromObject(contents.get(i));
			 String word = (String) obj.get("word");
			 if(word.equalsIgnoreCase(keyword)) {
				 
				 Integer rank = (Integer) obj.get("rank");
				 System.err.println("rank=======" + rank);
				 addModel(model, "rank", rank);
				 break;
			 }
		}
		
		super.writeJsonData(response, model);
	}
	
	@RequestMapping("getAppDetail")
	public void getAppDetail(String appStoreID, String channelNum ,HttpServletResponse response, Model model) {
		TChannelAdverInfo adverInfo = new TChannelAdverInfo();
		adverInfo.setAdverAdid(appStoreID);
		adverInfo.setChannelNum(channelNum);
		List<TChannelAdverInfo> adverInfos = appChannelAdverInfoService.getByCondition(adverInfo);
		if(adverInfos != null && !adverInfos.isEmpty() && adverInfos.size() >= 1) {
			adverInfo = adverInfos.get(0);
			//已经存在任务的信息
			addModel(model, "isexist", true);
			addModel(model, "adverInfo", adverInfo);
		}else {

			String url = "https://itunes.apple.com/lookup";
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", appStoreID);
			params.put("country", "cn");
			String result = HttpRequestUtil.sendHttpsRequestByPost(url, params);
			JSONObject jsonObject = JSONObject.fromObject(result);
			//results
			JSONArray results = (JSONArray) jsonObject.get("results");
			JSONObject obj = (JSONObject) JSONObject.fromObject(results.get(0));
			
			String bundleid = (String) obj.get("bundleId");
			String applogo = (String) obj.get("artworkUrl60");
			addModel(model, "isexist", false);
			addModel(model, "applogo", applogo);
			addModel(model, "bundleid", bundleid);
		}
		
		super.writeJsonData(response, model);
	}
	
	@RequestMapping("export")
	public void exportIDFA(String adverIds, HttpServletResponse response)
	{
		appChannelAdverInfoService.exprotIDFA(response, adverIds);
	}
	
	@RequestMapping("exportAdver")
	public void exportAdver(HttpServletResponse response, String channelNum, String day)
	{
		appChannelAdverInfoService.exprotAdver(response, channelNum, day);
	}
	
	@RequestMapping("exportMonthAadver")
	public void exportMonthAadver(HttpServletResponse response, String channelNum, String month)
	{
		appChannelAdverInfoService.exportMonthAdver(response, channelNum, month);
	}
	
	@RequestMapping("releaseIp")
	public void releaseIp(HttpServletResponse response, String channelNum)
	{
		appChannelAdverInfoService.releaseIp(channelNum);
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
