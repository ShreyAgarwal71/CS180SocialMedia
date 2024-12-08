package com.lewall.components;

import java.util.UUID;
import java.util.function.Consumer;

import com.lewall.api.Connection;
import com.lewall.common.AggregatedComment;
import com.lewall.dtos.DeleteCommentDTO;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class CommentListView extends ListView<AggregatedComment> {
    public CommentListView(ObservableList<AggregatedComment> items, UUID postOwner, Consumer<Void> refetch) {
        super(items);

        this.setCellFactory(param -> new ListCell<AggregatedComment>() {
            @Override
            protected void updateItem(AggregatedComment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox comment = new CommentItem(item, postOwner, (v) -> {
                        Connection
                                .<DeleteCommentDTO, Void>post("/comment/delete",
                                        new DeleteCommentDTO(item.getComment().getId()))
                                .thenAccept((res) -> {
                                    refetch.accept(null);
                                });
                    }, (v) -> {
                        // Update comment;
                    });
                    setGraphic(comment);
                }
            }
        });
    }
}
