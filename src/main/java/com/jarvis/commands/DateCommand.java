package com.jarvis.commands;

import com.jarvis.core.Command;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateCommand implements Command {
    @Override
    public String execute(String[] args) {
        LocalDate today = LocalDate.now();
        String formatted = today.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy"));
        return "Today is " + formatted + ".";
    }

    @Override
    public String getName() {
        return "date";
    }

    @Override
    public String getDescription() {
        return "Tell the current date";
    }
}
