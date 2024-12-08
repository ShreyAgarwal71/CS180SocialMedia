package com.lewall.components;

import com.lewall.db.models.Comment;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CommentItem extends HBox {
    public CommentItem(Comment item) {
        this.setMaxHeight(35);

        VBox commnetContainer = new VBox();
        commnetContainer.setAlignment(Pos.CENTER);
        Text comment = new Text(item.getMessageComment());
        comment.getStyleClass().add("brand-label");
        commnetContainer.getChildren().addAll(comment);

        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(commnetContainer, spacer);

        this.getStyleClass().add("grey-bg");
        this.getStyleClass().add("grey-border");
        this.getStyleClass().add("rounded");
    }
}
