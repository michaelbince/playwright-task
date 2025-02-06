package tests.ui;

import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.SettingsPage;
import tests.utils.TestDataProvider;

public class UserUpdateTest extends UIBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private SettingsPage settingsPage;
    private Page page;

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
    public void testUpdateUserProfile(String email, String password, String userName, String bio, String image) {
        page.navigate(baseUrl);
        loginPage.goToLoginPage();
        loginPage.login(email, password);
        homePage.goToSettings();

        settingsPage.updateProfile(image, userName, bio, email, password);

        Assert.assertEquals(settingsPage.getProfileImageUrl(), image, "Profile image URL should be updated.");
        Assert.assertEquals(settingsPage.getUsername(), userName, "Username should be updated.");
        Assert.assertEquals(settingsPage.getBio(), bio, "Bio should be updated.");
        Assert.assertEquals(settingsPage.getEmail(), email, "Email should be updated.");

        homePage.logout();
    }
}
