package listeners;

import base.DriverFactory;
import com.aventstack.extentreports.*;
import org.testng.*;
import utils.ExtentManager;
import utils.ScreenshotUtils;
import utils.ExcelReportManager;

public class TestListener implements ITestListener {

    private static final ExtentReports extent = ExtentManager.getExtent();
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest t = extent.createTest(result.getMethod().getMethodName());
        test.set(t);
        test.get().info("Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().pass("Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail(result.getThrowable());

        String path = ScreenshotUtils.capture(DriverFactory.getDriver(), result.getMethod().getMethodName());
        if (path != null) {
            try { test.get().addScreenCaptureFromPath(path); } catch (Exception ignored) {}
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
        ExcelReportManager.flush(); // ✅ combined excel written once
    }
}