package com.automation.steps;

import com.automation.core.ConfigReader;
import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UiSteps {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @Given("I open the SauceDemo login page")
    public void openLoginPage() {
        loginPage = new LoginPage();
        loginPage.open();
    }

    @When("I log in with username {string} and password {string}")
    public void loginWithCredentials(String username, String password) {
        loginPage.login(username, password);
    }

    @When("I log in with valid credentials")
    public void loginWithValidCredentials() {
        loginPage.login(ConfigReader.get("ui.username"), ConfigReader.get("ui.password"));
    }

    @Then("I should see the products page")
    public void verifyProductsPage() {
        inventoryPage = new InventoryPage();
        assertEquals(inventoryPage.getTitle(), "Products");
    }

    @Then("I should see login error containing {string}")
    public void verifyLoginError(String expectedMessage) {
        assertTrue(loginPage.getErrorMessage().contains(expectedMessage),
                "Expected error to contain: " + expectedMessage);
    }

    @When("I add the backpack to the cart")
    public void addBackpackToCart() {
        inventoryPage = new InventoryPage();
        inventoryPage.addBackpackToCart();
    }

    @When("I remove the backpack from the cart")
    public void removeBackpackFromCart() {
        inventoryPage = new InventoryPage();
        inventoryPage.removeBackpackFromCart();
    }

    @When("I open the cart")
    public void openCart() {
        inventoryPage = new InventoryPage();
        inventoryPage.openCart();
        cartPage = new CartPage();
    }

    @Then("the cart should contain {string}")
    public void verifyCartContainsItem(String itemName) {
        assertEquals(cartPage.getFirstItemName(), itemName);
    }

    @Then("the cart badge should show {string} item")
    public void verifyCartBadgeCount(String expectedCount) {
        inventoryPage = new InventoryPage();
        assertEquals(inventoryPage.getCartItemCount(), expectedCount);
    }

    @Then("the cart badge should not be visible")
    public void verifyCartBadgeNotVisible() {
        inventoryPage = new InventoryPage();
        assertFalse(inventoryPage.isCartBadgeVisible(), "Cart badge should not be visible");
    }

    @When("I sort products by price from low to high")
    public void sortProductsByPriceLowToHigh() {
        inventoryPage = new InventoryPage();
        inventoryPage.sortByPriceLowToHigh();
    }

    @Then("products should be sorted by price from low to high")
    public void verifyProductsSortedByPrice() {
        inventoryPage = new InventoryPage();
        List<Double> prices = inventoryPage.getVisibleProductPrices();
        for (int index = 1; index < prices.size(); index++) {
            assertTrue(prices.get(index) >= prices.get(index - 1),
                    "Products are not sorted from low to high: " + prices);
        }
    }

    @When("I checkout with {string}, {string}, {string}")
    public void checkoutWithDetails(String firstName, String lastName, String postalCode) {
        cartPage.proceedToCheckout();
        checkoutPage = new CheckoutPage();
        checkoutPage.fillShippingDetails(firstName, lastName, postalCode);
        checkoutPage.finishOrder();
    }

    @When("I start checkout with first name only {string}")
    public void startCheckoutWithFirstNameOnly(String firstName) {
        cartPage.proceedToCheckout();
        checkoutPage = new CheckoutPage();
        checkoutPage.fillOnlyFirstName(firstName);
    }

    @Then("the order should be completed")
    public void verifyOrderCompleted() {
        assertEquals(checkoutPage.getCompletionMessage(), "Thank you for your order!");
    }

    @Then("I should see checkout error containing {string}")
    public void verifyCheckoutError(String expectedMessage) {
        assertTrue(checkoutPage.getErrorMessage().contains(expectedMessage),
                "Expected checkout error to contain: " + expectedMessage);
    }
}
