package com.lewall.pages;

import com.lewall.Navigator.NavigatorPageState;
import com.lewall.api.Connection;
import com.lewall.common.AggregatedPost;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.components.PostListView;
import com.lewall.dtos.FollowingPostsDTO;

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
public class Home extends Pane {
    /**
     * Constructor for the home page
     */
    public Home(NavigatorPageState state) {
        this.getStyleClass().add("primary-bg");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());

        flowPane.setOrientation(Orientation.VERTICAL);

        ObservableList<AggregatedPost> items = FXCollections.observableArrayList();

        Connection.<FollowingPostsDTO>get("/user/getFollowerPosts", false).thenAccept(response -> {
            FollowingPostsDTO followingPostsDTO = response.getBody();
            items.addAll(followingPostsDTO.getAggregatedPosts());
        });

        PostListView postListView = new PostListView(items);

        VBox column = new VBox(10);
        FlowPane.setMargin(column, new Insets(10, 0, 0, 85));

        Text fyp = new Text("Your LeWall");
        VBox.setMargin(fyp, new Insets(0, 0, 0, 10));

        Button refresh = new Button("Refresh");
        VBox.setMargin(refresh, new Insets(0, 0, 0, 10));

        refresh.getStyleClass().add("brand-text-button");
        refresh.setOnAction(e -> {
            items.clear();
            Connection.<FollowingPostsDTO>get("/user/getFollowerPosts", false).thenAccept(response -> {
                FollowingPostsDTO followingPostsDTO = response.getBody();
                items.addAll(followingPostsDTO.getAggregatedPosts());
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
