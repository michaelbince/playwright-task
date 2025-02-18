package tests.api;

import api.client.UserApiClient;
import api.models.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.utils.TestDataProvider;

public class UserSignUpTest extends APIBaseTest {
    private static final Logger logger = LogManager.getLogger(UserSignUpTest.class);
    private UserApiClient userApiClient;

    @BeforeClass
    public void setup() {
        logger.info("Setting up UserSignUpTest");
        userApiClient = new UserApiClient();
        logger.info("Setup completed");
    }

    @Test(description = "Verify successful user sign-up with randomly generated username and email",
            dataProvider = "randomValidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testUserSignUp(String email, String password, String userName) {
        logger.info("Starting test: testUserSignUp");
        logger.debug("Email: {}, Password: {}, User Name: {}", email, password, userName);

        User newUser = new User(email, password, userName);

        logger.info("Signing up user...");
        Response response = userApiClient.signUp(newUser);
        logger.debug("Sign-up response: {}", response.asString());
        logger.info("User signed up.");

        int statusCode = response.getStatusCode();
        logger.debug("Status code: {}", statusCode);
        Assert.assertEquals(statusCode, 201, "User sign-up should return status code 201.");
        logger.info("Status code is 201.");

        logger.info("Test testUserSignUp finished successfully.");
    }

    @Test(description = "Verify user sign-up fails when email is already registered",
            dataProvider = "validCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testUserSignUpWithAlreadyRegisteredEmail(String email, String password, String userName) {
        logger.info("Starting test: testUserSignUpWithAlreadyRegisteredEmail");
        logger.debug("Email: {}, Password: {}, User Name: {}", email, password, userName);

        User firstUser = new User(email, password, userName);
        logger.info("Signing up the first user...");
        userApiClient.signUp(firstUser);
        logger.info("First user signed up.");

        logger.info("Attempting sign-up with the same email...");
        Response response = userApiClient.signUp(firstUser);
        logger.debug("Sign-up response: {}", response.asString());
        logger.info("Sign-up attempt finished.");

        int statusCode = response.getStatusCode();
        logger.debug("Status code: {}", statusCode);
        Assert.assertEquals(statusCode, 422, "Expected status code 422 for already registered user.");
        logger.info("Status code is 422.");

        String errorMessage = response.jsonPath().getString("errors.body[0]");
        logger.debug("Error message: {}", errorMessage);
        Assert.assertEquals(errorMessage, "Email already exists.. try logging in",
                "The error message for an already registered email is not as expected.");
        logger.info("Error message is correct.");

        logger.info("Test testUserSignUpWithAlreadyRegisteredEmail finished (expected to fail).");
    }
}