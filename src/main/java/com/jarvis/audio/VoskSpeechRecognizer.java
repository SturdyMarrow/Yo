package com.jarvis.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Placeholder for Vosk Speech Recognition
 * Vosk is not available in Maven Central Repository
 * This is a stub implementation
 */
public class VoskSpeechRecognizer {
    private static final Logger logger = LoggerFactory.getLogger(VoskSpeechRecognizer.class);
    private SpeechRecognitionListener listener;
    private boolean isListening = false;

    public interface SpeechRecognitionListener {
        void onSpeechRecognized(String text);
        void onPartialResult(String text);
        void onError(String error);
    }

    public VoskSpeechRecognizer(String modelPath) {
        logger.warn("Vosk Speech Recognition is not available in this build");
    }

    public void setSpeechRecognitionListener(SpeechRecognitionListener listener) {
        this.listener = listener;
    }

    public void startListening() {
        isListening = true;
        logger.info("Speech recognition started (stub implementation)");
        if (listener != null) {
            listener.onError("Speech recognition not available - Vosk library not found");
        }
    }

    public void stopListening() {
        isListening = false;
        logger.info("Speech recognition stopped");
    }
}
