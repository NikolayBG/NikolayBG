package com.automation.api;

import com.automation.core.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class BookingClient {

    public BookingClient() {
        RestAssured.baseURI = ConfigReader.get("api.base.url");
    }

    public Response authenticate(Map<String, String> credentials) {
        return jsonRequest()
                .body(credentials)
                .post("/auth");
    }

    public Response createBooking(Map<String, Object> bookingBody) {
        return jsonRequest()
                .body(bookingBody)
                .post("/booking");
    }

    public Response getBooking(int bookingId) {
        return baseRequest()
                .get("/booking/" + bookingId);
    }

    public Response updateBooking(int bookingId, String token, Map<String, Object> bookingBody) {
        return authenticatedRequest(token)
                .body(bookingBody)
                .put("/booking/" + bookingId);
    }

    public Response deleteBooking(int bookingId, String token) {
        return authenticatedRequest(token)
                .delete("/booking/" + bookingId);
    }

    public Response updateBookingWithoutAuth(int bookingId, Map<String, Object> bookingBody) {
        return jsonRequest()
                .body(bookingBody)
                .put("/booking/" + bookingId);
    }

    public Response deleteBookingWithoutAuth(int bookingId) {
        return baseRequest()
                .delete("/booking/" + bookingId);
    }

    public Response updateBookingWithToken(int bookingId, String token, Map<String, Object> bookingBody) {
        return jsonRequest()
                .cookie("token", token)
                .body(bookingBody)
                .put("/booking/" + bookingId);
    }

    private RequestSpecification baseRequest() {
        return RestAssured.given();
    }

    private RequestSpecification jsonRequest() {
        return baseRequest().contentType("application/json");
    }

    private RequestSpecification authenticatedRequest(String token) {
        return jsonRequest().cookie("token", token);
    }
}
