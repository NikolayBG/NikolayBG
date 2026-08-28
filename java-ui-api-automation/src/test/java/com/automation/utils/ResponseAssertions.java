package com.automation.utils;

import io.restassured.response.Response;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

public class ResponseAssertions {

    private ResponseAssertions() {
    }

    public static void assertStatusCode(Response response, int expectedStatus) {
        assertEquals(response.statusCode(), expectedStatus,
                "Unexpected status code. Response body: " + response.asString());
    }

    public static void assertJsonPathEquals(Response response, String jsonPath, Object expectedValue) {
        assertEquals(response.jsonPath().get(jsonPath), expectedValue,
                "Unexpected value at path '" + jsonPath + "'. Response body: " + response.asString());
    }

    public static void assertTokenPresent(Response response) {
        String token = response.jsonPath().getString("token");
        assertNotNull(token, "Expected auth token in response: " + response.asString());
        assertTrue(!token.isBlank(), "Auth token should not be blank");
    }

    public static void assertTokenMissing(Response response) {
        assertNull(response.jsonPath().getString("token"),
                "Token should not be returned. Response body: " + response.asString());
    }

    public static void assertValidCreateBookingResponse(Response response) {
        assertNotNull(response.jsonPath().get("bookingid"), "bookingid should be present");
        assertValidBookingFields(response, "booking");
    }

    public static void assertValidGetBookingResponse(Response response) {
        assertValidBookingFields(response, "");
    }

    public static void assertDeleteSuccess(Response response) {
        assertTrue(response.asString().contains("Created"),
                "Delete response should confirm success. Body: " + response.asString());
    }

    public static void assertResponseBodyContains(Response response, String expectedText) {
        assertTrue(response.asString().contains(expectedText),
                "Response body should contain '" + expectedText + "'. Body: " + response.asString());
    }

    private static void assertValidBookingFields(Response response, String rootPath) {
        String prefix = rootPath.isEmpty() ? "" : rootPath + ".";
        assertJsonPathEquals(response, prefix + "firstname", "Jim");
        assertJsonPathEquals(response, prefix + "lastname", "Brown");
        assertJsonPathEquals(response, prefix + "totalprice", 111);
        assertJsonPathEquals(response, prefix + "depositpaid", true);
        assertJsonPathEquals(response, prefix + "bookingdates.checkin", "2026-01-01");
        assertJsonPathEquals(response, prefix + "bookingdates.checkout", "2026-01-05");
        assertJsonPathEquals(response, prefix + "additionalneeds", "Breakfast");
    }
}
