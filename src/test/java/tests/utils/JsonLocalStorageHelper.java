package tests.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import utils.TestDataLoader;
import java.util.HashMap;
import java.util.Map;

public class JsonLocalStorageHelper {
    private static final String TEST_DATA_FILE = "src/test/resources/testdata/userTestData.json";
    private static final Map<String, Object> testData = TestDataLoader.loadTestData(TEST_DATA_FILE);
    private static final String VALID_USER_KEY = "validUser";
    private static final String EMAIL_KEY = "email";
    private static final String USERNAME_KEY = "username";
    private static final String BIO_USER_KEY = "bio";
    private static final String IMAGE_USER_KEY = "image";

    public static String getLoggedUserJson(String token) throws Exception {
        Map<String, String> user = (Map<String, String>) testData.get(VALID_USER_KEY);
        Map<String, Object> loggedUser = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token "+ token );

        Map<String, String> loggedUserData = new HashMap<>();
        loggedUserData.put("email",  user.get(EMAIL_KEY));
        loggedUserData.put("username", user.get(USERNAME_KEY));
        loggedUserData.put("bio", user.get(BIO_USER_KEY));
        loggedUserData.put("image", user.get(IMAGE_USER_KEY));
        loggedUserData.put("token", token);

        loggedUser.put("headers", headers);
        loggedUser.put("isAuth", true);
        loggedUser.put("loggedUser", loggedUserData);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(loggedUser);
    }
}
