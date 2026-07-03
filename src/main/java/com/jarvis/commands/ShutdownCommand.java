package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ShutdownCommand.class);

    @Override
    public String execute(String[] args) {
        try {
            Runtime.getRuntime().exec("shutdown /s /t 30");
            logger.info("Shutdown initiated");
            return "System will shut down in 30 seconds. Say 'cancel shutdown' to abort.";
        } catch (Exception e) {
            logger.error("Error initiating shutdown", e);
            return "Error initiating shutdown: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "shutdown";
    }

    @Override
    public String getDescription() {
        return "Shut down the computer";
    }
}
