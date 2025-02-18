package tests.ui;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignUpPage;
import tests.utils.TestDataProvider;

public class UserSignUpTest extends UIBaseTest {
    private static final Logger logger = LogManager.getLogger(UserSignUpTest.class);
    private SignUpPage signUpPage;
    private HomePage homePage;
    private Page page;

    @BeforeMethod
    public void setup() {
        logger.info("Setting up UserSignUpTest");
        page = getPage();
        signUpPage = new SignUpPage(page);
        homePage = new HomePage(page);
        logger.info("Setup completed");
    }

    @Test(description = "Verify successful signup and correct display of logged-in username",
            dataProvider = "randomValidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testSuccessfulSignUp(String email, String password, String userName) {
        logger.info("Starting test: testSuccessfulSignUp");
        logger.debug("Email: {}, Password: {}, User Name: {}", email, password, userName);

        logger.info("Navigating to base URL...");
        page.navigate(baseUrl);
        logger.info("Navigated to base URL.");

        logger.info("Going to sign-up page...");
        signUpPage.goToSignUpPage();
        logger.info("On sign-up page.");

        logger.info("Signing up user...");
        signUpPage.signUp(userName, email, password);
        logger.info("User signed up.");

        String actualUserName = homePage.getLoggedInUsername();
        logger.debug("Actual User Name: {}", actualUserName);
        Assert.assertEquals(actualUserName, userName, "Logged in username should be the same as expected.");
        logger.info("Logged in username is correct.");

        logger.info("Test testSuccessfulSignUp finished successfully.");
    }
}