package stepdefs;

import io.cucumber.spring.CucumberContextConfiguration;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.example.service.OrderService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

@CucumberContextConfiguration
@SpringBootTest(classes = OrderService.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
public class MyStepdefs {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

    RequestSpecification request;

    /*@Given("a vendor exists with a name of {string}")
    public void a_vendor_exists_with_a_name_of(String name) {

        String bodyTxt = "{ \"Name\" : \""+ name + "\" , \"Country\" : \"Turkey\" }";
        //.body("{ \"Name\" : \"Hypersonic\", \"Country\" : \"Turkey\" }")

        Response postResponse = RestAssured.given().port(port).contentType("application/json")
                .body(bodyTxt)
                .when()
                .post("/api/orders");
        System.out.println("response of post: " + postResponse.prettyPrint());
        postResponse.then()
                .statusCode(HttpStatus.CREATED.value());


    }*/

    @Given("Order list API is provided")
    public void orderListAPIIsProvided() throws Exception {
        request = RestAssured.given().port(port);
    }

    Response response;

    @When("User call order list API")
    public void userCallOrderListAPI() throws Exception {
        JSONObject requestParams = new JSONObject();
        request.header("Content-Type", "application/json");
        System.out.print(request.body(requestParams.toString()));
        response = request.post("/api/orders");
        System.out.println("response of post: " + response.prettyPrint());
    }

    @Then("list will be shown")
    public void listWillBeShown() {
        throw new PendingException();
    }

    @Given("Order get API is provided")
    public void orderGetAPIIsProvided() {
        throw new PendingException();
    }

    @When("User call order get API")
    public void userCallOrderGetAPI() {
        throw new PendingException();
    }

    @Then("Specific order info will be shown")
    public void specificOrderInfoWillBeShown() {
        throw new PendingException();
    }
}
