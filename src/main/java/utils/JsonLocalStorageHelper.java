package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;

public class JsonLocalStorageHelper {
    public static String getLoggedUserJson() throws Exception {
        Map<String, Object> loggedUser = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Token eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRvbV9tYXJ2b2xvQGV4YW1wbGUuY29tIiwiaWF0IjoxNzM4NzkyOTgzfQ.tZKHpttVZjlFozwJLBznF1sHC9YiY9ezKUVio-bddAY");

        Map<String, String> loggedUserData = new HashMap<>();
        loggedUserData.put("email", "tom_marvolo@example.com");
        loggedUserData.put("username", "Tom Marvolo Riddle");
        loggedUserData.put("bio", "I CANNOT BE NAME");
        loggedUserData.put("image", "www.my-voldi-photo-url.com");
        loggedUserData.put("token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InRvbV9tYXJ2b2xvQGV4YW1wbGUuY29tIiwiaWF0IjoxNzM4NzkyOTgzfQ.tZKHpttVZjlFozwJLBznF1sHC9YiY9ezKUVio-bddAY");

        loggedUser.put("headers", headers);
        loggedUser.put("isAuth", true);
        loggedUser.put("loggedUser", loggedUserData);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(loggedUser);
    }
}
