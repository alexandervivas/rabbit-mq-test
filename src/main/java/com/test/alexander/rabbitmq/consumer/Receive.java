package com.test.alexander.rabbitmq.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.test.alexander.rabbitmq.util.RabbitMQUtil;
import com.test.alexander.rabbitmq.util.provider.RabbitMQUtilProvider;

public class Receive {

	public static void main(String[] argv) {
		
		Injector rabbitMQInjector = Guice.createInjector(new RabbitMQUtilProvider());
		RabbitMQUtil rabbitMQ = rabbitMQInjector.getInstance(RabbitMQUtil.class);
		
		try {
			rabbitMQ.recive(new MessageProcessor());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
