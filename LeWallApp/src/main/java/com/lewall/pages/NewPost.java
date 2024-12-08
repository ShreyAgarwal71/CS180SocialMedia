package com.lewall.pages;

import java.time.format.DateTimeFormatter;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator;
import com.lewall.Navigator.NavigatorPageState;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.dtos.ClassesDTO;
import com.lewall.dtos.CreatePostDTO;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;

/**
 * A class to create a new post
 *
 * @author Ates Isfendiyaroglu, L17
 * @verion 5 December 2024
 */
public class NewPost extends Pane {
	private static final Logger logger = LogManager.getLogger(NewPost.class);
	List<String> classes;

	/**
	 * Constructor for the NewPost page
	 */
	public NewPost(NavigatorPageState state) {
		Text areaTitle = new Text("Inscribe Quote");
		areaTitle.getStyleClass().add("brand-title");

		VBox quoteGroup = new VBox(3);
		Text quoteAreaLabel = new Text("Enter Quote");
		VBox.setMargin(quoteAreaLabel, new Insets(0, 0, 0, 5));
		quoteAreaLabel.getStyleClass().add("brand-label");
		TextArea quoteArea = new TextArea();
		quoteArea.setPromptText("Write your post's body here...");
		quoteArea.setWrapText(true);
		quoteArea.getStyleClass().add("brand-text-area");
		quoteArea.setPrefHeight(75);
		quoteArea.setPrefWidth(400);
		quoteGroup.getChildren().addAll(quoteAreaLabel, quoteArea);

		VBox courseGroup = new VBox(3);

		Text courseLabel = new Text("Select Class");
		courseLabel.getStyleClass().add("brand-label");

		ComboBox<String> courseDropdown = new ComboBox<String>();
		courseDropdown.setPromptText("Select a class...");
		courseDropdown.setPrefWidth(400);
		courseDropdown.getStyleClass().add("brand-dropdown");
		if (LocalStorage.get("/post/getClasses") == null)
			Connection.get("/post/getClasses", true).thenAccept(response -> {
				classes = LocalStorage.get("/post/getClasses", ClassesDTO.class).getClasses();
				Platform.runLater(() -> {
					courseDropdown.getItems().addAll(classes);
				});
			});
		else {
			classes = LocalStorage.get("/post/getClasses", ClassesDTO.class).getClasses();
			courseDropdown.getItems().addAll(classes);
		}
		courseGroup.getChildren().addAll(courseLabel, courseDropdown);

		Button submitButton = new Button("Inscribe Quote");
		submitButton.getStyleClass().add("accent-button");
		submitButton.setPrefWidth(400);
		submitButton.setOnAction(e -> {
			// Stringitle = titleField.getText();
			String body = quoteArea.getText();

			// Sends date in UTC
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			String date = dtf.format(Instant.now().atZone(java.time.ZoneId.of("UTC")));

			String selectedClass = courseDropdown.getValue();

			if (!body.isEmpty() && !selectedClass.isEmpty() && selectedClass != null) {
				logger.info(
						"New post created: Body = {}, Date = {}, Class = {}",
						body, date, selectedClass);
				// TODO: IMG-URL

				CreatePostDTO post = new CreatePostDTO(body, date, null, "Class");
				Connection.post("/post/create", post).thenAccept(response -> {
					Platform.runLater(() -> {
						Navigator.navigateTo(Navigator.EPage.PROFILE);
					});
				});
			} else {
				logger.warn("Post creation failed: Title or Body is empty");
			}
		});

		// Graphical stuff
		this.getStyleClass().add("primary-bg");

		VBox navbar = new Navbar();
		StackPane.setAlignment(navbar, Pos.TOP_LEFT);
		StackPane.setMargin(navbar, new Insets(10));

		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.BLACK);
		shadow.setOffsetX(0);
		shadow.setOffsetY(0);
		shadow.setRadius(15);
		shadow.setSpread(0.5);

		FlowPane fp = new FlowPane(10, 10);
		fp.prefWidthProperty().bind(this.widthProperty());
		fp.prefHeightProperty().bind(this.heightProperty());
		fp.setAlignment(Pos.TOP_LEFT);
		fp.setOrientation(Orientation.VERTICAL);

		VBox postForm = new VBox(10);
		FlowPane.setMargin(postForm, new Insets(10, 0, 0, 90));
		postForm.setAlignment(Pos.TOP_LEFT);
		postForm.getChildren().addAll(areaTitle, courseGroup, quoteGroup, submitButton);

		fp.getChildren().add(postForm);

		Group group = new Group();
		group.setEffect(shadow);
		group.getChildren().add(fp);

		HBox footer = new Footer();
		StackPane.setAlignment(footer, Pos.BOTTOM_CENTER);

		StackPane mainStack = new StackPane();
		mainStack.getChildren().addAll(group, fp, navbar, footer);

		this.getChildren().add(mainStack);
	}
}
