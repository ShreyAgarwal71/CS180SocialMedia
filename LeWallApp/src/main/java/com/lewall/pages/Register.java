package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;
import com.lewall.components.PasswordField;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.dtos.CreateUserDTO;

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

public class Register extends Pane {
    private static final Logger logger = LogManager.getLogger(Register.class);

    public Register() {
        this.getStyleClass().add("primary-bg");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        Rectangle registerBox = new Rectangle(235, 300);
        registerBox.setFill(new Color(0, 0, 0, 0));
        registerBox.setStroke(Color.rgb(255, 255, 255, 0.05));
        registerBox.setStrokeWidth(1);
        registerBox.setArcWidth(10);
        registerBox.setArcHeight(10);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setRadius(15);
        shadow.setSpread(0.5);

        // Not Functional
        Rectangle blurLayer = new Rectangle(235, 300);
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(0);
        blurLayer.setEffect(blur);
        blurLayer.setFill(new Color(1, 1, 1, 0.01));

        Group group = new Group();
        group.setEffect(shadow);
        group.getChildren().add(registerBox);

        stackPane.getChildren().add(blurLayer);
        stackPane.getChildren().add(group);

        VBox registerForm = new VBox(10);
        registerForm.setAlignment(Pos.CENTER);

        Text title = new Text("LeWall");
        title.getStyleClass().add("brand-title");
        title.setFill(Color.WHITE);
        Text subTitle = new Text("Begin adding notes to LeWall");
        subTitle.getStyleClass().add("brand-subtitle");
        VBox.setMargin(subTitle, new Insets(0, 0, 15, 0));

        TextField emailField = new TextField();
        emailField.setFocusTraversable(false);
        emailField.getStyleClass().add("brand-field");
        emailField.setMaxSize(200, 30);
        emailField.setPromptText("username@purdue.edu");

        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Register");
        registerButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getPassword();
            String username = email;

            Connection.<CreateUserDTO, AuthTokenDTO>post("/auth/register", new CreateUserDTO(
                    username, password, "", "", email))
                    .thenAccept(response -> {
                        String token = response.getBody().getToken();
                        if (token != null) {
                            logger.debug("Registration Successful");
                            LocalStorage.set("token", token);

                            Platform.runLater(() -> {
                                Navigator.navigateTo(Navigator.EPage.HOME);
                            });
                        } else {
                            logger.debug("Registration Failed");
                        }
                    }).exceptionally(ex -> {
                        logger.error("Error Message: " + ex.getMessage());
                        return null;
                    });
        });
        registerButton.setMinSize(200, 30);
        registerButton.getStyleClass().add("brand-button");

        Button signWithGoogleButton = new Button("Sign up with Google");
        signWithGoogleButton.setOnAction(e -> {
            logger.debug("Sign up with Google");
        });
        signWithGoogleButton.setMinSize(200, 30);
        signWithGoogleButton.getStyleClass().add("brand-button");

        Image googleImage = new Image("imgs/google-icon.png");
        ImageView googleImageView = new ImageView(googleImage);

        googleImageView.setFitWidth(16);
        googleImageView.setFitHeight(16);
        signWithGoogleButton.setGraphic(googleImageView);

        Button loginButton = new Button("Already Have an Account? Login");
        loginButton.setOnAction(e -> {
            Navigator.navigateTo(Navigator.EPage.LOGIN);
        });
        loginButton.getStyleClass().add("brand-text-button");
        VBox.setMargin(loginButton, new Insets(5, 0, 20, 0));

        registerForm.getChildren().addAll(
                title,
                subTitle,
                emailField,
                passwordField,
                registerButton,
                signWithGoogleButton,
                loginButton);

        stackPane.getChildren().add(registerForm);

        flowPane.getChildren().add(stackPane);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().add(flowPane);

        HBox footer = new Footer();
        StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);
        mainStack.getChildren().add(footer);

        this.getChildren().add(mainStack);
    }
}