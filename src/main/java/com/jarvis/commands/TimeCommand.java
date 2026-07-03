package com.jarvis.commands;

import com.jarvis.core.Command;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeCommand implements Command {
    @Override
    public String execute(String[] args) {
        LocalTime now = LocalTime.now();
        String formatted = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return "The current time is " + formatted + ".";
    }

    @Override
    public String getName() {
        return "time";
    }

    @Override
    public String getDescription() {
        return "Tell the current time";
    }
}
