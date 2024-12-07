package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.components.Navbar;
import com.lewall.db.models.User;
import com.lewall.dtos.UserDTO;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Profile extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);
	private User user;
    
    public Profile() {
        this.getStyleClass().add("primary-bg");

        VBox navbar = new Navbar();
        HBox.setMargin(navbar, new Insets(10, 10, 10, 10));

        Text profileTitle = new Text("Inscriber Profile");
        profileTitle.getStyleClass().add("profile-title");
        profileTitle.setFill(Color.WHITE);
        VBox.setMargin(profileTitle, new Insets(10,0,0,0));

		Connection.get("/user", true).thenAccept(response -> {
			user = LocalStorage.get("/user", UserDTO.class).getUser();
			logger.info("User " + user.getEmail() + " loaded");
		});

		while (user == null) {
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				logger.error(e);
			}
		}

        Text profileSubtitle = new Text("@" + user.getDisplayName());
        profileSubtitle.getStyleClass().add(user.getBio());
        profileSubtitle.setFill(Color.rgb(137, 139, 239, 1));
        VBox.setMargin(profileSubtitle, new Insets(3, 0,0,0));

        Rectangle idCard = new Rectangle(270, 120);
        idCard.setFill(Color.rgb(25, 18, 35));
        idCard.setStroke(Color.rgb(255, 255, 255, 0.05));
        idCard.setStrokeWidth(1);
        idCard.setArcWidth(10);
        idCard.setArcHeight(10);
        VBox.setMargin(idCard, new Insets(10, 0,0,0));

        VBox idCardBox = new VBox();
        idCardBox.getChildren().addAll(profileTitle, profileSubtitle, idCard);
        HBox.setMargin(idCardBox, new Insets(0,200, 0, 10));

        HBox topHalf = new HBox();
        topHalf.getChildren().addAll(navbar, idCardBox);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().add(topHalf);
        this.getChildren().add(mainStack);
    }
}
