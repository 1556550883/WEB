package com.ruanyun.web.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 *  #(c) IFlytek ahsw <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 返回封装dwz的ajax 提交， 返回数据
 * 
 *  <br/>创建说明: 2013-11-25 下午12:26:31 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class CallbackAjaxDone {
/**
 * 功能描述:ajax的表单提交 返回的 数据格式 封装
 *
 * @author L H T  2013-11-25 下午12:34:22
 * 
 * @param statusCode 返回的状态  200 成功，300失败，301回话超时
 * @param message  提示信息
 * @param navTabId  显示的  id 就是rel 引用的值,所指打开上级窗口的id
 * @param forwardUrl 需要跳转的地址
 * @param callbackType  callbackType如果是closeCurrent就会关闭当前tab</br>
 *                      只有callbackType="forward"时需要forwardUrl值</br>
 *                      当callbackType="redirect"时 ，重定向整个页面，
 *                      不在根据navTabId的值，forwardUrl，只要给您要跳转的方法路劲就行
 * @return
 */
	public static Map<String,String> AjaxDone(String statusCode,String message,String navTabId,String forwardUrl,String callbackType){
		Map<String, String> map = new HashMap<String, String>();
		map.put("statusCode", statusCode);
		map.put("message", message);
		map.put("navTabId", navTabId);
		map.put("rel", "");
		map.put("callbackType", callbackType);
		map.put("forwardUrl",forwardUrl);
		return map;
		
	}

}
