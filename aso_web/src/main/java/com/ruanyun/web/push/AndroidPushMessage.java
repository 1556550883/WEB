package com.ruanyun.web.push;

import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushBroadcastMessageRequest;
import com.baidu.yun.channel.model.PushBroadcastMessageResponse;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.ruanyun.common.utils.ThreadService;
import com.ruanyun.web.push.model.PushMessageModel;
import com.ruanyun.web.util.Constants;
public class AndroidPushMessage {
	//ApiKey/SecretKey 百度推送需要
	private static final  String APIKEY=Constants.APIKEY;
	private static final String SECRETKEY=Constants.SECRETKEY;
	
	
	/**
	 * 功能描述:发送消息
	 *
	 * @author yangliu  2013-11-20 下午03:19:21
	 * 
	 * @param msg 消息对象
	 * @return
	 */
	public static Integer pushMessage(PushMessageModel  msg){
		//设置百度密匙
		ChannelKeyPair pair = new ChannelKeyPair(APIKEY, SECRETKEY);
		//百度连接客户端
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		//创建请求对象
		PushBroadcastMessageRequest request = new PushBroadcastMessageRequest();
		//type  1: web 2: pc 3:android 4:ios 5:wp	
//		request.setDeviceType(msg.getType());  
		// 0--广播消息 1--通知
		request.setMessageType(msg.getMsgType());  
		
		try {
			request.setMessage(JSONObject.fromObject(msg).toString());
			System.out.println(JSONObject.fromObject(msg).toString());
			PushBroadcastMessageResponse response = channelClient.pushBroadcastMessage(request);
			return response.getSuccessAmount();
		} catch (ChannelClientException e) {
			e.printStackTrace();
		} catch (ChannelServerException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 功能描述:发送用户信息
	 *
	 * @author yangliu  2013-11-20 下午03:20:44
	 * 
	 * @param msg 消息对象
	 * @param list 用户
	 * @return
	 */
	public static Integer pushMessage(PushMessageModel msg,String userId){
		//设置百度密匙
		ChannelKeyPair pair = new ChannelKeyPair(APIKEY, SECRETKEY);
		//百度连接客户端
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		//创建请求对象
		PushUnicastMessageRequest request = new PushUnicastMessageRequest();
		//type  1: web 2: pc 3:android 4:ios 5:wp	
//		request.setDeviceType(msg.getType());	
		// 0--广播消息 1--通知
		request.setMessageType(msg.getMsgType());
		//设置用户ID
		request.setUserId(userId);
		try {
			request.setMessage(JSONObject.fromObject(msg).toString());
			PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
			return response.getSuccessAmount();
		} catch (ChannelClientException e) {
		} catch (ChannelServerException e) {
		}
		return -1;
	}
	
	/**
	 * 功能描述:发送ios和android 通用通知
	 *
	 * @author L H T  2014-5-26 下午12:00:30
	 * 
	 * @param msg 消息实体
	 * @param baiduId 百度id
	 * @return
	 */
	public static  int pushChatMsg(PushMessageModel msg,String baiduId){
		System.out.println("消息id推送=="+baiduId);
		//设置百度密匙
		ChannelKeyPair pair = new ChannelKeyPair(APIKEY, SECRETKEY);
		//百度连接客户端
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		//创建请求对象
		PushUnicastMessageRequest request = new PushUnicastMessageRequest();
		//type  1: web 2: pc 3:android 4:ios 5:wp	
		//request.setDeviceType(msg.getPushPlatType());
		
		// DeployStatus => 1: Developer 2: Production
		request.setDeployStatus(1);
		// 0--广播消息 1--通知
		request.setMessageType(msg.getMsgType());
		//设置用户ID
		request.setUserId(baiduId);
		try {

			request.setMessage(JSONObject.fromObject(msg).toString());
			System.out.println("查看指定id推送数据==="+JSONObject.fromObject(msg).toString());
			PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
			return response.getSuccessAmount();
		} catch (ChannelClientException e) {
			e.printStackTrace();
		} catch (ChannelServerException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 功能描述: 使用线程发送消息
	 *
	 * @author yangliu  2013-11-20 下午04:31:16
	 * 
	 * @param msg
	 */
	public static void pushMessageThread(final PushMessageModel  msg){
		ThreadService.submit(new Runnable() {
			@Override
			public void run() {
				pushMessage(msg);
			}
		});
	}
	
	/**
	 * 功能描述:给固定用户发送消息
	 *
	 * @author yangliu  2013-11-20 下午04:32:00
	 * 
	 * @param msg 消息
	 * @param userId 用户ID
	 */
	public static void pushMessageThread(final PushMessageModel  msg,String userId){
		ThreadService.submit(new Runnable() {
			@Override
			public void run() {
				pushMessage(msg);
			}
		});
	}
	
	/**
	 * 功能描述:给固定用户发送消息
	 *
	 * @author yangliu  2013-11-20 下午04:32:00
	 * 
	 * @param msg 消息
	 * @param userId 用户ID
	 */
	public static void pushMessageThread(final PushMessageModel  msg,List<String> list){
		Iterator<String> it =  list.iterator();
		while(it.hasNext()){
			pushMessage(msg, it.next());
		}
	}

}
