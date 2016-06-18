package com.test.alexander.rabbitmq.enums;

public enum PropertiesEnum {

	CONFIG_FILE("src/main/resources/config.properties"),
	QUEUE_HOST("queue.host"),
	QUEUE_NAME("queue.name");
	
	private String key;
	
	PropertiesEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
}
