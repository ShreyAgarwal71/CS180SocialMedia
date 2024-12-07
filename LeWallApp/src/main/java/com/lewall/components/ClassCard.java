package com.lewall.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * ClassCard component for displaying user details in search results.
 * 
 * @author Shrey Agarwal
 * @version December 6, 2024
 */
public class ClassCard extends HBox {
    private String className;

    /**
     * Constructs a ClassCard for the given class.
     * 
     * @param className the class to display
     */
    public ClassCard(String className) {
        this.className = className;

        // Set layout properties
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getStyleClass().add("user-card");

        VBox classDetails = new VBox();
        classDetails.setAlignment(Pos.CENTER_LEFT);

        Label displayClass = new Label(className);
        displayClass.getStyleClass().add("user-text");
        classDetails.getChildren().addAll(displayClass);
        this.getChildren().addAll(classDetails);

        this.setStyle("user-card");

        this.setOnMouseEntered(e -> {
            this.setStyle("-fx-background-color: #f0f0f0;");
        });
        this.setOnMouseExited(e -> {
            this.setStyle("user-card");
        });
    }
}
