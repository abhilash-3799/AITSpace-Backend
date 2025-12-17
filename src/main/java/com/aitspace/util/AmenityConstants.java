package com.aitspace.util;

import java.util.Arrays;
import java.util.List;

public class AmenityConstants {

    public static final List<String> ALL_AMENITIES = Arrays.asList(
            "tv", "video", "wifi", "projector", "whiteboard",
            "phone", "camera", "ac", "coffee"
    );

    public static String getAmenityDisplayName(String amenityId) {
        switch (amenityId) {
            case "tv": return "TV Screen";
            case "video": return "Video Conference";
            case "wifi": return "WiFi";
            case "projector": return "Projector";
            case "whiteboard": return "Whiteboard";
            case "phone": return "Conference Phone";
            case "camera": return "Camera";
            case "ac": return "Air Conditioning";
            case "coffee": return "Coffee Machine";
            default: return amenityId;
        }
    }

    private AmenityConstants() {}
}