package com.test.alexander.rabbitmq.util.config.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import com.google.inject.Singleton;
import com.test.alexander.rabbitmq.enums.PropertiesEnum;
import com.test.alexander.rabbitmq.util.config.IConfiguration;

@Singleton
public class FileConfigurationImpl implements IConfiguration {
	
	private Properties properties;
	
	public FileConfigurationImpl() throws IOException {
		File file = new File(PropertiesEnum.CONFIG_FILE.getKey());
		
		if(!file.exists() || file.isDirectory()) {
			throw new IOException(PropertiesEnum.CONFIG_FILE.getKey() + " is not a valid properties file");
		}
		
		try {
			Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			
			try {
				properties = new Properties();
				properties.load(reader);
			} finally {
				reader.close();
			}
		} catch (Throwable e) {
            throw new RuntimeException("Unable to read property file: " + file.getAbsolutePath(), e);
        }
	}

	public String getQueueHost() {
		return properties.getProperty(PropertiesEnum.QUEUE_HOST.getKey());
	}

	public String getQueueName() {
		return properties.getProperty(PropertiesEnum.QUEUE_NAME.getKey());
	}

}
