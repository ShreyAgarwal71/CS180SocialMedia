package com.lewall.components.Post;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.lewall.common.AggregatedComment;
import com.lewall.common.AggregatedPost;
import com.lewall.components.Comment.CommentListView.ObservableComment;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.layout.VBox;

public class PostListView extends ListView<PostListView.ObservablePost> {
    ListView<ObservablePost> that;
    Set<UUID> visiblePostIds = new HashSet<>();

    public static class ObservablePost {
        private Post post;
        private User user;
        private ObservableList<ObservableComment> comments;
        private StringProperty likeCount;
        private StringProperty dislikeCount;
        private StringProperty commentCount;
        private BooleanProperty isLiked;
        private BooleanProperty isDisliked;

        public ObservablePost(AggregatedPost post, UUID authenticatedUserId) {
            this.post = post.getPost();
            this.user = post.getUser();

            this.comments = FXCollections.observableArrayList();
            for (AggregatedComment comment : post.getComments()) {
                this.comments.add(new ObservableComment(comment, authenticatedUserId));
            }

            this.likeCount = new SimpleStringProperty(String.valueOf(post.getPost().getLikes()));
            this.dislikeCount = new SimpleStringProperty(String.valueOf(post.getPost().getDislikes()));
            this.commentCount = new SimpleStringProperty(String.valueOf(post.getComments().size()));
            this.isLiked = new SimpleBooleanProperty(
                    post.getPost().getUsersLiked().contains(authenticatedUserId.toString()));
            this.isDisliked = new SimpleBooleanProperty(
                    post.getPost().getUsersDisliked().contains(authenticatedUserId.toString()));
        }

        public Post getPost() {
            return post;
        }

        public User getUser() {
            return user;
        }

        public ObservableList<ObservableComment> getComments() {
            return comments;
        }

        public StringProperty getLikeCount() {
            return likeCount;
        }

        public StringProperty getDislikeCount() {
            return dislikeCount;
        }

        public StringProperty getCommentCount() {
            return commentCount;
        }

        public BooleanProperty isLiked() {
            return isLiked;
        }

        public BooleanProperty isDisliked() {
            return isDisliked;
        }
    }

    public Set<UUID> getVisiblePostIds() {
        return visiblePostIds;
    }

    public PostListView(ObservableList<ObservablePost> items) {
        super(items);
        that = this;

        this.setPrefWidth(480);

        // Determine the visible items
        this.setOnScroll(_ -> {
            VirtualFlow<?> flow2 = (VirtualFlow<?>) this.lookup(".virtual-flow");
            if (flow2 != null) {
                int firstVisibleIndex = flow2.getFirstVisibleCell().getIndex();
                int lastVisibleIndex = flow2.getLastVisibleCell().getIndex();

                // Add the visible items to the set
                visiblePostIds.clear();
                for (int i = firstVisibleIndex; i <= lastVisibleIndex; i++) {
                    visiblePostIds.add(items.get(i).getPost().getId());
                }
            }
        });

        this.setCellFactory(_ -> new ListCell<ObservablePost>() {
            @Override
            protected void updateItem(ObservablePost item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    boolean isLast = getIndex() == items.size() - 1;
                    VBox post = new PostCard(
                            item,
                            pid -> {
                                items.removeIf(ele -> ele.getPost().getId().equals(pid));
                            });
                    post.setPadding(new Insets(0, 0, isLast ? 50 : 10, 0));
                    setGraphic(post);
                }
            }
        });
    }
}
