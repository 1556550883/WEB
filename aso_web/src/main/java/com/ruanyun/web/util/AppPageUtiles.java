package com.ruanyun.web.util;

import java.util.Map;

import com.ruanyun.common.model.Page;
import com.ruanyun.common.utils.EmptyUtils;

/**
 * 
 *  #(c) IFlytek ahsw <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 封装 手机端传过来的当前分页信息
 * 
 *  <br/>创建说明: 2013-12-2 下午03:35:57 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class AppPageUtiles {
	/**
	 * 功能描述:封装手机端的page信息
	 *
	 * @author L H T  2013-12-2 下午03:38:42
	 * 
	 * @param pageNum  当前第几页
	 * @param numPerPage  每页显示多少条
	 * @return
	 */
	public static Page<Map<String, Object>> packPages (Integer pageNum,Integer numPerPage){
		if (EmptyUtils.isEmpty(pageNum)) {
			pageNum=1;
		}
		if (EmptyUtils.isEmpty(numPerPage)) {
			numPerPage=5;
		}
		Page<Map<String,Object>> page=new Page<Map<String,Object>>();
		page.setPageNum(pageNum);
		page.setNumPerPage(numPerPage);
		return page;
	}

}
