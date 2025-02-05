package tests.api;

import api.client.LoginApiClient;
import api.client.UserApiClient;
import api.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.utils.TestDataProvider;

public class UserSignInTest extends APIBaseTest {
    private UserApiClient userApiClient;
    private LoginApiClient loginApiClient;

    @BeforeClass
    public void setup() {
        userApiClient = new UserApiClient();
        loginApiClient = new LoginApiClient();
    }

    @Test(description = "Verify successful sign-in with correct credentials",
            dataProvider = "randomValidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testSuccessfulSignInWithValidCredentials(String email, String password, String userName) {
        User existingUser = new User(email, password, userName);

        userApiClient.signUp(existingUser);

        Response loginResponse = loginApiClient.login(existingUser);

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Sign-in request should return status 200.");
        Assert.assertEquals(loginResponse.jsonPath().getString("user.email"), email, "Email in response should match the input email.");
    }

    @Test(description = "Verify failed sign-in with incorrect credentials",
            dataProvider = "invalidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testSignInFailureWithInvalidCredentials(String email, String password, String userName) {
        User invalidUser = new User(email, password);

        Response response = loginApiClient.login(invalidUser);

        Assert.assertEquals(response.getStatusCode(), 422, "Sign-in request with wrong credentials should return status 422.");
        Assert.assertTrue(response.jsonPath().getList("errors.body").contains("Wrong email/password combination"),
                "Error message for incorrect credentials should be 'Wrong email/password combination'.");
    }
}

