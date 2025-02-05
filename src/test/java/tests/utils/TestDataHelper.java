package tests.utils;

import com.github.javafaker.Faker;

public class TestDataHelper {

    private static final Faker faker = new Faker();

    public static String generateUniqueUsername() {
        return faker.name().username();
    }

    public static String generateUniqueEmail() {
        return faker.internet().emailAddress();
    }
    public static String generateRandomPassword() {
        return faker.internet().password(8, 16);
    }

}
