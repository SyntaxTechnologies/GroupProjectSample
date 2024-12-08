@addEmployee
Feature: Add Employee

  Background:
    Given user is logged in with admin credentials
    And user navigates to AddEmployeePage

  @smoke @sprint5 @DB
  Scenario: Add Employee
    When admin user enters "John" "J" and "Doe"
    And admin user click on save button
    Then employee "John" added successfully
    And the user deletes the employee

  @regression
  Scenario: Add Employee without employee id
    When admin user enters "Jane" "J" and "Smith"
    And user deletes employee id
    And admin user click on save button
    Then employee "Jane" added successfully
    And the user deletes the employee

  @regression
  Scenario: AddEmployee and create Login Credentials
    When admin user enters "xmen" "J" and "Xaro"
    And user clicks on create login checkbox
    And user enters login credentials as "XMEN" and ".#!!1234XMEnnNNN2323"
    And admin user click on save button
    Then employee "xmen" added successfully
    And the user deletes the employee

  @regression
  Scenario Outline: Adding multiple employees
    And admin user enters "<FirstName>" "<MiddleName>" and "<LastName>"
    And admin user click on save button
    Then employee "<FirstName>" added successfully
    And the user deletes the employee
    Examples:
      | FirstName | MiddleName | LastName |
      | Gulnam    | G          | Mazar    |
      | Rampal    | R          | Chambel  |

  @MissingRequiredEmployeeInformation102
  Scenario: missing employee information
    When user do not enter either firstname or lastname show Require field missing error
      |firstName|middleName|lastName|
      |         |ms        |Zck     |
      |Justin   |ms        |        |





