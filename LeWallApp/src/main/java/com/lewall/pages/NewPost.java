package com.lewall.pages;

import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.dtos.ClassesDTO;
import com.lewall.dtos.CreatePostDTO;

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
	List<String> classes;

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

		if (LocalStorage.get("/post/getClasses") == null)
			Connection.get("/post/getClasses", true).thenAccept(response -> {
				classes = LocalStorage.get("/post/getClasses", ClassesDTO.class).getClasses();
			});
		else
			classes = LocalStorage.get("/post/getClasses", ClassesDTO.class).getClasses();


		Label courseLabel = new Label("Classes:");
		ComboBox<String> courseDropdown = new ComboBox<String>();
		courseDropdown.getItems().addAll(classes);

		Button submitButton = new Button("Submit");
		submitButton.setOnAction(e -> {
			String title = titleField.getText();
			String body = bodyArea.getText();

			// Sends date in UTC
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String date = dtf.format(Instant.now().atZone(java.time.ZoneId.of("UTC")));

			String selectedClass = courseDropdown.getValue();


			if (!title.isEmpty() && !body.isEmpty() && !selectedClass.isEmpty() && selectedClass != null) {
				logger.info(
							"New post created: Title = {}, Body = {}, Date = {}, Class = {}", 
							title, body, date, selectedClass);
				// TODO: IMG-URL
				CreatePostDTO post = new CreatePostDTO(body, date, null, UUID.fromString(selectedClass));
				Connection.post("/post/create", post);
			} else {
				logger.warn("Post creation failed: Title or Body is empty");
			}
		});

		// TODO: create layout or smth idk
	}
}
