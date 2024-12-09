package com.lewall.pages;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator.NavigatorPageState;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.common.AggregatedPost;
import com.lewall.common.Theme;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.components.PostListView;
import com.lewall.db.models.Post;
import com.lewall.db.models.User;
import com.lewall.dtos.AggregatedPostsDTO;
import com.lewall.dtos.FollowUserDTO;
import com.lewall.dtos.PostsDTO;
import com.lewall.dtos.UnfollowUserDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.dtos.UserIdDTO;
import com.lewall.dtos.BlockUserDTO;
import com.lewall.dtos.UnblockUserDTO;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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
	private User profileUser;
	private List<Post> posts;

	private StringProperty userUsername = new SimpleStringProperty("");
	private StringProperty userDisplayName = new SimpleStringProperty("");
	private StringProperty userQuotes = new SimpleStringProperty("");
	private StringProperty userFollowers = new SimpleStringProperty("");
	private StringProperty userFollowing = new SimpleStringProperty("");

	public Profile(NavigatorPageState state) {
		super();

		User authenicatedUser = LocalStorage.get("/user", UserDTO.class).getUser();

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

		Rectangle idCard = new Rectangle(315, otherUser ? 180 : 115);
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
			boolean isFollowingProfileUser = getAuthenicatedUser().getFollowing()
					.contains(profileUser.getId().toString());

			Button followButton = new Button(isFollowingProfileUser ? "Unfollow" : "Follow");
			followButton.getStyleClass().add("accent-button");
			followButton.setPrefWidth(300);

			if (getAuthenicatedUser().getBlockedUsers().contains(profileUser.getId().toString())) {
				followButton.setDisable(true);
				followButton.setOpacity(0.75);
			}

			followButton.setOnAction(e -> {
				if (getAuthenicatedUser().getFollowing().contains(profileUser.getId().toString())) {
					Connection
							.<UnfollowUserDTO, UserDTO>post("/user/unfollow", new UnfollowUserDTO(profileUser.getId()))
							.thenAccept(response -> {
								profileUser = response.getBody().getUser();
								userFollowers.set(profileUser.getFollowers().size() + "");
								userFollowing.set(profileUser.getFollowing().size() + "");

								// Update the user in local storage
								Connection.<UserDTO>get("/user", true).thenAccept((res) -> {
								});

								Platform.runLater(() -> {
									followButton.setText("Follow");
								});
							});
				} else {
					Connection.<FollowUserDTO, UserDTO>post("/user/follow", new FollowUserDTO(profileUser.getId()))
							.thenAccept(response -> {
								profileUser = response.getBody().getUser();
								userFollowers.set(profileUser.getFollowers().size() + "");
								userFollowing.set(profileUser.getFollowing().size() + "");

								// Update the user in local storage
								Connection.<UserDTO>get("/user", true).thenAccept((res) -> {
								});

								Platform.runLater(() -> {
									followButton.setText("Unfollow");
								});
							});
				}
			});

			boolean hasBlockedProfileUser = getAuthenicatedUser().getBlockedUsers()
					.contains(profileUser.getId().toString());

			Button blockButton = new Button(hasBlockedProfileUser ? "Unblock" : "Block");
			blockButton.getStyleClass().add("brand-text-button");
			blockButton.setPrefWidth(300);
			blockButton.setOnAction(e -> {
				System.out.println(getAuthenicatedUser().getBlockedUsers().toString());
				System.out.println(profileUser.getId().toString());
				System.out.println(getAuthenicatedUser().getBlockedUsers().contains(profileUser.getId().toString()));
				if (getAuthenicatedUser().getBlockedUsers().contains(profileUser.getId().toString())) {
					Connection
							.<UnblockUserDTO, UserDTO>post("/user/unblock", new UnblockUserDTO(profileUser.getId()))
							.thenAccept(response -> {
								profileUser = response.getBody().getUser();
								userFollowers.set(profileUser.getFollowers().size() + "");
								userFollowing.set(profileUser.getFollowing().size() + "");

								Connection.<UserDTO>get("/user", true).thenAccept((res) -> {
								});

								Platform.runLater(() -> {
									followButton.setDisable(false);
									followButton.setOpacity(1.00);
									blockButton.setText("Block");
								});
							}).exceptionally(ee -> {
								logger.error(ee);
								return null;
							});
				} else {
					Connection.<BlockUserDTO, UserDTO>post("/user/block", new BlockUserDTO(profileUser.getId()))
							.thenAccept(response -> {

								profileUser = response.getBody().getUser();
								userFollowers.set(profileUser.getFollowers().size() + "");
								userFollowing.set(profileUser.getFollowing().size() + "");

								Connection.<UserDTO>get("/user", true).thenAccept((res) -> {
								});

								Platform.runLater(() -> {
									followButton.setText("Follow");
									followButton.setOpacity(0.75);
									followButton.setDisable(true);
									blockButton.setText("Unblock");
								});
							});
				}
			});

			content.getChildren().addAll(followButton, blockButton);
		}

		StackPane idCardItems = new StackPane();
		idCardItems.getChildren().addAll(idCard, content);
		VBox.setMargin(idCardItems, new Insets(20, 0, 0, 0));

		VBox idCardBox = new VBox();
		idCardBox.setMaxWidth(300);
		idCardBox.getChildren().addAll(profileTitle, profileSubtitle, idCardItems);

		HBox footer = new Footer();
		StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);

		FlowPane fp = new FlowPane(10, 10);
		fp.prefWidthProperty().bind(this.widthProperty());
		fp.prefHeightProperty().bind(this.heightProperty());
		fp.setOrientation(Orientation.VERTICAL);

		ObservableList<AggregatedPost> items = FXCollections.observableArrayList();

		Connection.<UserIdDTO, AggregatedPostsDTO>post("/user/getPosts", new UserIdDTO(profileUser.getId()))
				.thenAccept(response -> {
					AggregatedPostsDTO postsDTO = response.getBody();
					items.addAll(postsDTO.getAggregatedPosts());
				});

		ListView<AggregatedPost> postListView = new PostListView(items);
		postListView.setMaxHeight(220);

		VBox column = new VBox(10);
		column.setAlignment(Pos.TOP_LEFT);
		fp.setAlignment(Pos.TOP_LEFT);
		FlowPane.setMargin(column, new Insets(0, 0, 0, 90));

		Text yourQuotes = new Text(otherUser ? "Their Quotes" : "Your Quotes");
		yourQuotes.getStyleClass().add("brand-title");
		VBox.setMargin(yourQuotes, new Insets(10, 0, 0, 10));

		Text yourQuotesHint = new Text("Navigate to \"inscribe\" to add quotes...");
		yourQuotesHint.setFill(Color.web(Theme.TEXT_GREY));
		VBox.setMargin(yourQuotesHint, new Insets(0, 0, 0, 10));
		VBox yourQuoteHeader = new VBox(3);
		yourQuoteHeader.getChildren().addAll(yourQuotes, yourQuotesHint);

		column.getChildren().addAll(idCardBox, yourQuoteHeader, postListView);
		fp.getChildren().add(column);

		StackPane mainStack = new StackPane();
		mainStack.getChildren().addAll(fp);

		VBox navbar = new Navbar();
		StackPane.setAlignment(navbar, Pos.TOP_LEFT);
		StackPane.setMargin(navbar, new Insets(10));

		mainStack.getChildren().add(footer);
		mainStack.getChildren().add(navbar);

		this.getChildren().add(mainStack);
	}

	private User getAuthenicatedUser() {
		return LocalStorage.get("/user", UserDTO.class).getUser();
	}
}
