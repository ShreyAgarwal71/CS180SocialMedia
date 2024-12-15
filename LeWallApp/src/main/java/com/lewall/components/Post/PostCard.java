package com.lewall.components.Post;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.function.Consumer;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.common.Theme;
import com.lewall.common.Util;
import com.lewall.components.Comment.CommentListView;
import com.lewall.components.Comment.CommentListView.ObservableComment;
import com.lewall.components.Post.PostListView.ObservablePost;
import com.lewall.dtos.UserDTO;
import com.lewall.dtos.AddCommentDTO;
import com.lewall.dtos.AggregatedCommentsDTO;
import com.lewall.dtos.DeletePostDTO;
import com.lewall.dtos.PostDTO;
import com.lewall.dtos.LikePostDTO;
import com.lewall.dtos.PostCommentsDTO;
import com.lewall.dtos.HidePostDTO;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
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

public class PostCard extends VBox {
    private ObservablePost post;

    public PostCard(
            ObservablePost item,
            Consumer<UUID> onDelete) {
        super(5);
        this.post = item;

        String imageURL = item.getPost().getImageURL();
        if (!Util.isImageURLValid(imageURL))
            imageURL = null;
        int width = 450;
        int height = 225;

        StackPane mainStack = new StackPane();
        mainStack.setMaxWidth(width);
        this.setMaxWidth(width);

        Rectangle container = new Rectangle(width, height);
        container.setFill(new Color(0, 0, 0, 0));
        container.setStroke(Color.rgb(255, 255, 255, 0.05));
        container.setStrokeWidth(1);
        container.setArcWidth(0);
        container.setArcHeight(0);

        VBox postContents = new VBox(5);
        VBox postQuote = getPostQuoteComponent(
                imageURL == null ? width - 20 : height - 10);

        if (imageURL != null) {
            StackPane.setAlignment(postContents, Pos.CENTER_RIGHT);
        }

        HBox postReactions = getPostReactionsComponent(mainStack);

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

            imageView.setFitWidth(container.getHeight() - 20);
            imageView.setFitHeight(container.getHeight() - 20);

            VBox imageContainer = new VBox();
            imageContainer.setMaxWidth(container.getHeight() - 20);
            imageContainer.setAlignment(Pos.CENTER);
            imageContainer.getStyleClass().addAll("grey-border", "semi-grey-bg");
            imageContainer.getChildren().add(imageView);

            StackPane.setMargin(imageContainer, new Insets(10, 10, 10, 10));
            StackPane.setAlignment(imageContainer, Pos.CENTER_LEFT);
            StackPane.setMargin(postContents, new Insets(10, 10, 10, 10));
            postContents.setMaxWidth(container.getHeight() - 15);

            mainStack.getChildren().add(imageContainer);
        } else {
            postContents.setMaxWidth(width - 20);
            StackPane.setMargin(postContents, new Insets(10));
        }

        mainStack.getChildren().add(postContents);

        HBox postClass = new HBox(5);
        postClass.setPadding(new Insets(5, 0, 0, 10));

        Text postClassText = new Text("@" + item.getPost().getClassId());
        postClassText.setFill(Color.web(Theme.TEXT_GREY));
        postClass.getChildren().add(postClassText);

        UserDTO userDTO = LocalStorage.get("/user", UserDTO.class);
        if (userDTO != null) {
            if (userDTO.getUser().getId().equals(item.getPost().getUserId())) {
                Button deleteButton = new Button("Delete Quote");
                deleteButton.getStyleClass().add("brand-text-button");
                HBox.setMargin(deleteButton, new Insets(0, 10, 0, 0));
                deleteButton.setOnAction(_ -> {
                    Connection.post("/post/delete", new DeletePostDTO(item.getPost().getId())).thenAccept(_ -> {
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
                Button hideButton = new Button("Hide Post");
                hideButton.getStyleClass().add("brand-text-button");
                HBox.setMargin(hideButton, new Insets(0, 10, 0, 0));
                hideButton.setOnAction(_ -> {
                    Connection.post("/post/hide", new HidePostDTO(item.getPost().getId())).thenAccept(_ -> {
                        Platform.runLater(() -> {
                            onDelete.accept(item.getPost().getId());
                        });
                    });
                });

                HBox classAndHide = new HBox(5);
                HBox spacer = new HBox();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                classAndHide.getChildren().addAll(postClass, spacer, hideButton);

                this.getChildren().addAll(classAndHide, mainStack);
            }
        }
    }

    private HBox getPostReactionsComponent(StackPane mainStack) {
        HBox postReactions = new HBox(10);
        postReactions.setAlignment(Pos.CENTER);

        postReactions.getStyleClass().addAll("grey-border", "semi-grey-bg");

        Text postLikes = new Text();
        postLikes.textProperty().bind(post.getLikeCount());
        postLikes.setFont(Theme.INRIA_SERIF_SMALL);
        postLikes.setFill(Color.web(Theme.TEXT_GREY));

        Text dislikes = new Text();
        dislikes.textProperty().bind(post.getDislikeCount());
        dislikes.setFont(Theme.INRIA_SERIF_SMALL);
        dislikes.setFill(Color.web(Theme.TEXT_GREY));

        ImageView likeIcon = new ImageView(new Image("imgs/like.png"));
        ImageView likedIcon = new ImageView(new Image("imgs/liked.png"));

        likeIcon.setFitWidth(22);
        likeIcon.setFitHeight(22);

        likedIcon.setFitWidth(22);
        likedIcon.setFitHeight(22);

        ImageView dislikeIcon = new ImageView(new Image("imgs/dislike.png"));
        ImageView dislikedIcon = new ImageView(new Image("imgs/disliked.png"));

        dislikeIcon.setFitWidth(22);
        dislikeIcon.setFitHeight(22);

        dislikedIcon.setFitWidth(22);
        dislikedIcon.setFitHeight(22);

        Button likesButton = new Button();
        likesButton.getStyleClass().add("brand-text-button");

        Button dislikesButton = new Button();
        dislikesButton.getStyleClass().add("brand-text-button");

        if (post.isLiked().get()) {
            likesButton.setGraphic(likedIcon);
        } else {
            likesButton.setGraphic(likeIcon);
        }

        if (post.isDisliked().get()) {
            dislikesButton.setGraphic(dislikedIcon);
        } else {
            dislikesButton.setGraphic(dislikeIcon);
        }

        post.isLiked().addListener((_, _, isNowLiked) -> {
            if (isNowLiked) {
                likesButton.setGraphic(likedIcon);
            } else {
                likesButton.setGraphic(likeIcon);
            }
        });

        post.isDisliked().addListener((_, _, isNowDisliked) -> {
            if (isNowDisliked) {
                dislikesButton.setGraphic(dislikedIcon);
            } else {
                dislikesButton.setGraphic(dislikeIcon);
            }
        });

        UserDTO authenticatedUser = LocalStorage.get("/user", UserDTO.class);
        UUID authenticatedUserId = authenticatedUser.getUser().getId();

        dislikesButton.setOnAction(_ -> {
            Connection.<LikePostDTO, PostDTO>post("/post/dislike", new LikePostDTO(post.getPost().getId()))
                    .thenAccept(response -> {
                        Platform.runLater(() -> {
                            post.isLiked().set(response.getBody().getPost().getUsersLiked()
                                    .contains(authenticatedUser.getUser().getId().toString()));
                            post.isDisliked().set(response.getBody().getPost().getUsersDisliked()
                                    .contains(authenticatedUser.getUser().getId().toString()));

                            post.getDislikeCount().set(String.valueOf(response.getBody().getPost().getDislikes()));
                            post.getLikeCount().set(String.valueOf(response.getBody().getPost().getLikes()));
                        });
                    });
        });

        likesButton.setOnAction(_ -> {
            Connection.<LikePostDTO, PostDTO>post("/post/like", new LikePostDTO(post.getPost().getId()))
                    .thenAccept(response -> {
                        Platform.runLater(() -> {
                            post.isLiked().set(response.getBody().getPost().getUsersLiked()
                                    .contains(authenticatedUser.getUser().getId().toString()));
                            post.isDisliked().set(response.getBody().getPost().getUsersDisliked()
                                    .contains(authenticatedUser.getUser().getId().toString()));

                            post.getDislikeCount().set(String.valueOf(response.getBody().getPost().getDislikes()));
                            post.getLikeCount().set(String.valueOf(response.getBody().getPost().getLikes()));
                        });
                    });
        });

        ImageView commentIcon = new ImageView(new Image("imgs/comment.png"));

        commentIcon.setFitWidth(22);
        commentIcon.setFitHeight(22);

        Text postComments = new Text();
        postComments.textProperty().bind(post.getCommentCount());
        postComments.setFont(Theme.INRIA_SERIF_SMALL);
        postComments.setFill(Color.web(Theme.TEXT_GREY));

        Button seeCommentsButton = new Button();
        seeCommentsButton.setGraphic(commentIcon);
        seeCommentsButton.getStyleClass().add("brand-text-button");

        seeCommentsButton.setOnAction(_ -> {
            Rectangle dimBackground = new Rectangle(450, 225);
            dimBackground.setFill(new Color(0, 0, 0, 0.25));
            mainStack.getChildren().addAll(dimBackground);

            VBox mainStackCopy = new VBox(5);
            mainStackCopy.setMaxSize(300, 190);
            mainStackCopy.getStyleClass().add("grey-bg");
            mainStackCopy.getStyleClass().add("grey-border");

            Button closeButton = new Button("Close Comments");
            closeButton.getStyleClass().add("brand-text-button");
            mainStackCopy.setPadding(new Insets(5));

            HBox createCommentContainer = new HBox(5);
            TextField commentField = new TextField();
            commentField.setPromptText("Write a comment...");
            commentField.getStyleClass().add("brand-field");
            commentField.setPrefWidth(235);
            Button postCommentButton = new Button("Post");
            postCommentButton.getStyleClass().add("accent-button");

            ListView<ObservableComment> commentListView = new CommentListView(
                    post.getComments(),
                    post.getPost().getUserId(),
                    _ -> {
                        Connection
                                .<PostCommentsDTO, AggregatedCommentsDTO>post("/post/getComments",
                                        new PostCommentsDTO(post.getPost().getId()))
                                .thenAccept(commentsResponse -> {
                                    Platform.runLater(() -> {
                                        post.getComments().clear();
                                        commentsResponse.getBody().getComments().forEach(comment -> {
                                            post.getComments().add(new ObservableComment(comment, authenticatedUserId));
                                        });
                                    });
                                });
                    });

            // add a comment
            postCommentButton.setOnAction(_ -> {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                String date = dtf.format(Instant.now().atZone(java.time.ZoneId.of("UTC")));

                Connection.<AddCommentDTO, Void>post("/comment/add",
                        new AddCommentDTO(post.getPost().getId(), commentField.getText(), date))
                        .thenAccept(_ -> {
                            commentField.setText("");
                            Connection
                                    .<PostCommentsDTO, AggregatedCommentsDTO>post("/post/getComments",
                                            new PostCommentsDTO(post.getPost().getId()))
                                    .thenAccept(commentsResponse -> {
                                        Platform.runLater(() -> {
                                            post.getComments().clear();
                                            commentsResponse.getBody().getComments().forEach(comment -> {
                                                post.getComments()
                                                        .add(new ObservableComment(comment, authenticatedUserId));
                                            });
                                        });
                                    });
                        });
            });

            createCommentContainer.getChildren().addAll(commentField, postCommentButton);

            mainStackCopy.getChildren().addAll(closeButton, commentListView, createCommentContainer);
            mainStackCopy.setAlignment(Pos.TOP_LEFT);
            mainStack.getChildren().addAll(mainStackCopy);
            closeButton.setOnAction(_ -> {
                mainStack.getChildren().remove(dimBackground);
                mainStack.getChildren().remove(mainStackCopy);
            });
        });

        HBox likeGroup = new HBox(5, postLikes, likesButton);
        HBox dislikeGroup = new HBox(5, dislikes, dislikesButton);
        HBox commentGroup = new HBox(5, postComments, seeCommentsButton);

        likeGroup.setAlignment(Pos.CENTER);
        dislikeGroup.setAlignment(Pos.CENTER);
        commentGroup.setAlignment(Pos.CENTER);

        postReactions.setPadding(new Insets(5, 10, 5, 10));
        postReactions.getChildren().addAll(likeGroup, dislikeGroup, commentGroup);

        return postReactions;
    }

    private VBox getPostQuoteComponent(double width) {
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

        String quote = String.format("\"%s\"", post.getPost().getMessagePost());
        Text postText = new Text(quote);
        postText.setWrappingWidth(width - 20);
        postText.setFill(Color.web(Theme.TEXT_GREY));
        postText.setFont(Theme.INRIA_SERIF);

        Text postAuthor = new Text(post.getUser().getDisplayName());
        Text postImprintedDate = new Text(Util.formatDateString(post.getPost().getDate()));

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
