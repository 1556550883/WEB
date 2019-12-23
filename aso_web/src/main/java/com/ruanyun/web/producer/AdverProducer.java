package com.ruanyun.web.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AdverProducer extends EndPoint
{
	public AdverProducer(String endpointName) throws IOException, TimeoutException
	{
		super(endpointName, false);
	}
}
