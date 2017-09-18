package com.ruanyun.web.controller.sys.background;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.TShopsInfo;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.service.background.ShopInfoService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.HttpSessionUtils;

/**
 * 商铺信息
 * @author xqzhang
 *
 */
@Controller
@RequestMapping("shopInfo")
public class ShopInfoController extends BaseController {
	@Autowired
	private ShopInfoService shopInfoService;
	
	/**
	 * 
	 * 功能描述：获得商铺列表
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param page
	 * @param shopsInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	public String getShopInfoList(Page<TShopsInfo> page,TShopsInfo shopsInfo,Model model){
		addModel(model, "pageList", shopInfoService.queryPage(page, shopsInfo));
		addModel(model, "bean", shopsInfo);
		return "pc/shopInfo/list";
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
			addModel(model, "bean", shopInfoService.getInfoById(id));
		}else{
			addModel(model, "type", type);
		}
		return "pc/shopInfo/edit";
	}
	/**
	 * 
	 * 功能描述：增加或者修改商铺
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param info
	 * @param response
	 */
	@RequestMapping("edit")
	public void saveOrUpd(TShopsInfo info,HttpServletResponse response,HttpSession session){
		try {
			TUser user=HttpSessionUtils.getCurrentUser(session);
			shopInfoService.saveOrUpd(info,user);
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "shopInfo/list", "closeCurrent"));
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	/**
	 * 
	 * 功能描述：删除
	 * @author: xqzhang
	 * @date:2016-1-6
	 * @param ids
	 * @param response
	 */
	@RequestMapping("delAll")
	public void delAll(String ids,HttpServletResponse response){
		try {
			shopInfoService.delAll(ids);
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "shopInfo/list", "redirect"));
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	/**
	 * 
	 * 功能描述:获取所有店铺列表
	 * @param response
	 * @param provinceId
	 *@author feiyang
	 *@date 2016-1-23
	 */
	@RequestMapping("allShop")
    public void getAllShop(HttpServletResponse response,String provinceId){	
		List<TShopsInfo> list=shopInfoService.getAllShopList();
		List<TShopsInfo> list1=new ArrayList<TShopsInfo>();
		if ("2".equals(provinceId)) {
			super.writeJsonData(response, list);
		}else {
			super.writeJsonData(response, list1);
		}
    }
}
