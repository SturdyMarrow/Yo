package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrightnessCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(BrightnessCommand.class);

    @Override
    public String execute(String[] args) {
        if (args.length == 0) {
            return "Please specify brightness level (0-100).";
        }

        try {
            int brightness = Integer.parseInt(args[0]);
            if (brightness >= 0 && brightness <= 100) {
                Runtime.getRuntime().exec(new String[]{"powershell", "-Command", 
                    "(Get-WmiObject -Namespace root/WMI -Class WmiMonitorBrightnessMethods).WmiSetBrightness(1, " + brightness + ")"}); 
                return "Set brightness to " + brightness + " percent.";
            } else {
                return "Brightness must be between 0 and 100.";
            }
        } catch (NumberFormatException e) {
            return "Invalid brightness value. Please provide a number between 0 and 100.";
        } catch (Exception e) {
            logger.error("Error controlling brightness", e);
            return "Error controlling brightness: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "brightness";
    }

    @Override
    public String getDescription() {
        return "Control screen brightness (0-100)";
    }
}
