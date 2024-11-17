package com.lewall.components;

import javafx.scene.control.TextField;

public class PasswordField extends TextField {
    private String password = "";
    private boolean isMasked = true;

    public PasswordField() {
        this.setOnKeyTyped(e -> {
            String input = this.getText();

            if (input.length() < password.length()) {
                password = password.substring(0, password.length() - 1);
            } else if (input.length() > password.length()) {
                password += input.charAt(input.length() - 1);
            }

            StringBuilder maskedInput = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                maskedInput.append(isMasked ? "•" : input.charAt(i));
            }
            this.setText(maskedInput.toString());
            this.positionCaret(maskedInput.length());
        });
        this.setFocusTraversable(false);
        this.getStyleClass().add("brand-field");
        this.setMaxSize(200, 30);
        this.setPromptText("Password");
    }

    public String getPassword() {
        return password;
    }
}
