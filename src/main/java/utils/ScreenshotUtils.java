package utils;

import org.openqa.selenium.*;
import java.io.File;
import java.nio.file.Files;

public class ScreenshotUtils {

    public static void capture(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);
            Files.createDirectories(new File("screenshots").toPath());
            Files.copy(src.toPath(),
                    new File("screenshots/" + testName + ".png").toPath());
        } catch (Exception ignored) {}
    }
}