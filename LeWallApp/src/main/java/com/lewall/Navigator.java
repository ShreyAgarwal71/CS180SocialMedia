package com.lewall;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.pages.Home;
import com.lewall.pages.Login;
import com.lewall.pages.Profile;
import com.lewall.pages.Register;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Navigator class for the application
 * 
 * @version 17 November 2024
 */
public class Navigator {
    private static final Logger logger = LogManager.getLogger(Navigator.class);

    private static Stack<Scene> history = new Stack<>();

    private static Stage stage;

    public enum EPage {
        LOGIN, REGISTER, HOME, PROFILE
    }

    /**
     * Set the stage for the application
     * 
     * @param stage
     *            the stage to set
     */
    public static void setStage(Stage stage) {
        Navigator.stage = stage;
    }

    /**
     * Navigate back to the previous scene
     * 
     * @return true if successful, false otherwise
     */
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

    /**
     * Navigate to a specific page
     * 
     * @param page
     *            the page to navigate to
     * @return true if successful, false otherwise
     */
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
            case REGISTER -> {
                scene = new Scene(new Register());
                history.push(scene);
            }
            case HOME -> {
                scene = new Scene(new Home());
                history.push(scene);
            }   
            case PROFILE -> {
                scene = new Scene(new Profile());
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

    /**
     * Update the scene to the most recent scene
     */
    private static void updateScene() {
        final Scene scene = history.peek();
        stage.setScene(scene);
    }
}
