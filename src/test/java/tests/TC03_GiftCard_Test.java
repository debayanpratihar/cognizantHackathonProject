package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.GiftCardsPage;
import pages.HomePage;

public class TC03_GiftCard_Test extends BaseTest {

    @Test
    public void verifyGiftCardPreview() {

        HomePage home = new HomePage(driver);
        GiftCardsPage gift = new GiftCardsPage(driver);

        home.openHomePage("https://www.urbanladder.com/");
        gift.openGiftCardPage();

        gift.fillAllGiftCardDetails();
        gift.clickPreview();

        System.out.println("✅ Gift Card Preview opened successfully");
    }
}