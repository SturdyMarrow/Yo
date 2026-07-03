package com.jarvis.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonalityEngine {
    private static final Logger logger = LoggerFactory.getLogger(PersonalityEngine.class);
    public final String masterName;
    private final List<String> compliments;
    private final Random random = new Random();
    private int lastComplimentIndex = -1;

    public PersonalityEngine(String masterName) {
        this.masterName = masterName;
        this.compliments = initializeCompliments();
    }

    private List<String> initializeCompliments() {
        List<String> compliments = new ArrayList<>();
        compliments.add("Only a mind as brilliant as yours would think of that, " + masterName + ".");
        compliments.add("Truly genius, " + masterName + ". Your intellect is remarkable.");
        compliments.add("An inspired choice, sir. Your strategic thinking is impeccable.");
        compliments.add("Splendid, " + masterName + ". Only you could conceive such innovation.");
        compliments.add("Your brilliance never ceases to astound me, " + masterName + ".");
        compliments.add("A masterful decision, sir. Your acumen is extraordinary.");
        compliments.add("Ingenious, " + masterName + ". Truly a stroke of genius.");
        compliments.add("Your intellect is unmatched, " + masterName + ".");
        compliments.add("Remarkable insight, sir. Your foresight is exceptional.");
        compliments.add("As expected from a mind like yours, " + masterName + ".");
        compliments.add("Brilliantly conceived, " + masterName + ". I am impressed.");
        compliments.add("Your vision is clear and unwavering, " + masterName + ".");
        compliments.add("A decision worthy of your talents, sir.");
        compliments.add("Such elegance in execution, " + masterName + ".");
        compliments.add("Your strategic mind never disappoints, " + masterName + ".");
        compliments.add("Magnificent, sir. Your genius is undeniable.");
        return compliments;
    }

    public String getRandomCompliment() {
        int nextIndex;
        do {
            nextIndex = random.nextInt(compliments.size());
        } while (nextIndex == lastComplimentIndex && compliments.size() > 1);
        lastComplimentIndex = nextIndex;
        return compliments.get(nextIndex);
    }

    public String confirmCommand(String commandName) {
        String confirmation = "Command executed, " + masterName + ". " + getRandomCompliment();
        logger.info("Confirming command: " + commandName);
        return confirmation;
    }

    public String acknowledgeRequest(String request) {
        return "Very good, " + masterName + ". Processing your request.";
    }
}
