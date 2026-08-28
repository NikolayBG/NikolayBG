@ui
Feature: SauceDemo UI tests

  @smoke
  Scenario: Successful login
    Given I open the SauceDemo login page
    When I log in with valid credentials
    Then I should see the products page

  Scenario Outline: Invalid login attempts
    Given I open the SauceDemo login page
    When I log in with username "<username>" and password "<password>"
    Then I should see login error containing "<expected_error>"
    Examples:
      | username      | password      | expected_error                         |
      | wrong         | wrong         | Username and password do not match     |
      | standard_user | wrong         | Username and password do not match     |
      | locked_out_user | secret_sauce | Sorry, this user has been locked out. |

  @smoke
  Scenario: Purchase a backpack
    Given I open the SauceDemo login page
    When I log in with valid credentials
    And I add the backpack to the cart
    And I open the cart
    Then the cart should contain "Sauce Labs Backpack"
    When I checkout with "John", "Smith", "1000"
    Then the order should be completed

  Scenario: Remove item from cart
    Given I open the SauceDemo login page
    When I log in with valid credentials
    And I add the backpack to the cart
    Then the cart badge should show "1" item
    When I remove the backpack from the cart
    Then the cart badge should not be visible

  Scenario: Sort products by price low to high
    Given I open the SauceDemo login page
    When I log in with valid credentials
    And I sort products by price from low to high
    Then products should be sorted by price from low to high

  Scenario: Checkout validation message
    Given I open the SauceDemo login page
    When I log in with valid credentials
    And I add the backpack to the cart
    And I open the cart
    When I start checkout with first name only "John"
    Then I should see checkout error containing "Last Name is required"
