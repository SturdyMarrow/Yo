package com.jarvis.commands;

import com.jarvis.core.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VolumeCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(VolumeCommand.class);

    @Override
    public String execute(String[] args) {
        if (args.length == 0) {
            return "Please specify volume level (0-100) or mute/unmute.";
        }

        try {
            String action = args[0].toLowerCase();
            if (action.equals("mute")) {
                Runtime.getRuntime().exec(new String[]{"powershell", "-Command", "(Get-Volume).Mute = $true"});
                return "Muted system audio.";
            } else if (action.equals("unmute")) {
                Runtime.getRuntime().exec(new String[]{"powershell", "-Command", "(Get-Volume).Mute = $false"});
                return "Unmuted system audio.";
            } else {
                int volume = Integer.parseInt(action);
                if (volume >= 0 && volume <= 100) {
                    Runtime.getRuntime().exec(new String[]{"powershell", "-Command", "(Get-Volume).Volume = " + (volume / 100.0f)});
                    return "Set volume to " + volume + ".";
                } else {
                    return "Volume must be between 0 and 100.";
                }
            }
        } catch (Exception e) {
            logger.error("Error controlling volume", e);
            return "Error controlling volume: " + e.getMessage();
        }
    }

    @Override
    public String getName() {
        return "volume";
    }

    @Override
    public String getDescription() {
        return "Control system volume (e.g., 'volume 50', 'volume mute', 'volume unmute')";
    }
}
