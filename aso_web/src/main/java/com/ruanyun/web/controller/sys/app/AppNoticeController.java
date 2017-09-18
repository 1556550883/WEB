/**
 * 手机端接口: files
 *@author feiyang
 *@date 2016-4-29
 */
package com.ruanyun.web.controller.sys.app;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.controller.BaseController;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TNotice;
import com.ruanyun.web.service.background.NoticeService;

/**
 *@author feiyang
 *@date 2016-4-29
 */
@Controller
@RequestMapping("app/notice")
public class AppNoticeController extends BaseController{

	@Autowired
	private NoticeService noticeService;

	/**
	 * 
	 * 手机端接口:获取最新一条公告
	 * 
	 * @param response
	 * @param info
	 * @param userNum
	 * @param sign
	 *@author feiyang
	 *@date 2016-1-18
	 */
	@RequestMapping("getNotice")
	public void getNotice(HttpServletResponse response,String userNum, String sign) {
		AppCommonModel model = new AppCommonModel(1,"查询成功");
		try {
			TNotice notice=noticeService.getNotice();
			if (EmptyUtils.isNotEmpty(notice)) {
				model.setObj(notice);			
			}else {
				model.setObj("{}");
			}
		} catch (Exception e) {
			model = new AppCommonModel(-1, "数据异常");
		}
		super.writeJsonDataApp(response, model);
	}
}
