package api.client;

import api.models.User;
import api.models.UserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserApiClient {
    private static final String USERS_ENDPOINT = "/users";

    public Response signUp(User userData) {
        UserRequest requestBody = new UserRequest(userData);

        return RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(USERS_ENDPOINT);
    }

}

