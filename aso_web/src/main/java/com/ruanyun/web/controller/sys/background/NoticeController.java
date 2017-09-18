/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-29
 */
package com.ruanyun.web.controller.sys.background;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TNotice;
import com.ruanyun.web.model.TPic;
import com.ruanyun.web.service.background.NoticeService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;

/**
 *@author feiyang
 *@date 2016-4-29
 */
@Controller
@RequestMapping("notice")
public class NoticeController extends BaseController{

	
	@Autowired
	private NoticeService noticeService;
	
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
	public String getPicList(Page<TNotice> page,TNotice info,Model model){
		Page<TNotice> pageList=noticeService.queryPageSql(page, info);
		addModel(model, "pageList",pageList);
		addModel(model, "bean", info);
		return "pc/notice/list";
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
	public String toedit(Integer noticeId,Model model,Integer type){
		if(EmptyUtils.isNotEmpty(noticeId)){
			addModel(model, "bean", noticeService.get(TNotice.class, noticeId));
		}
			addModel(model, "type", type);
		return "pc/notice/edit";
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
	public void delete(HttpServletRequest request, HttpServletResponse response,Integer noticeId) {
		String id = request.getParameter("ids");
		try {
			if (id != null) {
				// 说明是批量删除
				if (id.contains(",")) {
					String[] ids = id.split(",");
					for (int i = 0; i < ids.length; i++) {
						noticeService.delete(TNotice.class, Integer.valueOf(ids[i]));
					}
				} else {
					noticeService.delete(TNotice.class, Integer.valueOf(id));
				}
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","/notice/list", "redirect"));
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
	public void saveOrUpdate( Model model,TNotice info,HttpServletResponse response) {
	
		int result = noticeService.saveOrupdate(info);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","notice/list", "closeCurrent"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
	
	@InitBinder
	public void initBinders(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
}
