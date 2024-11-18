package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.api.Validation;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.dtos.LoginDTO;
import com.lewall.components.Footer;
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

public class Login extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);

    public Login() {
        this.getStyleClass().add("primary-bg");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.prefWidthProperty().bind(this.widthProperty());
        flowPane.prefHeightProperty().bind(this.heightProperty());
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        Rectangle loginBox = new Rectangle(235, 350);
        loginBox.setFill(new Color(0, 0, 0, 0));
        loginBox.setStroke(Color.rgb(255, 255, 255, 0.05));
        loginBox.setStrokeWidth(1);
        loginBox.setArcWidth(10);
        loginBox.setArcHeight(10);

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
        group.getChildren().add(loginBox);

        stackPane.getChildren().add(blurLayer);
        stackPane.getChildren().add(group);

        VBox loginForm = new VBox(3);
        loginForm.setAlignment(Pos.CENTER);

        Text title = new Text("LeWall");
        title.getStyleClass().add("brand-title");
        title.setFill(Color.WHITE);
        VBox.setMargin(title, new Insets(10, 0, 0, 0));

        Text subTitle = new Text("Begin adding notes to LeWall");
        subTitle.getStyleClass().add("brand-subtitle");
        VBox.setMargin(subTitle, new Insets(5, 0, 20, 0));

        Text loginError = new Text();
        loginError.getStyleClass().add("error-text");
        loginError.setWrappingWidth(200);
        VBox.setMargin(loginError, new Insets(3, 0, 5, 0));

        TextField emailField = new TextField();
        emailField.setFocusTraversable(false);
        emailField.getStyleClass().add("brand-field");
        emailField.setMaxSize(200, 30);
        emailField.setPromptText("username@purdue.edu");
        emailField.onKeyPressedProperty().set(e -> {
            loginError.setText("");
        });
        VBox.setMargin(emailField, new Insets(0, 0, 7, 0));

        PasswordField passwordField = new PasswordField();

        // Gear image setup
        Image gearImage = new Image("imgs/loading-gear.png");
        ImageView gearImageView = new ImageView(gearImage);
        gearImageView.setFitWidth(30);
        gearImageView.setFitHeight(30);
        gearImageView.setVisible(false); // Initially hidden

        // RotateTransition for spinning
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), gearImageView);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(Interpolator.LINEAR);

        Button loginButton = new Button("Login");
        VBox.setMargin(loginButton, new Insets(0, 0, 5, 0));
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getPassword();

            if (!Validation.isEmail(email) || password == null || password.isEmpty()) {
                loginError.setText("Invalid email or password format.");
                gearImageView.setVisible(false);
                rotateTransition.stop();
                return;
            }

            // Show and spin gear
            gearImageView.setVisible(true);
            rotateTransition.play();

            Connection.<LoginDTO, AuthTokenDTO>post("/auth/login", new LoginDTO(
                    email, password))
                    .thenAccept(response -> {
                        String token = response.getBody().getToken();
                        if (token != null) {
                            logger.debug("Login Successful");
                            LocalStorage.set("token", token);
                            Platform.runLater(() -> {
                                rotateTransition.stop();
                                gearImageView.setVisible(false);
                                Navigator.navigateTo(Navigator.EPage.HOME);
                            });
                        } else {
                            logger.debug("Login Failed");
                            loginError.setText("Internal server error, try again later.");
                            rotateTransition.stop();
                            gearImageView.setVisible(false);
                        }
                    }).exceptionally(ex -> {
                        logger.error(ex.getMessage());

                        while (ex.getCause() != null) {
                            ex = ex.getCause();
                        }

                        loginError.setText(ex.getMessage());
                        gearImageView.setVisible(false);
                        return null;
                    });
        });
        loginButton.setMinSize(200, 30);
        loginButton.getStyleClass().add("brand-button");

        Button signWithGoogleButton = new Button("Sign in with Google");
        signWithGoogleButton.setOnAction(e -> {
            logger.debug("Sign in with Google");
            loginError.setText("Feature Coming Soon.");
        });
        signWithGoogleButton.setMinSize(200, 30);
        signWithGoogleButton.getStyleClass().add("brand-button");

        Image googleImage = new Image("imgs/google-icon.png");
        ImageView googleImageView = new ImageView(googleImage);

        googleImageView.setFitWidth(16);
        googleImageView.setFitHeight(16);
        signWithGoogleButton.setGraphic(googleImageView);

        Button registerButton = new Button("Create Account");
        registerButton.setOnAction(e -> {
            Navigator.navigateTo(Navigator.EPage.REGISTER);
        });
        registerButton.getStyleClass().add("brand-text-button");
        VBox.setMargin(registerButton, new Insets(10, 0, 10, 0));

        loginForm.getChildren().addAll(
                title,
                subTitle,
                emailField,
                passwordField,
                gearImageView,
                loginError,
                loginButton,
                signWithGoogleButton,
                registerButton);

        stackPane.getChildren().add(loginForm);

        flowPane.getChildren().add(stackPane);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().add(flowPane);

        HBox footer = new Footer();
        StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);
        mainStack.getChildren().add(footer);

        this.getChildren().add(mainStack);
    }
}
