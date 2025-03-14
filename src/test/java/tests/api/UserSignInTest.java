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

    @Test(description = "Verify user sign-up is successful", dataProvider = "randomValidCredentials", dataProviderClass = TestDataProvider.class)
    public void testUserSignUp(String email, String password, String userName) {
        logger.info("Starting test: testUserSignUp");
        logger.debug("Email: {}, Password: {}, User Name: {}", email, password, userName);

        User newUser = new User(email, password, userName);

        logger.info("Signing up user...");
        Response signUpResponse = userApiClient.signUp(newUser);
        logger.debug("Sign-up response: {}", signUpResponse.asString());
        logger.info("User signed up successfully.");

        int statusCode = signUpResponse.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Sign-up request should return status 200.");
        logger.info("Sign-up was successful with status 200.");
    }

    @Test(description = "Verify successful sign-in", dataProvider = "randomValidCredentials", dataProviderClass = TestDataProvider.class, dependsOnMethods = "testUserSignUp")
    public void testSignInWithValidCredentials(String email, String password, String userName) {
        logger.info("Starting test: testSignInWithValidCredentials");
        logger.debug("Email: {}, Password: {}, User Name: {}", email, password, userName);

        User existingUser = new User(email, password, userName);
        logger.info("Logging in user...");
        Response loginResponse = loginApiClient.login(existingUser);
        logger.debug("Login response: {}", loginResponse.asString());

        int statusCode = loginResponse.getStatusCode();
        Assert.assertEquals(statusCode, 200, "Sign-in request should return status 200.");
        logger.info("Sign-in was successful with status 200.");
    }

    @Test(description = "Verify returned email in sign-in response", dataProvider = "randomValidCredentials", dataProviderClass = TestDataProvider.class, dependsOnMethods = "testSignInWithValidCredentials")
    public void testReturnedEmailMatches(String email, String password, String userName) {
        logger.info("Starting test: testReturnedEmailMatches");

        User existingUser = new User(email, password, userName);
        Response loginResponse = loginApiClient.login(existingUser);
        String returnedEmail = loginResponse.jsonPath().getString("user.email");

        Assert.assertEquals(returnedEmail, email, "Email in response should match the input email.");
        logger.info("Returned email matches input email.");
    }

    @Test(description = "Verify sign-in fails with incorrect credentials", dataProvider = "invalidCredentials", dataProviderClass = TestDataProvider.class)
    public void testFailedSignInWithInvalidCredentials(String email, String password, String userName) {
        logger.info("Starting test: testSignInFailureWithInvalidCredentials");
        logger.debug("Email: {}, Password: {}", email, password);

        User invalidUser = new User(email, password);
        logger.info("Attempting login with incorrect credentials...");
        Response response = loginApiClient.login(invalidUser);
        logger.debug("Login response: {}", response.asString());

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 422, "Sign-in with wrong credentials should return status 401.");
        logger.info("Sign-in failed as expected with status 401.");
    }

    @Test(description = "Verify error message for incorrect credentials", dataProvider = "invalidCredentials", dataProviderClass = TestDataProvider.class, dependsOnMethods = "testSignInFailureWithInvalidCredentials")
    public void testErrorMessageForInvalidCredentials(String email, String password, String userName) {
        logger.info("Verifying error message for incorrect credentials...");
        User invalidUser = new User(email, password, userName);
        Response response = loginApiClient.login(invalidUser);
        String errorMessage = response.jsonPath().getString("message");
        logger.debug("Error message: {}", errorMessage);

        Assert.assertEquals(errorMessage, "Wrong email/password combination",
                "Error message for incorrect credentials should be 'Wrong email/password combination'.");
        logger.info("Error message verified successfully.");
    }
}
