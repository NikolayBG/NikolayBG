package com.automation.pages;

import com.automation.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By cartItemName = By.cssSelector(".inventory_item_name");
    private final By emptyCartMessage = By.cssSelector(".cart_desc_label");

    public String getFirstItemName() {
        return getText(cartItemName);
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        click(checkoutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }

    public void continueShopping() {
        click(continueShoppingButton);
    }

    public boolean isCartEmpty() {
        return getText(emptyCartMessage).contains("cart is empty");
    }
}
