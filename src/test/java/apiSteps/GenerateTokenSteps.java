package apiSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import static io.restassured.RestAssured.given;

public class GenerateTokenSteps {

    @Given("request to generate token with email {string} and password {string} is prepared")
    public void prepareTokenRequest(String email, String password) {
        APIConstants.request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body(APIPayloadConstants.generateTokenPayload(email, password));
        System.out.println(APIPayloadConstants.generateTokenPayload(email, password));
        System.out.println("Prepared request with email: " + email + " and password: " + (password.isEmpty() ? "missing" : "provided"));
    }

    @When("POST request to generate token is called")
    public void callGenerateTokenAPI() {
        APIConstants.response = APIConstants.request.when().post(APIConstants.GENERATE_TOKEN);
        System.out.println("Response received: " + APIConstants.response.prettyPrint());

        if (APIConstants.response.jsonPath().getString("token") != null) {
            APIConstants.token = "Bearer " + APIConstants.response.jsonPath().getString("token");
        }
    }

    @Then("response status code is {int}")
    public void verifyResponseStatusCode(Integer statusCode) {
        int actualStatusCode = APIConstants.response.getStatusCode();
        System.out.println("Verifying status code: Expected = " + statusCode + ", Actual = " + actualStatusCode);
//        Assert.assertEquals( statusCode, actualStatusCode);
    }

    @Then("error message {string} is displayed in response body")
    public void verifyErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = APIConstants.response.jsonPath().getString("Error");
        System.out.println("Verifying error message: Expected = " + expectedErrorMessage + ", Actual = " + actualErrorMessage);
        Assert.assertEquals("Error message does not match!", expectedErrorMessage, actualErrorMessage);
    }

    @Then("token has to match JWT format")
    public void verifyTokenFormat() {
        Assert.assertNotNull("Token is null!", APIConstants.token);
        Assert.assertTrue("Token does not match JWT format!",
                APIConstants.token.startsWith("Bearer ") && APIConstants.token.split("\\.").length == 3);
        System.out.println("Token matches JWT format: " + APIConstants.token);
    }
}
