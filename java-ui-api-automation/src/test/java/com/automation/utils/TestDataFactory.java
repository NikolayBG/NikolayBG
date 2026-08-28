package com.automation.utils;

import java.util.HashMap;
import java.util.Map;

public class TestDataFactory {

    private TestDataFactory() {
    }

    public static Map<String, Object> validBooking() {
        Map<String, Object> booking = new HashMap<>();
        booking.put("firstname", "Jim");
        booking.put("lastname", "Brown");
        booking.put("totalprice", 111);
        booking.put("depositpaid", true);
        booking.put("bookingdates", Map.of(
                "checkin", "2026-01-01",
                "checkout", "2026-01-05"
        ));
        booking.put("additionalneeds", "Breakfast");
        return booking;
    }

    public static Map<String, Object> invalidBooking() {
        return Map.of("firstname", "OnlyName");
    }

    public static Map<String, String> validCredentials() {
        return Map.of(
                "username", com.automation.core.ConfigReader.get("api.username"),
                "password", com.automation.core.ConfigReader.get("api.password")
        );
    }

    public static Map<String, String> invalidCredentials() {
        return Map.of("username", "wrong_user", "password", "wrong_password");
    }

    public static Map<String, String> emptyUsernameCredentials() {
        return Map.of("username", "", "password", "password123");
    }
}
