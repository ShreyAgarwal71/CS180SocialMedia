package com.lewall.components;

import com.lewall.api.LocalStorage;
import com.lewall.common.Theme;
import com.lewall.db.models.User;
import com.lewall.dtos.UserDTO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

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
     * @param user
     *             the user to display
     */
    public UserCard(User user) {
        this.user = user;

        // Set layout properties
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(10);
        this.setPadding(new Insets(10));
        this.getStyleClass().add("user-card");

        if (user.getPassword().isEmpty()) {
            Text displayName = new Text(user.getUsername());
            displayName.getStyleClass().add("user-text");
            this.getChildren().addAll(displayName);
        } else {
            Text displayName = new Text(user.getDisplayName());
            displayName.setFill(Color.WHITE);

            Text mutual = new Text("");
            mutual.setFill(Color.web(Theme.ACCENT));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
            if (userDTO != null) {
                if (!displayName.equals(user.getDisplayName())) {
                    if (user.getFollowers().contains(userDTO.getUser().getId().toString())
                            && user.getFollowing().contains(userDTO.getUser().getId().toString())) {
                        mutual.setText("Matuals");
                        this.getChildren().addAll(displayName, spacer, mutual);
                    } else if (user.getFollowers().contains(userDTO.getUser().getId().toString())) {
                        mutual.setText("You follow");
                        this.getChildren().addAll(displayName, spacer, mutual);
                    } else if (user.getFollowing().contains(userDTO.getUser().getId().toString())) {
                        mutual.setText("Follows you");
                        this.getChildren().addAll(displayName, spacer, mutual);
                    } else {
                        this.getChildren().addAll(displayName);
                    }
                }
            }
        }

        // this.getStyleClass().add("user-card");
        this.getStyleClass().add("grey-bg");
        this.getStyleClass().add("grey-border");
        this.getStyleClass().add("user-card");
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
