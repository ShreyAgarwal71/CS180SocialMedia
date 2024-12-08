package com.lewall.components;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Consumer;

import com.lewall.Navigator;
import com.lewall.Navigator.EPage;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.common.AggregatedPost;
import com.lewall.common.Theme;
import com.lewall.db.models.Post;
import com.lewall.dtos.UserDTO;
import com.lewall.dtos.UserIdDTO;
import com.lewall.dtos.AddCommentDTO;
import com.lewall.dtos.DeletePostDTO;
import com.lewall.dtos.FollowingPostsDTO;
import com.lewall.dtos.PostDTO;
import com.lewall.dtos.LikePostDTO;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PostItem extends VBox {
    private StringProperty author = new SimpleStringProperty("");
    private final Consumer<AggregatedPost> setUpdatedPost;

    public PostItem(AggregatedPost item, Consumer<UUID> onDelete, Consumer<AggregatedPost> setUpdatedPost) {
        super(5);
        this.setUpdatedPost = setUpdatedPost;

        author.set(item.getUser().getDisplayName());

        StackPane mainStack = new StackPane();
        String imageURL = item.getPost().getImageURL();
        int width = 450;
        int height = 225;

        Rectangle container = new Rectangle(width, height);
        container.setFill(new Color(0, 0, 0, 0));
        container.setStroke(Color.rgb(255, 255, 255, 0.05));
        container.setStrokeWidth(1);
        container.setArcWidth(0);
        container.setArcHeight(0);

        VBox postContents = new VBox(5);
        VBox postQuote = getPostQuoteComponent(
                item.getPost().getMessagePost(),
                imageURL == null ? width - 20 : height - 10);

        if (imageURL != null) {
            StackPane.setAlignment(postContents, Pos.CENTER_RIGHT);
        }

        HBox postReactions = getPostReactionsComponent(item, mainStack);

        postContents.getChildren().addAll(postQuote, postReactions);

        Rectangle blurLayer = new Rectangle(container.getWidth(), container.getHeight());
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(0);
        blurLayer.setEffect(blur);
        blurLayer.setFill(new Color(1, 1, 1, 0.01));

        mainStack.getChildren().add(blurLayer);
        mainStack.getChildren().add(container);

        if (imageURL != null) {
            Image image = new Image(imageURL, true);
            ImageView imageView = new ImageView(image);

            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            imageView.setFitWidth(container.getHeight() - 15);
            imageView.setFitHeight(container.getHeight() - 15);

            VBox imageContainer = new VBox();
            imageContainer.setMaxWidth(container.getHeight() - 15);
            imageContainer.setAlignment(Pos.CENTER);
            imageContainer.getStyleClass().addAll("grey-border", "semi-grey-bg");
            imageContainer.getChildren().add(imageView);

            StackPane.setMargin(imageContainer, new Insets(10, 5, 10, 10));
            StackPane.setAlignment(imageContainer, Pos.CENTER_LEFT);
            StackPane.setMargin(postContents, new Insets(10, 10, 10, 5));
            postContents.setMaxWidth(container.getHeight() - 10);

            mainStack.getChildren().add(imageContainer);
        } else {
            postContents.setMaxWidth(width - 20);
            StackPane.setMargin(postContents, new Insets(10));
        }

        mainStack.getChildren().add(postContents);

        HBox postClass = new HBox(5);
        postClass.setPadding(new Insets(5, 0, 0, 10));

        Text postClassText = new Text("@" + item.getPost().getClassId() + " • 2 days ago");
        postClassText.setFill(Color.web(Theme.TEXT_GREY));
        postClass.getChildren().add(postClassText);

        UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
        if (userDTO != null) {
            if (userDTO.getUser().getId().equals(item.getPost().getUserId())) {
                Button deleteButton = new Button("Delete Quote");
                deleteButton.getStyleClass().add("brand-text-button");
                HBox.setMargin(deleteButton, new Insets(0, 10, 0, 0));
                deleteButton.setOnAction(event -> {
                    Connection.post("/post/delete", new DeletePostDTO(item.getPost().getId())).thenAccept(response -> {
                        Platform.runLater(() -> {
                            onDelete.accept(item.getPost().getId());
                        });
                    });
                });

                HBox classAndDelete = new HBox(5);
                HBox spacer = new HBox();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                classAndDelete.getChildren().addAll(postClass, spacer, deleteButton);

                this.getChildren().addAll(classAndDelete, mainStack);
            } else {
                this.getChildren().addAll(postClass, mainStack);
            }
        }

        // this.getChildren().addAll(postClass, mainStack);
    }

    private HBox getPostReactionsComponent(AggregatedPost item, StackPane mainStack) {
        HBox postReactions = new HBox(10);
        postReactions.setAlignment(Pos.CENTER);

        postReactions.getStyleClass().addAll("grey-border", "semi-grey-bg");

        Text postLikes = new Text(item.getPost().getLikes() + "");
        postLikes.setFont(Theme.INRIA_SERIF_SMALL);
        postLikes.setFill(Color.web(Theme.TEXT_GREY));

        ImageView likeIcon = new ImageView(new Image("imgs/like.png"));
        ImageView likedIcon = new ImageView(new Image("imgs/liked.png"));

        likeIcon.setFitWidth(22);
        likeIcon.setFitHeight(22);

        likedIcon.setFitWidth(22);
        likedIcon.setFitHeight(22);
        Button likesButton = new Button();
        likesButton.getStyleClass().add("brand-text-button");

        UserDTO authenticatedUser = LocalStorage.get("/user", UserDTO.class);
        boolean hasLiked = item.getPost().getUsersLiked().contains(authenticatedUser.getUser().getId().toString());
        if (hasLiked) {
            likesButton.setGraphic(likedIcon);
        } else {
            likesButton.setGraphic(likeIcon);
        }

        likesButton.setOnAction(event -> {
            if (!hasLiked) {
                Connection.<LikePostDTO, PostDTO>post("/post/like", new LikePostDTO(item.getPost().getId()))
                        .thenAccept(response -> {
                            Platform.runLater(() -> {
                                likesButton.setGraphic(likedIcon);
                                setUpdatedPost.accept(new AggregatedPost(
                                        response.getBody().getPost(),
                                        item.getComments(), item.getUser()));
                            });
                        });
            } else {
                Connection.<LikePostDTO, PostDTO>post("/post/unlike", new LikePostDTO(item.getPost().getId()))
                        .thenAccept(response -> {
                            Platform.runLater(() -> {
                                likesButton.setGraphic(likeIcon);
                                setUpdatedPost.accept(new AggregatedPost(
                                        response.getBody().getPost(),
                                        item.getComments(), item.getUser()));
                            });
                        });
            }
        });

        ImageView commentIcon = new ImageView(new Image("imgs/comment.png"));

        commentIcon.setFitWidth(22);
        commentIcon.setFitHeight(22);

        Text postComments = new Text(item.getComments().size() + "");
        postComments.setFont(Theme.INRIA_SERIF_SMALL);
        postComments.setFill(Color.web(Theme.TEXT_GREY));

        Button seeCommentsButton = new Button();
        seeCommentsButton.setGraphic(commentIcon);
        seeCommentsButton.getStyleClass().add("brand-text-button");

        seeCommentsButton.setOnAction(event -> {
            Rectangle dimBackground = new Rectangle(450, 225);
            dimBackground.setFill(new Color(0, 0, 0, 0.25));
            mainStack.getChildren().addAll(dimBackground);

            VBox mainStackCopy = new VBox();
            mainStackCopy.setMaxSize(300, 180);
            mainStackCopy.getStyleClass().add("grey-bg");
            mainStackCopy.getStyleClass().add("grey-border");

            Button closeButton = new Button("Close Comments");
            closeButton.getStyleClass().add("brand-text-button");
            mainStackCopy.setPadding(new Insets(5));

            HBox createCommentContainer = new HBox(5);
            TextField commentField = new TextField();
            commentField.setPromptText("Write a comment...");
            commentField.getStyleClass().add("brand-field");
            Button postCommentButton = new Button("Post");
            postCommentButton.getStyleClass().add("accent-button");

            // add a comment
            postCommentButton.setOnAction(e -> {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String date = dtf.format(Instant.now().atZone(java.time.ZoneId.of("UTC")));

                Connection.<AddCommentDTO, Void>post("/comment/add",
                        new AddCommentDTO(item.getPost().getId(), commentField.getText(), date))
                        .thenAccept(response -> {
                            System.out.println("Comment added");
                        });
            });

            createCommentContainer.getChildren().addAll(commentField, postCommentButton);

            mainStackCopy.getChildren().addAll(closeButton, createCommentContainer);
            mainStackCopy.setAlignment(Pos.TOP_LEFT);
            mainStack.getChildren().addAll(mainStackCopy);
            closeButton.setOnAction(e -> {
                mainStack.getChildren().remove(dimBackground);
                mainStack.getChildren().remove(mainStackCopy);
            });
        });

        HBox likeGroup = new HBox(5, postLikes, likesButton);
        HBox commentGroup = new HBox(5, postComments, seeCommentsButton);

        likeGroup.setAlignment(Pos.CENTER);
        commentGroup.setAlignment(Pos.CENTER);

        postReactions.setPadding(new Insets(5, 10, 5, 10));
        postReactions.getChildren().addAll(likeGroup, commentGroup);

        return postReactions;
    }

    private VBox getPostQuoteComponent(String quote, double width) {
        VBox postQuote = new VBox(10);
        postQuote.setPadding(new Insets(10));
        postQuote.setAlignment(Pos.CENTER_LEFT);
        postQuote.setMaxWidth(width);
        VBox.setVgrow(postQuote, Priority.ALWAYS);

        postQuote.setBackground(new Background(new BackgroundFill(
                Color.web(Theme.TRANSLUCENT_GREY),
                new CornerRadii(0),
                null)));
        postQuote.setBorder(new Border(new BorderStroke(
                Color.web(Theme.BORDER),
                BorderStrokeStyle.SOLID,
                new CornerRadii(0),
                new BorderWidths(1))));

        quote = String.format("\"%s\"", quote);
        Text postText = new Text(quote);
        postText.setWrappingWidth(width - 20);
        postText.setFill(Color.web(Theme.TEXT_GREY));
        postText.setFont(Theme.INRIA_SERIF);

        Text postAuthor = new Text();
        postAuthor.textProperty().bind(author);
        Text postImprintedDate = new Text("Imprinted Dec. 1st 2024");

        postAuthor.setFont(Theme.INRIA_SERIF);
        postImprintedDate.setFont(Theme.INRIA_SERIF_SMALL);

        postImprintedDate.setFill(Color.web(Theme.TEXT_GREY));
        postAuthor.setFill(Color.web(Theme.ACCENT));

        VBox postVerticalMetaContainer = new VBox(3, postAuthor, postImprintedDate);
        postVerticalMetaContainer.setAlignment(Pos.CENTER_RIGHT);
        HBox postMetaContainer = new HBox(postVerticalMetaContainer);
        postMetaContainer.setAlignment(Pos.CENTER_RIGHT);

        postQuote.getChildren().addAll(postText, postMetaContainer);

        return postQuote;
    }
}
