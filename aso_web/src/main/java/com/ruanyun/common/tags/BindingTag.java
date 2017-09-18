package com.ruanyun.common.tags;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.ruanyun.common.cache.impl.AreaCache;
import com.ruanyun.common.cache.impl.PublicCache;
import com.ruanyun.common.cache.impl.StaticObjectCache;
import com.ruanyun.common.utils.EmptyUtils;
import com.ruanyun.common.utils.SysCode;
import com.ruanyun.web.model.sys.TDictionary;
/**
 * 
 *  #(c) ruanyun shouhou <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 绑定数据到request中
 * 
 *  <br/>创建说明: 2013-9-16 下午12:05:12 yangliu  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class BindingTag extends TagSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 绑定名称 可以多个,分开
	 */
	private String bingdingName;
	/**
	 * 父类code 可以多个 ,分开  数量必须和 bingdingName的一致
	 */
	private String parentCode;
	/**
	 * 类型 
	 */
	private String type="1";
	public String getBingdingName() {
		return bingdingName;
	}
	public void setBingdingName(String bingdingName) {
		this.bingdingName = bingdingName;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public int doStartTag() throws JspException {
		ServletRequest request=super.pageContext.getRequest();
		//类型  公共字典
		if("1".equals(type)){
			if(EmptyUtils.isNotEmpty(parentCode)){
				String[] parentCodes=parentCode.split(SysCode.COMMA);
				String []bingingNames=bingdingName.split(SysCode.COMMA);
				for(int i=0;i<parentCodes.length;i++){
				List<TDictionary> list=PublicCache.getItemList(parentCodes[i]);
					request.setAttribute(bingingNames[i], list);
				}
			}
			//静态变量
		}else if("2".equals(type)){
			bingdingName="constants";
			 Map<String,Object> constantsMap=StaticObjectCache.constantsMap;
			request.setAttribute(bingdingName,constantsMap);
		//权限静态变量
		}else if ("3".equals(type)){
				bingdingName="authMap";
			request.setAttribute(bingdingName,StaticObjectCache.authMap);
		}else if("4".equals(type)){
			request.setAttribute(bingdingName, AreaCache.getProvinces());
		}
		return super.doStartTag();
	}
	
	

}
