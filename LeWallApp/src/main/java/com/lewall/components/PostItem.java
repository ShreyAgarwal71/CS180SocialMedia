package com.lewall.components;

import com.lewall.common.Theme;
import com.lewall.db.models.Post;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class PostItem extends VBox {
    public PostItem(Post item) {
        super(5);

        StackPane mainStack = new StackPane();
        String imageURL = "https://www.oberlin.edu/sites/default/files/styles/width_1160/public/content/facility/image/craig_lecture_hall.jpg";//
        // item.getImageURL();

        int width = 450;
        int height = 225;

        Rectangle container = new Rectangle(width, height);
        container.setFill(new Color(0, 0, 0, 0));
        container.setStroke(Color.rgb(255, 255, 255, 0.05));
        container.setStrokeWidth(1);
        container.setArcWidth(0);
        container.setArcHeight(0);

        VBox postContents = new VBox(5);
        VBox postQuote = getPostQuoteComponent(item.getMessagePost(), imageURL == null ? width - 20 : height - 10);

        if (imageURL != null) {
            StackPane.setAlignment(postContents, Pos.CENTER_RIGHT);
        }

        HBox postReactions = getPostReactionsComponent(item.getLikes(), 10);

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
        postClass.setPadding(new Insets(0, 0, 0, 10));

        Text postClassText = new Text("@" + item.getClassId() + " • 2 days ago");
        postClassText.setFill(Color.web(Theme.TEXT_GREY));
        postClass.getChildren().add(postClassText);

        this.getChildren().addAll(postClass, mainStack);
    }

    private HBox getPostReactionsComponent(int likes, int comments) {
        HBox postReactions = new HBox(10);
        postReactions.setAlignment(Pos.CENTER);

        postReactions.getStyleClass().addAll("grey-border", "semi-grey-bg");

        Text postLikes = new Text(likes + "");
        postLikes.setFont(Theme.INRIA_SERIF_SMALL);
        postLikes.setFill(Color.web(Theme.TEXT_GREY));

        ImageView likeIcon = new ImageView(new Image("imgs/like.png"));
        ImageView likedIcon = new ImageView(new Image("imgs/liked.png"));

        likeIcon.setFitWidth(22);
        likeIcon.setFitHeight(22);

        likedIcon.setFitWidth(18);
        likedIcon.setFitHeight(18);

        Text postComments = new Text(comments + "");
        postComments.setFont(Theme.INRIA_SERIF_SMALL);
        postComments.setFill(Color.web(Theme.TEXT_GREY));

        ImageView commentIcon = new ImageView(new Image("imgs/comment.png"));

        commentIcon.setFitWidth(22);
        commentIcon.setFitHeight(22);

        HBox likeGroup = new HBox(5, postLikes, likeIcon);
        HBox commentGroup = new HBox(5, postComments, commentIcon);

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

        Text postAuthor = new Text("~ Mahit Mehta");
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
