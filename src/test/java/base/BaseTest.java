package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ScreenshotUtils;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected final Logger log = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        log.info("========== Test Setup Started ==========");

        driver = DriverFactory.getDriver();

        // ✅ Best practice: use explicit waits only (keep implicit wait = 0)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));

        log.info("Browser launched successfully");
        log.info("========================================");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        log.info("========== Test Teardown Started ==========");

        // If test failed, capture screenshot (extra safety, listener can also do it)
        if (result.getStatus() == ITestResult.FAILURE) {
            try {
                String path = ScreenshotUtils.capture(driver, result.getMethod().getMethodName());
                log.error("Test Failed: {} | Screenshot saved: {}", result.getMethod().getMethodName(), path);
            } catch (Exception e) {
                log.error("Failed to capture screenshot on failure: {}", e.getMessage());
            }
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test Passed: {}", result.getMethod().getMethodName());
        } else if (result.getStatus() == ITestResult.SKIP) {
            log.warn("Test Skipped: {}", result.getMethod().getMethodName());
        }

        // Quit driver
        try {
            DriverFactory.quitDriver();
            log.info("Browser closed successfully");
        } catch (Exception e) {
            log.error("Error while closing browser: {}", e.getMessage());
        }

        log.info("=========================================");
        log.info("========== Test Teardown Completed ==========");
    }
}