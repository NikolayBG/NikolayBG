package com.automation.pages;

import com.automation.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {

    private final By pageTitle = By.cssSelector(".title");
    private final By addBackpackButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By removeBackpackButton = By.id("remove-sauce-labs-backpack");
    private final By cartLink = By.cssSelector(".shopping_cart_link");
    private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    private final By productPrices = By.cssSelector(".inventory_item_price");

    public String getTitle() {
        return getText(pageTitle);
    }

    public void addBackpackToCart() {
        click(addBackpackButton);
    }

    public void removeBackpackFromCart() {
        click(removeBackpackButton);
    }

    public void openCart() {
        click(cartLink);
    }

    public String getCartItemCount() {
        return getText(cartBadge);
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public void sortByPriceLowToHigh() {
        Select select = new Select(wait.until(org.openqa.selenium.support.ui.ExpectedConditions
                .elementToBeClickable(sortDropdown)));
        select.selectByValue("lohi");
    }

    public List<Double> getVisibleProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement priceElement : getElements(productPrices)) {
            String priceText = priceElement.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }
}
