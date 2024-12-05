package com.lewall.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lewall.Navigator;
import com.lewall.api.Connection;
import com.lewall.api.LocalStorage;
import com.lewall.api.Validation;
import com.lewall.dtos.AuthTokenDTO;
import com.lewall.dtos.LoginDTO;
import com.lewall.dtos.UserDTO;
import com.lewall.components.Footer;
import com.lewall.components.Navbar;
import com.lewall.components.PasswordField;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.animation.RotateTransition;
import javafx.animation.Interpolator;
import javafx.util.Duration;




public class Profile extends Pane {
    private static final Logger logger = LogManager.getLogger(Login.class);
    
    public Profile() {
        this.getStyleClass().add("primary-bg");

        VBox navbar = new Navbar();
        StackPane.setAlignment(navbar, Pos.TOP_LEFT);
        StackPane.setMargin(navbar, new Insets(10));

        Rectangle profileCard = new Rectangle(250, 120);
        

        StackPane mainStack = new StackPane();
        mainStack.getChildren().add(navbar);
        this.getChildren().add(mainStack);
    }
}
