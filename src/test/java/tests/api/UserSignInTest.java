package tests.api;

import api.client.LoginApiClient;
import api.client.UserApiClient;
import api.models.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.utils.TestDataProvider;

public class UserSignInTest extends APIBaseTest {
    private static final Logger logger = LogManager.getLogger(UserSignInTest.class);
    private UserApiClient userApiClient;
    private LoginApiClient loginApiClient;

    @BeforeClass
    public void setup() {
        logger.info("Setting up UserSignInTest");
        userApiClient = new UserApiClient();
        loginApiClient = new LoginApiClient();
        logger.info("Setup completed");
    }

    @Test(description = "Verify successful sign-in with correct credentials", dataProvider = "randomValidCredentials", dataProviderClass = TestDataProvider.class)
    public void testSuccessfulSignInWithValidCredentials(String email, String password, String userName) {
        logger.info("Starting test: testSuccessfulSignInWithValidCredentials");
        logger.debug("Email: {}, Password: {}, User Name: {}", email, password, userName);

        User existingUser = new User(email, password, userName);

        logger.info("Signing up user...");
        userApiClient.signUp(existingUser);
        logger.info("User signed up successfully.");

        logger.info("Logging in user...");
        Response loginResponse = loginApiClient.login(existingUser);
        logger.debug("Login response: {}", loginResponse.asString());
        logger.info("User logged in.");

        int statusCode = loginResponse.getStatusCode();
        logger.debug("Status Code: {}", statusCode);
        Assert.assertEquals(statusCode, 200, "Sign-in request should return status 200.");
        logger.info("Status code is 200.");

        String returnedEmail = loginResponse.jsonPath().getString("user.email");
        logger.debug("Returned Email: {}", returnedEmail);
        Assert.assertEquals(returnedEmail, email, "Email in response should match the input email.");
        logger.info("Email in response matches input email.");

        logger.info("Test testSuccessfulSignInWithValidCredentials finished successfully.");
    }

    @Test(description = "Verify failed sign-in with incorrect credentials", dataProvider = "invalidCredentials", dataProviderClass = TestDataProvider.class)
    public void testSignInFailureWithInvalidCredentials(String email, String password, String userName) {
        logger.info("Starting test: testSignInFailureWithInvalidCredentials");
        logger.debug("Email: {}, Password: {}, User Name: {}", email, password, userName);

        User invalidUser = new User(email, password);

        logger.info("Attempting login with invalid credentials...");
        Response response = loginApiClient.login(invalidUser);
        logger.debug("Login response: {}", response.asString());
        logger.info("Login attempt finished.");

        int statusCode = response.getStatusCode();
        logger.debug("Status Code: {}", statusCode);
        Assert.assertEquals(statusCode, 422, "Sign-in request with wrong credentials should return status 422.");
        logger.info("Status code is 422.");

        String errorMessage = response.jsonPath().getString("errors.body");
        logger.debug("Error Message: {}", errorMessage);
        Assert.assertTrue(response.jsonPath().getList("errors.body").contains("Wrong email/password combination"),
                "Error message for incorrect credentials should be 'Wrong email/password combination'.");
        logger.info("Error message is correct.");

        logger.info("Test testSignInFailureWithInvalidCredentials finished (expected to fail).");
    }
}