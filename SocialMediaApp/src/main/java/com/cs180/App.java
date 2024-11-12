package com.cs180;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.cs180.api.Connection;
import com.cs180.dtos.AuthTokenDTO;
import com.cs180.dtos.LoginDTO;
import com.cs180.dtos.CreateUserDTO;
import com.formdev.flatlaf.util.SystemInfo;

/**
 * AppClient is the main class for the Social Media App client.
 * 
 * @author Mahit Mehta
 * @version 2024-11-06
 * 
 */
public class App implements Runnable {
    private static final Logger logger = LogManager.getLogger(App.class);

    private void start() {
        if (!Connection.connect()) {
            logger.error("Initial Server Connection Failed");
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
                    .<CreateUserDTO, AuthTokenDTO>post("/auth/register",
                            new CreateUserDTO("mahitm", "1234", "Mahit Mehta", "I am a student", "mathit@gmail.com"))
                    .thenAccept(response -> {
                        logger.debug("Body: " + response.getBody().getToken());
                    }).exceptionally(e -> {
                        logger.error(e.getMessage());
                        return null;
                    });
        });

        JButton loginButton = new javax.swing.JButton("Login");
        loginButton.addActionListener(_ -> {
            Connection.<LoginDTO, AuthTokenDTO>post("/auth/login", new LoginDTO("mahit.py@gmail.com", "1234"))
                    .thenAccept(response -> {
                        logger.debug("Body: " + response.getBody().getToken());
                    }).exceptionally(e -> {
                        logger.error(e.getMessage());
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
