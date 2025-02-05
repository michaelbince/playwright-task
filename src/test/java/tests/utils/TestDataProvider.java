package tests.utils;

import org.testng.annotations.DataProvider;
import utils.TestDataLoader;
import java.util.Map;

public class TestDataProvider {

    private static final String TEST_DATA_FILE = "src/test/resources/testdata/userTestData.json";
    private static final Map<String, Object> testData = TestDataLoader.loadTestData(TEST_DATA_FILE);
    private static final String EMAIL_KEY = "email";
    private static final String PASSWORD_KEY = "password";
    private static final String USERNAME_KEY = "username";
    private static final String ERROR_MESSAGE_KEY = "errorMessage";
    private static final String VALID_USER_KEY = "validUser";
    private static final String INVALID_USER_KEY = "invalidUser";

    @DataProvider(name = "validCredentials")
    public static Object[][] provideValidCredentials() {
        Map<String, String> validUser = (Map<String, String>) testData.get(VALID_USER_KEY);
        return new Object[][]{
                {validUser.get(EMAIL_KEY), validUser.get(PASSWORD_KEY), validUser.get(USERNAME_KEY)}
        };
    }

    @DataProvider(name = "invalidCredentials")
    public static Object[][] provideInvalidCredentials() {
        Map<String, String> invalidUser = (Map<String, String>) testData.get(INVALID_USER_KEY);
        return new Object[][]{
                {invalidUser.get(EMAIL_KEY), invalidUser.get(PASSWORD_KEY), invalidUser.get(ERROR_MESSAGE_KEY)}
        };
    }

    @DataProvider(name = "randomValidCredentials")
    public static Object[][] provideRandomValidCredentials() {
        return new Object[][]{
                {TestDataHelper.generateUniqueEmail(), TestDataHelper.generateRandomPassword(), TestDataHelper.generateUniqueUsername()}
        };
    }
}
