package com.ruanyun.web.producer;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

import com.rabbitmq.client.MessageProperties;

public class QueueProducer extends EndPoint
{  
	public QueueProducer(String endpointName) throws IOException, TimeoutException 
	{
		super(endpointName);
	}

    public void sendMessage(Serializable object) throws IOException 
    {  
        channel.basicPublish("", endPointName, MessageProperties.PERSISTENT_TEXT_PLAIN, SerializationUtils.serialize(object));  
    }
}
