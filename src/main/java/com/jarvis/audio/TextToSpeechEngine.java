package com.jarvis.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Placeholder for Text-to-Speech Engine
 * FreeTTS is not available in Maven Central Repository
 * This is a stub implementation
 */
public class TextToSpeechEngine {
    private static final Logger logger = LoggerFactory.getLogger(TextToSpeechEngine.class);

    public TextToSpeechEngine() {
        logger.warn("Text-to-Speech Engine initialized (stub - FreeTTS not available)");
    }

    public void speak(String text) {
        logger.info("TTS (Stub): " + text);
        System.out.println("[JARVIS SAYS] " + text);
    }

    public void shutdown() {
        logger.info("TTS Engine shutdown");
    }
}
