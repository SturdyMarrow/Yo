package com.jarvis.core;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jarvis.commands.*;

public class CommandDispatcher {
    private static final Logger logger = LoggerFactory.getLogger(CommandDispatcher.class);
    private final Map<String, Command> commands = new HashMap<>();
    private final PersonalityEngine personalityEngine;

    public CommandDispatcher(PersonalityEngine personalityEngine) {
        this.personalityEngine = personalityEngine;
        registerCommands();
    }

    private void registerCommands() {
        commands.put("open", new OpenAppCommand());
        commands.put("search", new SearchCommand());
        commands.put("volume", new VolumeCommand());
        commands.put("time", new TimeCommand());
        commands.put("date", new DateCommand());
        commands.put("brightness", new BrightnessCommand());
        commands.put("shutdown", new ShutdownCommand());
        commands.put("restart", new RestartCommand());
        commands.put("lock", new LockCommand());
        commands.put("sleep", new SleepCommand());
        commands.put("system", new SystemStatsCommand());
        commands.put("battery", new BatteryCommand());
        commands.put("help", new HelpCommand(commands));
        logger.info("Registered " + commands.size() + " commands");
    }

    public String dispatch(String voiceInput) {
        String[] parts = voiceInput.toLowerCase().trim().split("\\s+", 2);
        String commandName = parts[0];
        String[] args = parts.length > 1 ? parts[1].split("\\s+") : new String[0];

        if (commands.containsKey(commandName)) {
            try {
                Command cmd = commands.get(commandName);
                String result = cmd.execute(args);
                String confirmation = personalityEngine.confirmCommand(commandName);
                logger.info("Command '" + commandName + "' executed successfully");
                return confirmation + " " + result;
            } catch (Exception e) {
                logger.error("Error executing command: " + commandName, e);
                return "I encountered an error. " + e.getMessage();
            }
        } else {
            logger.warn("Unknown command: " + commandName);
            return "I'm sorry. I don't recognize that command. Say 'help' for available commands.";
        }
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
