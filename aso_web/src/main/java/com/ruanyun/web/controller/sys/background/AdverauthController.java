package com.ruanyun.web.controller.sys.background;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.cache.impl.AreaCache;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TAdverAuth;
import com.ruanyun.web.model.TArticleInfo;
import com.ruanyun.web.model.TSchool;
import com.ruanyun.web.service.background.AdverauthService;
import com.ruanyun.web.service.background.SchoolService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;

/**
 * 广告认证
 *
 *  创建说明: 2017-1-9 上午11:02:07 wsp  创建文件<br/>
 */
@Controller
@RequestMapping("adverauth")
public class AdverauthController extends BaseController{
	@Autowired
	private AdverauthService adverauthService;
	@Autowired
	private SchoolService schoolService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
	
	/**
	 * 功能描述:文章 广告认证  
	 * @author wsp  2017-1-9 上午11:11:19
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String getList(Page<TAdverAuth> page,TAdverAuth info,Model model){
		addModel(model, "pageList", adverauthService.queryPage(page, info));
		addModel(model, "bean", info);
		return "pc/adverauth/list";
	}
	
	/**
	 * 功能描述:添加
	 * @author wsp  2017-1-9 下午02:26:20
	 * @param adverAuth
	 * @param model
	 * @return
	 */
	@RequestMapping("toedit")
	public String toedit(TAdverAuth adverAuth,Model model,TArticleInfo articleInfo){
		addModel(model, "bean", adverAuth);
		addModel(model, "provinces", AreaCache.getProvinces());//获取省份列表
		return "pc/adverauth/edit";
	}
	
	
	/**
	 * 功能描述:增加或者修改
	 * @author wsp  2017-1-9 下午03:59:28
	 * @param adverAuth
	 * @param response
	 * @param jsonStr
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(TAdverAuth adverAuth, HttpServletResponse response,String jsonStr) {
	
		int result = adverauthService.saveOrupdate(adverAuth, jsonStr);
		if (result == 1) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_1","adverauth/list", "forward"));
		} else {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	/**
	 * 功能描述:批量删除
	 * @author wsp  2017-1-9 下午05:37:16
	 * @param response
	 * @param authId
	 */
	@RequestMapping("delAll")
	public void delAll(HttpServletResponse response,String ids) {
		int result = adverauthService.delAll(ids);
		if (result == 1) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_1","adverauth/list", "forward"));
		} else {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	/**
	 * 功能描述:查找带回学校
	 * @author wsp  2017-1-9 下午01:25:00
	 * @param page
	 * @param school
	 * @param model
	 * @return
	 */
	@RequestMapping("getFindBackSchool")
	public String getFindBackUser(Page<TSchool>page,TSchool school,Model model){
		if(EmptyUtils.isNotEmpty(school.getCommonNumName())){
			List<TAdverAuth> adverAuthList = adverauthService.getAllByCondition(TAdverAuth.class, new String[]{"commonNum","authType"}, new Object[]{school.getCommonNumName(),2}); 
			String schoolIds = "";
			for (int i = 0; i < adverAuthList.size(); i++) {
				if(i == 0)
					schoolIds += adverAuthList.get(i).getCommonAuthNum();
				else
					schoolIds += ","+adverAuthList.get(i).getCommonAuthNum();
			}
			school.setSchoolIds(schoolIds);
		}
		addModel(model, "pageList", schoolService.pageSql(page, school));
		addModel(model, "bean", school);
		return "pc/adverauth/findBackSchool";
	}
	

}
