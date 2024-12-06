package com.lewall;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.LocalStorage;
import com.lewall.api.Connection;
import com.lewall.dtos.UserDTO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Main class for the application
 * 
 * @version 17 November 2024
 * 
 * @author Mahit Mehta
 */
public class App extends Application {
    public static final String ID = "LeWall";

    private static final Logger logger = LogManager.getLogger(App.class);

    @Override
    public void start(Stage stage) throws Exception {
        if (!Connection.connect()) {
            logger.error("Initial Server Connection Failed");
        }

        LocalStorage.init();

        Navigator.setStage(stage);

        stage.setTitle("LeWall");
        stage.setWidth(650);
        stage.setHeight(480);
        stage.setResizable(false);

        if (LocalStorage.get("token") != null) {
            Connection.<UserDTO>get("/user", true).thenAccept(response -> {
                Platform.runLater(() -> {
                    Navigator.navigateTo(Navigator.EPage.HOME);
                    stage.show();
                });
            }).exceptionally(e -> {
                while (e.getCause() != null) {
                    e = e.getCause();
                }
                logger.error(e.getMessage());

                LocalStorage.clear();

                Platform.runLater(() -> {
                    Navigator.navigateTo(Navigator.EPage.LOGIN);
                    stage.show();
                });
                return null;
            });

            return;
        }

        Navigator.navigateTo(Navigator.EPage.LOGIN);
        stage.show();
    }

    /**
     * Main method for the application, Will launch the application
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch(App.class, args);
    }
}
