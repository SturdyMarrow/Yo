package com.jarvis.commands;

import com.jarvis.core.Command;
import java.util.Map;
import java.util.StringBuilder;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String execute(String[] args) {
        StringBuilder help = new StringBuilder("Available commands: ");
        for (String cmdName : commands.keySet()) {
            help.append(cmdName).append(", ");
        }
        return help.toString();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Display available commands";
    }
}
