package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lewall.Navigator;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.dtos.UserSearchDTO;
import com.lewall.dtos.UsersFoundDTO;
import com.lewall.db.models.User;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.util.List;

/**
 * Search page for the application with GUI search functionality
 * 
 * @author Shrey Agarwal
 * @version December 4, 2024
 */
public class Search extends Pane {
    private static final Logger logger = LogManager.getLogger(Search.class);

    /**
     * Constructor for the search page
     */
    public Search() {
        this.getStyleClass().add("primary-bg");

        // Main layout container
        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        flowPane.setAlignment(Pos.CENTER);

        VBox column = new VBox(10);
        column.setAlignment(Pos.CENTER);

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
        searchField.setPromptText("Enter search query...");
        searchField.setPrefWidth(200);

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
            }

            Connection.<UserSearchDTO, UsersFoundDTO>post("/user/userSearches", new UserSearchDTO(query))
                    .thenAccept(userResponse -> {
                        UsersFoundDTO usersFound = userResponse.getBody();
                        Platform.runLater(() -> {
                            searchResults.getChildren().clear(); // Clear previous results
                            List<User> users = usersFound.getUsers();
                            if (users != null && !users.isEmpty()) {
                                Text resultsTitle = new Text("Top Matches:");
                                resultsTitle.getStyleClass().add("brand-subtitle");
                                searchResults.getChildren().add(resultsTitle);

                                users.stream().limit(10).forEach(user -> {
                                    Text userText = new Text(
                                            user.getDisplayName() + " (" + user.getEmail() + ")");
                                    userText.getStyleClass().add("result-text");
                                    searchResults.getChildren().add(userText);
                                });
                            } else {
                                Text noResults = new Text("No matches found.");
                                noResults.getStyleClass().add("brand-subtitle");
                                searchResults.getChildren().add(noResults);
                            }
                        });
                    }).exceptionally(e -> {
                        logger.error(e.getMessage(), e);
                        Platform.runLater(() -> searchError.setText("Failed to fetch search results."));
                        return null;
                    });
        });

        // searchBox.getChildren().addAll(searchField, searchButton);
        column.getChildren().addAll(searchField, searchButton, searchError, searchResults);

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
