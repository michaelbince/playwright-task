package api.client;

import api.models.UpdateUser;
import api.models.UpdateUserRequest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UserUpdateApiClient {
    private static final String USER_ENDPOINT = "/user";


    public Response updateUser(UpdateUser updatedUserData, String authToken) {
        RequestSpecification request = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Token " + authToken)
                .body(new UpdateUserRequest(updatedUserData)).log().all();;

        return request.when().put(USER_ENDPOINT);
    }
}
