package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CollectionsPage extends BasePage {

    private static final Logger log = LogManager.getLogger(CollectionsPage.class);

    private final By topCategories = By.cssSelector("div[data-testid^='navigation-desktop-category']");

    public CollectionsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAllMegaMenuItems() {
        List<String> allItems = new ArrayList<>();

        List<WebElement> categories =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(topCategories));

        log.info("Found top categories: {}", categories.size());

        for (WebElement category : categories) {
            actions.moveToElement(category).perform();
            String menuId = category.getAttribute("aria-controls");
            if (menuId == null || menuId.isEmpty()) continue;

            By subMenuContainer = By.id(menuId);
            WebElement subMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(subMenuContainer));
            List<WebElement> links = subMenu.findElements(By.tagName("a"));

            for (WebElement link : links) {
                String text = link.getText().trim();
                if (!text.isEmpty() && !allItems.contains(text)) {
                    allItems.add(text);
                }
            }
        }

        log.info("Collected mega menu items: {}", allItems.size());
        return allItems;
    }
}
