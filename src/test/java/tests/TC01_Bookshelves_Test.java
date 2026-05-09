package tests;

import base.BaseTest;
import models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.FilterDrawerPage;
import pages.HomePage;
import pages.ProductListingPage;
import utils.ExcelReportManager;

import java.util.List;

public class TC01_Bookshelves_Test extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC01_Bookshelves_Test.class);

    @Test
    public void displayBookshelvesBelow15000_Open_ExcludeOOS() {

        HomePage home = new HomePage(driver);
        FilterDrawerPage filter = new FilterDrawerPage(driver);
        ProductListingPage plp = new ProductListingPage(driver);

        home.openHomePage("https://www.urbanladder.com/");
        home.searchProduct("Bookshelves");

        // ✅ apply both price + open storage
        filter.applyPrice15000AndOpenStorage();

        List<Product> items = plp.getFirst3ValidBookshelves();

        log.info("---- TC01 Output ----");
        for (int i = 0; i < items.size(); i++) {
            log.info("{}. {} | ₹{}", i + 1, items.get(i).name, items.get(i).price);
            System.out.println((i + 1) + ". " + items.get(i).name + " - ₹" + items.get(i).price);
        }

        ExcelReportManager.writeProducts("TC01_Bookshelves", items);
        Assert.assertTrue(items.size() > 0, "No valid bookshelves found as per conditions.");
    }
}