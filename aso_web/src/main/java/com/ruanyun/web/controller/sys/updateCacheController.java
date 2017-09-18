package com.ruanyun.web.controller.sys;


import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruanyun.common.cache.impl.AuthCache;
import com.ruanyun.common.cache.impl.CopyCach;
import com.ruanyun.common.cache.impl.PublicCache;
import com.ruanyun.common.cache.impl.StaticObjectCache;
import com.ruanyun.common.controller.BaseController;
import com.ruanyun.web.util.CallbackAjaxDone;
import com.ruanyun.web.util.Constants;
/**
 * 
 *  #(c) IFlytek weixin <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 更新字典表数据和静态类数据
 * 
 *  <br/>创建说明: 2014-4-25 下午01:45:03 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
@Controller
@RequestMapping("cacheOperate")
public class updateCacheController extends BaseController {


	@Autowired
	@Qualifier("authCache")
	private AuthCache authCache;
	@Autowired
	@Qualifier("publicCache")
	private PublicCache publicCache;
	@Autowired
	@Qualifier("staticObjectCache")
	private StaticObjectCache staticObjectCache;
	@Autowired
	@Qualifier("copyCach")
	private CopyCach copyCach;

	 
	/**
	 * 功能描述:天转缓存更新页面
	 *
	 * @author L H T  2014-5-14 上午10:10:48
	 * 
	 * @return
	 */
	@RequestMapping("toUpdate")
	public String toUpdate(){
		return "pc/updatecache/update";
	}
	
	/**
	 * 功能描述:更新缓存
	 *
	 * @author L H T  2014-4-25 下午01:48:38
	 *
	 */
	@RequestMapping("updateCache")
	public void updateCache(HttpServletResponse response){
		try {
			logger.info("手动调用缓存更新");
			authCache.update();
			publicCache.update();
			staticObjectCache.update();
			copyCach.update();
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_SUCCESS_CODE, "缓存已更新", "main_A_1129", "cacheOperate/toUpdate", "forward"));
		} catch (Exception e) {
			logger.info("手动调用缓存更新失败");
			super.writeJsonData(response, CallbackAjaxDone.AjaxDone(Constants.STATUS_FAILD_CODE, "缓存更新失败", "", "", ""));
		}
		
	}
	
	
}
