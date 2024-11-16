package com.lewall.api;

import java.util.regex.Pattern;

public class Validation {
    public static boolean isEmail(String email) {
        // has to be proper email format: ___@___.___
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        return emailPattern.matcher(email).matches();
    }

    public static boolean isSecurePassword(String password) {
        // 8 chars long, one upper letter, one lower letter, one special character
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        return passwordPattern.matcher(password).matches();
    }
}
