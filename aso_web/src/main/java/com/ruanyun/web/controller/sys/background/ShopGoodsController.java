/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-8
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
import com.ruanyun.web.model.TShopGoods;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.ShopGoodsService;
import com.ruanyun.web.service.background.ShopInfoService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;
import com.ruanyun.web.util.SecurityUtils;
import com.sun.mail.imap.protocol.BODY;

/**
 *@author feiyang
 *@date 2016-1-8
 */
@Controller
@RequestMapping("shopGoods")
public class ShopGoodsController extends BaseController{

	
	
	@Autowired
	private  ShopGoodsService shopGoodsService;
	
	@Autowired
	private ShopInfoService shopInfoService;
	
	/**
	 * 
	 * 功能描述:商品列表
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("list")
	public String getChannelInfoList(HttpSession session,Page<TShopGoods> page,TShopGoods info,Model model){
		TUser tUser=HttpSessionUtils.getCurrentUser(session);
		Page<TShopGoods> pageList=shopGoodsService.queryPage(page, info, tUser);
		addModel(model, "pageList", pageList);
		addModel(model, "bean", info);
		return "pc/shopGoods/list";
	}
	
	
	/**
	 * 
	 * 功能描述:删除
	 * @param request
	 * @param response
	 *@author feiyang
	 *@date 2016-1-6
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
							shopGoodsService.delete(TShopGoods.class, Integer.valueOf(ids[i]));
						}
					} else {
						shopGoodsService.delete(TShopGoods.class, Integer.valueOf(id));
					}
					super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","/shopGoods/list", "redirect"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
			}
		}
	
		
		
		/**
		 * 
		 * 功能描述:查看
		 * @param info
		 * @param model
		 * @param session
		 * @return
		 *@author feiyang
		 *@date 2016-1-6
		 */
			@RequestMapping("toEdit")
			public String toEdit(TShopGoods info, Model model,HttpSession session,Integer type) {
				addModel(model, "info", info);
				if (EmptyUtils.isNotEmpty(info.getGoodId())) {
					TShopGoods oldInfo= shopGoodsService.get(TShopGoods.class, info.getGoodId());
					addModel(model, "info", oldInfo);
					if ("2".equals(oldInfo.getGoodType())) {
						addModel(model, "shopList", shopInfoService.getAllShopList());						
					}
					
				}	
				addModel(model, "type", type);
				return "pc/shopGoods/edit";
			}

			
			/**
			 * 
			 * 功能描述:商品管理 增加和删除
			 * @param info
			 * @param response
			 * @param loginName
			 * @param session
			 *@author feiyang
			 *@date 2016-1-6
			 */
			@RequestMapping("saveOrUpdate")
			public void saveOrUpdate(HttpServletRequest request, Model model,TShopGoods info , HttpSession session,HttpServletResponse response,MultipartFile picFile) {
				TUser user = HttpSessionUtils.getCurrentUser(session);		
				int result = shopGoodsService.saveOrupdate(info, request, user, picFile);
				try {
					if (result == 1) {
						super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE,Constants.MESSAGE_SUCCESS, "main_","shopGoods/list", "closeCurrent"));
					} 
				} catch (Exception e) {
					super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "","", ""));
				}
			}
			
}
