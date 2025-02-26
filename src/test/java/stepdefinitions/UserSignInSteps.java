package stepdefinitions;

import api.client.LoginApiClient;
import api.models.User;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

public class UserSignInSteps {

    private User user;
    private Response response;
    private LoginApiClient loginApiClient = new LoginApiClient(); // Removed UserApiClient

    @Before // Cucumber hook
    public void setupAPI() {
        RestAssured.baseURI = "https://conduit-realworld-example-app.fly.dev/api";
    }

    @Given("a user with email {string} and password {string}")
    public void aUserWithEmailAndPassword(String email, String password) {
        user = new User(email, password);
    }

    @When("the user logs in")
    public void theUserLogsIn() {
        response = loginApiClient.login(user);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
    }

    @Then("the response should contain the email {string}")
    public void theResponseShouldContainTheEmail(String expectedEmail) {
        String returnedEmail = response.jsonPath().getString("user.email");
        Assert.assertEquals(returnedEmail, expectedEmail);
    }

    @Given("an unregistered user with email {string} and password {string}")
    public void anUnregisteredUserWithEmailAndPassword(String email, String password) {
        user = new User(email, password);
    }

    @When("the user attempts to log in")
    public void theUserAttemptsToLogIn() {
        response = loginApiClient.login(user);
    }

    @Then("the response should contain the error message {string}")
    public void theResponseShouldContainTheErrorMessage(String expectedErrorMessage) {
        Assert.assertTrue(response.jsonPath().getList("errors.body").contains(expectedErrorMessage),
                "Error message for incorrect credentials should be '" + expectedErrorMessage + "'.");
    }
}