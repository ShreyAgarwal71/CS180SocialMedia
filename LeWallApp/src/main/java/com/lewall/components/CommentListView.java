package com.lewall.components;

import com.lewall.db.models.Comment;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class CommentListView extends ListView<Comment> {
    public CommentListView(ObservableList<Comment> items) {
        super(items);

        this.setCellFactory(param -> new ListCell<Comment>() {
            @Override
            protected void updateItem(Comment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    boolean isLast = getIndex() == items.size() - 1;
                    HBox comment = new CommentItem(item);
                    // comment.setPadding(new Insets(0, 0, isLast ? 40 : 10, 0));
                    setGraphic(comment);
                }
            }
        });
    }
}
