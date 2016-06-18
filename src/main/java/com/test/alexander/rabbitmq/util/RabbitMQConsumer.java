package com.test.alexander.rabbitmq.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.test.alexander.rabbitmq.util.observer.IRabbitMQObserver;

@Singleton
public class RabbitMQConsumer extends DefaultConsumer {
	
	private List<IRabbitMQObserver> observersList;

	public RabbitMQConsumer(Channel channel) {
		super(channel);
	}
	
	public void addRabbitMQObserver(IRabbitMQObserver rabbitMQObserver) {
		if(observersList == null) {
			observersList = new ArrayList<IRabbitMQObserver>();
		}
		
		observersList.add(rabbitMQObserver);
	}
	
	public void clearRabbitMQObserversList() {
		if(observersList != null) {
			observersList.clear();
		}
	}
	
	@Override
	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
		String message = new String(body, "UTF-8");
		
		for(IRabbitMQObserver observer : observersList) {
			observer.handleMessage(message);
		}
	}
}
