package com.jarvis.audio;

import org.vosk.LibVosk;
import org.vosk.LogLevel;
import org.vosk.Model;
import org.vosk.Recognizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.sound.sampled.*;
import java.io.IOException;

public class VoskSpeechRecognizer {
    private static final Logger logger = LoggerFactory.getLogger(VoskSpeechRecognizer.class);
    private Model model;
    private Recognizer recognizer;
    private AudioInputStream audioStream;
    private TargetDataLine microphone;
    private volatile boolean isListening = false;
    private SpeechRecognitionListener listener;

    public interface SpeechRecognitionListener {
        void onSpeechRecognized(String text);
        void onPartialResult(String text);
        void onError(String error);
    }

    public VoskSpeechRecognizer(String modelPath) throws IOException {
        LibVosk.setLogLevel(LogLevel.DEBUG);
        this.model = new Model(modelPath);
        logger.info("Vosk model loaded from: " + modelPath);
    }

    public void setSpeechRecognitionListener(SpeechRecognitionListener listener) {
        this.listener = listener;
    }

    public void startListening() {
        if (isListening) {
            logger.warn("Already listening");
            return;
        }

        new Thread(() -> {
            try {
                recognizer = new Recognizer(model, 16000.0f);
                AudioFormat format = new AudioFormat(16000.0f, 16, 1, true, false);
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
                microphone = (TargetDataLine) AudioSystem.getLine(info);
                microphone.open(format);
                microphone.start();
                
                isListening = true;
                logger.info("Speech recognition started");
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                while (isListening) {
                    bytesRead = microphone.read(buffer, 0, buffer.length);
                    if (bytesRead > 0) {
                        if (recognizer.acceptWaveForm(buffer, bytesRead)) {
                            String result = recognizer.getResult();
                            if (listener != null && !result.isEmpty()) {
                                listener.onSpeechRecognized(parseVoskResult(result));
                            }
                        } else {
                            String partial = recognizer.getPartialResult();
                            if (listener != null) {
                                listener.onPartialResult(parseVoskResult(partial));
                            }
                        }
                    }
                }
            } catch (Exception e) {
                logger.error("Error in speech recognition", e);
                if (listener != null) {
                    listener.onError(e.getMessage());
                }
            } finally {
                stopListening();
            }
        }).start();
    }

    public void stopListening() {
        isListening = false;
        if (microphone != null) {
            microphone.stop();
            microphone.close();
        }
        if (recognizer != null) {
            recognizer.free();
        }
        logger.info("Speech recognition stopped");
    }

    private String parseVoskResult(String json) {
        try {
            if (json.contains("\"result\":")) {
                int start = json.indexOf("\"text\":") + 8;
                int end = json.indexOf('"', start);
                if (start > 7 && end > start) {
                    return json.substring(start, end);
                }
            }
            if (json.contains("\"partial\":")) {
                int start = json.indexOf("\"partial\":") + 11;
                int end = json.indexOf('"', start);
                if (start > 10 && end > start) {
                    return json.substring(start, end);
                }
            }
        } catch (Exception e) {
            logger.debug("Error parsing Vosk result", e);
        }
        return "";
    }

    public boolean isListening() {
        return isListening;
    }

    public void cleanup() {
        stopListening();
        if (model != null) {
            model.free();
        }
    }
}
