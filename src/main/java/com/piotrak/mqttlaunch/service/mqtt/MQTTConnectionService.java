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

//
//    /**
//     * Send command to the MQTT broker
//     * @param command Command to be sent
//     */
//    public void actOnConnection(Command command) {
//        try {
//            getConnection().send(command);
//        } catch (ConnectionException e) {
//            LOGGER.log(Level.WARNING, "Unable to send command: " + command, e);
//        }
//    }
//
    /**
     * Subscribe to the topic
     */
    @PostConstruct
    public void subscribeToTopic(){
        LOGGER.log(Level.INFO, "Subscribing to topic: " + subscribeTopic);
         mqttConnection.subscribe(subscribeTopic);
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

    /**
     * Check for the MQTT commands in the queue
     */
    public String checkForMessages() {
        return mqttConnection.getMessageQueue().poll();
    }
}
