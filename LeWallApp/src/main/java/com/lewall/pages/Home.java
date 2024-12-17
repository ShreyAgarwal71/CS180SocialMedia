package com.lewall.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator.NavigatorPageState;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.common.AggregatedPost;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.components.Post.PostListView;
import com.lewall.components.Post.PostListView.ObservablePost;
import com.lewall.dtos.AggregatedPostsDTO;
import com.lewall.dtos.FollowingPostsDTO;
import com.lewall.dtos.LimitDTO;
import com.lewall.dtos.PostRefetchDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.interfaces.IScheduledComponent;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Home page for the application
 * 
 * @author Mahit Mehta
 * @version 17 November 2024
 */
public class Home extends Pane implements IScheduledComponent {
    private final PostListView postListView;
    private final ScheduledThreadPoolExecutor SCHEDULER = new ScheduledThreadPoolExecutor(1);

    private static final Logger logger = LogManager.getLogger(Home.class);

    @Override
    public void shutdownPolling() {
        SCHEDULER.shutdown();
    }

    /**
     * Constructor for the home page
     */
    public Home(NavigatorPageState state) {
        this.getStyleClass().add("primary-bg");

        ObservableList<ObservablePost> items = FXCollections.observableArrayList();
        postListView = new PostListView(items);
        postListView.setPrefHeight(490);

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());

        flowPane.setOrientation(Orientation.VERTICAL);

        UserDTO authenticatedUser = LocalStorage.get("/user", UserDTO.class);
        UUID authenticatedUserId = authenticatedUser.getUser().getId();

        HashMap<UUID, ObservablePost> postRefMap = new HashMap<>();

        Connection.<LimitDTO, FollowingPostsDTO>post("/user/getFollowerPosts", new LimitDTO(3, new HashSet<>()))
                .thenAccept(response -> {
                    FollowingPostsDTO followingPostsDTO = response.getBody();
                    Platform.runLater(() -> {
                        List<ObservablePost> posts = new ArrayList<>();
                        for (AggregatedPost post : followingPostsDTO.getAggregatedPosts()) {
                            ObservablePost op = new ObservablePost(post, authenticatedUserId);
                            postRefMap.put(post.getPost().getId(), op);
                            posts.add(op);
                        }
                        items.addAll(posts);
                    });
                });

        SCHEDULER.scheduleAtFixedRate(() -> {
            Set<UUID> postIds = postListView.getVisiblePostIds();
            logger.debug("Refetching posts: " + postIds);
            Connection.<PostRefetchDTO, AggregatedPostsDTO>post("/post/refetch", new PostRefetchDTO(postIds))
                    .thenAccept(response -> {
                        AggregatedPostsDTO aggregatedPostsDTO = response.getBody();
                        Platform.runLater(() -> {
                            for (AggregatedPost post : aggregatedPostsDTO.getAggregatedPosts()) {
                                ObservablePost op = postRefMap.get(post.getPost().getId());
                                if (op != null) {
                                    op.getLikeCount().set(String.valueOf(post.getPost().getLikes()));
                                    op.getDislikeCount().set(String.valueOf(post.getPost().getDislikes()));

                                    boolean isLiked = post.getPost().getUsersLiked()
                                            .contains(authenticatedUserId.toString());
                                    boolean isDisliked = post.getPost().getUsersDisliked()
                                            .contains(authenticatedUserId.toString());
                                    op.isLiked().set(isLiked);
                                    op.isDisliked().set(isDisliked);
                                    op.getCommentCount().set(String.valueOf(post.getComments().size()));
                                }
                            }
                        });
                    });
        }, 1, 1, TimeUnit.SECONDS);

        VBox column = new VBox(10);
        FlowPane.setMargin(column, new Insets(10, 0, 0, 85));

        Text fyp = new Text("Your LeWall");
        VBox.setMargin(fyp, new Insets(0, 0, 0, 10));

        Button refresh = new Button("Refresh");
        VBox.setMargin(refresh, new Insets(0, 0, 0, 10));

        refresh.getStyleClass().add("brand-text-button");
        refresh.setOnAction(_ -> {
            items.clear();
            Connection.<LimitDTO, FollowingPostsDTO>post("/user/getFollowerPosts", new LimitDTO(3, new HashSet<>()))
                    .thenAccept(response -> {
                        FollowingPostsDTO followingPostsDTO = response.getBody();
                        Platform.runLater(() -> {
                            Platform.runLater(() -> {
                                for (AggregatedPost post : followingPostsDTO.getAggregatedPosts()) {
                                    items.add(new ObservablePost(post, authenticatedUserId));
                                }
                            });
                        });
                    });
        });

        fyp.getStyleClass().add("brand-title");
        column.getChildren().add(fyp);
        column.getChildren().add(refresh);
        column.getChildren().add(postListView);

        flowPane.getChildren().add(column);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().add(flowPane);

        HBox footer = new Footer();
        StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);

        VBox navbar = new Navbar();
        StackPane.setAlignment(navbar, Pos.TOP_LEFT);
        StackPane.setMargin(navbar, new Insets(10));

        mainStack.getChildren().add(footer);
        mainStack.getChildren().add(navbar);

        this.getChildren().add(mainStack);
    }
}
