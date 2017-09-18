/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-25
 */
package com.ruanyun.web.controller.sys.background;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TAdverInferface;
import com.ruanyun.web.service.background.AdverInferfaceService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;


/**
 *@author feiyang
 *@date 2016-1-25
 */
@Controller
@RequestMapping("adverInferface")
public class AdverInferfaceController extends BaseController {
	@Autowired
	private AdverInferfaceService adverInferfaceService;

	/**
	 * 
	 * 功能描述:广告接口参数
	 * 
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-25
	 */
	@RequestMapping("list")
	public String getShopInfoList(Page<TAdverInferface> page,
			TAdverInferface info, Model model) {
		addModel(model, "pageList", adverInferfaceService.queryPage(page, info));
		addModel(model, "bean", info);
		return "pc/adverInferface/list";
	}

	/**
	 * 
	 * 功能描述:跳转配置
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-26
	 */
	@RequestMapping("toConfigure")
	public String toConfigure(TAdverInferface info, Model model) {
		addModel(model, "info", info);
		addModel(model,"list", adverInferfaceService.getAdverINferfaceAll(info.getAdverNum(), info.getInferfaceRequestType(), info.getInferfaceType()));
		return "pc/adverInferface/configure";
	}

	/**
	 * 功能描述:配置参数
	 *
	 * @author yangliu  2016年1月26日 下午5:08:52
	 * 
	 * @param model
	 * @param response
	 * @param parameterType 参数类型 数据库字典中的 数组
	 * @param parameterName 客户配置的参数名称  数组
	 * @param inferfaceRequestType 请求类型
	 * @param adverNum 广告编号
	 * @param inferfaceType 请求类型
	 */
	@RequestMapping("saveConfigure")
	public void saveConfigure( Model model,HttpServletResponse response,String [] parameterType,String [] parameterName,Integer inferfaceRequestType,String adverNum,String inferfaceType ) {		
		int result = adverInferfaceService.saveConfigure(parameterType,parameterName,inferfaceRequestType,adverNum,inferfaceType);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","adverInferface/list", "closeCurrent"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	/**
	 * 
	 * 功能描述:跳转修改界面
	 * @param info
	 * @param model
	 * @param type
	 * @return
	 *@author feiyang
	 *@date 2016-1-26
	 */
	@RequestMapping("toEdit")
	public String toEdit(TAdverInferface info, Model model,Integer type) {
		if (EmptyUtils.isNotEmpty(info.getInferaceId())) {
			addModel(model, "info", adverInferfaceService.get(TAdverInferface.class, info.getInferaceId()));
		}
		addModel(model, "type", type);
		return "pc/adverInferface/edit";
	}
	
	/**
	 * 
	 * 功能描述:修改参数配置
	 * @param model
	 * @param info
	 * @param response
	 * @param tAdverEffectiveInfo
	 *@author feiyang
	 *@date 2016-1-26
	 */
	@RequestMapping("updateInferface")
	public void updateInferface( Model model,TAdverInferface adverInferface,HttpServletResponse response) {		
		int result=adverInferfaceService.updateInferface(adverInferface);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","adverInferface/list", "closeCurrent"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
}
