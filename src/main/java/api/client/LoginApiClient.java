package api.client;

import api.models.User;
import api.models.UserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class LoginApiClient {
    private static final String LOGIN_ENDPOINT = "/users/login";

    public Response login(User userData) {
        UserRequest requestBody = new UserRequest(userData);

        return RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(LOGIN_ENDPOINT);
    }
}
