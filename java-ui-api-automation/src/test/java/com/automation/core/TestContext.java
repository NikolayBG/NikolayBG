package com.automation.core;

import io.restassured.response.Response;

/**
 * Stores data shared between API steps in the same scenario.
 * A new instance is created per scenario through Cucumber step definitions.
 */
public class TestContext {

    private Response lastResponse;
    private int bookingId;
    private String authToken;

    public Response getLastResponse() {
        return lastResponse;
    }

    public void setLastResponse(Response lastResponse) {
        this.lastResponse = lastResponse;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
