package com.lewall.pages;

import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.components.Navbar;
import com.lewall.dtos.ClassesDTO;
import com.lewall.dtos.CreatePostDTO;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;

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
		TextField titleField = new TextField();
		titleField.setPromptText("Write your title here...");
		titleField.getStyleClass().add("brand-field");

		TextArea bodyArea = new TextArea();
		bodyArea.setPromptText("Write your post's body here...");
		bodyArea.setWrapText(true);
		bodyArea.getStyleClass().add("brand-field");
		// TODO: CSS

		// I love null-unsafe code

		if (LocalStorage.get("/post/getClasses") == null)
			Connection.get("/post/getClasses", true).thenAccept(response -> {
				classes = LocalStorage.get("/post/getClasses", ClassesDTO.class).getClasses();
			});
		else
			classes = LocalStorage.get("/post/getClasses", ClassesDTO.class).getClasses();


		ComboBox<String> courseDropdown = new ComboBox<String>();
		courseDropdown.getItems().addAll(classes);
		// TODO: CSS

		Button submitButton = new Button("Submit");
		submitButton.getStyleClass().add("brand-button");
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

		// Graphical stuff
		this.getStyleClass().add("primary-bg");

		VBox navbar = new Navbar();
        StackPane.setAlignment(navbar, Pos.TOP_LEFT);
        StackPane.setMargin(navbar, new Insets(10));

		Rectangle postBox = new Rectangle(235, 300);
        postBox.setFill(new Color(0, 0, 0, 0));
        postBox.setStroke(Color.rgb(255, 255, 255, 0.05));
        postBox.setStrokeWidth(1);
        postBox.setArcWidth(10);
        postBox.setArcHeight(10);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.BLACK);
        shadow.setOffsetX(0);
        shadow.setOffsetY(0);
        shadow.setRadius(15);
        shadow.setSpread(0.5);

        Group group = new Group();
        group.setEffect(shadow);
        group.getChildren().add(postBox);

		FlowPane fp = new FlowPane(10, 10);
        fp.prefWidthProperty().bind(this.widthProperty());
        fp.prefHeightProperty().bind(this.heightProperty());
        fp.setAlignment(Pos.CENTER);
        fp.setOrientation(Orientation.VERTICAL);

		HBox lastLine = new HBox(2);
		lastLine.setAlignment(Pos.CENTER);
		lastLine.getChildren().addAll(courseDropdown, submitButton);

		VBox postForm = new VBox(3);
		postForm.setAlignment(Pos.CENTER);
		postForm.getChildren().addAll(titleField, bodyArea, lastLine);

		fp.getChildren().add(postForm);

		StackPane mainStack = new StackPane();
		mainStack.getChildren().addAll(group, fp, navbar);

		this.getChildren().add(mainStack);

		logger.info("Finished loading page: NEWPOST");
	}
}
