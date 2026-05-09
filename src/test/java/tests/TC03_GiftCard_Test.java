package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GiftCardsPage;
import utils.ExcelReportManager;

public class TC03_GiftCard_Test extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC03_GiftCard_Test.class);

    @Test
    public void giftCardInvalidEmailCaptureError() {

        GiftCardsPage gift = new GiftCardsPage(driver);

        gift.openGiftCardPage();
        gift.fillGiftCardWithInvalidReceiverEmail();

        String err = gift.captureAnyValidationError();
        System.out.println("Captured Error: " + err);
        log.info("Captured Error: {}", err);

        ExcelReportManager.writeGiftCard("TC03_GiftCard", "testuser@gmail.com", "(blank)", err);
        Assert.assertTrue(err.length() > 0, "No validation error captured for invalid email.");
    }
}