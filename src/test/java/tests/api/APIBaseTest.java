package tests.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

abstract public class APIBaseTest {

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "https://conduit-realworld-example-app.fly.dev/api";
    }
}
