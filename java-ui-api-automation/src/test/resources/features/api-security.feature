@api @security
Feature: Restful Booker authentication security tests

  Scenario: Authentication fails with invalid credentials
    When I authenticate with invalid API credentials
    Then the API response status should be 200
    And the auth response should not contain a token
    And the auth response should contain reason "Bad credentials"

  Scenario: Authentication fails with empty username
    When I authenticate with empty API username
    Then the API response status should be 200
    And the auth response should not contain a token
    And the auth response should contain reason "Bad credentials"

  Scenario: Update booking without authentication is rejected
    When I create a booking
    And I update the created booking without authentication
    Then the API response status should be 403
    And the API response should contain message "Forbidden"

  Scenario: Delete booking without authentication is rejected
    When I create a booking
    And I delete the created booking without authentication
    Then the API response status should be 403
    And the API response should contain message "Forbidden"

  Scenario: Update booking with invalid token is rejected
    Given I have an API authentication token
    And the auth response should contain a valid token
    When I create a booking
    And I update the created booking with an invalid token
    Then the API response status should be 403
    And the API response should contain message "Forbidden"
