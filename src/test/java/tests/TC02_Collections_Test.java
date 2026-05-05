package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.CollectionsPage;
import pages.HomePage;

public class TC02_Collections_Test extends BaseTest {

    @Test
    public void collectionsTest() {

        HomePage home = new HomePage(driver);
        CollectionsPage collections = new CollectionsPage(driver);

        home.openHomePage("https://www.urbanladder.com/");
        collections.getAllMegaMenuItems();
    }

}