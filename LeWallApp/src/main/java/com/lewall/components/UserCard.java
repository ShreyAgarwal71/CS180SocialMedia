package com.lewall.components;

import com.lewall.db.models.User;
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

        Label displayName = new Label(user.getDisplayName());
        displayName.getStyleClass().add("user-text");

        Label email = new Label(user.getEmail());
        email.getStyleClass().add("user-text");

        Label followers = new Label(user.getFollowers().size() + " followers");
        followers.getStyleClass().add("user-text");

        Label following = new Label(user.getFollowing().size() + " following");
        following.getStyleClass().add("user-text");

        userDetails.getChildren().addAll(displayName, email, followers, following);

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
