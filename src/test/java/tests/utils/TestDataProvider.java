package tests.utils;

import org.testng.annotations.DataProvider;
import utils.TestDataLoader;
import java.util.Map;

public class TestDataProvider {

    public static final String TEST_DATA_FILE = "src/test/resources/testdata/userTestData.json";
    public static final Map<String, Object> TEST_DATA = TestDataLoader.loadTestData(TEST_DATA_FILE);
    public static final String EMAIL_KEY = "email";
    public static final String PASSWORD_KEY = "password";
    public static final String USERNAME_KEY = "username";
    public static final String ERROR_MESSAGE_KEY = "errorMessage";
    public static final String VALID_USER_KEY = "validUser";
    public static final String INVALID_USER_KEY = "invalidUser";

    public static final String BIO_USER_KEY = "bio";

    public static final String IMAGE_USER_KEY = "image";

    @DataProvider(name = "validCredentials")
    public static Object[][] provideValidCredentials() {
        Map<String, String> validUser = (Map<String, String>) TEST_DATA.get(VALID_USER_KEY);
        return new Object[][]{
                {validUser.get(EMAIL_KEY), validUser.get(PASSWORD_KEY), validUser.get(USERNAME_KEY)}
        };
    }

    @DataProvider(name = "invalidCredentials")
    public static Object[][] provideInvalidCredentials() {
        Map<String, String> invalidUser = (Map<String, String>) TEST_DATA.get(INVALID_USER_KEY);
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

    @DataProvider(name = "validCredentialsToUpdate")
    public static Object[][] provideValidCredentialsToUpdate() {
        Map<String, String> validUser = (Map<String, String>) TEST_DATA.get(VALID_USER_KEY);
        return new Object[][]{
                {validUser.get(EMAIL_KEY), validUser.get(PASSWORD_KEY),
                        validUser.get(USERNAME_KEY),validUser.get(BIO_USER_KEY), validUser.get(IMAGE_USER_KEY) }
        };
    }

}
