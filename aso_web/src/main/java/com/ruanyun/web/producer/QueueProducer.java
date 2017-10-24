package com.ruanyun.web.producer;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.SerializationUtils;

public class QueueProducer extends EndPoint
{  
	public QueueProducer(String endpointName) throws IOException, TimeoutException 
	{
		super(endpointName);
	}

    public synchronized void sendMessage(Serializable object) throws IOException 
    {  
        channel.basicPublish("", endPointName, null, SerializationUtils.serialize(object));  
    }
}
