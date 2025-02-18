package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.microsoft.playwright.Page;
import tests.ui.UIBaseTest;

import java.nio.file.Paths;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        Object testInstance = result.getInstance();
        if (testInstance instanceof UIBaseTest) {
            Page page = ((UIBaseTest) testInstance).getPage();
            String screenshotPath = "screenshots/" + result.getName() + ".png";
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(screenshotPath)));
            logger.error("Test failed! Screenshot saved at: " + screenshotPath);
        }
    }
}