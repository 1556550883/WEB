package com.ruanyun.common.interceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.ExpressionToRegex;
import com.ruanyun.common.utils.RequestUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.model.AppCommonModel;
import com.ruanyun.web.model.TUserLogin;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.HttpSessionUtils;
import com.ruanyun.web.util.PropertiesUtils;

public class CommonInterceptor extends HandlerInterceptorAdapter {
	int i=0;
	protected static final Logger LOGGER = LoggerFactory
			.getLogger(CommonInterceptor.class);
	
	//开启是否验证权限 是否修改试图返回页面
	private boolean validationAuthority=false,modifyView=false;
	
	
	
	/////////////////////////权限验证开始///////////////////////////////////////
	
	private String whiteList, blackList, noSession;

	private String[] whiteListURLs = null, blackListURLs = null;

	private final String[] NULL_STRING_ARRAY = new String[0];
	private final String URL_SPLIT_PATTERN = "[, ;\r\n]";// 逗号 空格 分号 换行

	// 用spring的Ant过滤的通配符似乎不太适合自己的写法
	// private final PathMatcher pathMatcher = new AntPathMatcher();

	public String getBlackList() {
		return blackList;
	}

	public void setBlackList(String blackList) {
		this.blackList = blackList;
	}

	public String getWhiteList() {
		return whiteList;
	}

	public void setWhiteList(String whiteList) {
		this.whiteList = whiteList;
	}

	public String getNoSession() {
		return noSession == null ? "" : noSession;
	}

	public void setNoSession(String noSession) {
		this.noSession = noSession;
	}
	
	
	public boolean isValidationAuthority() {
		return validationAuthority;
	}

	public void setValidationAuthority(boolean validationAuthority) {
		this.validationAuthority = validationAuthority;
	}

	public boolean isModifyView() {
		return modifyView;
	}

	public void setModifyView(boolean modifyView) {
		this.modifyView = modifyView;
	}

	/**
	 * 功能描述:拦截UIL  权限过滤
	 *
	 * @author yangliu  2013-8-31 上午10:57:25
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO 用户NoSession拦截器
		if (validationAuthority) {
			whiteListURLs = strToArray(getWhiteList());
			blackListURLs = strToArray(getBlackList());

			String currentServlet = request.getServletPath();
			/**
			 * 解决手机端不能播放视频的问题
			 */
			if(EmptyUtils.isNotEmpty(currentServlet)&&currentServlet.startsWith("/sp")){
				HttpSessionUtils.setUserToSession(request.getSession(), new TUser());
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				return super.preHandle(request, response, handler);
			}
			boolean inWhite = false, inBlack = false;

			ExpressionToRegex e2r = new ExpressionToRegex();

			for (String urlPattern : whiteListURLs) {
				inWhite = e2r.match(urlPattern, currentServlet);
				if (inWhite)
					break;
			}

			for (String urlPattern : blackListURLs) {
				inBlack = e2r.match(urlPattern, currentServlet);
				if (inBlack)
					return false;
			}

			if (!inWhite && !inBlack) {
				if(currentServlet.startsWith("/app")){
				LOGGER.info("Servlet:" + request.getRequestURL() + "被拦截");
				TUser userObj=HttpSessionUtils.getCurrentUser(request.getSession());
				if ( userObj== null) {
					LOGGER.info("Servlet:" + request.getRequestURL() + "被跳转");					
					response.getWriter().print(			
							"<script>top.window.location.href = \""
									+ request.getContextPath() + getNoSession()
									+ "\";</script>");
					return false;
					}
				}
//				//获取访问路径
//				String noSessionPath = request.getRequestURL().toString();
//				//如果访问路径中包含"pc"字符串,说明用户访问前台,在noSession的情况下跳转到前台登录页
//				if(noSessionPath.indexOf("pc")!=-1){
//					response.getWriter().print(
//							"<script>top.window.location.href = \""
//									+ request.getContextPath() + "/pcLogin/login"
//									+ "\";</script>");
//				}else{
//					//如果访问路径中不包含"pc"字符串,说明用户访问后台,在noSession的情况下跳转到后台登录页
//					//LOGGER.info("Servlet:" + request.getRequestURL() + "被跳转");
//					response.getWriter().print(
//							"<script>top.window.location.href = \""
//									+ request.getContextPath() + getNoSession()
//									+ "\";</script>");
//				}
				
			}
		}
		return super.preHandle(request, response, handler);
	}

	private String[] strToArray(String urlStr) {
		if (urlStr == null) {
			return NULL_STRING_ARRAY;
		}
		String[] urlArray = urlStr.split(URL_SPLIT_PATTERN); 

		List<String> urlList = new ArrayList<String>();

		for (String url : urlArray) {
			url = url.trim();
			if (url.length() == 0) {
				continue;
			}
			urlList.add(url);
		}

		return urlList.toArray(NULL_STRING_ARRAY);
	}
	
	
	/////////////////////////权限验证结束///////////////////////////////////////

}
