package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatteryCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(BatteryCommand.class);

    @Override
    public String execute(String[] args) {
        try {
            // Simplified version - returns a default message
            // Full version requires OSHI library system info access
            String batteryStatus = "Battery information not available in this build.";
            logger.info(batteryStatus);
            return batteryStatus;
        } catch (Exception e) {
            logger.error("Error retrieving battery info", e);
            return "Error retrieving battery information.";
        }
    }

    @Override
    public String getName() {
        return "battery";
    }

    @Override
    public String getDescription() {
        return "Display current battery percentage";
    }
}
