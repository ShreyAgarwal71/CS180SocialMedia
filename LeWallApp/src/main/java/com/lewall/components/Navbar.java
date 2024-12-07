package com.lewall.components;

import com.lewall.Navigator;
import com.lewall.Navigator.EPage;
import com.lewall.api.LocalStorage;
import com.lewall.common.Theme;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Navbar extends VBox {
    private static class NavIcon extends VBox {
        public NavIcon(String label, String relativeIconPath, EPage page) {
            super(5);

            this.setAlignment(Pos.CENTER);
            this.onMouseClickedProperty().set(e -> {
                if (page.equals(Navigator.getCurrentPage())) {
                    return;
                }
                Navigator.navigateTo(page);
            });

            Image icon = new Image("imgs/" + relativeIconPath);
            ImageView iconView = new ImageView(icon);
            iconView.setFitWidth(24);
            iconView.setFitHeight(24);

            Text textLabel = new Text(label);

            if (page.equals(Navigator.getCurrentPage())) {
                textLabel.setFill(Color.web(Theme.ACCENT));
                textLabel.getStyleClass().add("nav-label-active");
            } else {
                textLabel.setFill(Color.web(Theme.TEXT_GREY));
                textLabel.getStyleClass().add("nav-label");
            }

            this.getStyleClass().add("nav-item");
            iconView.getStyleClass().add("nav-icon");

            this.getChildren().addAll(
                    iconView,
                    textLabel);
        }
    }

    public Navbar() {
        super(15);

        VBox itemsColumn = new VBox(35);

        itemsColumn.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        itemsColumn.setBackground(new Background(new BackgroundFill(
                Color.web(Theme.PRIMARY_GREY),
                new CornerRadii(3),
                null)));
        itemsColumn.setPadding(new Insets(25, 10, 25, 10));
        itemsColumn.setBorder(new Border(new BorderStroke(
                Color.web(Theme.BORDER),
                BorderStrokeStyle.SOLID,
                new CornerRadii(3),
                new BorderWidths(1))));

        // ImageView logo = new ImageView(new Image("imgs/logo.png"));
        // logo.setFitWidth(54);
        // logo.setFitHeight(28);

        itemsColumn.getChildren().addAll(
                // logo,
                new NavIcon("Inscribe", "add.png", EPage.NEWPOST),
                new NavIcon("Home", "home.png", EPage.HOME),
                new NavIcon("Explore", "search.png", EPage.SEARCH),
                new NavIcon("Profile", "user.png", EPage.PROFILE));

        itemsColumn.getStyleClass().add("navbar");

        Text logoutText = new Text("Log Out");
        logoutText.setFill(Color.web(Theme.TEXT_GREY));

        Image logoutIcon = new Image("imgs/logout.png");
        ImageView logoutIconView = new ImageView(logoutIcon);
        logoutIconView.setFitWidth(24);
        logoutIconView.setFitHeight(24);

        VBox logoutColumn = new VBox(5);
        logoutColumn.setAlignment(Pos.CENTER);
        logoutColumn.getChildren().addAll(logoutIconView, logoutText);

        logoutColumn.getStyleClass().add("logout-container");
        logoutText.getStyleClass().add("logout-text");
        logoutIconView.getStyleClass().add("logout-icon");
        logoutColumn.setCursor(Cursor.HAND);

        logoutColumn.onMouseClickedProperty().set(e -> {
            LocalStorage.clear();
            Navigator.navigateTo(EPage.LOGIN);
        });

        this.setAlignment(Pos.TOP_CENTER);
        this.setMaxWidth(70);
        this.getChildren().addAll(itemsColumn, logoutColumn);
    }
}
