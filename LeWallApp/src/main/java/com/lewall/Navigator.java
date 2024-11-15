package com.lewall;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.pages.Login;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Navigator {
    private static final Logger logger = LogManager.getLogger(Navigator.class);

    private static Stack<Scene> history = new Stack<>();

    private static Stage stage;

    public enum EPage {
        LOGIN, SIGNUP
    }

    public static void setStage(Stage stage) {
        Navigator.stage = stage;
    }

    public static boolean back() {
        if (stage == null) {
            logger.error("Stage not set");
            return false;
        }

        if (history.size() > 1) {
            history.pop();
            updateScene();

            return true;
        }

        logger.error("No previous scene");
        return false;
    }

    public static boolean navigateTo(EPage page) {
        if (stage == null) {
            logger.error("Stage not set");
            return false;
        }

        Scene scene = null;

        switch (page) {
            case LOGIN -> {
                scene = new Scene(new Login());
                history.push(scene);
            }
            default -> {
                logger.error("Unimplemented Page: " + page);
                return false;
            }
        }

        scene.getStylesheets().add("css/global.css");
        scene.setFill(Color.rgb(0, 0, 0, 0));

        updateScene();
        return true;
    }

    private static void updateScene() {
        stage.setScene(history.peek());
    }
}
