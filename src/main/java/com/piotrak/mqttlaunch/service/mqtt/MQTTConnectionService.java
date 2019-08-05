package com.piotrak.mqttlaunch.service.mqtt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
@Service("mqttConnectionService")
public class MQTTConnectionService {

    private Logger LOGGER = Logger.getLogger("MQTTConnectionService");

    private MQTTConnection mqttConnection;

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
     * @param topic Topic to be subscribed to
     */
    public void subscribeToTopic(String topic){
        LOGGER.log(Level.INFO, "Subscribing to topic: " + topic);
         mqttConnection.subscribe(topic);
    }
//
//    /**
//     * Check for the MQTT commands in the queue
//     */
//    @Scheduled(fixedDelay = 1000)
//    @Async
//    @Override
//    public void checkForCommands() {
//        LOGGER.log(Level.FINE, "Looking for commands from the MQTT Connection");
//        super.checkForCommands();
//    }
//
//    /**
//     * Send the command to the appropriate elementService
//     * @param command Command to be sent
//     */
//    @Override
//    public void sendCommandToElementService(Command command) {
//        ElementService service = elementServiceTopicsMap.get(((MQTTCommand) command).getTopic());
//        if(service != null){
//            service.commandReceived(command);
//        } else{
//            LOGGER.log(Level.WARNING, "Unable to localize the ElementService for command: " + command);
//        }
//    }
//
}
