package com.test.alexander.rabbitmq.producer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.test.alexander.rabbitmq.util.RabbitMQUtil;
import com.test.alexander.rabbitmq.util.provider.RabbitMQUtilProvider;

public class Send {

	public static void main(String[] argv) {
		String message = "Hello World!";
		
		Injector rabbitMQInjector = Guice.createInjector(new RabbitMQUtilProvider());
		RabbitMQUtil rabbitMQ = rabbitMQInjector.getInstance(RabbitMQUtil.class);
		
		try {
			rabbitMQ.send(message);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
