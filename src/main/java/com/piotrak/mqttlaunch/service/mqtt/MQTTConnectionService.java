package com.piotrak.mqttlaunch.service.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for the MQTT connection
 */
@Service("mqttConnectionService")
@ConfigurationProperties
public class MQTTConnectionService {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    private MQTTConnection mqttConnection;

    private String subscribeTopic;

    private String publishTopic;

    public MQTTConnectionService(@Autowired MQTTConnection mqttConnection) {
        this.mqttConnection = mqttConnection;
        mqttConnection.connect();
    }

    /**
     * Subscribe to the topic
     */
    @PostConstruct
    public void subscribeToTopic(){
        LOGGER.log(Level.INFO, "Subscribing to topic: " + subscribeTopic);
         mqttConnection.subscribe(subscribeTopic);
    }

    /**
     * Check for the MQTT commands in the queue
     */
    public String checkForMessages() {
        return mqttConnection.getMessageQueue().poll();
    }

    /**
     * Publish a message to the broker
     * @param message message to be published
     */
    public void publishMessage(String message){
        mqttConnection.sendMessage(message, publishTopic);
    }

    public String getSubscribeTopic() {
        return subscribeTopic;
    }

    public void setSubscribeTopic(String subscribeTopic) {
        this.subscribeTopic = subscribeTopic;
    }

    public String getPublishTopic() {
        return publishTopic;
    }

    public void setPublishTopic(String publishTopic) {
        this.publishTopic = publishTopic;
    }
}
