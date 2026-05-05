package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    private final By searchBox = By.id("searchInput");
    private final By giftCardsLink =
            By.xpath("//a[normalize-space()='Gift Cards']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    /** Open Urban Ladder home page */
    public void openHomePage(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
    }

    /** Search any product */
    public void searchProduct(String productName) {
        wait.until(ExpectedConditions.elementToBeClickable(searchBox));
        type(searchBox, productName);
        driver.findElement(searchBox).sendKeys(Keys.ENTER);
    }

    /** Click Gift Cards link (opens new tab) */
    public void openGiftCards() {
        wait.until(ExpectedConditions.elementToBeClickable(giftCardsLink));
        driver.findElement(giftCardsLink).click();
    }
}