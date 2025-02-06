package utils;

import com.microsoft.playwright.*;

public class BrowserFactory {
    public static Browser createBrowser(Playwright playwright, String browserName) {
        switch (browserName.toLowerCase()) {
            case "firefox":
                return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
            case "webkit":
                return playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
            case "chromium":
            default:
                return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
    }
}
