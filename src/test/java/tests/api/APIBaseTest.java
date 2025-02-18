package tests.api;

import com.epam.reportportal.testng.ReportPortalTestNGListener;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Listeners(ReportPortalTestNGListener.class)
abstract public class APIBaseTest {

    @BeforeClass
    public void setupAPI() {
        RestAssured.baseURI = "https://conduit-realworld-example-app.fly.dev/api";
    }
}
