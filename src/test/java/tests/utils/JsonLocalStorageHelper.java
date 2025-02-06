package tests.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class JsonLocalStorageHelper {
    public static String getLoggedUserJson(String token) throws Exception {
        Map<String, String> user = (Map<String, String>) TestDataProvider.TEST_DATA.get(TestDataProvider.VALID_USER_KEY);
        Map<String, Object> loggedUser = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token "+ token );

        Map<String, String> loggedUserData = new HashMap<>();
        loggedUserData.put("email",  user.get(TestDataProvider.EMAIL_KEY));
        loggedUserData.put("username", user.get(TestDataProvider.USERNAME_KEY));
        loggedUserData.put("bio", user.get(TestDataProvider.BIO_USER_KEY));
        loggedUserData.put("image", user.get(TestDataProvider.IMAGE_USER_KEY));
        loggedUserData.put("token", token);

        loggedUser.put("headers", headers);
        loggedUser.put("isAuth", true);
        loggedUser.put("loggedUser", loggedUserData);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(loggedUser);
    }
}
