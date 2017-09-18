/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-3-3
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.model.Page;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TRedPackageLottery;
import com.ruanyun.web.service.background.RedPackageLotteryService;

/**
 *@author feiyang
 *@date 2016-3-3
 */
@Controller
@RequestMapping("app/redPackageLottery")
public class AppRedPackageLotteryController extends BaseController{

	@Autowired
	private RedPackageLotteryService redPackageLotteryService;

	/**
	 * 
	 * 功能描述:往期开奖
	 * @param response
	 * @param userNum
	 * @param sign
	 * @param page
	 * @param info
	 *@author feiyang
	 *@date 2016-3-3
	 */
	@RequestMapping("getList")
	public void getList(HttpServletResponse response,String userNum, String sign,Page<TRedPackageLottery>page,TRedPackageLottery info) {
		AppCommonModel model = new AppCommonModel(1," 查询成功");
		try {
			model.setObj(redPackageLotteryService.PageSql(page, info,1));
		} catch (Exception e) {
			model = new AppCommonModel(-1, e.getMessage());
		}
		super.writeJsonDataApp(response, model);
	}
}
