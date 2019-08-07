package com.piotrak.mqttlaunch.service.launcher;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for launching the programs
 */
@Service
@ConfigurationProperties
public class LauncherService {

    private Logger LOGGER = Logger.getLogger(this.getClass().getName());

    /**
     * Messages corresponding to the programs
     */
    private Map<String, String> message = new HashMap<>();

    /**
     * Launch the program based on the message received
     * @param message message for the program
     */
    public void launchApplication(String message){
        LOGGER.log(Level.INFO, "Message arrived: " + message);
        String exec = getMessage().get(message);
        if(exec != null){
            LOGGER.log(Level.INFO, "Launching: " + exec);
            try {
                Process process = new ProcessBuilder(exec).start();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Unable to launch " + exec, e.getMessage());
            }
        } else{
            LOGGER.log(Level.SEVERE, "Unable to find program to launch for the message: " + message);
        }
    }

    public Map<String, String> getMessage() {
        return message;
    }

    public void setMessage(Map<String, String> message) {
        this.message = message;
    }
}
