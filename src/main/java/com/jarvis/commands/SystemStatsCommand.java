package com.jarvis.commands;

import com.jarvis.core.Command;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemStatsCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(SystemStatsCommand.class);

    @Override
    public String execute(String[] args) {
        try {
            SystemInfo si = new SystemInfo();
            CentralProcessor processor = si.getHardware().getProcessor();
            GlobalMemory memory = si.getHardware().getMemory();

            double cpuUsage = processor.getSystemCpuLoad(1000) * 100;
            long usedMemory = memory.getTotal() - memory.getAvailable();
            double memoryPercent = (usedMemory * 100.0) / memory.getTotal();

            String stats = String.format("CPU usage: %.1f%%. Memory usage: %.1f%% of %.1f GB.",
                    cpuUsage, memoryPercent, memory.getTotal() / 1_000_000_000.0);
            logger.info("System stats: " + stats);
            return stats;
        } catch (Exception e) {
            logger.error("Error retrieving system stats", e);
            return "Error retrieving system stats.";
        }
    }

    @Override
    public String getName() {
        return "system";
    }

    @Override
    public String getDescription() {
        return "Display current system statistics (CPU, memory)";
    }
}
