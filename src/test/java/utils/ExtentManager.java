package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ExtentManager {

    private static ExtentReports extent;

    // Prevent object creation
    private ExtentManager() {}

    /**
     * Returns a singleton ExtentReports instance.
     * Thread-safe to avoid multiple report instances when suite runs.
     */
    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            extent = createInstance();
        }
        return extent;
    }

    /**
     * Create and configure ExtentReports + Spark HTML reporter.
     */
    private static ExtentReports createInstance() {

        // Create report folder
        File reportDir = new File("reports");
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }

        // Timestamped report file name
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String reportPath = "reports/ExtentReport_" + timestamp + ".html";

        // Spark reporter config
        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setDocumentTitle("UrbanLadder Hackathon Report");
        spark.config().setReportName("Automation Execution Report");
        spark.config().setTimeStampFormat("dd-MMM-yyyy HH:mm:ss");

        // Create ExtentReports
        ExtentReports ext = new ExtentReports();
        ext.attachReporter(spark);

        // Add environment/system info
        ext.setSystemInfo("Project", "UrbanLadder Hackathon");
        ext.setSystemInfo("OS", System.getProperty("os.name"));
        ext.setSystemInfo("OS Version", System.getProperty("os.version"));
        ext.setSystemInfo("Java Version", System.getProperty("java.version"));
        ext.setSystemInfo("User", System.getProperty("user.name"));

        return ext;
    }

    /**
     * Flush report at the end of execution.
     */
    public static synchronized void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}