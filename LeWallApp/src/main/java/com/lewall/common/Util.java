package com.lewall.common;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Util {
    public static String formatDateString(String date) {
        String[] splitDate = date.trim().split("/");
        HashMap<String, String> months = new HashMap<>(12);
        months.put("01", "Jan.");
        months.put("02", "Feb.");
        months.put("03", "Mar.");
        months.put("04", "Apr.");
        months.put("05", "May.");
        months.put("06", "Jun.");
        months.put("07", "Jul.");
        months.put("08", "Aug.");
        months.put("09", "Sep.");
        months.put("10", "Oct.");
        months.put("11", "Nov.");
        months.put("12", "Dec.");

        return String.format("Established %s %s, %s", months.get(splitDate[0]), splitDate[1], splitDate[2]);
    }

    public static boolean isImageURLValid(String imageURL) {
        try {
            Image image = new Image(imageURL, true);
            new ImageView(image);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
