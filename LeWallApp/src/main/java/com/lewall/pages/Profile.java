package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.api.Validation;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.dtos.LoginDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.components.PasswordField;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
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
import javafx.animation.RotateTransition;
import javafx.animation.Interpolator;
import javafx.util.Duration;




public class Profile extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);
    
    public Profile() {
        this.getStyleClass().add("primary-bg");

        VBox navbar = new Navbar();
        HBox.setMargin(navbar, new Insets(10, 10, 10, 10));

        Text profileTitle = new Text("Inscriber Profile");
        profileTitle.getStyleClass().add("profile-title");
        profileTitle.setFill(Color.WHITE);
        VBox.setMargin(profileTitle, new Insets(10,0,0,0));

        Text profileSubtitle = new Text("@notzayan"); // TODO: take user data to show their name
        profileSubtitle.getStyleClass().add("profile-subtitle");
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
