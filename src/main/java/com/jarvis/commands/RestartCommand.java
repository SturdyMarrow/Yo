package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestartCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(RestartCommand.class);

    @Override
    public String execute(String[] args) {
        try {
            Runtime.getRuntime().exec("shutdown /r /t 30");
            logger.info("Restart initiated");
            return "System will restart in 30 seconds.";
        } catch (Exception e) {
            logger.error("Error initiating restart", e);
            return "Error initiating restart: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "restart";
    }

    @Override
    public String getDescription() {
        return "Restart the computer";
    }
}
