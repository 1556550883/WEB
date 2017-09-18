package com.ruanyun.common.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ruanyun.common.cache.impl.AreaCache;
import com.ruanyun.common.cache.impl.PublicCache;
import com.ruanyun.common.utils.EmptyUtils;

@SuppressWarnings("serial")
public class ShowTag extends TagSupport {
	private String type="1";
	/**
	 * 父类code
	 */
	private String parentCode;
	
	/**
	 * 之类code
	 */
	private String itemCode;
	
	/**
	 * 是否显示简称
	 */
	private boolean jc=false;

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	

	
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public boolean isJc() {
		return jc;
	}
	public void setJc(boolean jc) {
		this.jc = jc;
	}

	@Override
	public int doStartTag() throws JspException {
		String result="";
		if ("1".equals(type)) {
			if(jc){
				result=PublicCache.getItemShortName(parentCode, itemCode);
			}else{
				result=PublicCache.getItemName(parentCode, itemCode);
			}
			
		}else if("2".equals(type)){//省份
			result = AreaCache.getProvinceName(itemCode);
		}else if("3".equals(type)){ //城市
			result = AreaCache.getCityName(parentCode, itemCode);
		}
		if(EmptyUtils.isEmpty(result))
			result=itemCode;
		JspWriter out=pageContext.getOut();
		try {
			out.write(result);
		} catch (IOException e) {
		}
		return super.doStartTag();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
