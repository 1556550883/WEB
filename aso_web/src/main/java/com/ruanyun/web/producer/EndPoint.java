package com.ruanyun.web.producer;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class EndPoint
{  
	 protected Channel channel;  
	 protected Connection connection;  
	 protected String endPointName;  
    
	 public EndPoint(String endpointName) throws IOException, TimeoutException
	 {  
	      this.endPointName = endpointName;  
	        
	      //Create a connection factory  
	      ConnectionFactory factory = new ConnectionFactory();  
	        
	      //hostname of your rabbitmq server  
	      factory.setHost("localhost");  
	      factory.setPort(5672);  
	      factory.setUsername("aso");  
	      factory.setPassword("123456");  
	      factory.setVirtualHost("aso_host"); 
	      //getting a connection  
	      connection = factory.newConnection();  
	        
	      //creating a channel  
	      channel = connection.createChannel();  
	        
	      //declaring a queue for this channel. If queue does not exist,  
	      //it will be created on the server.  
	      channel.queueDeclare(endpointName, true, false, false, null);  
	 }  
    
	 public void sendMessage(Serializable object, String endName) throws IOException 
	 {  
	     channel.basicPublish("", endName, MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(object));  
	 }
 /** 
  * 关闭channel和connection。并非必须，因为隐含是自动调用的。 
  * @throws IOException 
 * @throws TimeoutException 
  */  
	public void close() throws IOException, TimeoutException
	{  
	    this.channel.close();  
	    this.connection.close();  
	}  
}  
