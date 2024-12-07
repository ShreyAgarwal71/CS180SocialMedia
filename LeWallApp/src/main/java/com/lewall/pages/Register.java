package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator;
import com.lewall.Navigator.NavigatorPageState;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.api.Validation;
import com.lewall.components.Footer;
import com.lewall.components.PasswordField;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.dtos.CreateUserDTO;
import com.lewall.dtos.UserDTO;

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

/**
 * Register page for the application
 * 
 * @author Mahit Mehta
 * @version 17 November 2024
 */
public class Register extends Pane {
    private static final Logger logger = LogManager.getLogger(Register.class);

    /**
     * Constructor for the register page
     */
    public Register(NavigatorPageState state) {
        this.getStyleClass().add("primary-bg");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        Rectangle registerBox = new Rectangle(235, 350);
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

        VBox registerForm = new VBox(3);
        registerForm.setAlignment(Pos.CENTER);

        Text title = new Text("LeWall");
        title.getStyleClass().add("brand-title");
        title.setFill(Color.WHITE);
        VBox.setMargin(title, new Insets(15, 0, 0, 0));

        Text subTitle = new Text("Begin adding notes to LeWall");
        subTitle.getStyleClass().add("brand-subtitle");
        VBox.setMargin(subTitle, new Insets(5, 0, 20, 0));

        Text registerError = new Text();
        registerError.getStyleClass().add("error-text");
        registerError.setWrappingWidth(200);
        VBox.setMargin(registerError, new Insets(3, 0, 5, 0));

        TextField displayNameField = new TextField();
        displayNameField.setFocusTraversable(false);
        displayNameField.getStyleClass().add("brand-field");
        displayNameField.setMaxSize(200, 30);
        displayNameField.setPromptText("Full Name");
        displayNameField.onKeyPressedProperty().set(e -> {
            registerError.setText("");
        });
        VBox.setMargin(displayNameField, new Insets(0, 0, 7, 0));

        TextField emailField = new TextField();
        emailField.setFocusTraversable(false);
        emailField.getStyleClass().add("brand-field");
        emailField.setMaxSize(200, 30);
        emailField.setPromptText("username@purdue.edu");
        emailField.onKeyPressedProperty().set(e -> {
            registerError.setText("");
        });
        VBox.setMargin(emailField, new Insets(0, 0, 7, 0));

        PasswordField passwordField = new PasswordField();

        Button registerButton = new Button("Register");
        registerButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getPassword();
            String username = email;
            String displayName = displayNameField.getText();

            if (displayName == null || displayName.isEmpty()) {
                registerError.setText("Display Name is required.");
                return;
            }

            if (!Validation.isEmail(email)) {
                registerError.setText("Invalid Email");
                return;
            }

            if (!Validation.isSecurePassword(password)) {
                registerError.setText(
                        "Password must have 8+ characters, 1 lowercase and uppercase letter, number, & special character.");
                return;
            }

            Connection.<CreateUserDTO, AuthTokenDTO>post("/auth/register", new CreateUserDTO(
                    username, password, displayName, "", email))
                    .thenAccept(response -> {
                        String token = response.getBody().getToken();
                        if (token != null) {
                            logger.debug("Registration Successful");
                            LocalStorage.set("token", token);

                            Connection.<UserDTO>get("/user", true).thenAccept(userResponse -> {
                                Platform.runLater(() -> {
                                    Navigator.navigateTo(Navigator.EPage.HOME);
                                });
                            }).exceptionally(e -> {
                                while (e.getCause() != null) {
                                    e = e.getCause();
                                }

                                logger.error(e.getMessage());
                                LocalStorage.remove("token");
                                registerError.setText(e.getMessage());
                                return null;
                            });
                        } else {
                            logger.debug("Registration Failed");
                        }
                    }).exceptionally(ex -> {
                        logger.error(ex.getMessage());

                        while (ex.getCause() != null) {
                            ex = ex.getCause();
                        }

                        registerError.setText(ex.getMessage());
                        return null;
                    });
        });
        registerButton.setMinSize(200, 30);
        registerButton.getStyleClass().add("brand-button");
        VBox.setMargin(registerButton, new Insets(0, 0, 5, 0));

        Button signWithGoogleButton = new Button("Sign up with Google");
        signWithGoogleButton.setOnAction(e -> {
            logger.debug("Sign up with Google");
            registerError.setText("Feature Coming Soon.");
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
        VBox.setMargin(loginButton, new Insets(10, 0, 20, 0));

        registerForm.getChildren().addAll(
                title,
                subTitle,
                displayNameField,
                emailField,
                passwordField,
                registerError,
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