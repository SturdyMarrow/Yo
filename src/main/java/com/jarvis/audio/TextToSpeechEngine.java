package com.jarvis.audio;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextToSpeechEngine {
    private static final Logger logger = LoggerFactory.getLogger(TextToSpeechEngine.class);
    private Voice voice;
    private volatile boolean isSpeaking = false;

    public TextToSpeechEngine() {
        try {
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.Kal");
            VoiceManager voiceManager = VoiceManager.getInstance();
            voice = voiceManager.getVoice("kevin16");
            
            if (voice != null) {
                voice.allocate();
                voice.setRate(150f);
                voice.setPitch(100f);
                logger.info("Text-to-Speech engine initialized with voice: " + voice.getName());
            } else {
                logger.warn("Default voice not found, attempting to use first available voice");
                Voice[] availableVoices = voiceManager.getVoices();
                if (availableVoices.length > 0) {
                    voice = availableVoices[0];
                    voice.allocate();
                    logger.info("Using fallback voice: " + voice.getName());
                } else {
                    logger.error("No TTS voices available");
                }
            }
        } catch (Exception e) {
            logger.error("Error initializing Text-to-Speech", e);
        }
    }

    public void speak(String text) {
        if (voice == null) {
            logger.warn("Voice not initialized, cannot speak: " + text);
            return;
        }

        new Thread(() -> {
            try {
                isSpeaking = true;
                logger.info("Speaking: " + text);
                voice.speak(text);
            } catch (Exception e) {
                logger.error("Error during speech synthesis", e);
            } finally {
                isSpeaking = false;
            }
        }).start();
    }

    public boolean isSpeaking() {
        return isSpeaking;
    }

    public void cleanup() {
        if (voice != null) {
            voice.deallocate();
        }
    }
}
