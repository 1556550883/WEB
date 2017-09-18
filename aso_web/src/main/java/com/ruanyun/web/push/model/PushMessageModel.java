package com.ruanyun.web.push.model;

/**
 * 发送消息bean
 * @author yangliu
 *
 */
public class PushMessageModel {
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 内容
	 */
	private String description;
	
	/**
	 * 推送类型  ---平台
	 *  1: web 2: pc 3:android 4:ios 5:wp	
	 */
//	private Integer type=3;
	
	/**
	 * 推送的消息类型
	 * 
	 * 0--广播消息 1--通知
	 */
	private Integer msgType=1;
	/**
	 * 放置推送的其他内容
	 */
	private Object obj_content="";
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public Integer getType() {
//		return type;
//	}
//	public void setType(Integer type) {
//		this.type = type;
//	}
	public Integer getMsgType() {
		return msgType;
	}
	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}
	public Object getObj_content() {
		return obj_content;
	}
	public void setObj_content(Object objContent) {
		obj_content = objContent;
	}
	
	

}
