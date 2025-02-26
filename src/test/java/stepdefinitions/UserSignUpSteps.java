package stepdefinitions;

import api.client.UserApiClient;
import api.models.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import tests.utils.TestDataProvider;

public class UserSignUpSteps {

    private User user;
    private Response response;
    private UserApiClient userApiClient = new UserApiClient();

    @Before
    public void setupAPI() {
        RestAssured.baseURI = "https://conduit-realworld-example-app.fly.dev/api";
    }

    @Given("a user with random valid credentials")
    public void aUserWithRandomValidCredentials() {
        Object[][] credentials = TestDataProvider.provideRandomValidCredentials();
        user = new User((String) credentials[0][0], (String) credentials[0][1], (String) credentials[0][2]);
    }

    @Given("a registered user with valid credentials")
    public void aRegisteredUserWithValidCredentials() {
        Object[][] credentials = TestDataProvider.provideValidCredentials();
        user = new User((String) credentials[0][0], (String) credentials[0][1], (String) credentials[0][2]);
        userApiClient.signUp(user);
    }

    @When("the user signs up")
    public void theUserSignsUp() {
        response = userApiClient.signUp(user);
    }

    @When("the user signs up again")
    public void theUserSignsUpAgain() {
        response = userApiClient.signUp(user);
    }

    @Then("the User Sign-Up response status should be {int}")
    public void theUserSignUpResponseStatusShouldBe(int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
    }

    @Then("the User Sign-Up response should contain the error message {string}")
    public void theUserSignUpResponseShouldContainTheErrorMessage(String expectedErrorMessage) {
        String errorMessage = response.jsonPath().getString("errors.body[0]");
        Assert.assertEquals(errorMessage, expectedErrorMessage);
    }
}