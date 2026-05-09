package pages;

import base.BasePage;
import models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.DataParser;

import java.util.ArrayList;
import java.util.List;

public class ProductListingPage extends BasePage {

    private static final Logger log = LogManager.getLogger(ProductListingPage.class);

    private final By productCards = By.cssSelector("div.o0mbO");
    private final By productName  = By.cssSelector("h3.XxwSy");
    private final By sellingPrice = By.cssSelector("div.UYQNp");

    private final By sortByBtn = By.cssSelector("div[role='button'][aria-label='Sort By filter']");
    private final By popularityOption = By.cssSelector("div[role='radio'][aria-label='Sort by Popularity']");

    public ProductListingPage(WebDriver driver) {
        super(driver);
    }

    public void waitForProducts() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
    }

    public void sortByPopularity() {
        log.info("Sorting by Popularity");
        wait.until(ExpectedConditions.elementToBeClickable(sortByBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(popularityOption)).click();
        waitForProducts();
    }

    public List<Product> getTop3Products() {
        waitForProducts();
        List<Product> result = new ArrayList<>();
        List<WebElement> cards = driver.findElements(productCards);

        for (int i = 0; i < Math.min(3, cards.size()); i++) {
            WebElement card = cards.get(i);
            String name = card.findElement(productName).getText().trim();
            int price = DataParser.priceToInt(card.findElement(sellingPrice).getText());
            result.add(new Product(name, price));
        }
        return result;
    }

    /**
     * TC01 Requirement AFTER applying UI filters:
     * - We already applied Max Price 15000
     * - We already applied Storage Type = Open Storage
     * Now we just pick first 3 items below 15000 and exclude out-of-stock (if visible).
     */
    public List<Product> getFirst3ValidBookshelves() {
        waitForProducts();

        List<Product> result = new ArrayList<>();
        List<WebElement> cards = driver.findElements(productCards);

        for (WebElement card : cards) {
            if (result.size() == 3) break;

            try {
                String name = card.findElement(productName).getText().trim();
                int price = DataParser.priceToInt(card.findElement(sellingPrice).getText());
                if (price < 0 || price >= 15000) continue;

                String text = card.getText().toLowerCase();
                if (text.contains("out of stock")) continue;

                result.add(new Product(name, price));
            } catch (Exception ignored) {}
        }

        log.info("Bookshelves matched count: {}", result.size());
        return result;
    }
}