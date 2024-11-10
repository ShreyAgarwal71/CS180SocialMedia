package com.cs180;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.cs180.api.Connection;
import com.cs180.dtos.AuthTokenDTO;
import com.cs180.dtos.LoginDTO;
import com.cs180.dtos.SignUpDTO;
import com.formdev.flatlaf.util.SystemInfo;

/**
 * AppClient is the main class for the Social Media App client.
 * 
 * @author Mahit Mehta
 * @version 2024-11-06
 * 
 */
public class App implements Runnable {
    private void start() {
        try {
            Connection.connect();
        } catch (IOException e) {
            System.out.println("Failed to connect to server");
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) {
        if (SystemInfo.isMacOS) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("apple.awt.application.name", "Social Media UI");
            System.setProperty("apple.awt.application.appearance", "NSAppearanceNameDarkAqua");
        }

        SwingUtilities.invokeLater(new App());

        App app = new App();
        app.start();
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Social Media UI");
        frame.getContentPane().setBackground(new java.awt.Color(11, 11, 11));

        JPanel panel = new JPanel();
        panel.setBackground(new java.awt.Color(11, 11, 11));

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(_ -> {
            Connection
                    .<SignUpDTO, AuthTokenDTO>post("/auth/register",
                            new SignUpDTO("mahit.py@gmail.com", "1234", "mahitm"))
                    .thenAccept(response -> {
                        System.out.println("Body: " + response.getBody().getToken());
                    }).exceptionally(e -> {
                        System.out.println(e.getMessage());
                        return null;
                    });
        });

        JButton loginButton = new javax.swing.JButton("Login");
        loginButton.addActionListener(_ -> {
            Connection.<LoginDTO, AuthTokenDTO>post("/auth/login", new LoginDTO("mahit.py@gmail.com", "1234"))
                    .thenAccept(response -> {
                        System.out.println("Body: " + response.getBody().getToken());
                    }).exceptionally(e -> {
                        System.out.println(e.getMessage());
                        return null;
                    });
        });
        registerButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));

        panel.add(registerButton);
        panel.add(loginButton);
        frame.getContentPane().setLayout(new java.awt.BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
