package com.ruanyun.web.producer;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class EndPoint extends Observable
{  
	 protected Channel channel;  
	 protected Connection connection;  
	 protected String endPointName; 
	 protected boolean autoDelete;  
	 protected ConnectionFactory factory;
	 protected AMQP.Queue.DeclareOk dok;    
	 public EndPoint(String endpointName, boolean autoDelete) throws IOException, TimeoutException
	 {  
	      this.endPointName = endpointName;  
	      this.autoDelete = autoDelete;
	        
	      //Create a connection factory  
	      factory = new ConnectionFactory();  
	      //hostname of your rabbitmq server  
	      factory.setHost("localhost");  
	      factory.setPort(5672);  
	      factory.setUsername("aso");  
	      factory.setPassword("123456");  
	      factory.setVirtualHost("aso_host"); 
	      factory.setConnectionTimeout(5000);
	      //getting a connection  
	      connection = factory.newConnection();  
	        
	      //creating a channel  
	      channel = connection.createChannel();  
	        
	      //declaring a queue for this channel. If queue does not exist,  
	      //it will be created on the server.  
	      dok = channel.queueDeclare(endpointName, true, false, autoDelete, null);  
	 }  
    
	 public void sendMessage(Serializable object, String endName) throws IOException 
	 {  
	     channel.basicPublish("", endName, MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(object));  
	 }
	 
	 public int getMessageCount() 
	 {
		 return dok.getMessageCount();
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
