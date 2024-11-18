package com.lewall.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class PasswordField extends StackPane {
    private static final Logger logger = LogManager.getLogger(PasswordField.class);

    private String password = "";
    private boolean isMasked = true;

    public PasswordField() {
        TextField field = new TextField();
        field.setOnKeyTyped(e -> {
            String input = field.getText();
            int opPosition = field.getCaretPosition();

            if (input.length() == 0) {
                password = "";
            } else if (input.length() < password.length()) {
                String newPassword = password.substring(0, opPosition);
                if (opPosition < password.length()) {
                    newPassword += password.substring(opPosition + 1);
                }

                password = newPassword;
            } else if (input.length() > password.length()) {
                String newPassword = password.substring(0, opPosition - 1) + input.charAt(opPosition - 1);
                if (opPosition - 1 < password.length()) {
                    newPassword += password.substring(opPosition - 1);
                }
                password = newPassword;
            }

            StringBuilder maskedInput = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                maskedInput.append(isMasked ? "•" : input.charAt(i));
            }
            field.setText(maskedInput.toString());
            field.positionCaret(opPosition);
        });
        field.setFocusTraversable(false);
        field.getStyleClass().add("brand-field");
        field.setMaxSize(200, 30);
        field.setPromptText("Password");

        Image eyeReveal = new Image("imgs/eye-reveal.png");
        Image eyeHide = new Image("imgs/eye-hide.png");
        ImageView eye = new ImageView(eyeReveal);
        eye.setCursor(Cursor.HAND);
        eye.setFitWidth(16);
        eye.setFitHeight(16);
        eye.setPreserveRatio(true);
        StackPane.setMargin(eye, new Insets(0, 30, 0, 0));

        eye.onMouseClickedProperty().set(e -> {
            if (isMasked) {
                eye.setImage(eyeHide);
                field.setText(password);
            } else {
                eye.setImage(eyeReveal);
                StringBuilder maskedInput = new StringBuilder();
                for (int i = 0; i < field.getText().length(); i++) {
                    maskedInput.append("•");
                }
                field.setText(maskedInput.toString());
            }
            field.positionCaret(field.getText().length());

            isMasked = !isMasked;
        });

        StackPane.setAlignment(eye, Pos.CENTER_RIGHT);

        getChildren().add(field);
        getChildren().add(eye);
    }

    public String getPassword() {
        return password;
    }
}
