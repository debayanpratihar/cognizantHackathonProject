package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private static final Logger log = LogManager.getLogger(HomePage.class);

    private final By searchById = By.id("searchInput");
    private final By searchFallback = By.cssSelector("input[type='search'], input[placeholder*='Search']");

    private final By closePopup = By.cssSelector("button[aria-label='Close'], .close, .Close, button.close");
    private final By acceptCookies = By.xpath("//button[contains(.,'Accept') or contains(.,'Got it') or contains(.,'OK')]");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openHomePage(String url) {
        log.info("Opening URL: {}", url);
        driver.get(url);
        waitForPageReady();
        dismissOverlays();

        if (isPresent(searchById)) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchById));
        } else {
            wait.until(ExpectedConditions.visibilityOfElementLocated(searchFallback));
        }
        log.info("Home page loaded successfully");
    }

    public void searchProduct(String text) {
        log.info("Searching for: {}", text);
        if (isPresent(searchById)) {
            type(searchById, text);
            driver.findElement(searchById).sendKeys(Keys.ENTER);
        } else {
            type(searchFallback, text);
            driver.findElement(searchFallback).sendKeys(Keys.ENTER);
        }
    }

    private void dismissOverlays() {
        try { if (isPresent(acceptCookies)) jsClick(acceptCookies); } catch (Exception ignored) {}
        try { if (isPresent(closePopup)) jsClick(closePopup); } catch (Exception ignored) {}
    }
}