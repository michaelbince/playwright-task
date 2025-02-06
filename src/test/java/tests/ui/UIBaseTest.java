package tests.ui;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import utils.BrowserFactory;

public abstract class UIBaseTest {
    protected static final String baseUrl = "https://conduit-realworld-example-app.fly.dev";
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeClass
    @org.testng.annotations.Parameters({"browser"})
    public void setupUI(@Optional("chromium") String browserName) {
        playwright = Playwright.create();
        browser = BrowserFactory.createBrowser(playwright, browserName);
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterClass
    public void tearDownUI() {
        if (context != null) context.close();
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    protected Page getPage() {
        return page;
    }
}
