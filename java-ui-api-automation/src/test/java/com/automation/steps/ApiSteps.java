package com.automation.steps;

import com.automation.api.BookingClient;
import com.automation.core.TestContext;
import com.automation.utils.ResponseAssertions;
import com.automation.utils.TestDataFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

public class ApiSteps {

    private final BookingClient bookingClient = new BookingClient();
    private final TestContext context = new TestContext();

    @Given("I have an API authentication token")
    public void authenticateWithValidCredentials() {
        Response response = bookingClient.authenticate(TestDataFactory.validCredentials());
        context.setLastResponse(response);
        context.setAuthToken(response.jsonPath().getString("token"));
        ResponseAssertions.assertTokenPresent(response);
    }

    @When("I authenticate with invalid API credentials")
    public void authenticateWithInvalidCredentials() {
        Response response = bookingClient.authenticate(TestDataFactory.invalidCredentials());
        context.setLastResponse(response);
    }

    @When("I authenticate with empty API username")
    public void authenticateWithEmptyUsername() {
        Response response = bookingClient.authenticate(TestDataFactory.emptyUsernameCredentials());
        context.setLastResponse(response);
    }

    @When("I create a booking")
    public void createBooking() {
        Response response = bookingClient.createBooking(TestDataFactory.validBooking());
        context.setLastResponse(response);
        context.setBookingId(response.jsonPath().getInt("bookingid"));
    }

    @When("I create a booking with an invalid payload")
    public void createBookingWithInvalidPayload() {
        Response response = bookingClient.createBooking(TestDataFactory.invalidBooking());
        context.setLastResponse(response);
    }

    @When("I read the created booking")
    public void readCreatedBooking() {
        Response response = bookingClient.getBooking(context.getBookingId());
        context.setLastResponse(response);
    }

    @When("I update the created booking")
    public void updateCreatedBooking() {
        Response response = bookingClient.updateBooking(
                context.getBookingId(),
                context.getAuthToken(),
                TestDataFactory.validBooking()
        );
        context.setLastResponse(response);
    }

    @When("I update the created booking without authentication")
    public void updateCreatedBookingWithoutAuth() {
        Response response = bookingClient.updateBookingWithoutAuth(
                context.getBookingId(),
                TestDataFactory.validBooking()
        );
        context.setLastResponse(response);
    }

    @When("I update the created booking with an invalid token")
    public void updateCreatedBookingWithInvalidToken() {
        Response response = bookingClient.updateBookingWithToken(
                context.getBookingId(),
                "invalid.token.value",
                TestDataFactory.validBooking()
        );
        context.setLastResponse(response);
    }

    @When("I delete the created booking")
    public void deleteCreatedBooking() {
        Response response = bookingClient.deleteBooking(context.getBookingId(), context.getAuthToken());
        context.setLastResponse(response);
    }

    @When("I delete the created booking without authentication")
    public void deleteCreatedBookingWithoutAuth() {
        Response response = bookingClient.deleteBookingWithoutAuth(context.getBookingId());
        context.setLastResponse(response);
    }

    @When("I request booking {int}")
    public void requestBookingById(int bookingId) {
        Response response = bookingClient.getBooking(bookingId);
        context.setLastResponse(response);
    }

    @Then("the API response status should be {int}")
    public void verifyApiStatusCode(int expectedStatus) {
        ResponseAssertions.assertStatusCode(context.getLastResponse(), expectedStatus);
    }

    @Then("the created booking response should match the valid booking details")
    public void verifyCreatedBookingDetails() {
        ResponseAssertions.assertValidCreateBookingResponse(context.getLastResponse());
    }

    @Then("the booking should match the valid booking details")
    public void verifyBookingDetails() {
        ResponseAssertions.assertValidGetBookingResponse(context.getLastResponse());
    }

    @Then("the delete response should confirm success")
    public void verifyDeleteSuccess() {
        ResponseAssertions.assertDeleteSuccess(context.getLastResponse());
    }

    @Then("the API response should contain message {string}")
    public void verifyResponseMessage(String expectedMessage) {
        ResponseAssertions.assertResponseBodyContains(context.getLastResponse(), expectedMessage);
    }

    @Then("the booking response should contain firstname {string}")
    public void verifyBookingResponseFirstName(String expectedFirstName) {
        ResponseAssertions.assertJsonPathEquals(
                context.getLastResponse(),
                "booking.firstname",
                expectedFirstName
        );
    }

    @Then("the booking should contain firstname {string}")
    public void verifyBookingFirstName(String expectedFirstName) {
        ResponseAssertions.assertJsonPathEquals(
                context.getLastResponse(),
                "firstname",
                expectedFirstName
        );
    }

    @Then("the auth response should contain a valid token")
    public void verifyValidAuthToken() {
        ResponseAssertions.assertTokenPresent(context.getLastResponse());
    }

    @Then("the auth response should not contain a token")
    public void verifyAuthTokenMissing() {
        ResponseAssertions.assertTokenMissing(context.getLastResponse());
    }

    @Then("the auth response should contain reason {string}")
    public void verifyAuthFailureReason(String expectedReason) {
        ResponseAssertions.assertJsonPathEquals(
                context.getLastResponse(),
                "reason",
                expectedReason
        );
    }
}
