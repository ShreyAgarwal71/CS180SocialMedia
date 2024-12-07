package com.lewall.pages;

import java.util.UUID;

import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.components.PostItem;
import com.lewall.db.models.Post;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
    public Home() {
        this.getStyleClass().add("primary-bg");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        // flowPane.setAlignment(Pos.CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);
        // Post(UUID userId, String messagePost, String date, int likes, String
        // imageURL, UUID classId)
        Post[] posts = new Post[] {
                new Post(UUID.randomUUID(), "I learned the difference between Stacks and Queues", "11/17/2024", 1,
                        "https://www.purdue.edu/uns/images/2020/pt-bell-towerOG.jpg",
                        UUID.randomUUID()),
                new Post(UUID.randomUUID(), "I learned the difference between Stacks and Queues", "11/17/2024", 2,
                        "https://www.teachhub.com/wp-content/uploads/2020/05/Classroom-Management-for-an-Effective-Learning-Environment-768x512.jpg",
                        UUID.randomUUID()),
                new Post(UUID.randomUUID(), "I learned the difference between Stacks and Queues", "11/17/2024", 3,
                        "https://www.teachhub.com/wp-content/uploads/2020/05/Classroom-Management-for-an-Effective-Learning-Environment-768x512.jpg",
                        UUID.randomUUID()),
                new Post(UUID.randomUUID(), "I learned the difference between Stacks and Queues", "11/17/2024", 4,
                        "https://www.teachhub.com/wp-content/uploads/2020/05/Classroom-Management-for-an-Effective-Learning-Environment-768x512.jpg",
                        UUID.randomUUID()),
        };

        ObservableList<Post> items = FXCollections.observableArrayList(
                posts);

        ListView<Post> postListView = new ListView<>(items);
        postListView.setPrefWidth(420);

        postListView.setCellFactory(param -> new ListCell<Post>() {
            @Override
            protected void updateItem(Post item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    boolean isLast = getIndex() == items.size() - 1;
                    VBox post = new PostItem(item);
                    post.setPadding(new Insets(0, 0, isLast ? 40 : 10, 0));
                    setGraphic(post);
                }
            }
        });

        VBox column = new VBox(10);
        FlowPane.setMargin(column, new Insets(10, 0, 0, 85));
        // column.setAlignment(Pos.CENTER);

        // UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
        // if (userDTO != null) {
        // String displayName = userDTO.getUser().getDisplayName();

        Text fyp = new Text("Your LeWall");
        VBox.setMargin(fyp, new Insets(0, 0, 0, 10));
        fyp.getStyleClass().add("brand-title");
        column.getChildren().add(fyp);
        column.getChildren().add(postListView);

        // Button logOut = new Button("Log Out");
        // logOut.getStyleClass().add("brand-button");
        // logOut.setOnAction(e -> {
        // logger.info("Logging out");
        // LocalStorage.clear();
        // Navigator.navigateTo(EPage.LOGIN);
        // });
        // column.getChildren().add(logOut);

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
