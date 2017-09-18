package com.ruanyun.common.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.util.PropertiesUtils;

public class PageMessageTag extends BodyTagSupport{
	
	final String default_pageName="/default/pagename/";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 页面名称
	 */
	private String pageName;
	
	/**
	 * 替换字符
	 */
	private String replaceStr;
	
	/**
	 * 结果
	 */
	private String result;

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReplaceStr() {
		return replaceStr;
	}

	public void setReplaceStr(String replaceStr) {
		this.replaceStr = replaceStr;
	}
	
	@Override
	 public int doAfterBody() throws JspException {
		HttpServletRequest request=(HttpServletRequest) pageContext.getRequest();
		if(EmptyUtils.isEmpty(result)){
			String queryString=getMsgParamValue(request.getQueryString());
			if(EmptyUtils.isEmpty(queryString))
				return SKIP_BODY;
			result=queryString;
		}
		
		BodyContent bodycontent = getBodyContent();
		
		String path=request.getServletPath();
		if(EmptyUtils.isEmpty(pageName))
			pageName=path.replaceAll("/WEB-INF/jsp/*[a-z]*/*|.jsp$", "/");
		System.out.println(pageName);
		String bodyString=PropertiesUtils.PAGE_MESSAGE_CONFIG.getValue(pageName+StringUtils.defaultString(result));
		if(EmptyUtils.isEmpty(bodyString)){
			bodyString=PropertiesUtils.PAGE_MESSAGE_CONFIG.getValue(default_pageName+StringUtils.defaultString(result));
		}
		if(EmptyUtils.isNotEmpty(replaceStr) && EmptyUtils.isNotEmpty(bodyString)){
	         bodyString= bodycontent.getString().replace(replaceStr, bodyString);  
		} 
		 JspWriter out = bodycontent.getEnclosingWriter();
		 try {
			out.write(bodyString);
		} catch (IOException e) {
		}
	      return SKIP_BODY;  
	  }
	
	/**
	 * 获取msg的值
	 * @param queryString
	 * @return
	 */
	protected String getMsgParamValue(String queryString) {
		if(EmptyUtils.isNotEmpty(queryString)&&queryString.indexOf("msg")>=0){
			return ("&"+queryString).replaceAll(".*&*msg=|&+.*", "");
		}
		return "";
	}
}
