package pages;

import base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FilterDrawerPage extends BasePage {

    private final By allFiltersBtn =
            By.cssSelector("div[aria-label='Open all filters']");

    private final By filterDialog =
            By.cssSelector("div[role='dialog']");

    private final By priceAccordion =
            By.cssSelector("div[aria-label='Price']");

    private final By maxPriceSlider =
            By.xpath("(//div[@role='slider'])[2]");

    private final By maxPriceInput =
            By.xpath("//input[contains(@aria-label,'Maximum value')]");

    private final By applyFilterBtn =
            By.cssSelector("button[data-testid='plp-filter-apply-button']");

    public FilterDrawerPage(WebDriver driver) {
        super(driver);
    }

    public void applyPriceFilterOnly() {

        wait.until(ExpectedConditions.elementToBeClickable(allFiltersBtn)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(filterDialog));

        wait.until(ExpectedConditions.elementToBeClickable(priceAccordion)).click();

        WebElement slider = wait.until(
                ExpectedConditions.visibilityOfElementLocated(maxPriceSlider)
        );

        new Actions(driver)
                .clickAndHold(slider)
                .moveByOffset(-30, 0)
                .release()
                .perform();

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(maxPriceInput)
        );

        input.clear();
        input.sendKeys("15000");

        WebElement applyBtn = wait.until(
                ExpectedConditions.visibilityOfElementLocated(applyFilterBtn)
        );

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", applyBtn);
    }
}
