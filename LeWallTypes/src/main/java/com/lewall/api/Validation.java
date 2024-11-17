package com.lewall.api;

import java.util.regex.Pattern;

/**
 * This class contains methods to validate user input
 * 
 * @Author Zayan and Mahit Mehta
 * @version 11-16-2024
 */
public class Validation {
    /**
     * Checks if the given email is in the proper email format (___@___.___)
     * 
     * @param email
     *            the email to be checked
     * @return true if the email is in the proper format, false otherwise
     */
    public static boolean isEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        return emailPattern.matcher(email).matches();
    }

    /**
     * Checks if the given password is secure (8 chars long, one upper letter, one
     * lower letter, one special character)
     * 
     * @param password
     *            the password to be checked
     * @return true if the password is secure, false otherwise
     */
    public static boolean isSecurePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        return passwordPattern.matcher(password).matches();
    }
}
