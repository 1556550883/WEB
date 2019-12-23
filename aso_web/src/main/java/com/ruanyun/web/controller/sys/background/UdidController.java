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

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.TimeUtil;
import com.ruanyun.web.model.TPhoneUdidModel;
import com.ruanyun.web.producer.UdidQueueConsumer;
import com.ruanyun.web.service.background.UdidService;

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
	
}
