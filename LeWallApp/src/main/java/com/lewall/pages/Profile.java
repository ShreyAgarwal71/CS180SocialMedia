package com.lewall.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator.NavigatorPageState;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.common.Theme;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;
import com.lewall.dtos.FollowUserDTO;
import com.lewall.dtos.PostsDTO;
import com.lewall.dtos.UnfollowUserDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.dtos.UserIdDTO;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
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
	private User profileUser;
	private User authenicatedUser;
	private List<Post> posts;

	private StringProperty userUsername = new SimpleStringProperty("");
	private StringProperty userDisplayName = new SimpleStringProperty("");
	private StringProperty userQuotes = new SimpleStringProperty("");
	private StringProperty userFollowers = new SimpleStringProperty("");
	private StringProperty userFollowing = new SimpleStringProperty("");

	public Profile(NavigatorPageState state) {
		super();

		authenicatedUser = LocalStorage.get("/user", UserDTO.class).getUser();

		// If the state is a user, then we are viewing someone else's profile
		if (state.getState() instanceof User) {
			profileUser = (User) state.getState();
		} else {
			profileUser = authenicatedUser;
		}

		boolean otherUser = profileUser.getId() != authenicatedUser.getId();

		userUsername.set("@" + profileUser.getUsername().split("@")[0]);
		userDisplayName.set(profileUser.getDisplayName());
		userFollowers.set(profileUser.getFollowers().size() + "");
		userFollowing.set(profileUser.getFollowing().size() + "");

		Connection.<UserIdDTO, PostsDTO>post("/post/all", new UserIdDTO(profileUser.getId())).thenAccept(response -> {
			posts = response.getBody().getPosts();
			userQuotes.set(posts.size() + "");
		});

		this.getStyleClass().add("primary-bg");

		Text profileTitle = new Text("Inscriber Profile");
		profileTitle.getStyleClass().add("brand-title");
		profileTitle.setFill(Color.WHITE);
		VBox.setMargin(profileTitle, new Insets(10, 0, 0, 0));

		Text profileSubtitle = new Text();
		profileSubtitle.textProperty().bind(userUsername);
		profileSubtitle.setFill(Color.web(Theme.ACCENT));
		VBox.setMargin(profileSubtitle, new Insets(3, 0, 0, 0));

		Rectangle idCard = new Rectangle(315, otherUser ? 150 : 115);
		idCard.setFill(Color.rgb(25, 18, 35));
		idCard.setStroke(Color.rgb(255, 255, 255, 0.05));
		idCard.setStrokeWidth(1);
		idCard.setArcWidth(10);
		idCard.setArcHeight(10);
		VBox.setMargin(idCard, new Insets(20, 0, 0, 0));

		Text nFollowers = new Text();
		nFollowers.setStyle("-fx-font-weight: bold;");
		nFollowers.textProperty().bind(userFollowers);
		nFollowers.setFill(Color.WHITE);

		VBox nFollowersBox = new VBox();
		Text followers = new Text("Followers");
		followers.setFill(Color.web(Theme.TEXT_GREY));
		nFollowersBox.getChildren().addAll(nFollowers, followers);

		Text nFollowing = new Text();
		nFollowing.setStyle("-fx-font-weight: bold;");
		nFollowing.textProperty().bind(userFollowing);
		nFollowing.setFill(Color.WHITE);

		VBox nFollowingBox = new VBox();
		Text following = new Text("Following");
		following.setFill(Color.web(Theme.TEXT_GREY));
		nFollowingBox.getChildren().addAll(nFollowing, following);

		Text nPosts = new Text();
		nPosts.textProperty().bind(userQuotes);
		nPosts.setStyle("-fx-font-weight: bold;");
		nPosts.setFill(Color.WHITE);

		VBox nPostsBox = new VBox();
		Text posts = new Text("Quotes");
		posts.setFill(Color.web(Theme.TEXT_GREY));
		nPostsBox.getChildren().addAll(nPosts, posts);

		HBox userStats = new HBox(15);
		userStats.getChildren().addAll(nPostsBox, nFollowersBox, nFollowingBox);

		Text displayName = new Text();
		displayName.textProperty().bind(userDisplayName);
		displayName.getStyleClass().add("profile-title");
		displayName.setFill(Color.WHITE);

		HBox profileDetails = new HBox(5);
		Text pronouns = new Text("Un/known");
		pronouns.setFill(Color.web(Theme.TEXT_GREY));

		Text separator = new Text(" | ");
		separator.setFill(Color.web(Theme.TEXT_GREY));

		Text purdueTag = new Text("@lifeatpurdue");
		purdueTag.setFill(Color.web(Theme.ACCENT));

		profileDetails.getChildren().addAll(pronouns, separator, purdueTag);

		VBox nameAndDetails = new VBox(5);
		nameAndDetails.getChildren().addAll(displayName, profileDetails);

		VBox userDetails = new VBox(15);
		userDetails.getChildren().addAll(nameAndDetails, userStats);

		ImageView pfp = new ImageView(new Image("./imgs/pfp.png"));
		pfp.setFitWidth(65);
		pfp.setFitHeight(65);

		HBox profile = new HBox(15);
		profile.getChildren().addAll(pfp, userDetails);

		VBox content = new VBox(15);
		content.getChildren().add(profile);
		StackPane.setMargin(content, new Insets(15));

		if (otherUser) {
			boolean isFollowingProfileUser = authenicatedUser.getFollowers().contains(profileUser.getId().toString());

			Button followButton = new Button(isFollowingProfileUser ? "Unfollow" : "Follow");
			followButton.getStyleClass().add("accent-button");
			followButton.setPrefWidth(300);
			followButton.setOnAction(e -> {
				if (isFollowingProfileUser) {
					Connection.<UnfollowUserDTO, UserDTO>post("/unfollow", new UnfollowUserDTO(profileUser.getId()))
							.thenAccept(response -> {
								followButton.setText("Follow");

								profileUser = response.getBody().getUser();
								userFollowers.set(profileUser.getFollowers().size() + "");

								// Update the user in local storage
								Connection.get("/user", true);
							});
				} else {
					Connection.<FollowUserDTO, UserDTO>post("/follow", new FollowUserDTO(profileUser.getId()))
							.thenAccept(response -> {
								followButton.setText("Unfollow");

								profileUser = response.getBody().getUser();
								userFollowers.set(profileUser.getFollowers().size() + "");

								// Update the user in local storage
								Connection.get("/user", true);
							});
				}
			});
			content.getChildren().add(followButton);
		}

		StackPane idCardItems = new StackPane();
		idCardItems.getChildren().addAll(idCard, content);
		VBox.setMargin(idCardItems, new Insets(20, 0, 0, 0));

		VBox idCardBox = new VBox();
		idCardBox.getChildren().addAll(profileTitle, profileSubtitle, idCardItems);

		HBox footer = new Footer();
		StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);

		FlowPane fp = new FlowPane(10, 10);
		fp.prefWidthProperty().bind(this.widthProperty());
		fp.prefHeightProperty().bind(this.heightProperty());
		fp.setOrientation(Orientation.VERTICAL);

		FlowPane.setMargin(idCardBox, new Insets(0, 0, 0, 90));
		fp.getChildren().add(idCardBox);

		StackPane mainStack = new StackPane();
		mainStack.getChildren().addAll(fp);

		VBox navbar = new Navbar();
		StackPane.setAlignment(navbar, Pos.TOP_LEFT);
		StackPane.setMargin(navbar, new Insets(10));

		mainStack.getChildren().add(footer);
		mainStack.getChildren().add(navbar);

		this.getChildren().add(mainStack);
	}
}
