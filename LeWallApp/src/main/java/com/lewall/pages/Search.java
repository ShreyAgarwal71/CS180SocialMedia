package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lewall.Navigator;
import com.lewall.Navigator.EPage;
import com.lewall.Navigator.NavigatorPageState;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.components.UserCard;
import com.lewall.components.ClassCard;
import com.lewall.dtos.ClassesDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.dtos.UserSearchDTO;
import com.lewall.dtos.UsersFoundDTO;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.List;
import java.util.ArrayList;

/**
 * Search page for the application with GUI search functionality
 * 
 * @author Shrey Agarwal
 * @version December 4, 2024
 */
public class Search extends Pane {
    private static final Logger logger = LogManager.getLogger(Search.class);

    private boolean isSearchingResults = false;

    /**
     * Constructor for the search page
     */
    public Search(NavigatorPageState state) {
        this.getStyleClass().add("primary-bg");

        // Main layout container
        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        // flowPane.setAlignment(Pos.CENTER);

        VBox column = new VBox(10);
        FlowPane.setMargin(column, new Insets(10, 0, 0, 90));
        // column.setAlignment(Pos.CENTER);

        Text searchTitle = new Text("Explore LeWall");
        searchTitle.getStyleClass().add("brand-title");
        column.getChildren().add(searchTitle);

        Text searchError = new Text();
        searchError.getStyleClass().add("error-text");
        searchError.setWrappingWidth(300);
        VBox.setMargin(searchError, new Insets(3, 0, 5, 0));

        VBox searchResults = new VBox(10);
        searchResults.setAlignment(Pos.CENTER);
        searchResults.setPadding(new Insets(10));

        // Search bar and button
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Search for classes and users...");
        searchField.setPrefWidth(200);
        searchField.setFocusTraversable(false);
        searchField.getStyleClass().add("brand-field");

        Image searchPic = new Image("imgs/search.png");
        ImageView searchPicView = new ImageView(searchPic);

        searchPicView.setFitWidth(16);
        searchPicView.setFitHeight(16);
        searchPicView.setPreserveRatio(true);
        StackPane.setMargin(searchPicView, new Insets(0, 10, 0, 0));
        StackPane.setAlignment(searchPicView, Pos.CENTER_RIGHT);

        StackPane searchIcon = new StackPane();
        searchIcon.getChildren().add(searchField);

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("brand-button");
        searchButton.setOnAction(event -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                searchError.setText("Search query cannot be empty.");
                searchResults.getChildren().clear();
                return;
            } else {
                searchError.setText("");
                searchResults.getChildren().clear();
                isSearchingResults = false;
            }

            Text resultsTitle = new Text("Top Matches:");
            resultsTitle.getStyleClass().add("brand-subtitle");
            searchResults.getChildren().add(resultsTitle);

            /*
             * Connection.get("/post/getClasses", true).thenAccept(response -> {
             * List<String> classes = LocalStorage.get("/post/getClasses",
             * ClassesDTO.class).getClasses();
             * Platform.runLater(() -> {
             * for (int i = 0; i < classes.size(); i++) {
             * if (classes.get(i).contains(query)) {
             * ClassCard classCard = new ClassCard(classes.get(i));
             * searchResults.getChildren().add(classCard);
             * isSearchingResults = true;
             * }
             * }
             * });
             * });
             */

            Connection.<UserSearchDTO, UsersFoundDTO>post("/user/userSearches", new UserSearchDTO(query))
                    .thenAccept(userResponse -> {
                        UsersFoundDTO usersFound = userResponse.getBody();
                        Platform.runLater(() -> {
                            List<User> users = usersFound.getUsers();
                            if (users != null && !users.isEmpty()) {
                                users.stream().limit(10).forEach(user -> {
                                    UserCard userCard = new UserCard(user);
                                    searchResults.getChildren().add(userCard);
                                    isSearchingResults = true;
                                    userCard.setOnMouseClicked(e -> {

                                        UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
                                        if (userDTO != null) {
                                            String displayName = userDTO.getUser().getDisplayName();
                                            if (displayName.equals(user.getDisplayName())) {
                                                Navigator.navigateTo(EPage.PROFILE);
                                            } else {
                                                Navigator.navigateTo(EPage.PROFILE, new NavigatorPageState(user));
                                            }
                                        }
                                    });
                                });
                            } else {
                                // isSearchingResults = false;
                            }
                        });
                    }).exceptionally(e -> {
                        logger.error(e.getMessage(), e);
                        Platform.runLater(
                                () -> searchError.setText("Failed to fetch search results." + e.getMessage()));
                        return null;
                    });

            if (!isSearchingResults) {
                searchResults.getChildren().clear();
                Text noResults = new Text("No matches found.");
                noResults.getStyleClass().add("brand-subtitle");
                searchResults.getChildren().add(noResults);
            }
        });

        column.getChildren().addAll(searchIcon, searchButton, searchError, searchResults);
        flowPane.getChildren().add(column);

        // Stack pane to centralize content
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(flowPane);
        // stackPane.setPadding(new Insets(20));

        HBox footer = new Footer();
        StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);

        VBox navbar = new Navbar();
        StackPane.setAlignment(navbar, Pos.TOP_LEFT);
        StackPane.setMargin(navbar, new Insets(10));

        stackPane.getChildren().add(footer);
        stackPane.getChildren().add(navbar);

        this.getChildren().add(stackPane);
    }
}
