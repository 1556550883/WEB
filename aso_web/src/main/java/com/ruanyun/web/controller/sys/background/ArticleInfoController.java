/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-16
 */
package com.ruanyun.web.controller.sys.background;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TArticleInfo;
import com.ruanyun.web.model.TPic;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.ArticleInfoService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 *@author feiyang
 *@date 2016-4-16
 */
@Controller
@RequestMapping("articleInfo")
public class ArticleInfoController extends BaseController{

	@Autowired
	private ArticleInfoService articleInfoService;
	
	/**
	 * 功能描述:绑定时间
	 */
	@InitBinder
	public void initBinders(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
	
	
	/**
	 * 
	 * 功能描述:图片分页
	 * @param page
	 * @param t
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-23
	 */
	@RequestMapping("list")
	public String getPicList(Page<TArticleInfo> page,TArticleInfo info,Model model){
		addModel(model, "pageList", articleInfoService.pageSql(page, info));
		addModel(model, "bean", info);
		return "pc/articleInfo/list";
	}
	/**
	 * 
	 * 功能描述：跳转到增加或者修改
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param id
	 * @param model
	 * @param type
	 * @return
	 */
	@RequestMapping("toedit")
	public String toedit(Integer articleId,Model model,Integer type){
		if(EmptyUtils.isNotEmpty(articleId)){
			addModel(model, "bean", articleInfoService.get(TArticleInfo.class, articleId));
		}
			addModel(model, "type", type);
		return "pc/articleInfo/edit";
	}
	
	
	
	/**
	 * 
	 * 功能描述:删除
	 * @param request
	 * @param response
	 *@author feiyang
	 *@date 2016-1-7
	 */
	@RequestMapping("/delAll")
	public void delete(HttpServletRequest request, HttpServletResponse response,Integer articleId) {
		String id = request.getParameter("ids");
		try {
			if (id != null) {
				// 说明是批量删除
				if (id.contains(",")) {
					String[] ids = id.split(",");
					for (int i = 0; i < ids.length; i++) {
						articleInfoService.delete(TArticleInfo.class, Integer.valueOf(ids[i]));
					}
				} else {
					articleInfoService.delete(TArticleInfo.class, Integer.valueOf(id));
				}
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","articleInfo/list", "redirect"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	/**
	 * 
	 * 功能描述:增加或者修改
	 * @param info
	 * @param response
	 * @param loginName
	 * @param session
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("saveOrUpdate")
	public void saveOrUpdate(HttpServletRequest request, Model model,TArticleInfo info , HttpServletResponse response,MultipartFile picFile,HttpSession session) {
		TUser tUser=HttpSessionUtils.getCurrentUser(session);	 
		int result = articleInfoService.saveOrupdate(info, request, picFile, tUser);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","articleInfo/list", "forward"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	/**
	 * 新闻详情
	 * @param articleId
	 * @param model
	 * @return
	 */
	@RequestMapping("toeditc/{articleNum}")
	public String toeditc(@PathVariable String  articleNum,Model model){
		TArticleInfo articleInfo = articleInfoService.get(TArticleInfo.class, "articleNum",articleNum);
		addModel(model, "bean", articleInfo);
		articleInfoService.updateAccessTimes(articleInfo);
		return "pc/articleInfo/editc";
	}
	
}
