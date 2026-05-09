package pages;

import base.BasePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GiftCardsPage extends BasePage {

    private static final Logger log = LogManager.getLogger(GiftCardsPage.class);

    private final String GIFT_URL = "https://urbanladder.woohoo.in/en-gb/digital/urban-ladder-e-gift-card";

    private final By denomination = By.id("denomination");
    private final By quantity = By.id("quantity");

    private final By firstThemeImage = By.cssSelector("#design-theme img");

    // Sender (first set)
    private final By senderFirstName = By.xpath("(//input[@id='firstname'])[1]");
    private final By senderLastName  = By.xpath("(//input[@id='lastname'])[1]");
    private final By senderEmail     = By.xpath("(//input[@id='email'])[1]");
    private final By senderPhone     = By.id("telephone");

    // Receiver (second set)
    private final By receiverFirstName = By.xpath("(//input[@id='firstname'])[2]");
    private final By receiverLastName  = By.xpath("(//input[@id='lastname'])[2]");
    private final By receiverEmail     = By.xpath("(//input[@id='email'])[2]");

    private final By messageBox = By.id("giftMessage");
    private final By previewBtn = By.xpath("//button[contains(.,'PREVIEW')]");

    // ✅ Multiple fallbacks for error
    private final By receiverEmailError1 =
            By.xpath("(//input[@id='email'])[2]/ancestor::div[contains(@class,'has-float-label')]//div[contains(@class,'invalid-feedback')]");
    private final By anyInvalidFeedbackNonEmpty =
            By.xpath("//div[contains(@class,'invalid-feedback') and normalize-space()!='']");
    private final By anyTextDangerNonEmpty =
            By.xpath("//*[contains(@class,'text-danger') and normalize-space()!='']");

    public GiftCardsPage(WebDriver driver) {
        super(driver);
    }

    public void openGiftCardPage() {
        log.info("Opening gift card page: {}", GIFT_URL);
        driver.get(GIFT_URL);
        waitForPageReady();
        wait.until(ExpectedConditions.visibilityOfElementLocated(denomination));
    }

    /**
     * Fill all required fields but keep receiver email INVALID to trigger error.
     * Use blank receiver email to guarantee "required" validation.
     */
    public void fillGiftCardWithInvalidReceiverEmail() {
        log.info("Filling denomination and quantity");
        type(denomination, "2000");
        type(quantity, "1");

        // choose theme
        try {
            log.info("Selecting first theme image");
            WebElement theme = wait.until(ExpectedConditions.elementToBeClickable(firstThemeImage));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", theme);
        } catch (Exception ignored) {}

        log.info("Filling sender details");
        type(senderFirstName, "Test");
        type(senderLastName, "User");
        type(senderEmail, "testuser@gmail.com");
        type(senderPhone, "9999999999");

        log.info("Filling receiver details (INVALID email)");
        type(receiverFirstName, "Receiver");
        type(receiverLastName, "User");

        // ✅ Guaranteed invalid: leave empty (required field)
        WebElement rEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(receiverEmail));
        rEmail.clear();
        rEmail.sendKeys(Keys.TAB);

        // Avoid emojis (BMP restriction)
        type(messageBox, "Happy Birthday. Enjoy your gift.");

        log.info("Clicking Preview (JS click)");
        WebElement preview = wait.until(ExpectedConditions.visibilityOfElementLocated(previewBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", preview);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", preview);
    }

    /** Capture error message using multiple fallbacks */
    public String captureAnyValidationError() {
        // 1) receiver email invalid-feedback (might be empty)
        try {
            WebElement e1 = driver.findElement(receiverEmailError1);
            String t1 = e1.getText().trim();
            if (!t1.isEmpty()) return t1;
        } catch (Exception ignored) {}

        // 2) any invalid feedback with text
        try {
            WebElement e2 = wait.until(ExpectedConditions.visibilityOfElementLocated(anyInvalidFeedbackNonEmpty));
            String t2 = e2.getText().trim();
            if (!t2.isEmpty()) return t2;
        } catch (Exception ignored) {}

        // 3) any text-danger with text
        try {
            WebElement e3 = wait.until(ExpectedConditions.visibilityOfElementLocated(anyTextDangerNonEmpty));
            String t3 = e3.getText().trim();
            if (!t3.isEmpty()) return t3;
        } catch (Exception ignored) {}

        return "";
    }
}