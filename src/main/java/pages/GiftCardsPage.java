package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GiftCardsPage extends BasePage {

    private final By giftCardsLink =
            By.xpath("//a[normalize-space()='Gift Cards']");

    private final By denomination = By.id("denomination");
    private final By quantity = By.id("quantity");

    // Sender
    private final By senderFirstName = By.xpath("(//input[@id='firstname'])[1]");
    private final By senderLastName  = By.xpath("(//input[@id='lastname'])[1]");
    private final By senderEmail     = By.xpath("(//input[@id='email'])[1]");
    private final By senderPhone     = By.id("telephone");

    // Receiver
    private final By receiverFirstName = By.xpath("(//input[@id='firstname'])[2]");
    private final By receiverLastName  = By.xpath("(//input[@id='lastname'])[2]");
    private final By receiverEmail     = By.xpath("(//input[@id='email'])[2]");

    private final By messageBox = By.id("giftMessage");

    private final By previewBtn =
            By.xpath("//button[contains(text(),'PREVIEW')]");

    public GiftCardsPage(WebDriver driver) {
        super(driver);
    }

    public void openGiftCardPage() {
        String parent = driver.getWindowHandle();
        driver.findElement(giftCardsLink).click();

        for (String win : driver.getWindowHandles()) {
            if (!win.equals(parent)) {
                driver.switchTo().window(win);
                break;
            }
        }

        wait.until(ExpectedConditions.visibilityOfElementLocated(denomination));
    }

    public void fillAllGiftCardDetails() {

        type(denomination, "2000");
        type(quantity, "1");

        // Sender
        type(senderFirstName, "Test");
        type(senderLastName, "User");
        type(senderEmail, "testuser@gmail.com");
        type(senderPhone, "9999999999");

        // Receiver
        type(receiverFirstName, "Receiver");
        type(receiverLastName, "User");
        type(receiverEmail, "receiver@gmail.com");

       
        type(messageBox, "Happy Birthday. Enjoy your gift.");
    }

    public void clickPreview() {

        WebElement preview =
                wait.until(ExpectedConditions.visibilityOfElementLocated(previewBtn));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", preview);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", preview);
    }
}