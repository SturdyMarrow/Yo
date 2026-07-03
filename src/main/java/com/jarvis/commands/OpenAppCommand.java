package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenAppCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(OpenAppCommand.class);

    @Override
    public String execute(String[] args) {
        if (args.length == 0) {
            return "Please specify an application to open.";
        }

        String appName = args[0].toLowerCase();
        try {
            switch (appName) {
                case "browser":
                case "chrome":
                case "firefox":
                    Desktop.getDesktop().browse(new java.net.URI("https://www.google.com"));
                    return "Opening browser.";
                case "notepad":
                    Runtime.getRuntime().exec("notepad");
                    return "Opening Notepad.";
                case "calculator":
                    Runtime.getRuntime().exec("calc");
                    return "Opening Calculator.";
                case "explorer":
                case "files":
                    Runtime.getRuntime().exec("explorer");
                    return "Opening File Explorer.";
                default:
                    return "Application '" + appName + "' not recognized.";
            }
        } catch (IOException e) {
            logger.error("Error opening application: " + appName, e);
            return "Error opening application: " + e.getMessage();
        } catch (Exception e) {
            logger.error("Unexpected error", e);
            return "An unexpected error occurred.";
        }
    }

    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getDescription() {
        return "Open an application (e.g., browser, notepad, calculator, explorer)";
    }
}
