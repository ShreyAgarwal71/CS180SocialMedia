package com.lewall.pages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

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

		// TODO: CHANGE THIS WHENEVER UUID REQUESTS GETS AVAILABLE!!!
		ArrayList<UUID> classes = new ArrayList<>(5);

		Label courseLabel = new Label("Classes:");
		ComboBox courseDropdown = new ComboBox<>(null);

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

		// Layout setup
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(20));
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		gridPane.add(titleLabel, 0, 0);
		gridPane.add(titleField, 1, 0);
		gridPane.add(bodyLabel, 0, 1);
		gridPane.add(bodyArea, 1, 1);
		gridPane.add(submitButton, 1, 2);

		// Add the gridPane to the NewPost Pane
		VBox layout = new VBox(10, gridPane);
		layout.setPadding(new Insets(20));

		this.getChildren().add(layout);
	}
}
