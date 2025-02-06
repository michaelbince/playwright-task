package tests.e2e;

import com.microsoft.playwright.Page;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SettingsPage;
import tests.ui.UIBaseTest;
import tests.utils.TestDataProvider;
import tests.utils.JsonLocalStorageHelper;

public class UserSettingsTest extends UIBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private SettingsPage settingsPage;
    private Page page;

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "https://conduit-realworld-example-app.fly.dev/api";
    }
    @BeforeMethod
    public void setup() {
        page = getPage();
        loginPage = new LoginPage(page);
        homePage = new HomePage(page);
        settingsPage = new SettingsPage(page);
    }

    @Test(description = "Verify user can update all their profile information",
            dataProvider = "validCredentialsToUpdate",
            dataProviderClass = TestDataProvider.class)
    public void testUpdateUserProfile(String email, String password, String userName, String bio, String image) throws Exception {
        page.navigate(baseUrl);
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRvbV9tYXJ2b2xvQGV4YW1wbGUuY29tIiwiaWF0IjoxNzM4NzkyOTgzfQ.tZKHpttVZjlFozwJLBznF1sHC9YiY9ezKUVio-bddAY";
        String loggedUserJson = JsonLocalStorageHelper.getLoggedUserJson(token);
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