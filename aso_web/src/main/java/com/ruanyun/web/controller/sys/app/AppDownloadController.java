package com.ruanyun.web.controller.sys.app;

import java.io.FileNotFoundException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.util.FileUtils;

@Controller
@RequestMapping("download")
public class AppDownloadController extends BaseController
{
	@RequestMapping("HappyApp.plist")
	public void getDictionaryList(HttpServletResponse response)
	{
		try 
		{
			FileUtils.downloadLocal(response, "HappyApp.plist", "bin");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("mySolution.ipa")
	public void getipa(HttpServletResponse response)
	{
		try 
		{
			FileUtils.downloadLocal(response, "mySolution.ipa", "bin");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("happywebclip.mobileconfig")
	public void getWebClip(HttpServletResponse response)
	{
		try 
		{
			FileUtils.downloadLocal(response, "happywebclip.mobileconfig", "bin");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	@RequestMapping("happyzhuan.mobileconfig")
	public void getMobileProvision(HttpServletResponse response)
	{
		try 
		{
			String str = "application/x-apple-aspen-config";
			FileUtils.downloadMobileConfig(response, "happyzhuan.mobileconfig", str);
			
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping("install.mobileprovision")
	public void installProvision(HttpServletResponse response)
	{
		try 
		{
			FileUtils.downloadLocal(response, "install.mobileprovision", "bin");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
