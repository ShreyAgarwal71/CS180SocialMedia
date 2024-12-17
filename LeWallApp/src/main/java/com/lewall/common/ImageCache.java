package com.lewall.common;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

public class ImageCache {
    private static final Map<String, Image> cache = new HashMap<>();

    public static Image getImage(String url) {
        return cache.computeIfAbsent(url, key -> new Image(key, true));
    }
}