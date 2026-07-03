package com.jarvis.commands;

import com.jarvis.core.Command;
import oshi.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatteryCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(BatteryCommand.class);

    @Override
    public String execute(String[] args) {
        try {
            SystemInfo si = new SystemInfo();
            double batteryPercent = si.getHardware().getPowerSupplies().stream()
                    .mapToDouble(ps -> ps.getRemainingCapacityPercent())
                    .average()
                    .orElse(0);
            
            String batteryStatus = String.format("Battery at %.0f%%.", batteryPercent * 100);
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
