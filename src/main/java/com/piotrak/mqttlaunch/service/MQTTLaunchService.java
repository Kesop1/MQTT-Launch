package com.piotrak.mqttlaunch.service;

import com.piotrak.mqttlaunch.service.launcher.LauncherService;
import com.piotrak.mqttlaunch.service.mqtt.MQTTConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for communication between MQTT and programs
 */
@Service
public class MQTTLaunchService {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    @Autowired
    private MQTTConnectionService mqttConnectionService;

    @Autowired
    private LauncherService launcherService;

    /**
     * Check for the MQTT messages in the queue, if found launch the corresponding program
     */
    @Scheduled(fixedDelay = 1000)
    @Async
    public void checkForMessages() {
        LOGGER.log(Level.FINE, "Looking for messages from the MQTT Connection");
        String message;
        do {
            message = mqttConnectionService.checkForMessages();
            if (message != null) {
                LOGGER.log(Level.FINE, "Message found: " + message);
                if("on".equalsIgnoreCase(message)){
                    mqttConnectionService.publishMessage("on");
                } else {
                    launcherService.launchApplication(message);
                }
            }
        } while (message != null);
    }
}
