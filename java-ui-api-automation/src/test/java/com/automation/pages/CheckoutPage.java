package com.automation.pages;

import com.automation.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CheckoutPage extends BasePage {

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By completeHeader = By.cssSelector(".complete-header");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public void fillShippingDetails(String firstName, String lastName, String postalCode) {
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(postalCodeField, postalCode);
        click(continueButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-two"));
    }

    public void fillOnlyFirstName(String firstName) {
        type(firstNameField, firstName);
        click(continueButton);
    }

    public void submitWithoutDetails() {
        click(continueButton);
    }

    public void finishOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(finishButton));
        click(finishButton);
    }

    public String getCompletionMessage() {
        return getText(completeHeader);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
