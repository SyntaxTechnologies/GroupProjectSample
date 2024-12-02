package steps;

import io.cucumber.java.en.And;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.*;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AddEmployeeSteps extends CommonMethods {
    String random;
    public  String empID;
    public  String empName;
    String firstName, lastName;
    //
    @Given("user navigates to AddEmployeePage")
    public void user_navigates_to_AddEmployeePage() {

        dash.navigateToAddEmployee();
    }

    @When("admin user enters {string} {string} and {string}")
    public void admin_user_enters_and(String firstName, String middleName, String lastName) {
        random = randomAlphabets();
        this.firstName=firstName;
        this.lastName=lastName;
        firstName=firstName+random;
        middleName=middleName+random;
        lastName=lastName+random;
        empName = firstName+" "+middleName+" "+lastName;
        sendText(addEmployee.firstName, firstName);
        sendText(addEmployee.middleName, middleName);
        sendText(addEmployee.lastName, lastName);
        sendText(addEmployee.photograph, Constants.BUG_BUSTERS_PICTURE_FILEPATH);
        empID = addEmployee.empID.getAttribute("value");
    }

    @When("admin user click on save button")
    public void admin_user_click_on_save_button() {

        click(addEmployee.saveBtn);
    }

    @Then("employee {string} added successfully")
    public void employee_added_successfully(String username) {
        Assert.assertEquals("Assertion Failed!", username+random, pDetails.firstName.getAttribute("value"));
    }







    @And("user deletes employee id")
    public void userDeletesEmployeeId() {
        addEmployee.empID.clear();
    }

    @And("user clicks on create login checkbox")
    public void userClicksOnCreateLoginCheckbox() {
        jsClick(addEmployee.checkBox);
    }

    @And("user enters login credentials as {string} and {string}")
    public void userEntersLoginCredentialsAsAnd(String username, String password) {
        String random = randomAlphabets();
        sendText(addEmployee.createUsername, username+random);
        sendText(addEmployee.createPassword, password);
        sendText(addEmployee.confirmPassword, password);
    }

    @And("user captures employee id")
    public void userCapturesEmployeeId() {

        empID = addEmployee.empID.getAttribute("value");
    }

    @When("user do not enter either firstname or lastname show Require field missing error")
    public void user_do_not_enter_either_firstname_or_lastname(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> employeesInformation = dataTable.asMaps();

        for (Map<String, String> employeeInfo:employeesInformation
        ) {
            //getting the values against the key in map
            String firstNameValue = employeeInfo.get("firstName");
            String middleNameValue = employeeInfo.get("middleName");
            String lastNameValue = employeeInfo.get("lastName");

            if (firstNameValue  == null ) {
                firstNameValue = "";
            }

            if (lastNameValue == null) {
                lastNameValue = "";
            }

            //filling the name in the fields
            sendText(addEmployee.firstName,firstNameValue);
            sendText( addEmployee.middleName,middleNameValue);
            sendText(addEmployee.lastName,lastNameValue);
            click(addEmployee.saveBtn);
            WebElement actualValueWebElement;
            if(firstNameValue.isEmpty()) {
                actualValueWebElement = addEmployee.requiredFirstNameError;
            } else {
                actualValueWebElement = addEmployee.requiredLastNameError;
            }
            Assert.assertEquals("Required",actualValueWebElement.getText());
        }
    }
}
