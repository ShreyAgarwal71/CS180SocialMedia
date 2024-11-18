package com.lewall.components;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * Footer component for the application
 * 
 * @author Mahit Mehta
 * @version 17 November 2024
 */
public class Footer extends HBox {
    /**
     * Constructor for the footer
     */
    public Footer() {
        super(10);

        Text bottomLabel = new Text("© 2024 Purdue LeWall");
        Text bottomLabelVersion = new Text("Build [ v0.0.1 ]");
        bottomLabel.getStyleClass().add("bottom-label");
        bottomLabelVersion.getStyleClass().add("bottom-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setMaxHeight(15);
        this.getChildren().addAll(bottomLabelVersion, spacer, bottomLabel);
        this.setPadding(new Insets(0, 10, 10, 10));
    }
}
