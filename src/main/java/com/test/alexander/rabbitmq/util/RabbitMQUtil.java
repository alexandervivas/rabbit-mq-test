package com.test.alexander.rabbitmq.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.test.alexander.rabbitmq.util.config.IConfiguration;
import com.test.alexander.rabbitmq.util.observer.IRabbitMQObserver;

public class RabbitMQUtil {
	
	private Connection queueConnection;
	private Channel queueChannel;
	private RabbitMQConsumer queueConsumer;
	private IConfiguration config;
	private Logger logger = LogManager.getLogger(RabbitMQUtil.class);
	
	@Inject
	public void setConfig(IConfiguration config) {
		this.config = config;
	}
	
	private Channel getRabbitMQChannel() throws IOException, TimeoutException {
		
		if(queueConnection == null || queueChannel == null) {
			
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(config.getQueueHost());
			queueConnection = factory.newConnection();
			queueChannel = queueConnection.createChannel();
			queueConsumer = new RabbitMQConsumer(queueChannel);
		}
		
		return queueChannel;
	}
	
	private void closeRabbitMQChannel() throws IOException, TimeoutException {
		if(queueChannel != null) {
			queueChannel.close();
		}
		
		if(queueConnection != null) {
			queueConnection.close();
		}
	}
	
	public void send(String message) throws IOException, TimeoutException {
		// establish a connection and create a channel
		Channel rabbitChannel = getRabbitMQChannel();
		// because declaring a queue is idempotent
		rabbitChannel.queueDeclare(config.getQueueName(), false, false, false, null);
		// send the message
		rabbitChannel.basicPublish("", config.getQueueName(), null, message.getBytes());
		// prints out the send signal
		logger.info(" [x] Sent '" + message + "'");
		// close the channel and connection
		closeRabbitMQChannel();
	}
	
	public void recive(IRabbitMQObserver rabbitMQObserver) throws IOException, TimeoutException {
		// establish a connection and create a channel
		Channel rabbitChannel = getRabbitMQChannel();
		// because declaring a queue is idempotent
		rabbitChannel.queueDeclare(config.getQueueName(), false, false, false, null);
		// prints out the ready state
		logger.info(" [*] Waiting for messages. To exit press CTRL+C");
		// add observers
		queueConsumer.clearRabbitMQObserversList();
		queueConsumer.addRabbitMQObserver(rabbitMQObserver);
		rabbitChannel.basicConsume(config.getQueueName(), true, queueConsumer);
	}
}
