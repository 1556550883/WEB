package com.ruanyun.web.controller.sys.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.service.background.DictionaryService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserScoreService;

@Controller
@RequestMapping("/invite")
public class AppUserInviteController extends BaseController
{
	@Autowired	
	private UserAppService userAppService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private UserScoreService userScoreService;
	
	@RequestMapping("guest")
	public String inviteGuest(HttpServletResponse response, HttpServletRequest request, Model model, String id, String udid, String userId)
	{
		if(id == null || id.isEmpty()) 
		{
			id = "-1";
		}
		
		if(udid == null || udid.isEmpty()) 
		{
			udid = "-1";
		}
		
		if(userId == null || userId.isEmpty()) 
		{
			userId = "-1";
		}
		
		addModel(model, "userId", userId);
		addModel(model, "master", id);
		addModel(model, "udid", udid);
		return "app/invite/inviteGuest";
	}
	
	@RequestMapping("deviceUuid")
	public void guestPhone(HttpServletResponse response, HttpServletRequest request, Model model, String masterid) throws IOException
	{
		 System.out.println("run this ====================");
	    request.setCharacterEncoding("UTF-8");
	    String udid = "";
	    //工作室账号使用
	    List<String> values = Arrays.asList(masterid.split("_"));
	    masterid = values.get(0);
	    String userId = values.get(1);
	     
	    if(!userId.equals("-1")) 
	    {
	    	masterid = "-1";
	    	TUserApp tUserApp = userAppService.getUserAppById(Integer.parseInt(userId));
	    	udid = tUserApp.getLoginName();
	    }
	    else 	    //非工作室账号
	    {
	    	 InputStream is = request.getInputStream();
	   	  //已HTTP请求输入流建立一个BufferedReader对象
	   	    BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
	   	    StringBuilder sb = new StringBuilder();
	   	 
	   	    //读取HTTP请求内容
	   	    String buffer = null;
	   	    while ((buffer = br.readLine()) != null) {
	   	         sb.append(buffer);
	   	    }
	   	    
	   	    String content = sb.toString().substring(sb.toString().indexOf("<key>UDID</key>"), sb.toString().indexOf("<key>VERSION</key>"));
	   	    //content就是接收到的xml字符串
	   	    //进行xml解析即可
	   	    udid = content.substring(content.indexOf("<string>") + 8,content.indexOf("</string>"));
	   	    
	   	    System.out.println("=======================" + udid);
	   	    TUserApp tUserApp = userAppService.getUserAppByUserName(udid);
	   		   
	   	    if(tUserApp == null) 
	   		{
	   			tUserApp = new TUserApp();
	   			tUserApp.setUserApppType(2);
	   			tUserApp.setLoginName(udid);
	   			tUserApp.setLoginPwd("");
	   			tUserApp.setLoginControl("1");
	   			tUserApp.setPhoneSerialNumber("-1");
	   			tUserApp.setLevel(dictionaryService.getVestorLevel());
	   			tUserApp.setMasterID(masterid);
	   			tUserApp.setLimitTime(20);
	   			tUserApp.setCreateDate(new Date());
	   			
	   			userAppService.saveOrUpdate(tUserApp, request, null);
	   		}
	   	    
	   	    if(!masterid.equals("-1")) 
	   	    {
	   	    	 TUserApp masterUser = userAppService.getUserAppById(Integer.parseInt(masterid));
	   	    	//TUserScore score = userScoreService.getScore(masterUser.getUserNum());
	   	    	int count = userAppService.getApprenticeNum(masterid);
	   	    	if(masterUser != null && count > 0) 
	   	    	{
	   	    		userScoreService.updateApprentice(masterUser.getUserNum(), count);
	   	    	}
	   	    }
	    }
	    
	    response.setContentType("text/html;charset=UTF-8");
	    response.setStatus(301);
	    if(masterid.equals("-1")) 
	    {
	    	response.setHeader("Location", "https://moneyzhuan.com/invite/guest?udid=" + udid);
	    }
	    else
	    {
	    	response.setHeader("Location", "https://moneyzhuan.com/invite/guest?id=" + masterid + "&udid=" + udid);
	    }
	}
}
