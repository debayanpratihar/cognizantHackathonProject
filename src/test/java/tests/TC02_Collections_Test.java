package tests;

import base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CollectionsPage;
import pages.HomePage;
import utils.ExcelReportManager;

import java.util.List;

public class TC02_Collections_Test extends BaseTest {

    private static final Logger log = LogManager.getLogger(TC02_Collections_Test.class);

    @Test
    public void collectionsTest() {

        HomePage home = new HomePage(driver);
        CollectionsPage collections = new CollectionsPage(driver);

        home.openHomePage("https://www.urbanladder.com/");

        List<String> items = collections.getAllMegaMenuItems();

        log.info("Collected menu items count: {}", items.size());
        ExcelReportManager.writeList("TC02_Collections", "Mega Menu Items (hover)", items);

        Assert.assertTrue(items.size() > 0, "No menu items were extracted.");
    }
}