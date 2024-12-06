package com.lewall.common;

import javafx.scene.text.Font;

public class Theme {
    public static final String ACCENT = "#898BEF";
    public static final String PRIMARY_GREY = "#0E0E0E";
    public static final String TEXT_GREY = "#858585";
    public static final String BORDER = "#ffffff06";

    public static final Font INRIA_SERIF = Font
            .loadFont(Theme.class.getResourceAsStream("/fonts/InriaSerif-Regular.ttf"), 16);

    public static final Font INRIA_SERIF_SMALL = Font
            .loadFont(Theme.class.getResourceAsStream("/fonts/InriaSerif-Regular.ttf"), 12);
}
