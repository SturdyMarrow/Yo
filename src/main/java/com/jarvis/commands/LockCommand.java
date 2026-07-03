package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LockCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(LockCommand.class);

    @Override
    public String execute(String[] args) {
        try {
            Runtime.getRuntime().exec("rundll32.exe user32.dll,LockWorkStation");
            logger.info("Computer locked");
            return "Locking the system.";
        } catch (Exception e) {
            logger.error("Error locking system", e);
            return "Error locking system: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "lock";
    }

    @Override
    public String getDescription() {
        return "Lock the computer";
    }
}
