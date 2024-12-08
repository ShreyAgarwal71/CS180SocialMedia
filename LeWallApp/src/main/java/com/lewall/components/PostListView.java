package com.lewall.components;

import com.lewall.common.AggregatedPost;
import com.lewall.db.models.Post;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class PostListView extends ListView<AggregatedPost> {
    ListView<AggregatedPost> that;

    public PostListView(ObservableList<AggregatedPost> items) {
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

        this.setCellFactory(param -> new ListCell<AggregatedPost>() {
            @Override
            protected void updateItem(AggregatedPost item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    boolean isLast = getIndex() == items.size() - 1;
                    VBox post = new PostItem(
                            item,
                            pid -> {
                                items.removeIf(ele -> ele.getPost().getId().equals(pid));
                            }, updatedPost -> {
                                item.getPost().setLikes(updatedPost.getPost().getLikes());
                                item.getPost().setUsersLiked(updatedPost.getPost().getUsersLiked());
                                that.refresh();
                            });
                    post.setPadding(new Insets(0, 0, isLast ? 40 : 10, 0));
                    setGraphic(post);
                }
            }
        });
    }
}
