package apiSteps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.APIConstants;
import utils.APIPayloadConstants;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CreateEmployeeSteps {

    /**
     * Prepares the payload for employee creation using provided details.
     */
    @Given("a payload is prepared with the following details:")
    public void preparePayload(DataTable dataTable) {
        List<Map<String, String>> employeeDetailsList = dataTable.asMaps(String.class, String.class);
        Map<String, String> employeeDetails = employeeDetailsList.get(0);

        String payload = APIPayloadConstants.createEmployeeJsonPayloadDynamic(
                employeeDetails.get("emp_firstname"),
                employeeDetails.get("emp_lastname"),
                employeeDetails.get("emp_middle_name"),
                employeeDetails.get("emp_gender"),
                employeeDetails.get("emp_birthday"),
                employeeDetails.get("emp_status"),
                employeeDetails.get("emp_job_title")
        );

        APIConstants.request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .header(APIConstants.HEADER_AUTHORIZATION_KEY, APIConstants.token)
                .body(payload);
    }

    /**
     * Prepares an empty payload for negative test cases.
     */
    @Given("an empty payload is prepared")
    public void prepareEmptyPayload() {
        APIConstants.request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE_KEY, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .header(APIConstants.HEADER_AUTHORIZATION_KEY, APIConstants.token)
                .body("{}");
    }

    /**
     * Sends the POST request to create an employee.
     */
    @When("the post request is sent to create an employee")
    public void sendPostRequest() {
        APIConstants.response = APIConstants.request.when().post(APIConstants.CREATE_EMPLOYEE);
        APIConstants.response.prettyPrint();
    }

    /**
     * Validates the status code of the response.
     */
    @Then("the status code should be {int}")
    public void validateStatusCode(int expectedStatusCode) {
        int actualStatusCode = APIConstants.response.getStatusCode();
        Assert.assertEquals("Status code mismatch!", expectedStatusCode, actualStatusCode);
        System.out.println("Status code validated: " + actualStatusCode);
    }

    /**
     * Validates the dynamic response message or error.
     */
    @Then("the response {string} should be {string}")
    public void validateResponse(String responseKey, String expectedMessage) {
        String actualMessage = APIConstants.response.jsonPath().getString(responseKey);
        Assert.assertEquals("Response mismatch for key: " + responseKey, expectedMessage, actualMessage);
        System.out.println("Response " + responseKey + " validated: " + actualMessage);
    }

    @Then("the {string} should be {string}")
    public void validateErrorResponse(String responseKey, String expectedMessage) {
        // Extract the value using the dynamic response key
        String actualMessage = APIConstants.response.jsonPath().getString(responseKey);

        // Validate the extracted message against the expected message
        Assert.assertEquals("Mismatch in " + responseKey + " response!", expectedMessage, actualMessage);

        // Log validation success
        System.out.println(responseKey + " validated successfully: " + actualMessage);
    }
}
