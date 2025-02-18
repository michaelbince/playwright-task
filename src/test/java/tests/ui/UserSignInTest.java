package tests.ui;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import tests.utils.TestDataProvider;

public class UserSignInTest extends UIBaseTest {
    private static final Logger logger = LogManager.getLogger(UserSignInTest.class);
    private LoginPage loginPage;
    private HomePage homePage;
    private Page page;

    @BeforeMethod
    public void setup() {
        logger.info("Setting up UserSignInTest");
        page = getPage();
        loginPage = new LoginPage(page);
        homePage = new HomePage(page);
        logger.info("Setup completed");
    }

    @Test(description = "Verify successful login with valid credentials",
            dataProvider = "validCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testSuccessfulLogin(String email, String password, String expectedUserName) {
        logger.info("Starting test: testSuccessfulLogin");
        logger.debug("Email: {}, Password: {}, Expected User Name: {}", email, password, expectedUserName);

        logger.info("Navigating to base URL...");
        page.navigate(baseUrl);
        logger.info("Navigated to base URL.");

        logger.info("Going to login page...");
        loginPage.goToLoginPage();
        logger.info("On login page.");

        logger.info("Logging in...");
        loginPage.login(email, password);
        logger.info("Logged in.");

        String actualUserName = homePage.getLoggedInUsername();
        logger.debug("Actual User Name: {}", actualUserName);
        Assert.assertEquals(actualUserName, expectedUserName, "Logged in username should be the same as expected.");
        logger.info("Logged in username is correct.");

        logger.info("Logging out...");
        homePage.logout();
        logger.info("Logged out.");

        logger.info("Test testSuccessfulLogin finished successfully.");
    }

    @Test(description = "Verify failed login with invalid credentials",
            dataProvider = "invalidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testFailedLogin(String email, String password, String invalidCredentialsError) {
        logger.info("Starting test: testFailedLogin");
        logger.debug("Email: {}, Password: {}, Expected Error: {}", email, password, invalidCredentialsError);

        logger.info("Navigating to base URL...");
        page.navigate(baseUrl);
        logger.info("Navigated to base URL.");

        logger.info("Going to login page...");
        loginPage.goToLoginPage();
        logger.info("On login page.");

        logger.info("Logging in (attempting with invalid credentials)...");
        loginPage.login(email, password);
        logger.info("Login attempt finished.");

        String errorMessage = loginPage.getErrorMessage();
        logger.debug("Error Message: {}", errorMessage);
        Assert.assertEquals(errorMessage, invalidCredentialsError, "Message should indicate that login failed.");
        logger.info("Error message is correct.");

        logger.info("Going to home page...");
        homePage.goToHomePage();
        logger.info("On home page.");

        logger.info("Test testFailedLogin finished (expected to fail).");
    }
}