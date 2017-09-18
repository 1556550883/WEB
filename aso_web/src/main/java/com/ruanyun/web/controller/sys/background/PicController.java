/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-23
 */
package com.ruanyun.web.controller.sys.background;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TChannelInfo;
import com.ruanyun.web.model.TPic;
import com.ruanyun.web.model.TUserLevel;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.PicService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 *@author feiyang
 *@date 2016-1-23
 */
@Controller
@RequestMapping("pic")
public class PicController extends BaseController{

	
	@Autowired
	private PicService picService;

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
	public String getPicList(Page<TPic> page,TPic t,Model model){
		addModel(model, "pageList", picService.pageSql(page, t));
		addModel(model, "bean", t);
		return "pc/pic/list";
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
	public String toedit(Integer id,Model model,Integer type){
		if(EmptyUtils.isNotEmpty(id)){
			addModel(model, "bean", picService.get(TPic.class, id));
		}
			addModel(model, "type", type);
		return "pc/pic/edit";
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
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("ids");
		try {
			if (id != null) {
				// 说明是批量删除
				if (id.contains(",")) {
					String[] ids = id.split(",");
					for (int i = 0; i < ids.length; i++) {
						picService.delete(TPic.class, Integer.valueOf(ids[i]));
					}
				} else {
					picService.delete(TPic.class, Integer.valueOf(id));
				}
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","/pic/list", "redirect"));
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
	public void saveOrUpdate(HttpServletRequest request, Model model,TPic info , HttpServletResponse response,MultipartFile picFile) {
	
		int result = picService.saveOrupdate(info, request,picFile);
		try {
			if (result == 1) {
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","pic/list", "closeCurrent"));
			} 
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
		}
	}
	
}
