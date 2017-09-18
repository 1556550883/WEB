package com.ruanyun.web.util;

import com.ruanyun.web.push.AndroidPushMessage;
import com.ruanyun.web.push.model.PushMessageModel;
/**
 * 
 *  #(c) IFlytek ahsw <br/>
 *
 *  版本说明: $id:$ <br/>
 *
 *  功能说明: 公共的消息推送
 * 
 *  <br/>创建说明: 2013-12-7 上午10:32:19 L H T  创建文件<br/>
 * 
 *  修改历史:<br/>
 *
 */
public class CommonPushMsg {
	/**
	 * 功能描述:消息推送
	 *
	 * @author L H T  2013-12-7 上午10:39:03
	 * 
	 * @param title 推送标题
	 * @param description 推送描述
	 * @param obj_content 推送其他自定义内容
	 * @param type  推送平台类型  1: web 2: pc 3:android 4:ios 5:wp
	 * @param msgType 推送消息类型 0--广播消息 1--通知
	 * @return
	 */
	public static void pushMsg(String title,String description,Object obj_content,Integer msgType){
		PushMessageModel  pushmsg=new PushMessageModel();
		pushmsg.setTitle(title);
		pushmsg.setDescription(description);
		pushmsg.setObj_content(obj_content);
//		pushmsg.setType(type);
		pushmsg.setMsgType(msgType);
		AndroidPushMessage.pushMessage(pushmsg);
	}
	/**
	 * 功能描述:推送指定用户消息（需要百度id）
	 *
	 * @author L H T  2013-12-7 上午10:39:03
	 * 
	 * @param title 推送标题
	 * @param description 推送描述
	 * @param obj_content 推送其他自定义内容
	 * @param msgType 推送消息类型 0--广播消息 1--通知
	 * @param userBaiDuId 推送消息的  用户百度id
	 * @return
	 */
	public static void pushUserMsg(String title,String description,Object obj_content,Integer msgType,String userBaiDuId){
		PushMessageModel  pushmsg=new PushMessageModel();
		pushmsg.setTitle(title);
		pushmsg.setDescription(description);
		pushmsg.setObj_content(obj_content);
//		pushmsg.setType(type);
		pushmsg.setMsgType(msgType);
		int result=AndroidPushMessage.pushChatMsg(pushmsg,userBaiDuId );
		System.out.println("推送结果："+result);
	}
	
}
