package tests.ui;

import com.microsoft.playwright.Page;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SettingsPage;
import tests.utils.TestDataProvider;

public class UserUpdateTest extends UIBaseTest {
    private static final Logger logger = LogManager.getLogger(UserUpdateTest.class);
    private LoginPage loginPage;
    private HomePage homePage;
    private SettingsPage settingsPage;
    private Page page;

    @BeforeMethod
    public void setup() {
        logger.info("Setting up UserUpdateTest");
        page = getPage();
        loginPage = new LoginPage(page);
        homePage = new HomePage(page);
        settingsPage = new SettingsPage(page);
        logger.info("Setup completed");
    }

    @Test(description = "Verify user can update all their profile information",
            dataProvider = "validCredentialsToUpdate",
            dataProviderClass = TestDataProvider.class)
    public void testUpdateUserProfile(String email, String password, String userName, String bio, String image) {
        logger.info("Starting test: testUpdateUserProfile");
        logger.debug("Email: {}, Password: {}, User Name: {}, Bio: {}, Image: {}", email, password, userName, bio, image);

        logger.info("Navigating to base URL...");
        page.navigate(baseUrl);
        logger.info("Navigated to base URL.");

        logger.info("Going to login page...");
        loginPage.goToLoginPage();
        logger.info("On login page.");

        logger.info("Logging in...");
        loginPage.login(email, password);
        logger.info("Logged in.");

        logger.info("Going to settings page...");
        homePage.goToSettings();
        logger.info("On settings page.");

        logger.info("Updating profile...");
        settingsPage.updateProfile(image, userName, bio, email, password);
        logger.info("Profile updated.");

        String actualImage = settingsPage.getProfileImageUrl();
        logger.debug("Actual Image URL: {}", actualImage);
        Assert.assertEquals(actualImage, image, "Profile image URL should be updated.");
        logger.info("Profile image URL is correct.");

        String actualUsername = settingsPage.getUsername();
        logger.debug("Actual Username: {}", actualUsername);
        Assert.assertEquals(actualUsername, userName, "Username should be updated.");
        logger.info("Username is correct.");

        String actualBio = settingsPage.getBio();
        logger.debug("Actual Bio: {}", actualBio);
        Assert.assertEquals(actualBio, bio, "Bio should be updated.");
        logger.info("Bio is correct.");

        String actualEmail = settingsPage.getEmail();
        logger.debug("Actual Email: {}", actualEmail);
        Assert.assertEquals(actualEmail, email, "Email should be updated.");
        logger.info("Email is correct.");

        logger.info("Logging out...");
        homePage.logout();
        logger.info("Logged out.");

        logger.info("Test testUpdateUserProfile finished successfully.");
    }
}