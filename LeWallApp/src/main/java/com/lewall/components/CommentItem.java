package com.lewall.components;

import java.util.UUID;
import java.util.function.Consumer;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.common.AggregatedComment;
import com.lewall.common.Theme;
import com.lewall.dtos.CommentDTO;
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

public class CommentItem extends HBox {
    public CommentItem(AggregatedComment item, UUID postOwnerId, Consumer<Void> onDelete,
            Consumer<AggregatedComment> setUpdatedCommment) {
        super(5);
        this.setMaxHeight(35);

        VBox commnetContainer = new VBox();
        commnetContainer.setAlignment(Pos.CENTER);

        String rawUsername = item.getUser().getUsername();
        String formattedUsername = rawUsername.split("@")[0];
        Text usernameEle = new Text(formattedUsername);
        usernameEle.setFill(Color.web(Theme.ACCENT));

        Text comment = new Text(item.getComment().getMessageComment());
        comment.getStyleClass().add("brand-label");
        commnetContainer.getChildren().addAll(comment);

        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        UserDTO authenticatedUser = LocalStorage.get("/user", UserDTO.class);
        boolean commentOwner = authenticatedUser.getUser().getId().equals(item.getComment().getUserId());
        boolean postOwner = authenticatedUser.getUser().getId().equals(postOwnerId);
        boolean canDelete = commentOwner || postOwner;

        this.getChildren().addAll(usernameEle, commnetContainer, spacer);

        Text likes = new Text(item.getComment().getLikes() + "");
        likes.setFont(Theme.INRIA_SERIF_SMALL);
        likes.setFill(Color.web(Theme.TEXT_GREY));

        Text dislikes = new Text(item.getComment().getDislikes() + "");
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

        boolean hasLiked = item.getComment().getLikedBy().contains(authenticatedUser.getUser().getId().toString());
        boolean hasDisliked = item.getComment().getDislikedBy()
                .contains(authenticatedUser.getUser().getId().toString());

        if (hasLiked) {
            likesButton.setGraphic(likedIcon);
        } else {
            likesButton.setGraphic(likeIcon);
        }

        if (hasDisliked) {
            dislikesButton.setGraphic(dislikedIcon);
        } else {
            dislikesButton.setGraphic(dislikeIcon);
        }

        HBox likeGroup = new HBox(5, likes, likesButton);
        HBox dislikeGroup = new HBox(5, dislikes, dislikesButton);
        likeGroup.setAlignment(Pos.CENTER);
        dislikeGroup.setAlignment(Pos.CENTER);

        likesButton.setOnAction(event -> {
            if (!hasLiked) {
                Connection
                        .<LikeCommentDTO, CommentDTO>post("/comment/like",
                                new LikeCommentDTO(item.getComment().getId()))
                        .thenAccept(response -> {
                            Platform.runLater(() -> {
                                likesButton.setGraphic(likedIcon);
                                setUpdatedCommment.accept(new AggregatedComment(
                                        response.getBody().getComment(),
                                        item.getUser()));
                            });
                        });
            } else {
                Connection
                        .<LikeCommentDTO, CommentDTO>post("/comment/unlike",
                                new LikeCommentDTO(item.getComment().getId()))
                        .thenAccept(response -> {
                            Platform.runLater(() -> {
                                likesButton.setGraphic(likeIcon);
                                setUpdatedCommment.accept(new AggregatedComment(
                                        response.getBody().getComment(),
                                        item.getUser()));
                            });
                        });
            }
        });

        this.getChildren().addAll(likeGroup, dislikeGroup);

        if (canDelete) {
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(e -> {
                onDelete.accept(null);
            });

            deleteButton.getStyleClass().add("brand-text-button");
            this.getChildren().add(deleteButton);
        }

        this.getStyleClass().add("grey-bg");
        this.getStyleClass().add("grey-border");
        this.getStyleClass().add("rounded");
    }
}
