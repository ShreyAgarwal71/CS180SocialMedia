package com.lewall.components.Comment;

import java.util.UUID;
import java.util.function.Consumer;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.common.Theme;
import com.lewall.components.Comment.CommentListView.ObservableComment;
import com.lewall.dtos.CommentDTO;
import com.lewall.dtos.DeleteCommentDTO;
import com.lewall.dtos.LikeCommentDTO;
import com.lewall.dtos.UserDTO;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CommentCard extends VBox {
    public CommentCard(ObservableComment item, UUID postOwnerId, Consumer<Void> onDelete) {
        HBox container = new HBox(5);
        container.setMaxHeight(35);

        VBox commnetContainer = new VBox();
        commnetContainer.setAlignment(Pos.CENTER);

        String rawUsername = item.getUser().getUsername();
        String formattedUsername = rawUsername.split("@")[0];
        Text usernameEle = new Text(formattedUsername);
        usernameEle.setFill(Color.web(Theme.ACCENT));

        Text comment = new Text(item.getComment().getMessageComment());
        comment.getStyleClass().add("brand-label");
        commnetContainer.getChildren().addAll(comment);

        container.setPadding(new Insets(10));
        container.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        UserDTO authenticatedUser = LocalStorage.get("/user", UserDTO.class);
        boolean commentOwner = authenticatedUser.getUser().getId().equals(item.getComment().getUserId());
        boolean postOwner = authenticatedUser.getUser().getId().equals(postOwnerId);
        boolean canDelete = commentOwner || postOwner;

        container.getChildren().addAll(usernameEle, commnetContainer, spacer);

        Text likes = new Text();
        likes.textProperty().bind(item.getLikeCount());
        likes.setFont(Theme.INRIA_SERIF_SMALL);
        likes.setFill(Color.web(Theme.TEXT_GREY));

        Text dislikes = new Text();
        dislikes.textProperty().bind(item.getDislikeCount());
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

        if (item.isLiked().get()) {
            likesButton.setGraphic(likedIcon);
        } else {
            likesButton.setGraphic(likeIcon);
        }

        if (item.isDisliked().get()) {
            dislikesButton.setGraphic(dislikedIcon);
        } else {
            dislikesButton.setGraphic(dislikeIcon);
        }

        item.isLiked().addListener((_, _, isNowLiked) -> {
            if (isNowLiked) {
                likesButton.setGraphic(likedIcon);
            } else {
                likesButton.setGraphic(likeIcon);
            }
        });

        item.isDisliked().addListener((_, _, isNowDisliked) -> {
            if (isNowDisliked) {
                dislikesButton.setGraphic(dislikedIcon);
            } else {
                dislikesButton.setGraphic(dislikeIcon);
            }
        });

        HBox likeGroup = new HBox(5, likes, likesButton);
        HBox dislikeGroup = new HBox(5, dislikes, dislikesButton);
        likeGroup.setAlignment(Pos.CENTER);
        dislikeGroup.setAlignment(Pos.CENTER);

        likesButton.setOnAction(_ -> {
            Connection
                    .<LikeCommentDTO, CommentDTO>post("/comment/like",
                            new LikeCommentDTO(item.getComment().getId()))
                    .thenAccept(response -> {
                        Platform.runLater(() -> {
                            item.isLiked().set(response.getBody().getComment().getLikedBy()
                                    .contains(authenticatedUser.getUser().getId().toString()));
                            item.isDisliked().set(response.getBody().getComment().getDislikedBy()
                                    .contains(authenticatedUser.getUser().getId().toString()));

                            item.getDislikeCount().set(String.valueOf(response.getBody().getComment().getDislikes()));
                            item.getLikeCount().set(String.valueOf(response.getBody().getComment().getLikes()));
                        });
                    });
        });

        dislikesButton.setOnAction(_ -> {
            Connection
                    .<LikeCommentDTO, CommentDTO>post("/comment/dislike",
                            new LikeCommentDTO(item.getComment().getId()))
                    .thenAccept(response -> {
                        Platform.runLater(() -> {
                            item.isLiked().set(response.getBody().getComment().getLikedBy()
                                    .contains(authenticatedUser.getUser().getId().toString()));
                            item.isDisliked().set(response.getBody().getComment().getDislikedBy()
                                    .contains(authenticatedUser.getUser().getId().toString()));

                            item.getDislikeCount().set(String.valueOf(response.getBody().getComment().getDislikes()));
                            item.getLikeCount().set(String.valueOf(response.getBody().getComment().getLikes()));
                        });
                    });
        });

        container.getChildren().addAll(likeGroup, dislikeGroup);

        if (canDelete) {
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(_ -> {
                Connection
                        .<DeleteCommentDTO, Void>post("/comment/delete",
                                new DeleteCommentDTO(item.getComment().getId()))
                        .thenAccept(_ -> {
                            Platform.runLater(() -> {
                                onDelete.accept(null);
                            });
                        });
            });

            deleteButton.getStyleClass().add("brand-text-button");
            container.getChildren().add(deleteButton);
        }

        container.getStyleClass().add("grey-bg");
        container.getStyleClass().add("grey-border");
        container.getStyleClass().add("rounded");

        this.setPadding(new Insets(0, 0, 5, 0));
        this.getChildren().add(container);
    }
}
