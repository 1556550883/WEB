package com.ruanyun.web.controller.sys.background;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.model.sys.TDictionary;
import com.ruanyun.web.service.background.DictionaryService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;

/**
 * 	字典表
 *
 *  创建说明: 2016-10-20 下午05:49:44 wsp  创建文件<br/>
 */
@Controller
@RequestMapping("dictionary")
public class DictionaryController extends BaseController{
	
	@Autowired
	private DictionaryService dictionaryService ;

	/**
	 * 功能描述:绑定时间
	 */
	@InitBinder
	public void initBinders(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
	
	/**
	 * 跳转到修改系统参数页面
	 */
	@RequestMapping("toEdit")
	public String toEdit(Model model,TDictionary dictionary){
		//appleId排重开关 
		addModel(model, "bean", dictionaryService.get(TDictionary.class,"parentCode","APPLE_ID_CHECK"));
		//app最少体验时间
		addModel(model, "leastTaskTime", dictionaryService.get(TDictionary.class,"parentCode","LEAST_TASK_TIME").getItemCode());
		//app最小提现金额
		addModel(model, "leastForward", dictionaryService.get(TDictionary.class,"parentCode","LEAST_FORWARD").getItemCode());
		
		addModel(model, "notice", dictionaryService.get(TDictionary.class,"parentCode","NOTICE").getItemCode());
		
		addModel(model, "downloadUrl", dictionaryService.get(TDictionary.class,"parentCode","DOWNLOAD_URL").getItemCode());
		
		addModel(model, "vestorLevel", dictionaryService.get(TDictionary.class,"parentCode","VESTOR_LEVEL").getItemCode());
		
		addModel(model, "idfaCheck", dictionaryService.get(TDictionary.class,"parentCode","IDFA_CHECK"));
		
		addModel(model, "phoneModelPercent", dictionaryService.get(TDictionary.class,"parentCode","PHONEMODEL_PERCENT").getItemCode());
		
		addModel(model, "openApplication", dictionaryService.get(TDictionary.class,"parentCode","OPEN_APPLICATION").getItemCode());
		return "pc/dictionary/edit";
	}
	
	/**
	 * 修改系统参数
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(Model model, String appleIdCheck, Integer leastTaskTime, Integer leastForward,
			String notice,String downloadUrl,Integer vestorLevel,  String idfaCheck,Integer phoneModelPercent,String openApplication, HttpServletResponse response)
	{
		dictionaryService.updateSystemParameter(appleIdCheck, leastTaskTime, leastForward, notice, downloadUrl, vestorLevel, idfaCheck, phoneModelPercent,openApplication);
		
		super.writeJsonData(response,CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "dictionary/toEdit", "redirect"));
	}
}