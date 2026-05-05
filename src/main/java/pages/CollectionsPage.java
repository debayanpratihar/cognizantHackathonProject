package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CollectionsPage extends BasePage {

    // ✅ All top‑level navigation categories
    private final By topCategories =
            By.cssSelector("div[data-testid^='navigation-desktop-category']");

    public CollectionsPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getAllMegaMenuItems() {

        List<String> allItems = new ArrayList<>();

        List<WebElement> categories =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(topCategories));

        System.out.println("========================================");
        System.out.println("COLLECTIONS → BEING AT HOME SUB‑MENUS");
        System.out.println("========================================");

        for (WebElement category : categories) {

            // Hover on category
            actions.moveToElement(category).perform();

            // Get submenu container id from aria-controls
            String menuId = category.getAttribute("aria-controls");
            if (menuId == null || menuId.isEmpty()) {
                continue;
            }

            // Submenu container
            By subMenuContainer = By.id(menuId);

            WebElement subMenu =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(subMenuContainer));

            // All links inside submenu
            List<WebElement> links = subMenu.findElements(By.tagName("a"));

            for (WebElement link : links) {
                String text = link.getText().trim();
                if (!text.isEmpty() && !allItems.contains(text)) {
                    allItems.add(text);
                    System.out.println(text);
                }
            }
        }

        return allItems;
    }
}