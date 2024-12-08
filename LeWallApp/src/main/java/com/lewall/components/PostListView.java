package com.lewall.components;

import com.lewall.db.models.Post;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class PostListView extends ListView<Post> {
    ListView<Post> that;

    public PostListView(ObservableList<Post> items) {
        super(items);
        that = this;

        this.setPrefWidth(480);

        // Calculate the visible items

        // postListView.setOnScroll(event -> {
        // // Use lookup to get the VirtualFlow
        // VirtualFlow<?> flow = (VirtualFlow<?>) postListView.lookup(".virtual-flow");
        // if (flow != null) {
        // int firstVisibleIndex = flow.getFirstVisibleCell().getIndex();
        // int lastVisibleIndex = flow.getLastVisibleCell().getIndex();

        // System.out.println("Visible items:" + firstVisibleIndex + " to " +
        // lastVisibleIndex);
        // }
        // });

        this.setCellFactory(param -> new ListCell<Post>() {
            @Override
            protected void updateItem(Post item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    boolean isLast = getIndex() == items.size() - 1;
                    VBox post = new PostItem(
                            item,
                            pid -> {
                                items.removeIf(ele -> ele.getId().equals(pid));
                            }, updatedPost -> {
                                item.setLikes(updatedPost.getLikes());
                                item.setUsersLiked(updatedPost.getUsersLiked());
                                that.refresh();
                            });
                    post.setPadding(new Insets(0, 0, isLast ? 40 : 10, 0));
                    setGraphic(post);
                }
            }
        });
    }
}
