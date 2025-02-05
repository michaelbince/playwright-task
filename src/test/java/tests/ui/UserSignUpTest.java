package tests.ui;

import com.microsoft.playwright.Page;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.SignUpPage;
import tests.utils.TestDataProvider;

public class UserSignUpTest extends UIBaseTest {
    private SignUpPage signUpPage;
    private HomePage homePage;
    private Page page;

    @BeforeMethod
    public void setup() {
        page = getPage();
        signUpPage = new SignUpPage(page);
        homePage = new HomePage(page);
    }

    @Test(description = "Verify successful signup and correct display of logged-in username",
            dataProvider = "randomValidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testSuccessfulSignUp(String email, String password, String userName) {
        signUpPage.goToSignUpPage();
        signUpPage.signUp(userName, email, password);

        Assert.assertEquals(homePage.getLoggedInUsername(), userName, "Logged in username should be the same as expected.");
    }
}
