package com.test.alexander.rabbitmq.util.observer;

public interface IRabbitMQObserver {

	public void handleMessage(String message);
}
