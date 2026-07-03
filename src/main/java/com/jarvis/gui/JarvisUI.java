package com.jarvis.gui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.jarvis.audio.VoskSpeechRecognizer;
import com.jarvis.audio.TextToSpeechEngine;
import com.jarvis.core.CommandDispatcher;
import com.jarvis.core.PersonalityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JarvisUI {
    private static final Logger logger = LoggerFactory.getLogger(JarvisUI.class);
    private TextArea logArea;
    private Label statusLabel;
    private Button listenButton;
    private Button stopButton;
    private VoskSpeechRecognizer speechRecognizer;
    private TextToSpeechEngine ttsEngine;
    private CommandDispatcher commandDispatcher;
    private PersonalityEngine personalityEngine;

    public void show(Stage primaryStage) {
        try {
            // Initialize engines
            personalityEngine = new PersonalityEngine("Sir");
            commandDispatcher = new CommandDispatcher(personalityEngine);
            ttsEngine = new TextToSpeechEngine();
            
            // Initialize speech recognition
            speechRecognizer = new VoskSpeechRecognizer("model");
            speechRecognizer.setSpeechRecognitionListener(new VoskSpeechRecognizer.SpeechRecognitionListener() {
                @Override
                public void onSpeechRecognized(String text) {
                    Platform.runLater(() -> handleCommand(text));
                }

                @Override
                public void onPartialResult(String text) {
                    Platform.runLater(() -> logArea.appendText("[Partial] " + text + "\n"));
                }

                @Override
                public void onError(String error) {
                    Platform.runLater(() -> logArea.appendText("[Error] " + error + "\n"));
                }
            });

            // Create UI
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(15));

            // Header
            VBox header = createHeader();
            root.setTop(header);

            // Log area
            logArea = new TextArea();
            logArea.setWrapText(true);
            logArea.setEditable(false);
            logArea.setPrefHeight(400);
            ScrollPane scrollPane = new ScrollPane(logArea);
            scrollPane.setFitToWidth(true);
            root.setCenter(scrollPane);

            // Control buttons
            HBox controls = createControls();
            root.setBottom(controls);

            // Create scene and stage
            Scene scene = new Scene(root, 800, 600);
            primaryStage.setTitle("J.A.R.V.I.S. - Just A Rather Very Intelligent System");
            primaryStage.setScene(scene);
            primaryStage.show();

            logArea.appendText("J.A.R.V.I.S. initialized. Ready to serve.\n");
            logger.info("GUI initialized successfully");
        } catch (Exception e) {
            logger.error("Error initializing UI", e);
            throw new RuntimeException("Failed to initialize GUI", e);
        }
    }

    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setStyle("-fx-border-color: #cccccc; -fx-border-width: 0 0 2 0; -fx-padding: 10;");
        
        Label title = new Label("J.A.R.V.I.S.");
        title.setStyle("-fx-font-size: 24; -fx-font-weight: bold;");
        
        statusLabel = new Label("Status: Ready");
        statusLabel.setStyle("-fx-font-size: 12;");
        
        header.getChildren().addAll(title, statusLabel);
        header.setAlignment(Pos.CENTER_LEFT);
        return header;
    }

    private HBox createControls() {
        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        controls.setStyle("-fx-border-color: #cccccc; -fx-border-width: 2 0 0 0;");
        
        listenButton = new Button("Start Listening");
        listenButton.setPrefWidth(150);
        listenButton.setOnAction(e -> startListening());
        
        stopButton = new Button("Stop Listening");
        stopButton.setPrefWidth(150);
        stopButton.setDisable(true);
        stopButton.setOnAction(e -> stopListening());
        
        Button clearButton = new Button("Clear Log");
        clearButton.setPrefWidth(150);
        clearButton.setOnAction(e -> logArea.clear());
        
        controls.getChildren().addAll(listenButton, stopButton, clearButton);
        return controls;
    }

    private void startListening() {
        statusLabel.setText("Status: Listening...");
        listenButton.setDisable(true);
        stopButton.setDisable(false);
        speechRecognizer.startListening();
        logArea.appendText("[System] Listening started...\n");
    }

    private void stopListening() {
        statusLabel.setText("Status: Ready");
        listenButton.setDisable(false);
        stopButton.setDisable(true);
        speechRecognizer.stopListening();
        logArea.appendText("[System] Listening stopped.\n");
    }

    private void handleCommand(String voiceInput) {
        logArea.appendText("[Voice] " + voiceInput + "\n");
        String response = commandDispatcher.dispatch(voiceInput);
        logArea.appendText("[J.A.R.V.I.S.] " + response + "\n");
        ttsEngine.speak(response);
    }
}
