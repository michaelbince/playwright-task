package tests.ui;

import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import tests.utils.TestDataProvider;

public class UserSignInTest extends UIBaseTest {
    private LoginPage loginPage;
    private HomePage homePage;
    private Page page;

    @BeforeMethod
    public void setup() {
        page = getPage();
        loginPage = new LoginPage(page);
        homePage = new HomePage(page);
    }

    @Test(description = "Verify successful login with valid credentials",
            dataProvider = "validCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testSuccessfulLogin(String email, String password, String expectedUserName) {
        page.navigate(baseUrl);
        loginPage.goToLoginPage();
        loginPage.login(email, password);

        String actualUserName = homePage.getLoggedInUsername();
        Assert.assertEquals(actualUserName, expectedUserName, "Logged in username should be the same as expected.");

        homePage.logout();
    }

    @Test(description = "Verify failed login with invalid credentials",
            dataProvider = "invalidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testFailedLogin(String email, String password, String invalidCredentialsError) {
        page.navigate(baseUrl);
        loginPage.goToLoginPage();
        loginPage.login(email, password);
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertEquals(errorMessage, invalidCredentialsError, "Message should indicate that login failed.");
        homePage.goToHomePage();
    }
}
