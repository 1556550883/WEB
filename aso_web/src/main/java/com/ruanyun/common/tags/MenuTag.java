package com.ruanyun.common.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class MenuTag extends TagSupport{
	
	@SuppressWarnings("unused")
	private HttpServletRequest request;

	
	
	@Override
	public int doStartTag() throws JspException {
		
		return super.doStartTag();
	}
	
	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	
}
