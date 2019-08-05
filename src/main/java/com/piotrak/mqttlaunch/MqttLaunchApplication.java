package com.piotrak.mqttlaunch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication

@PropertySource(value = {"classpath:application.properties",
		"file:/usr/local/tomcat/webapps/application.properties"}, ignoreResourceNotFound = true)
public class MqttLaunchApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MqttLaunchApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MqttLaunchApplication.class);
	}
}
