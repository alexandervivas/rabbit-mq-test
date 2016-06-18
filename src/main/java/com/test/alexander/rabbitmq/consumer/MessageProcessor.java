package com.test.alexander.rabbitmq.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.test.alexander.rabbitmq.util.observer.IRabbitMQObserver;

public class MessageProcessor implements IRabbitMQObserver {

	private Logger logger = LogManager.getLogger(MessageProcessor.class);

	public void handleMessage(String message) {
		logger .info(" [x] Received '" + message + "'");
	}

}
