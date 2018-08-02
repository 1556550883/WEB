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
			FileUtils.downloadLocal(response, "HappyApp.plist");
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
			FileUtils.downloadLocal(response, "mySolution.ipa");
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
