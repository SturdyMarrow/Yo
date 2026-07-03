package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SleepCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SleepCommand.class);

    @Override
    public String execute(String[] args) {
        try {
            Runtime.getRuntime().exec("rundll32.exe powrprof.dll,SetSuspendState 0,1,0");
            logger.info("Sleep mode activated");
            return "Putting system to sleep.";
        } catch (Exception e) {
            logger.error("Error activating sleep mode", e);
            return "Error activating sleep: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "sleep";
    }

    @Override
    public String getDescription() {
        return "Put the computer to sleep";
    }
}
