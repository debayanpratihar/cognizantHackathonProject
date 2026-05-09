package tests;

import base.BaseTest;
import models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListingPage;
import utils.ExcelReportManager;

import java.util.List;

public class TC04_StudyChair_Test extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC04_StudyChair_Test.class);

    @Test
    public void printTop3StudyChairsByPopularity() {

        HomePage home = new HomePage(driver);
        ProductListingPage plp = new ProductListingPage(driver);

        home.openHomePage("https://www.urbanladder.com/");
        home.searchProduct("Study Chair");

        plp.sortByPopularity();
        List<Product> top3 = plp.getTop3Products();

        for (Product p : top3) {
            System.out.println(p.name + " - ₹" + p.price);
        }

        ExcelReportManager.writeProducts("TC04_StudyChair", top3);
        Assert.assertTrue(top3.size() > 0, "No study chairs extracted.");
    }
}