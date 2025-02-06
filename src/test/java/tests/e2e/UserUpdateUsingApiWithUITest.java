package tests.e2e;

import api.client.LoginApiClient;
import api.models.User;
import com.microsoft.playwright.Page;
import io.restassured.RestAssured;
import io.restassured.response.Response;
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
    private HomePage homePage;
    private SettingsPage settingsPage;
    private LoginApiClient loginApiClient;
    private Page page;

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "https://conduit-realworld-example-app.fly.dev/api";
    }
    @BeforeMethod
    public void setup() {
        page = getPage();
        homePage = new HomePage(page);
        settingsPage = new SettingsPage(page);
        loginApiClient = new LoginApiClient();
    }

    @Test(description = "Verify user can update all their profile information",
            dataProvider = "validCredentialsToUpdate",
            dataProviderClass = TestDataProvider.class)
    public void testUpdateUserProfile(String email, String password, String userName, String bio, String image) throws Exception {
        User existingUser = new User(email, password, userName);
        Response loginResponse = loginApiClient.login(existingUser);
        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login request should return status code 200.");
        String authToken = loginResponse.jsonPath().getString("user.token");

        page.navigate(baseUrl);


        String loggedUserJson = JsonLocalStorageHelper.getLoggedUserJson(authToken);
        page.evaluate("jsonString => { localStorage.setItem('loggedUser', jsonString); }", loggedUserJson);
        page.reload();

        homePage.goToSettings();

        settingsPage.updateProfile(image, userName, bio, email, password);

        Assert.assertEquals(settingsPage.getProfileImageUrl(), image, "Profile image URL should be updated.");
        Assert.assertEquals(settingsPage.getUsername(), userName, "Username should be updated.");
        Assert.assertEquals(settingsPage.getBio(), bio, "Bio should be updated.");
        Assert.assertEquals(settingsPage.getEmail(), email, "Email should be updated.");

        homePage.logout();
    }
}