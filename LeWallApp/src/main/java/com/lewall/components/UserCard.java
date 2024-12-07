package com.lewall.components;

import com.lewall.Navigator;
import com.lewall.Navigator.EPage;
import com.lewall.api.LocalStorage;
import com.lewall.db.models.User;
import com.lewall.dtos.UserDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * UserCard component for displaying user details in search results.
 * 
 * @author Shrey Agarwal
 * @version December 6, 2024
 */
public class UserCard extends HBox {
    private User user;

    /**
     * Constructs a UserCard for the given user.
     * 
     * @param user the user to display
     */
    public UserCard(User user) {
        this.user = user;

        // Set layout properties
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getStyleClass().add("user-card");

        VBox userDetails = new VBox();
        userDetails.setAlignment(Pos.CENTER_LEFT);
        // userDetails.getStyleClass().add("user-card");

        if (user.getPassword().isEmpty()) {
            Label displayName = new Label(user.getUsername());
            displayName.getStyleClass().add("user-text");
            userDetails.getChildren().addAll(displayName);
        } else {
            Label displayName = new Label(user.getDisplayName());
            displayName.getStyleClass().add("user-text");

            Label mutual = new Label("");

            UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
            if (userDTO != null) {
                String loggedInUser = userDTO.getUser().getDisplayName();
                if (!displayName.equals(user.getDisplayName())) {
                    if (user.getFollowing().contains(loggedInUser)) {
                        mutual = new Label("Follows you");
                        mutual.getStyleClass().add("user-text");
                        userDetails.getChildren().addAll(displayName, mutual);
                    } else if (user.getFollowers().contains(loggedInUser)) {
                        mutual = new Label("You follow");
                        mutual.getStyleClass().add("user-text");
                        userDetails.getChildren().addAll(displayName, mutual);
                    } else if (user.getFollowers().contains(loggedInUser)
                            && user.getFollowing().contains(loggedInUser)) {
                        mutual = new Label("Mutuals");
                        mutual.getStyleClass().add("user-text");
                        userDetails.getChildren().addAll(displayName, mutual);
                    } else {
                        userDetails.getChildren().addAll(displayName);
                    }
                }
            }
            /*
             * Label email = new Label(user.getEmail());
             * email.getStyleClass().add("user-text");
             * 
             * Label followers = new Label(user.getFollowers().size() + " followers");
             * followers.getStyleClass().add("user-text");
             * 
             * Label following = new Label(user.getFollowing().size() + " following");
             * following.getStyleClass().add("user-text");
             * 
             * userDetails.getChildren().addAll(displayName, email, followers, following);
             */
        }
        this.getChildren().addAll(userDetails);

        // this.getStyleClass().add("user-card");
        this.setStyle("user-card");

        this.setOnMouseEntered(e -> {
            // this.getChildren().forEach(child ->
            // child.getStyleClass().remove("user-card"));
            this.setStyle("-fx-background-color: #f0f0f0;");
            // this.getChildren().forEach(child -> child.forEach(child ->
            // child.setStyle("user-text-hover"));
        });
        this.setOnMouseExited(e -> {
            this.setStyle("user-card");

            // this.getChildren().forEach(child -> child.getStyleClass().add("user-card"));
        });
    }

    /**
     * Returns the user associated with this card.
     * 
     * @return the user
     */
    public User getUser() {
        return user;
    }
}
