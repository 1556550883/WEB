/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-2
 */
package com.ruanyun.web.controller.sys.background;

import java.util.List;

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
import com.ruanyun.web.model.TRedPackage;
import com.ruanyun.web.model.TRedPackageLottery;
import com.ruanyun.web.service.background.RedPackageLotteryService;
import com.ruanyun.web.service.background.RedPackageService;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;

/**
 *@author feiyang
 *@date 2016-3-2
 */
@Controller
@RequestMapping("redPackageLottery")
public class RedPackageLotteryController extends BaseController{

	
	@Autowired
	private RedPackageLotteryService redPackageLotteryService;
	
	@Autowired
	private RedPackageService redPackageService;

	/**
	 * 
	 * 功能描述:列表
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	@RequestMapping("list")
	public String getChannelInfoList(Page<TRedPackageLottery> page,TRedPackageLottery info,Model model){
		Page<TRedPackageLottery> pageList=redPackageLotteryService.PageSql(page, info,0);
		addModel(model, "bean", info);
		addModel(model, "pageList", pageList);
		addModel(model, "isDayLottery", redPackageLotteryService.isDayLottery());
		return "pc/redPackageLottery/list";
	}
	
	
	
	/**
	 * 
	 * 功能描述:跳转到增加或者修改
	 * @param id
	 * @param model
	 * @param type
	 * @return
	 *@author feiyang
	 *@date 2016-3-2
	 */
	@RequestMapping("toedit")
	public String toedit(Integer id,Model model,Integer type){
		if(EmptyUtils.isNotEmpty(id)){
			addModel(model, "bean", redPackageLotteryService.get(TRedPackageLottery.class, id));
		}
		
		List<TRedPackage> packages=redPackageService.getAllRedPackage();
		addModel(model, "packages", packages);
		addModel(model, "type", type);
		return "pc/redPackageLottery/edit";
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
	public void saveOrUpd(TRedPackageLottery info,HttpServletResponse response){
		try {		
			redPackageLotteryService.saveOrUpd(info);
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, Constants.MESSAGE_SUCCESS, "main_", "redPackageLottery/list", "closeCurrent"));
		} catch (Exception e) {
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, Constants.MESSAGE_FAILED, "", "", ""));
		}
	}
	
	
	@InitBinder
	public void initBinders(WebDataBinder binder) {
		super.initBinder(binder, "yyyy-MM-dd", true);
	}
}
