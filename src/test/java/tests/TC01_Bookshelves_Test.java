package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.FilterDrawerPage;
import pages.HomePage;
import pages.ProductListingPage;

public class TC01_Bookshelves_Test extends BaseTest {

    @Test
    public void displayBookshelvesBelow15000() {

        HomePage home = new HomePage(driver);
        FilterDrawerPage filter = new FilterDrawerPage(driver);
        ProductListingPage plp = new ProductListingPage(driver);

        home.openHomePage("https://www.urbanladder.com/");
        home.searchProduct("Bookshelves");

        // Optional but stable
        filter.applyPriceFilterOnly();

        // Core requirement
        plp.displayFirst3ValidBookshelves();
    }
}
