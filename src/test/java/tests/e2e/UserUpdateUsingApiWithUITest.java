package tests.e2e;

import api.client.LoginApiClient;
import api.models.User;
import com.microsoft.playwright.Page;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SettingsPage;
import tests.ui.UIBaseTest;
import tests.utils.TestDataProvider;
import tests.utils.JsonLocalStorageHelper;

public class UserUpdateUsingApiWithUITest extends UIBaseTest {
    private static final Logger logger = LogManager.getLogger(UserUpdateUsingApiWithUITest.class);
    private HomePage homePage;
    private SettingsPage settingsPage;
    private LoginApiClient loginApiClient;
    private Page page;

    @BeforeClass
    public void setupAPI() {
        logger.info("Setting up API base URI");
        RestAssured.baseURI = "https://conduit-realworld-example-app.fly.dev/api";
        logger.info("API base URI set to: {}", RestAssured.baseURI);
    }

    @BeforeMethod
    public void setup() {
        logger.info("Setting up UserUpdateUsingApiWithUITest");
        page = getPage();
        homePage = new HomePage(page);
        settingsPage = new SettingsPage(page);
        loginApiClient = new LoginApiClient();
        logger.info("Setup completed");
    }

    @Test(description = "Verify user can update all their profile information",
            dataProvider = "validCredentialsToUpdate",
            dataProviderClass = TestDataProvider.class)
    public void testUpdateUserProfile(String email, String password, String userName, String bio, String image) throws Exception {
        logger.info("Starting test: testUpdateUserProfile");
        logger.debug("Email: {}, Password: {}, User Name: {}, Bio: {}, Image: {}", email, password, userName, bio, image);

        User existingUser = new User(email, password, userName);

        logger.info("Logging in via API...");
        Response loginResponse = loginApiClient.login(existingUser);
        logger.debug("Login response: {}", loginResponse.asString());
        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login request should return status code 200.");
        logger.info("User logged in via API.");

        String authToken = loginResponse.jsonPath().getString("user.token");
        logger.debug("Auth Token: {}", authToken);

        logger.info("Navigating to base URL...");
        page.navigate(baseUrl);
        logger.info("Navigated to base URL.");

        logger.info("Setting logged user in local storage...");
        String loggedUserJson = JsonLocalStorageHelper.getLoggedUserJson(authToken);
        page.evaluate("jsonString => { localStorage.setItem('loggedUser', jsonString); }", loggedUserJson);
        logger.info("Logged user set in local storage.");

        logger.info("Reloading page...");
        page.reload();
        logger.info("Page reloaded.");

        logger.info("Going to settings page...");
        homePage.goToSettings();
        logger.info("On settings page.");

        logger.info("Updating profile via UI...");
        settingsPage.updateProfile(image, userName, bio, email, password);
        logger.info("Profile updated via UI.");

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