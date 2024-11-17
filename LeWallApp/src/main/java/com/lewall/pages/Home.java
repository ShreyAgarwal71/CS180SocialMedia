package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator;
import com.lewall.Navigator.EPage;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Home extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);

    public Home() {
        this.getStyleClass().add("primary-bg");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);

        VBox column = new VBox(10);
        column.setAlignment(Pos.CENTER);

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