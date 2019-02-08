package com.ruanyun.web.controller.sys.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.TUserApp;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.producer.QueueProducer;
import com.ruanyun.web.service.background.DictionaryService;
import com.ruanyun.web.service.background.UserAppService;
import com.ruanyun.web.service.background.UserScoreService;
import com.ruanyun.web.util.Constants;

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
	public String inviteGuest(HttpServletResponse response, HttpServletRequest request, Model model, String id, String udid)
	{
		//id就是masterid
		if(id == null || id.isEmpty()) 
		{
			id = "-1";
		}
		
		if(udid == null || udid.isEmpty()) 
		{
			udid = "-1";
		}
		
		addModel(model, "master", id);
		addModel(model, "udid", udid);
		
		return "app/invite/inviteGuest";
	}
	
	@RequestMapping("deviceUuid")
	public void guestPhone(HttpServletResponse response, HttpServletRequest request, Model model) throws IOException
	{
	    request.setCharacterEncoding("UTF-8");
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
  	    String udid = content.substring(content.indexOf("<string>") + 8,content.indexOf("</string>"));
  	  
	    response.setContentType("text/html;charset=UTF-8");
	    response.setStatus(301);

	    if(udid  != null && !udid.equals("-1")) 
	    {
	    	response.setHeader("Location", Constants.BASD_URL  + "/invite/uuidpage?udid=" + udid);
	    }
	    else
	    {
	    	response.setHeader("Location", Constants.BASD_URL  + "/invite/error");
	    }
	}
	
	@RequestMapping("userRegister")
	public void setMaster(HttpServletResponse response, HttpServletRequest request, Model model, String udid, String masterID)
	{
		TUserApp tUserApp = userAppService.getUserAppByUserName(udid);
  	    if(tUserApp == null) 
  		{
  			tUserApp = new TUserApp();
  			tUserApp.setUserApppType(2);
  			tUserApp.setLoginName(udid);
  			tUserApp.setLoginPwd("");
  			tUserApp.setLoginControl("1");
  			tUserApp.setMasterID(masterID);
  			tUserApp.setPhoneSerialNumber("-1");
  			tUserApp.setLevel(dictionaryService.getVestorLevel());
  			tUserApp.setLimitTime(30);
  			tUserApp.setCreateDate(new Date());
  			
  			userAppService.saveOrUpdate(tUserApp, request, null);
  			
  			//师傅 
			TUserApp masterUser = userAppService.getUserAppById(Integer.parseInt(masterID));
	    	int count = userAppService.getApprenticeNum(masterID);
	    	if(masterUser != null && count > 0) 
	    	{
	    		//更新师傅徒弟数量
	    		userScoreService.updateApprentice(masterUser.getUserNum(), count);
	    		
	    		if(count != 0 && count%10 == 0) {
	    			
	    			TUserScore score = new TUserScore();
					score.setType(4);
					score.setUserNum(masterUser.getUserNum());//师傅num
					score.setRankingNum(tUserApp.getUserNum());//用来表示第十个徒弟num。如果不为空
					score.setScore((float) 10);
					try {
						QueueProducer.getQueueProducer().sendMessage(score, "socre");
					} catch (Exception e) {
						e.printStackTrace();
						}
	  	    	}
	    	}
  		}
  	    
		super.writeJsonDataApp(response, model);
	}
	
	@RequestMapping("home")
	public String homePage(HttpServletResponse response, HttpServletRequest request, Model model, String udid)
	{
		if(udid == null || udid.isEmpty()) 
		{
			udid = "-1";
		}
		
		addModel(model, "udid", udid);
		return "app/invite/homePage";
	}
	
	@RequestMapping("uuidpage")
	public String uuidpage(HttpServletResponse response, HttpServletRequest request, Model model, String udid)
	{	
		addModel(model, "udid", udid);
		return "app/invite/uuidPage";
	}
	
	@RequestMapping("error")
	public String homePage(HttpServletResponse response, HttpServletRequest request)
	{
		return "app/invite/errorPage";
	}
	
	@RequestMapping("isSafari")
	public String isSafariTip(HttpServletResponse response, HttpServletRequest request)
	{
		return "app/invite/isSafariOpen";
	}
}
