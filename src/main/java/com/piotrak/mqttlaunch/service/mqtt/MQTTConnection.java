package com.piotrak.mqttlaunch.service.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connection using the MQTT technology
 */
@Component("mqttConnection")
@ConfigurationProperties("mqtt")
public class MQTTConnection {

    private Logger LOGGER = Logger.getLogger("MQTTConnection");

    /**
     * MQTT broker host address
     */
    private String host = "0.0.0.0";

    /**
     * MQTT broker host port
     */
    private String port = "0";

    /**
     * MQTT broker host protocol
     */
    private String protocol = "tcp";

    /**
     * MqttClient that acts as a middle man between the application and MQTT broker
     */
    private MqttClient mqttClient;

    /**
     * Topics to listen to
     */
    private Set<String> subscribeTopics = new HashSet<>();

    /**
     * Commands received using the connection that are to be dealt with
     */
    private Queue<String> messageQueue = new LinkedList<>();

    /**
     * Connect to the MQTT broker
     */
    public void connect() {
        String uri = protocol + "://" + host + ":" + port;
        LOGGER.log(Level.INFO, "Connecting to " + uri);
        try {
            mqttClient = new MqttClient(uri, MqttClient.generateClientId(), new MemoryPersistence());
            setCallback();
            mqttClient.connect();
            LOGGER.log(Level.INFO, "Connected successfully");
            subscribeTopics.forEach(this::subscribe);
        } catch (MqttException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    /**
     * Is the application connected to the broker
     * @return true if the application is connected to the broker
     */
    public boolean isConnected() {
        if (mqttClient != null) {
            return mqttClient.isConnected();
        }
        return false;
    }

    /**
     * Disconnect from the MQTT broker
     */
    public void disconnect() {
        LOGGER.log(Level.INFO, "Disconnecting");
        if (isConnected()) {
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                LOGGER.log(Level.WARNING, "Exception occurred while disconnecting", e);
            }
        }
    }

    /**
     * Listen for messages in the topic
     * @param topic Topic to be subscribed to
     */
    void subscribe(String topic){
        subscribeTopics.add(topic);
        try {
            mqttClient.subscribe(topic);
        } catch (MqttException e) {
            LOGGER.log(Level.WARNING, "Unable to subscribe to topic " + topic, e);
        }
    }

    /**
     * Methods for MQTT connection
     */
    private void setCallback() {
        if (mqttClient != null) {
            mqttClient.setCallback(new MqttCallback() {

                /**
                 * When connection to the broker is lost retry then log the message
                 * @param throwable Error causing the disconnection
                 */
                @Override
                public void connectionLost(Throwable throwable) {
                    LOGGER.log(Level.SEVERE, "Connection lost! Retrying", throwable);
                    if(!isConnected()){
                        connect();
                    }
                    if(!isConnected()){
                        LOGGER.log(Level.WARNING, "Unable to reconnect, connection disabled");
                    }
                }

                /**
                 * Message arrived from the subscribed topic, add it to the messages queue
                 * @param topic Topic of the message
                 * @param mqttMessage Message content
                 */
                @Override
                public void messageArrived(String topic, MqttMessage mqttMessage) {
                    String message = new String(mqttMessage.getPayload());
                    LOGGER.log(Level.INFO, "Message received: " + topic + " " + message);
                    getMessageQueue().add(message);
                }

                /**
                 * Message sent was delivered successfully
                 * @param iMqttDeliveryToken token of the delivered message
                 */
                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                    LOGGER.log(Level.FINE, "Message successfully sent: " + iMqttDeliveryToken);
                }
            });
        }
    }

    public void sendMessage(String message, String topic){
        LOGGER.log(Level.INFO, String.format("Sending message: %s to the topic: %s", message, topic));
        try {
            getMqttClient().publish(topic, message.getBytes(), 0, false);
        } catch (MqttException e) {
            LOGGER.log(Level.SEVERE, "Unable to send the message", e.getMessage());
        }
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Queue<String> getMessageQueue() {
        return messageQueue;
    }

}
