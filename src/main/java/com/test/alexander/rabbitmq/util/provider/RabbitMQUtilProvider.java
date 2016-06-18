package com.test.alexander.rabbitmq.util.provider;

import com.google.inject.AbstractModule;
import com.test.alexander.rabbitmq.util.config.IConfiguration;
import com.test.alexander.rabbitmq.util.config.impl.FileConfigurationImpl;

public class RabbitMQUtilProvider extends AbstractModule {

	@Override
	protected void configure() {
		bind(IConfiguration.class).to(FileConfigurationImpl.class);
	}

}
