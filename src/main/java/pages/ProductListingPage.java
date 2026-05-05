package pages;

import base.BasePage;
import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class ProductListingPage extends BasePage {

    // Common product locators
    private final By productCards = By.cssSelector("div.o0mbO");
    private final By productName = By.cssSelector("h3.XxwSy");
    private final By sellingPrice = By.cssSelector("div.UYQNp");

    // Sort By Popularity
    private final By sortByBtn =
            By.cssSelector("div[role='button'][aria-label='Sort By filter']");
    private final By popularityOption =
            By.cssSelector("div[role='radio'][aria-label='Sort by Popularity']");

    public ProductListingPage(WebDriver driver) {
        super(driver);
    }

    /* =========================================================
       STUDY CHAIR USE CASE
       ========================================================= */

    public void sortByPopularity() {
        wait.until(ExpectedConditions.elementToBeClickable(sortByBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(popularityOption)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(productCards));
    }

    public List<Product> getTop3Products() {
        List<Product> result = new ArrayList<>();
        List<WebElement> cards = driver.findElements(productCards);

        for (int i = 0; i < Math.min(3, cards.size()); i++) {
            WebElement card = cards.get(i);

            String name = card.findElement(productName).getText().trim();
            int price = Integer.parseInt(
                    card.findElement(sellingPrice)
                            .getText()
                            .replaceAll("[^0-9]", "")
                            .trim()
            );

            result.add(new Product(name, price));
        }
        return result;
    }

    /* =========================================================
       BOOKSHELVES USE CASE
       ========================================================= */

    public void displayFirst3ValidBookshelves() {

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(productCards));

        List<WebElement> cards = driver.findElements(productCards);
        int count = 0;

        System.out.println("----- First 3 Bookshelves Below ₹15000 -----");

        for (WebElement card : cards) {
            if (count == 3) break;

            try {
                String name = card.findElement(productName).getText().trim();

                String priceText = card.findElement(sellingPrice)
                        .getText()
                        .replaceAll("[^0-9]", "")
                        .trim();

                if (priceText.isEmpty()) continue;

                int price = Integer.parseInt(priceText);

                if (price < 15000) {
                    System.out.println((count + 1) + ". Name  : " + name);
                    System.out.println("   Price : ₹" + price);
                    System.out.println("--------------------------------");
                    count++;
                }

            } catch (Exception e) {
                continue;
            }
        }

        if (count == 0) {
            System.out.println("No bookshelves found below ₹15000.");
        }
    }
}