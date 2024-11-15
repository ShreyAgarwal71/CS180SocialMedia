package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Connection;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.dtos.LoginDTO;

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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Login extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);

    private String password = "";

    public Login() {
        super();

        this.getStyleClass().add("root");
        this.getStylesheets().add("css/global.css");

        FlowPane flowPane = new FlowPane(10, 10);
        flowPane.setPrefSize(650, 450);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setOrientation(Orientation.VERTICAL);

        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);

        Rectangle loginBox = new Rectangle(235, 300);
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

        VBox loginForm = new VBox(10);
        loginForm.setAlignment(Pos.CENTER);

        Text title = new Text("LeWall");
        title.getStyleClass().add("login-title");
        title.setFill(Color.WHITE);
        Text subTitle = new Text("Begin adding notes to LeWall");
        subTitle.getStyleClass().add("login-subtitle");
        VBox.setMargin(subTitle, new Insets(0, 0, 15, 0));

        TextField emailField = new TextField();
        emailField.setFocusTraversable(false);
        emailField.getStyleClass().add("login-field");
        emailField.setMaxSize(200, 30);
        emailField.setPromptText("username@purdue.edu");

        TextField passwordField = new TextField();
        passwordField.setOnKeyTyped(e -> {
            String input = passwordField.getText();

            if (input.length() < password.length()) {
                password = password.substring(0, password.length() - 1);
            } else if (input.length() > password.length()) {
                password += input.charAt(input.length() - 1);
            }

            StringBuilder maskedInput = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                maskedInput.append("•");
            }
            passwordField.setText(maskedInput.toString());
            passwordField.positionCaret(maskedInput.length());
        });
        passwordField.setFocusTraversable(false);
        passwordField.getStyleClass().add("login-field");
        passwordField.setMaxSize(200, 30);
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            System.out.println(password);

            Connection.<LoginDTO, AuthTokenDTO>post("/auth/login", new LoginDTO(
                    email, password))
                    .thenAccept(response -> {
                        logger.debug("Body: " + response.getBody().getToken());
                    }).exceptionally(ex -> {
                        logger.error("Error Message: " + ex.getMessage());
                        return null;
                    });
        });
        loginButton.setMinSize(200, 30);
        loginButton.getStyleClass().add("login-button");

        Button signWithGoogleButton = new Button("Sign in with Google");
        signWithGoogleButton.setOnAction(e -> {
            logger.debug("Sign in with Google");
        });
        signWithGoogleButton.setMinSize(200, 30);
        signWithGoogleButton.getStyleClass().add("login-button");

        Image googleImage = new Image("imgs/google-icon.png");
        ImageView googleImageView = new ImageView(googleImage);

        googleImageView.setFitWidth(16);
        googleImageView.setFitHeight(16);
        signWithGoogleButton.setGraphic(googleImageView);

        Button registerButton = new Button("Create Account");
        registerButton.getStyleClass().add("text-button");
        VBox.setMargin(registerButton, new Insets(5, 0, 20, 0));

        loginForm.getChildren().addAll(
                title,
                subTitle,
                emailField,
                passwordField,
                loginButton,
                signWithGoogleButton,
                registerButton);

        stackPane.getChildren().add(loginForm);

        flowPane.getChildren().add(stackPane);

        StackPane mainStack = new StackPane();
        mainStack.getChildren().add(flowPane);

        HBox bottomRow = new HBox(10);
        Text bottomLabel = new Text("© 2024 Purdue LeWall");
        Text bottomLabelVersion = new Text("Build [ v0.0.1 ]");
        bottomLabel.getStyleClass().add("bottom-label");
        bottomLabelVersion.getStyleClass().add("bottom-label");

        Region spacer = new Region();
        bottomRow.setMaxHeight(15);
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomRow.getChildren().addAll(bottomLabelVersion, spacer, bottomLabel);
        bottomRow.setPadding(new Insets(0, 10, 10, 10));

        StackPane.setAlignment(bottomRow, Pos.BOTTOM_CENTER);
        mainStack.getChildren().add(bottomRow);

        this.getChildren().add(mainStack);
    }
}
