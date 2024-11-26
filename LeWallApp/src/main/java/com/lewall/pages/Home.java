package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lewall.Navigator;
import com.lewall.Navigator.EPage;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;
import com.lewall.db.models.User;
import com.lewall.dtos.UserDTO;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Home page for the application
 * 
 * @author Mahit Mehta
 * @version 17 November 2024
 */
public class Home extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);

    private static final GsonBuilder builder = new GsonBuilder();
    private static final Gson gson = builder.create();

    /**
     * Constructor for the home page
     */
    public Home() {
        this.getStyleClass().add("primary-bg");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);

        VBox column = new VBox(10);
        column.setAlignment(Pos.CENTER);

        UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
        if (userDTO != null) {
            String displayName = userDTO.getUser().getDisplayName();

            Text welcome = new Text("Welcome, " + displayName + "!");
            welcome.getStyleClass().add("brand-subtitle");

            column.getChildren().add(welcome);
        }

        Button logOut = new Button("Log Out");
        logOut.getStyleClass().add("brand-button");
        logOut.setOnAction(e -> {
            logger.info("Logging out");
            LocalStorage.clear();
            Navigator.navigateTo(EPage.LOGIN);
        });
        column.getChildren().add(logOut);

        flowPane.getChildren().add(column);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().add(flowPane);

        HBox footer = new Footer();
        StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);
        mainStack.getChildren().add(footer);

        this.getChildren().add(mainStack);
    }
}