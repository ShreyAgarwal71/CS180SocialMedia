package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.lewall.Navigator;
import com.lewall.Navigator.EPage;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.dtos.UserDTO;
//import com.lewall.dtos.UserSearchDTO;
//import com.lewall.dtos.UsersFoundDTO;

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

        // User greeting
        UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
        if (userDTO != null) {
            String displayName = userDTO.getUser().getDisplayName();

            Text welcome = new Text("Welcome, " + displayName + "!");
            welcome.getStyleClass().add("brand-subtitle");

            column.getChildren().add(welcome);
        }

        // Search bar and button
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);

        TextField searchField = new TextField();
        searchField.setPromptText("Enter search query...");
        searchField.setPrefWidth(200);

        Button searchButton = new Button("Search");
        searchButton.getStyleClass().add("brand-button");
        searchButton.setOnAction(e -> {
            String query = searchField.getText().trim();
            if (!query.isEmpty()) {
                logger.info("Searching for: " + query);
                // Perform search logic here
                // UserSearchDTO SearchDTO = new UserSearchDTO(query);
                // UsersFoundDTO FoundDTO = LocalStorage.get("/userSearches",
                // UserSearchDTO.class);
            }
        });

        searchBox.getChildren().addAll(searchField, searchButton);
        column.getChildren().add(searchBox);

        // Log out button
        Button logOut = new Button("Home");
        logOut.getStyleClass().add("brand-button");
        logOut.setOnAction(e -> {
            logger.info("Going Home page");
            LocalStorage.remove("/user");
            Navigator.navigateTo(EPage.HOME);
        });

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().add(logOut);

        column.getChildren().add(buttonBox);

        flowPane.getChildren().add(column);

        // Stack pane to centralize content
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(flowPane);
        stackPane.setPadding(new Insets(20));

        // Page layout with Navbar, content, and Footer
        VBox page = new VBox();
        page.getChildren().add(new Navbar());
        page.getChildren().add(stackPane);
        page.getChildren().add(new Footer());

        this.getChildren().add(page);
    }
}