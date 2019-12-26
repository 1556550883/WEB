package com.ruanyun.web.controller.sys.background;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.TPhoneUdidModel;
import com.ruanyun.web.model.TPhoneUdidWithIdfa;
import com.ruanyun.web.producer.UdidQueueConsumer;
import com.ruanyun.web.service.background.UdidService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.FileUtils;
import com.ruanyun.web.util.HttpRequestUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("udid")
public class UdidController extends BaseController 
{
	@Autowired
	private UdidService udidService;
	
	@RequestMapping("list")
	public String getUdidChannelInfoList(HttpServletRequest request, Model model,HttpServletResponse response)
	{
		List<String> udidList = new ArrayList<String>();
		List<TPhoneUdidModel> udidModelList = new ArrayList<TPhoneUdidModel>();
		udidList.add("iphone10,1");
		udidList.add("iphone10,2");
		udidList.add("iphone10,3");
		udidList.add("iphone11,2");
		udidList.add("iphone11,4");
		udidList.add("iphone12,1");
		udidList.add("iphone8,1");
		udidList.add("iphone8,2");
		udidList.add("iphone9,1");
		udidList.add("iphone9,2");
		
		for(String udidType: udidList) 
		{
			try 
			{
				UdidQueueConsumer sumer = new UdidQueueConsumer(udidType,false);
				//队列中剩余的总数
				int totalNum = sumer.getMessageCount();
				sumer.close();
				//今日消耗的数量
				int todayAmount = udidService.getUdidSumedNum(udidType, "idfa_udid_xiaoshou", TimeUtil.GetdayDate());
				//两日消耗的数量
				int twodayAmount = udidService.getUdidSumedNum(udidType, "idfa_udid_xiaoshou", TimeUtil.GetYestdayDate());
				//昨日消耗的数量
				int yestdayAmount = twodayAmount - todayAmount;
				
				TPhoneUdidModel udidmodel = new TPhoneUdidModel();
				udidmodel.setUdidType(udidType);
				udidmodel.setTotalNum(totalNum);
				udidmodel.setTodayAmountNum(todayAmount);
				udidmodel.setYestDayAmountNum(yestdayAmount);
				
				udidModelList.add(udidmodel);
			} 
			catch (IOException | TimeoutException e)
			{
				e.printStackTrace();
			}
		}
		
		addModel(model, "udidModelList", udidModelList);
		return "pc/udid/list";
	}
	
	@RequestMapping("activated")
	public void activated(HttpServletResponse response) {
		List<String> rest = udidService.importCsv();
		try {
			//0 tablename
			if(rest.get(0) != null) {
				udidService.activated(response,rest.get(0));
			}
		} catch (Exception e) {
		}
		
		JSONArray result = JSONArray.fromObject(rest);
		super.writeJsonData(response, result);
	}
	
	@RequestMapping("upload")
	public String upload(HttpServletResponse response)
	{
		return "pc/udid/upload";
	}
	
	@RequestMapping("saveFile")
	public void saveFile(HttpServletRequest request,HttpServletResponse response,MultipartFile udid, Integer isTest, String cookie) throws Exception{
		  String savePath = "C://Program Files//Apache Software Foundation//import//";
			// String filePath = savePath+udid.getName();
			 FileUtils upload = new FileUtils();
			 int upload_result=upload.uploadFile(udid,savePath,udid.getName() + ".csv","csv",request);
				//判断返回的结果  --显示给用户
			if (upload_result == 1 && isTest == 1) {
				//对udid进行分析是否有效
				List<TPhoneUdidModel> tPhoneUdidModels = udidService.getUdidFromFile();
				if(tPhoneUdidModels  != null && tPhoneUdidModels.size() > 2000) {
					//5000
					tPhoneUdidModels = tPhoneUdidModels.subList(0, 1999);
				}
				
				List<String> reustl = HttpRequestUtil.posts(tPhoneUdidModels, cookie);
				List<TPhoneUdidWithIdfa> ls = new ArrayList<TPhoneUdidWithIdfa>();
				for(String str : reustl) {
					JSONObject jsonObject = JSONObject.fromObject(str);
					JSONArray arr = (JSONArray) jsonObject.get("devices");
					if(arr.size()>0)
					{
						for(int i=0;i<arr.size();i++)
						{
							JSONObject job = arr.getJSONObject(i); 
							TPhoneUdidWithIdfa info = new TPhoneUdidWithIdfa();
							info.setUdid(job.get("deviceNumber").toString());
							if(job.get("model") == null) {
								info.setPhoneModel("未知");
							}else {
								info.setPhoneModel(job.get("model").toString());
							}
							
							ls.add(info);
						}
					}
				}
				
				udidService.exprotPhoneUdid(response,ls);
			} else {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","udid/list", "closeCurrent"));
			}
	}
	
	
	/**
	 * 功能描述:进入修改页面
	 */
	@RequestMapping("/toEdit")
	public String userAppEdit(String udidType,Model model)
	{
		addModel(model, "udidType", udidType);
		return "pc/udid/edit";
	}
	
	
	@RequestMapping("/editNumber")
	public void editNumber(HttpServletResponse response,String udidType,int number,Model model) throws IOException, TimeoutException
	{
		String tablename = udidType.replace(",","_");
		for(int i=0;i<number;i++) 
		{
			UdidQueueConsumer udidQ = new UdidQueueConsumer(udidType,false);
			String udid = udidQ.getMessage(udidType);
			if(udid != null) 
			{
				udidService.updateUdidStatus(udid,tablename,3);
			}
		}
	
		super.writeJsonData(response,CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "udid/list", "redirect"));
	}
	
}
