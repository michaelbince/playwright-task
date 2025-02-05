package tests.api;

import api.client.LoginApiClient;
import api.client.UserApiClient;
import api.client.UserUpdateApiClient;
import api.models.UpdateUser;
import api.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.utils.TestDataProvider;

public class UserUpdateTest extends APIBaseTest {
    private UserApiClient userApiClient;
    private LoginApiClient loginApiClient;

    private UserUpdateApiClient updateClient;

    @BeforeClass
    public void setup() {
        userApiClient = new UserApiClient();
        loginApiClient = new LoginApiClient();
        updateClient = new UserUpdateApiClient();
    }

    @Test(description = "Verify that an authenticated user can successfully update their profile",
            dataProvider = "validCredentialsToUpdate",
            dataProviderClass = TestDataProvider.class)
    public void testAuthenticatedUserCanUpdateProfile(String email, String password, String userName, String bio, String image) {
        User existingUser = new User(email, password, userName);

        Response loginResponse = loginApiClient.login(existingUser);

        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Login request should return status code 200.");
        String authToken = loginResponse.jsonPath().getString("user.token");

        UpdateUser updatedUser = new UpdateUser(
                email,
                password,
                userName,
                bio,
                image
        );

        Response updateResponse = updateClient.updateUser(updatedUser, authToken);

        Assert.assertEquals(updateResponse.getStatusCode(), 200, "User profile update should return status code 200.");
    }



}
