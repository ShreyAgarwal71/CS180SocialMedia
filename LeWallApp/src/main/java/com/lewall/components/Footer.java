package com.lewall.components;

import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
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
        this.getStyleClass().add("footer");

        Text bottomLabel = new Text("© 2024 Purdue LeWall");
        Text bottomLabelVersion = new Text("Build [ v0.0.1 ]");
        bottomLabel.getStyleClass().add("bottom-label");
        bottomLabelVersion.getStyleClass().add("bottom-label");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#000000", 0.5));
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setRadius(15);
        shadow.setSpread(0.5);

        this.setEffect(shadow);
        this.setMaxHeight(25);
        this.getChildren().addAll(bottomLabelVersion, spacer, bottomLabel);
        this.setPadding(new Insets(5, 10, 7, 10));
    }
}
