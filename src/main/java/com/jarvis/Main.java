package com.jarvis;

import javafx.application.Application;
import javafx.stage.Stage;
import com.jarvis.gui.JarvisUI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Initializing J.A.R.V.I.S. Assistant...");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            JarvisUI ui = new JarvisUI();
            ui.show(primaryStage);
            logger.info("J.A.R.V.I.S. GUI started successfully");
        } catch (Exception e) {
            logger.error("Failed to start J.A.R.V.I.S.", e);
            System.exit(1);
        }
    }
}
