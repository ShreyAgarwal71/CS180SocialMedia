package com.lewall;

import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.interfaces.IScheduledComponent;
import com.lewall.pages.Home;
import com.lewall.pages.Login;
import com.lewall.pages.NewPost;
import com.lewall.pages.Profile;
import com.lewall.pages.Register;
import com.lewall.pages.Search;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Navigator class for the application
 * 
 * @version 17 November 2024
 */
public class Navigator {
	private static final Logger logger = LogManager.getLogger(Navigator.class);

	private static Object mainLock = new Object();

	private static Stack<Page> history = new Stack<>();

	private static Stage stage;

	private static class Page {
		private EPage page;
		private Scene scene;

		public Page(EPage page, Scene scene) {
			this.page = page;
			this.scene = scene;
		}

		public EPage getPage() {
			return page;
		}

		public Scene getScene() {
			return scene;
		}
	}

	public static class NavigatorPageState {
		private Object state;

		public NavigatorPageState() {
			this.state = null;
		}

		public NavigatorPageState(Object state) {
			this.state = state;
		}

		public Object getState() {
			return state;
		}
	}

	public enum EPage {
		LOGIN, REGISTER, HOME, PROFILE, SEARCH, UPLOAD, EXPLORE, NEWPOST
	}

	/**
	 * Set the stage for the application
	 * 
	 * @param stage
	 *            the stage to set
	 */
	public static void setStage(Stage stage) {
		Navigator.stage = stage;
	}

	/**
	 * Get the current page {@link EPage} enum value
	 * 
	 * @return the current stage page enum, returns null if no page is set
	 */
	public static EPage getCurrentPage() {
		if (history.size() == 0) {
			return null;
		}

		return history.peek().getPage();
	}

	/**
	 * Navigate back to the previous scene
	 * 
	 * @return true if successful, false otherwise
	 */
	public static boolean back() {
		synchronized (mainLock) {
			if (stage == null) {
				logger.error("Stage not set");
				return false;
			}

			if (history.size() > 1) {
				history.pop();
				updateScene();

				return true;
			}

			logger.error("No previous scene");
			return false;
		}
	}

	public static boolean navigateTo(EPage page) {
		return navigateTo(page, new NavigatorPageState());
	}

	/**
	 * Navigate to a specific page
	 * 
	 * @param page
	 *            the page to navigate to
	 * @return true if successful, false otherwise
	 */
	public static boolean navigateTo(EPage page, NavigatorPageState state) {
		if (stage == null) {
			logger.error("Stage not set");
			return false;
		}

		synchronized (mainLock) {
			if (stage == null) {
				logger.error("Stage not set");
				return false;
			}

			Scene scene = null;

			if (history.size() > 0) {
				if (history.peek().scene.getRoot() instanceof IScheduledComponent) {
					((IScheduledComponent) history.peek().scene.getRoot()).shutdownPolling();
				}
			}

			history.push(new Page(page, scene));

			switch (page) {
				case LOGIN -> {
					scene = new Scene(new Login(state));
				}
				case REGISTER -> {
					scene = new Scene(new Register(state));
				}
				case HOME -> {
					scene = new Scene(new Home(state));
				}
				case PROFILE -> {
					scene = new Scene(new Profile(state));
				}
				case SEARCH -> {
					scene = new Scene(new Search(state));
				}
				case NEWPOST -> {
					scene = new Scene(new NewPost(state));
				}
				default -> {
					logger.error("Unimplemented Page: " + page);
					return false;
				}
			}

			history.peek().scene = scene;

			scene.getStylesheets().add("css/global.css");
			scene.setFill(Color.rgb(0, 0, 0, 0));

			updateScene();
			return true;
		}
	}

	/**
	 * Update the scene to the most recent scene
	 */
	private static void updateScene() {
		final Scene scene = history.peek().getScene();
		stage.setScene(scene);
	}
}
