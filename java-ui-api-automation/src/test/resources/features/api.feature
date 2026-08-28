@api
Feature: Restful Booker API tests

  @smoke
  Scenario: Create and read a booking
    When I create a booking
    Then the API response status should be 200
    And the created booking response should match the valid booking details
    When I read the created booking
    Then the API response status should be 200
    And the booking should match the valid booking details

  Scenario: Update a booking
    Given I have an API authentication token
    And the auth response should contain a valid token
    When I create a booking
    And I update the created booking
    Then the API response status should be 200
    And the booking should match the valid booking details

  Scenario: Delete a booking
    Given I have an API authentication token
    And the auth response should contain a valid token
    When I create a booking
    And I delete the created booking
    Then the API response status should be 201
    And the delete response should confirm success

  Scenario: Non-existent booking
    When I request booking 999999999
    Then the API response status should be 404
    And the API response should contain message "Not Found"

  Scenario: Invalid booking payload
    When I create a booking with an invalid payload
    Then the API response status should be 500
    And the API response should contain message "Internal Server Error"
