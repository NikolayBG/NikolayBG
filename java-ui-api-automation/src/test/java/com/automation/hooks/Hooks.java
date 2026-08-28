package com.automation.hooks;

import com.automation.core.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {

    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before("@ui")
    public void startBrowser() {
        logger.info("Starting browser for UI scenario");
        DriverFactory.createDriver();
    }

    @After("@ui")
    public void stopBrowser(Scenario scenario) {
        if (scenario.isFailed()) {
            takeScreenshot(scenario);
        }
        logger.info("Closing browser");
        DriverFactory.quitDriver();
    }

    private void takeScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "failure-screenshot");
        } catch (Exception exception) {
            logger.warn("Could not capture screenshot: {}", exception.getMessage());
        }
    }
}
