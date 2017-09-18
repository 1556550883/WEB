package com.ruanyun.common.tags;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.web.model.sys.TAuthority;
import com.ruanyun.web.model.sys.TUser;
import com.ruanyun.web.util.Constants;
import com.ruanyun.web.util.SecurityUtils;

/**
 * 
 *  #(c) ruanyun shouhou <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 权限控制tag
 * 
 *  <br/>创建说明: 2013-9-14 下午02:27:58 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class AuthorizeTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**提供的权限类型必须全部都满足*/
	 private String ifAllGranted;
	 /**提供权限满足一个就OK*/
	 private String ifAnyGranted;
	 /**提供权限不满足*/
	 private String ifNotGranted;

	@Override
	public int doStartTag() throws JspException {
		if (StringUtils.isEmpty(ifAllGranted)
				&& StringUtils.isEmpty(ifAnyGranted)
				&& StringUtils.isEmpty(ifNotGranted))
			return SKIP_BODY;
		HttpSession session = pageContext.getSession();
		TUser user = (TUser) session
				.getAttribute(Constants.SESSION_KEY_USERINFO);
		if(user==null)
			return SKIP_BODY;
		List<TAuthority> auths = user.getAuths();
		
		boolean isTrue=true;
		if(isTrue&&EmptyUtils.isNotEmpty(ifAllGranted))
			isTrue=SecurityUtils.isAllGranted(ifAllGranted, auths);
		if(isTrue&&EmptyUtils.isNotEmpty(ifAnyGranted))
			isTrue=SecurityUtils.isAnyGranted(ifAnyGranted, auths);
		if(isTrue&&EmptyUtils.isNotEmpty(ifNotGranted))
			isTrue=SecurityUtils.isNotGranted(ifNotGranted, auths);
		if (isTrue)
			return EVAL_BODY_INCLUDE;
		return SKIP_BODY;
	}
	
	public String getIfAllGranted() {
		return ifAllGranted;
	}

	public void setIfAllGranted(String ifAllGranted) {
		this.ifAllGranted = ifAllGranted;
	}

	public String getIfAnyGranted() {
		return ifAnyGranted;
	}

	public void setIfAnyGranted(String ifAnyGranted) {
		this.ifAnyGranted = ifAnyGranted;
	}

	public String getIfNotGranted() {
		return ifNotGranted;
	}

	public void setIfNotGranted(String ifNotGranted) {
		this.ifNotGranted = ifNotGranted;
	}
	

}
