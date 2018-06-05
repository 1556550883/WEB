/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-1-7
 */
package com.ruanyun.web.controller.sys.background;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.TUserScore;
import com.ruanyun.web.service.background.UserScoreService;

/**
 *@author feiyang
 *@date 2016-1-7
 */
@Controller
@RequestMapping("userScore")
public class UserScoreController extends BaseController
{
	@Autowired
	private UserScoreService userScoreService;
	/**
	 * 
	 * 功能描述:用户分数列表
	 * @param page
	 * @param info
	 * @param model
	 * @return
	 *@author feiyang
	 *@date 2016-1-6
	 */
	@RequestMapping("list")
	public String getUserScoreList(Page<TUserScore> page,TUserScore info,Model model){
		addModel(model, "pageList", userScoreService.queryPage(page, info));
		addModel(model, "bean", info);
		return "pc/userScore/list";
	}
}
