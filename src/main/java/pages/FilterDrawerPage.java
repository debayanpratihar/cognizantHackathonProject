package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FilterDrawerPage extends BasePage {

    private static final Logger log = LogManager.getLogger(FilterDrawerPage.class);

    private final By allFiltersBtn = By.cssSelector("div[aria-label='Open all filters']");
    private final By filterDialog  = By.cssSelector("div[role='dialog']");

    private final By priceAccordion = By.cssSelector("div[role='button'][aria-label='Price']");
    private final By maxPriceSlider = By.xpath("(//div[@role='slider'])[2]");
    private final By maxPriceInput  = By.xpath("//input[contains(@aria-label,'Maximum value')]");

    private final By storageAccordion = By.cssSelector("div[role='button'][aria-label='Storage Type']");

    // ✅ safest: click label text using JS (checkbox ancestor is flaky on this UI)
    private final By openStorageLabel = By.xpath("//*[normalize-space()='Open Storage']");

    private final By applyFilterBtn = By.cssSelector("button[data-testid='plp-filter-apply-button']");

    public FilterDrawerPage(WebDriver driver) {
        super(driver);
    }

    /** Apply: Max price = 15000 AND Storage Type = Open Storage */
    public void applyPrice15000AndOpenStorage() {

        log.info("Opening filter drawer");
        wait.until(ExpectedConditions.elementToBeClickable(allFiltersBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterDialog));

        // PRICE
        log.info("Opening Price accordion");
        wait.until(ExpectedConditions.elementToBeClickable(priceAccordion)).click();

        log.info("Triggering slider state (small move)");
        WebElement slider = wait.until(ExpectedConditions.visibilityOfElementLocated(maxPriceSlider));
        new Actions(driver).clickAndHold(slider).moveByOffset(-30, 0).release().perform();

        log.info("Setting max price = 15000");
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(maxPriceInput));
        input.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys("15000");
        input.sendKeys(Keys.TAB);

        // STORAGE TYPE
        log.info("Opening Storage Type accordion");
        wait.until(ExpectedConditions.elementToBeClickable(storageAccordion)).click();

        log.info("Selecting Open Storage");
        WebElement openStorage = wait.until(ExpectedConditions.visibilityOfElementLocated(openStorageLabel));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", openStorage);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", openStorage);

        // APPLY
        log.info("Clicking Apply Filter");
        WebElement applyBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(applyFilterBtn));

        // Some builds toggle enabled state
        wait.until(d -> applyBtn.isEnabled());

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", applyBtn);
    }
}