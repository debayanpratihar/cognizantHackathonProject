package tests;
import base.BaseTest;
import models.Product;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ProductListingPage;

public class TC04_StudyChair_Test extends BaseTest {

    @Test
    public void printTop3StudyChairsByPopularity() {

        HomePage home = new HomePage(driver);
        ProductListingPage plp = new ProductListingPage(driver);

        home.openHomePage("https://www.urbanladder.com/");
        home.searchProduct("Study Chair");

        plp.sortByPopularity();

        for (Product p : plp.getTop3Products()) {
            System.out.println(p.name + " - ₹" + p.price);
        }
    }
}

