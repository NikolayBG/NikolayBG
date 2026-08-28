package com.automation.pages;

import com.automation.core.BasePage;
import com.automation.core.ConfigReader;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By inventoryTitle = By.cssSelector(".title");

    public void open() {
        driver.get(ConfigReader.get("ui.base.url"));
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isOnProductsPage() {
        return isDisplayed(inventoryTitle) && getText(inventoryTitle).equals("Products");
    }
}
