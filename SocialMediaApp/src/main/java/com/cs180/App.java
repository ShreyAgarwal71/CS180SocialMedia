package com.cs180;

import java.awt.BorderLayout;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;

import javax.lang.model.type.NullType;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.cs180.api.Connection;
import com.cs180.api.Response;
import com.cs180.dtos.AuthTokenDTO;
import com.cs180.dtos.LoginDTO;
import com.formdev.flatlaf.util.SystemInfo;
import com.google.gson.reflect.TypeToken;

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

        javax.swing.JButton button = new javax.swing.JButton("Login");
        button.addActionListener(_ -> {
            try {
                Connection.<LoginDTO, String>post("/auth/login", new LoginDTO("mahit.py@gmail.com", "1234"))
                        .thenAccept(response -> {
                            System.out.println("Body: " + response.getBody());
                        }).exceptionally(e -> {
                            e.printStackTrace();
                            System.out.println("Failed to process request.");
                            return null;
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        button.setForeground(new java.awt.Color(255, 255, 255));

        panel.add(button);
        frame.getContentPane().setLayout(new java.awt.BorderLayout());
        frame.getContentPane().add(panel, BorderLayout.CENTER);

        frame.setSize(600, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
