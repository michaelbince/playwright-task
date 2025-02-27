package tests.api;

import api.client.LoginApiClient;
import api.client.UserApiClient;
import api.models.User;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.utils.CSVReader;

import java.io.IOException;

public class ParameterizedUserSignInTest extends APIBaseTest {

    private UserApiClient userApiClient;
    private LoginApiClient loginApiClient;

    @BeforeClass
    public void setup() {
        userApiClient = new UserApiClient();
        loginApiClient = new LoginApiClient();
    }

    @DataProvider(name = "csvValidCredentials")
    public Object[][] provideCsvValidCredentials() throws IOException {
        return CSVReader.readCSV("src/test/resources/valid_user_data.csv");
    }

    @DataProvider(name = "csvInvalidCredentials")
    public Object[][] provideCsvInvalidCredentials() throws IOException {
        return CSVReader.readCSV("src/test/resources/invalid_user_data.csv");
    }

    @Test(description = "Verify successful sign-in with correct credentials (CSV)",
            dataProvider = "csvValidCredentials")
    public void testSuccessfulSignInWithValidCredentialsCsv(String email, String password, String userName) {
        User existingUser = new User(email, password, userName);
        userApiClient.signUp(existingUser);
        Response loginResponse = loginApiClient.login(existingUser);
        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Sign-in request should return status 200.");
        Assert.assertEquals(loginResponse.jsonPath().getString("user.email"), email, "Email in response should match the input email.");
    }

    @Test(description = "Verify failed sign-in with incorrect credentials (CSV)",
            dataProvider = "csvInvalidCredentials")
    public void testSignInFailureWithInvalidCredentialsCsv(String email, String password, String errorMessage) {
        User invalidUser = new User(email, password);
        Response response = loginApiClient.login(invalidUser);
        Assert.assertEquals(response.getStatusCode(), 422, "Sign-in request with wrong credentials should return status 422.");
        Assert.assertTrue(response.jsonPath().getList("errors.body").contains(errorMessage),
                "Error message for incorrect credentials should be '" + errorMessage + "'.");
    }

    @Test(description = "Verify successful sign-in with default credentials",
            dataProvider = "defaultCredentials")
    public void testSuccessfulSignInWithDefaultCredentials(String email, String password, String userName) {
        User existingUser = new User(email, password, userName);
        userApiClient.signUp(existingUser);
        Response loginResponse = loginApiClient.login(existingUser);
        Assert.assertEquals(loginResponse.getStatusCode(), 200, "Sign-in request should return status 200.");
        Assert.assertEquals(loginResponse.jsonPath().getString("user.email"), email, "Email in response should match the input email.");
    }

    @DataProvider(name = "defaultCredentials")
    public Object[][] provideDefaultCredentials() {
        return new Object[][]{
                {"default@example.com", "defaultPass", "defaultUser"},
                {"test@example.com", "testPass", "testUser"}
        };
    }
}