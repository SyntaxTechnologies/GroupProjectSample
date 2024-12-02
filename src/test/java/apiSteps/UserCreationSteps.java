package apiSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import static io.restassured.RestAssured.given;

public class UserCreationSteps {


    private static String uniqueEmail; // Shared across steps to handle email reuse



    @Given("request with name {string}, email {string} and password {string}")
    public void prepareRequestWithDynamicEmail(String name, String emailPrefix, String password) {
        // Generate the unique email only if it's not already generated
        if (uniqueEmail == null) {
            String truncatedPrefix = emailPrefix.length() > 10 ? emailPrefix.substring(0, 10) : emailPrefix;
            uniqueEmail = truncatedPrefix + System.currentTimeMillis() % 100000 + "@ex.com";
            System.out.println("Generated new unique email: " + uniqueEmail);
        } else {
            System.out.println("Reusing existing unique email: " + uniqueEmail);
        }

        APIConstants.request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body(APIPayloadConstants.createUser(name, uniqueEmail, password));

        System.out.println("Prepared request with email: " + uniqueEmail);
    }

    @Given("request is prepared with name {string}, email {string} and password {string}")
    public void prepareRequest(String name, String email, String password) {
        APIConstants.request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body(APIPayloadConstants.createUser(name, email, password));

        System.out.println("Prepared request with email: " + email);
    }


    @When("Post call is made")
    public void makePostCall() {
        APIConstants.response = APIConstants.request.when().post(APIConstants.CREATE_USER);
        System.out.println("Response received: " + APIConstants.response.prettyPrint());
    }

    @Then("the status code for this is {int}")
    public void verifyStatusCode(int expectedStatusCode) {
        int actualStatusCode = APIConstants.response.getStatusCode();
        System.out.println("Verifying status code: Expected = " + expectedStatusCode + ", Actual = " + actualStatusCode);
        Assert.assertEquals("Status code does not match", expectedStatusCode, actualStatusCode);
    }

    @Then("the confirmation message appears {string}")
    public void verifyConfirmationMessage(String expectedMessage) {
        String actualMessage = APIConstants.response.jsonPath().getString("Message");
        System.out.println("Verifying confirmation message: Expected = " + expectedMessage + ", Actual = " + actualMessage);
        Assert.assertEquals("Confirmation message does not match", expectedMessage, actualMessage);
    }

    @Given("request is prepared with name {string}, email {string}, and password {string}")
    public void prepareRequestWithInvalidOrMissingFields(String name, String email, String password) {
        APIConstants.request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body(APIPayloadConstants.createUser(name, email, password));
        System.out.println("Prepared request with invalid/missing data: " + APIConstants.request.log().all());
    }

    @Then("the error message appears {string}")
    public void verifyErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = null;

        if (APIConstants.response.jsonPath().get("Message") != null) {
            actualErrorMessage = APIConstants.response.jsonPath().getString("Message");
        } else if (APIConstants.response.jsonPath().get("condition") != null &&
                APIConstants.response.jsonPath().get("data") != null) {
            actualErrorMessage = APIConstants.response.jsonPath().getString("data");
        }

        System.out.println("Verifying error message: Expected = " + expectedErrorMessage + ", Actual = " + actualErrorMessage);
        Assert.assertEquals("Error message does not match", expectedErrorMessage, actualErrorMessage);
    }
}
