package com.lewall.components;

import com.lewall.db.models.Post;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PostItem extends StackPane {
    public PostItem(Post item) {
        super();

        Rectangle container = new Rectangle(250, 150);
        container.setFill(new Color(0, 0, 0, 0));
        container.setStroke(Color.rgb(255, 255, 255, 0.05));
        container.setStrokeWidth(1);
        container.setArcWidth(3);
        container.setArcHeight(3);

        this.getChildren().add(container);
    }
}
