package com.lewall.pages;

import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.dtos.ClassesDTO;

import javafx.scene.layout.Pane;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * A class to create a new post
 *
 * @author Ates Isfendiyaroglu, L17
 * @verion 5 December 2024
 */
public class NewPost extends Pane {
	private static final Logger logger = LogManager.getLogger(Register.class);

	/**
	 * Constructor for the NewPost page
	 */
	public NewPost() {
		// Create UI components
		Label titleLabel = new Label("Title:");
		TextField titleField = new TextField();
		titleField.setPromptText("...");

		Label bodyLabel = new Label("Body:");
		TextArea bodyArea = new TextArea();
		bodyArea.setPromptText("...");
		bodyArea.setWrapText(true);

		// I love null-unsafe code
		UUID[] classes = null;

		// TODO: Courses is not the valid endpoint
		if (LocalStorage.get("Courses") == null) {
			Connection.get("/idk", true);
		} else {
			classes = LocalStorage.get("Courses", ClassesDTO.getclass());
		}

		Label courseLabel = new Label("Classes:");
		ComboBox<UUID> courseDropdown = new ComboBox<UUID>();
		courseDropdown.getItems().addAll(classes);

		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> {
			String title = titleField.getText();
			String body = bodyArea.getText();
			// The server determines the date
			String date = null;

			if (!title.isEmpty() && !body.isEmpty()) {
				logger.info("New post created: Title = {}, Body = {}", title, body);
				// Add logic to handle the new post creation here
			} else {
				logger.warn("Post creation failed: Title or Body is empty");
			}
		});

		// TODO: create layout or smth idk
	}
}
