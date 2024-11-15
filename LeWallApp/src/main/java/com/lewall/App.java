package com.lewall;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Connection;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    private static final Logger logger = LogManager.getLogger(App.class);

    @Override
    public void start(Stage stage) throws Exception {
        if (!Connection.connect()) {
            logger.error("Initial Server Connection Failed");
        }

        Navigator.setStage(stage);
        Navigator.navigateTo(Navigator.EPage.LOGIN);

        stage.setTitle("LeWall");
        stage.setWidth(640);
        stage.setHeight(480);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }
}
