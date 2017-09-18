package com.ruanyun.web.util;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;


/**
 * 
 *  #(c) ruanyun 
 *
 *  版本说明: $id:$ 
 *
 *  功能说明: 公共方法对象
 * 
 * 创建说明: 2013-10-15 上午09:51:03 houkun
 * 
 *  修改历史:<br/>
 *
 */
public class CommonMethod {
	/**
	 * 功能描述: 项目的绝对路径
	 *
	 * @author houkun  2013-10-15 上午09:52:08
	 * 
	 * @param request 请求对象
	 * @return
	 */
	public static String getProjectPath(HttpServletRequest request){
		String projectUrl = request.getSession().getServletContext().getRealPath("/");
		return projectUrl;
	}
	
    
    
    /**
     * 功能描述:获得Controller下的路径
     *
     * @author L H T  2014-1-9 上午10:24:53
     * 
     * @param request
     * @return
     * 
     * /ueditor/jsp/imageUp.jsp
     */
	public static String getPath(HttpServletRequest request){
		return request.getServletPath();
	}
	/**
	 * 功能描述:获取请求路径
	 *
	 * @author L H T  2014-1-9 上午10:25:06
	 * 
	 * @param request
	 * http://localhost:9090/ahsw/ueditor/jsp/imageUp.jsp
	 * @return
	 */
	public static String getUrl(HttpServletRequest request){
		return request.getRequestURL().toString();
	}
	
	/**
	 * 功能描述:获取工程名
	 *
	 * @author L H T  2014-1-9 上午10:25:17
	 * 
	 * @param request
	 * /ahsw
	 * @return
	 */
	public static String getContextPath(HttpServletRequest request){
		return request.getContextPath();
	}
	
	/**
	 * 功能描述:获得访问路径
	 *
	 * @author L H T  2014-1-9 上午10:25:27
	 * http://localhost:9090/ahsw/
	 * @param request
	 * @return
	 */
	public static String getVisitPath(HttpServletRequest request){
		return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	}
	
	/**
	 * 功能描述: 格式化 float 小数保留两位
	 *
	 * @author yangliu  2016年1月18日 下午9:41:36
	 * 
	 * @param f
	 * @return
	 */
	public static  Float formatFloat(Float f){
		BigDecimal   b  =   new BigDecimal(f);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
	
}
