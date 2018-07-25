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
	@RequestMapping("app")
	public void getDictionaryList(HttpServletResponse response, String fileName)
	{
		try 
		{
			FileUtils.downloadLocal(response, fileName);
		}
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
}
