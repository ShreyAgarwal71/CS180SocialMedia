package com.lewall.components;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.lewall.common.AggregatedPost;
import com.lewall.interfaces.IScheduledComponent;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.skin.VirtualFlow;
import javafx.scene.layout.VBox;

public class PostListView extends ListView<AggregatedPost> {
    ListView<AggregatedPost> that;
    Set<UUID> visiblePostIds = new HashSet<>();

    public PostListView(ObservableList<AggregatedPost> items) {
        super(items);
        that = this;

        this.setPrefWidth(480);
        // Calculate the visible items

        this.setOnScroll(event -> {
            // Use lookup to get the VirtualFlow
            VirtualFlow<?> flow = (VirtualFlow<?>) this.lookup(".virtual-flow");
            if (flow != null) {
                int firstVisibleIndex = flow.getFirstVisibleCell().getIndex();
                int lastVisibleIndex = flow.getLastVisibleCell().getIndex();

                // Add the visible items to the set
                for (int i = firstVisibleIndex; i <= lastVisibleIndex; i++) {
                    visiblePostIds.add(items.get(i).getPost().getId());
                }
            }
        });

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

                                item.getPost().setDislikes(updatedPost.getPost().getDislikes());
                                item.getPost().setUsersDisliked(updatedPost.getPost().getUsersDisliked());
                                that.refresh();
                            },
                            comments -> {
                                item.setComments(comments);
                                that.refresh();
                            },
                            pid -> {

                            });
                    post.setPadding(new Insets(0, 0, isLast ? 75 : 10, 0));
                    setGraphic(post);
                }
            }
        });
    }
}
