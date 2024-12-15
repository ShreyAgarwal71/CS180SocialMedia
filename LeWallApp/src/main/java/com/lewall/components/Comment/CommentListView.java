package com.lewall.components.Comment;

import java.util.UUID;
import java.util.function.Consumer;

import com.lewall.common.AggregatedComment;
import com.lewall.db.models.Comment;
import com.lewall.db.models.User;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class CommentListView extends ListView<CommentListView.ObservableComment> {
    public static class ObservableComment {
        private Comment comment;
        private User user;
        private StringProperty likeCount;
        private StringProperty dislikeCount;
        private BooleanProperty isLiked;
        private BooleanProperty isDisliked;

        public ObservableComment(AggregatedComment comment, UUID authenticatedUserId) {
            this.comment = comment.getComment();
            this.user = comment.getUser();
            this.likeCount = new SimpleStringProperty(String.valueOf(comment.getComment().getLikes()));
            this.dislikeCount = new SimpleStringProperty(String.valueOf(comment.getComment().getDislikes()));
            this.isLiked = new SimpleBooleanProperty(
                    comment.getComment().getLikedBy().contains(authenticatedUserId.toString()));
            this.isDisliked = new SimpleBooleanProperty(
                    comment.getComment().getDislikedBy().contains(authenticatedUserId.toString()));
        }

        public Comment getComment() {
            return comment;
        }

        public User getUser() {
            return user;
        }

        public StringProperty getLikeCount() {
            return likeCount;
        }

        public StringProperty getDislikeCount() {
            return dislikeCount;
        }

        public BooleanProperty isLiked() {
            return isLiked;
        }

        public BooleanProperty isDisliked() {
            return isDisliked;
        }
    }

    public CommentListView(ObservableList<ObservableComment> items, UUID postOwner, Consumer<Void> refetch) {
        super(items);

        this.setCellFactory(_ -> new ListCell<ObservableComment>() {
            @Override
            protected void updateItem(ObservableComment item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox comment = new CommentCard(item, postOwner, _ -> {
                        items.removeIf(ele -> ele.getComment().getId().equals(item.getComment().getId()));
                    });
                    setGraphic(comment);
                }
            }
        });
    }
}
