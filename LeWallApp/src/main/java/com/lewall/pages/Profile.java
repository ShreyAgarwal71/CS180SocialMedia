package com.lewall.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;
import com.lewall.dtos.PostsDTO;
import com.lewall.dtos.UserDTO;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 * Profile page
 *
 * @author Zayan Niaz, Ates Isfendiyaroglu
 * @version 6 December 2024
 */
public class Profile extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);
	private User user;
	private List<Post> posts;
    
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

		// I'm sorry
		while (user == null) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				logger.error(e);
			}
		}

        Text profileSubtitle = new Text("@" + user.getDisplayName());
        profileSubtitle.setFill(Color.rgb(137, 139, 239, 1));
        VBox.setMargin(profileSubtitle, new Insets(3, 0,0,0));

        Rectangle idCard = new Rectangle(270, 90);
        idCard.setFill(Color.rgb(25, 18, 35));
        idCard.setStroke(Color.rgb(255, 255, 255, 0.05));
        idCard.setStrokeWidth(1);
        idCard.setArcWidth(10);
        idCard.setArcHeight(10);
        VBox.setMargin(idCard, new Insets(20, 0,0,0));

		Text nFollowers = new Text(user.getFollowers().size() + "\nFollowers");
        nFollowers.setFill(Color.WHITE);
		Text nFollowing = new Text(user.getFollowing().size() + "\nFollowing");
        nFollowing.setFill(Color.WHITE);

		Connection.get("/user/getPosts", true).thenAccept(response -> {
			posts = LocalStorage.get("/user/getPosts", PostsDTO.class).getPosts();
			logger.info("Posts loaded");
		});

		// I'm sorry
		while (posts == null) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
				logger.error(e);
			}
		}

		Text nPosts = new Text(posts.size() + "\nQuotes");
        nPosts.setFill(Color.WHITE);

		HBox userStats = new HBox();
		HBox.setMargin(nPosts, new Insets(5, 12, 0, 5));
		HBox.setMargin(nFollowers, new Insets(5, 12, 0, 5));
		HBox.setMargin(nFollowing, new Insets(5, 0, 0, 5));
		userStats.getChildren().addAll(nPosts, nFollowers, nFollowing);

		Text displayName = new Text(user.getDisplayName());
        displayName.getStyleClass().add("profile-title");
		displayName.setFill(Color.WHITE);

        Text uName = new Text("@" + user.getDisplayName());
        uName.setFill(Color.rgb(137, 139, 239, 1));
        VBox.setMargin(uName, new Insets(3, 0,0,0));

		VBox userNames = new VBox();
		userNames.getChildren().addAll(displayName, uName);

		ImageView pfp = new ImageView(new Image("./imgs/pfp.png"));
		HBox.setMargin(pfp, new Insets(5, 5, 0, 5));
		HBox pfpAndNames = new HBox();
		pfpAndNames.getChildren().addAll(pfp, userNames);
		
		VBox cardNStats = new VBox();
		cardNStats.getChildren().addAll(pfpAndNames, userStats);

		StackPane idCardItems = new StackPane();
		idCardItems.getChildren().addAll(idCard, cardNStats);
		VBox.setMargin(idCardItems, new Insets(20, 0, 0, 0));

        VBox idCardBox = new VBox();
        idCardBox.getChildren().addAll(profileTitle, profileSubtitle, idCardItems);
        HBox.setMargin(idCardBox, new Insets(0,200, 0, 10));

        HBox topHalf = new HBox();
        topHalf.getChildren().addAll(navbar, idCardBox);

        HBox footer = new Footer();
        StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);

		FlowPane fp = new FlowPane(10, 10);
        fp.prefWidthProperty().bind(this.widthProperty());
        fp.prefHeightProperty().bind(this.heightProperty());
        fp.setAlignment(Pos.CENTER);
        fp.setOrientation(Orientation.VERTICAL);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().addAll(fp, topHalf, footer);
        this.getChildren().add(mainStack);
    }
}
