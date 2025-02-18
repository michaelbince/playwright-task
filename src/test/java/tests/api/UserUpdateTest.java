package tests.api;

import api.client.LoginApiClient;
import api.client.UserUpdateApiClient;
import api.models.UpdateUser;
import api.models.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.utils.TestDataProvider;

public class UserUpdateTest extends APIBaseTest {

    private static final Logger logger = LogManager.getLogger(UserUpdateTest.class);
    private LoginApiClient loginApiClient;
    private UserUpdateApiClient updateClient;

    @BeforeClass
    public void setup() {
        logger.info("Setting up UserUpdateTest");
        loginApiClient = new LoginApiClient();
        updateClient = new UserUpdateApiClient();
        logger.info("Setup completed");
    }

    @Test(description = "Verify that an authenticated user can successfully update their profile",
            dataProvider = "validCredentialsToUpdate",
            dataProviderClass = TestDataProvider.class)
    public void testAuthenticatedUserCanUpdateProfile(String email, String password, String userName, String bio, String image) {
        logger.info("Starting test: testAuthenticatedUserCanUpdateProfile");
        logger.debug("Email: {}, Password: {}, User Name: {}, Bio: {}, Image: {}", email, password, userName, bio, image);

        User existingUser = new User(email, password, userName);

        logger.info("Logging in user...");
        Response loginResponse = loginApiClient.login(existingUser);
        logger.debug("Login response: {}", loginResponse.asString());
        logger.info("User logged in.");

        int loginStatusCode = loginResponse.getStatusCode();
        logger.debug("Login Status Code: {}", loginStatusCode);
        Assert.assertEquals(loginStatusCode, 200, "Login request should return status code 200.");
        logger.info("Login status code is 200.");

        String authToken = loginResponse.jsonPath().getString("user.token");
        logger.debug("Auth Token: {}", authToken);

        UpdateUser updatedUser = new UpdateUser(email, password, userName, bio, image);

        logger.info("Updating user profile...");
        Response updateResponse = updateClient.updateUser(updatedUser, authToken);
        logger.debug("Update response: {}", updateResponse.asString());
        logger.info("User profile updated.");

        int updateStatusCode = updateResponse.getStatusCode();
        logger.debug("Update Status Code: {}", updateStatusCode);
        Assert.assertEquals(updateStatusCode, 200, "User profile update should return status code 200.");
        logger.info("Update status code is 200.");

        logger.info("Test testAuthenticatedUserCanUpdateProfile finished successfully.");
    }
}