package tests.api;

import api.client.UserApiClient;
import api.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tests.utils.TestDataHelper;
import tests.utils.TestDataProvider;

public class UserSignUpTest extends APIBaseTest {
    private UserApiClient userApiClient;

    @BeforeClass
    public void setup() {
        userApiClient = new UserApiClient();
    }

    @Test(description = "Verify successful user sign-up with randomly generated username and email",
            dataProvider = "randomValidCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testUserSignUp(String email, String password, String userName)  {
        User newUser = new User(email, password, userName);

        Response response = userApiClient.signUp(newUser);

        Assert.assertEquals(response.getStatusCode(), 201, "User sign-up should return status code 201.");
    }

    @Test(description = "Verify user sign-up fails when email is already registered",
            dataProvider = "validCredentials",
            dataProviderClass = TestDataProvider.class)
    public void testUserSignUpWithAlreadyRegisteredEmail(String email, String password, String userName) {
        User firstUser = new User(email, password, userName);
        userApiClient.signUp(firstUser);

        Response response = userApiClient.signUp(firstUser);

        Assert.assertEquals(response.getStatusCode(), 422, "Expected status code 422 for already registered user.");

        String errorMessage = response.jsonPath().getString("errors.body[0]");

        Assert.assertEquals(errorMessage, "Email already exists.. try logging in",
                "The error message for an already registered email is not as expected.");
    }

}


