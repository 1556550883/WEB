package com.baidu.yun.channel.sample;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

public class IosPushNotificationSample {

	public static void main(String[] args) {
		
		String apiKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		String secretKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
		
		BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		
		
		channelClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		
		try {
			
			PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			request.setDeviceType(4);	// device_type => 1: web 2: pc 3:android 4:ios 5:wp		
			request.setDeployStatus(2); // DeployStatus => 1: Developer 2: Production 
			request.setChannelId(11111111111L);	
			request.setUserId("1111111111111");	 
			
			request.setMessageType(1);
			request.setMessage("{\"aps\":{\"alert\":\"Hello Baidu Channel\"}}");
			
			PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
				
			System.out.println("push amount : " + response.getSuccessAmount()); 
			
		} catch (ChannelClientException e) {
			e.printStackTrace();
		} catch (ChannelServerException e) {
			System.out.println(
					String.format("request_id: %d, error_code: %d, error_message: %s" , 
						e.getRequestId(), e.getErrorCode(), e.getErrorMsg()
						)
					);
		}
		
	}
	
}
