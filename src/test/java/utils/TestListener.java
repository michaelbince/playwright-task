package utils;

import com.epam.reportportal.message.ReportPortalMessage;
import com.epam.reportportal.service.ReportPortal;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.microsoft.playwright.Page;
import tests.ui.UIBaseTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

public class TestListener extends ReportPortalTestNGListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof UIBaseTest) {
            Page page = ((UIBaseTest) testInstance).getPage();
            String screenshotPath = "screenshots/" + result.getName() + "_" + getCurrentTimestamp() + ".png";
            Path screenshotFile = Paths.get(screenshotPath);

            // Capture screenshot
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotFile));

            // Attach screenshot to ReportPortal
            attachScreenshotToReportPortal(screenshotFile.toFile());

            logger.error("Test failed! Screenshot saved at: " + screenshotPath);
        }
    }

    private void attachScreenshotToReportPortal(File screenshotFile) {
        try {
            ReportPortalMessage message = new ReportPortalMessage(screenshotFile, "Test failed. Screenshot attached.");
            ReportPortal.emitLog(message, "ERROR", Calendar.getInstance().getTime());
        } catch (IOException e) {
            logger.error("Failed to attach screenshot to ReportPortal", e);
        }
    }

    private String getCurrentTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }
}
