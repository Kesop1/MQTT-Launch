package com.piotrak.mqttlaunch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.swing.*;
import java.awt.*;

@SpringBootApplication
@EnableScheduling

@PropertySource(value = {"classpath:application.properties",
		"file:/usr/local/tomcat/webapps/application.properties"}, ignoreResourceNotFound = true)
public class MqttLaunchApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MqttLaunchApplication.class).headless(false).run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MqttLaunchApplication.class);
	}

	@Override
	public void run(String... args) {
		JFrame frame = new JFrame("MQTT-Launch");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(200,100);
		JPanel panel = new JPanel(new BorderLayout());
		JTextField text = new JTextField("MQTT-Launch application");
		panel.add(text, BorderLayout.CENTER);
		frame.setContentPane(panel);
		frame.setVisible(true);
	}
}
