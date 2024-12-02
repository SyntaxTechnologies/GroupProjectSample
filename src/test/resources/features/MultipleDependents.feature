Feature: Multiple Dependents

  Background:
    Given user is logged in with admin credentials
    When admin user clicks on PIM button
    And admin user clicks on Add Employee button
    And admin user enters "Bug" "Buster" and "Group"
    And admin user click on save button

  @Maria
  Scenario: Adding employee's multiple dependents
    When admin user clicks on Dependents and on add btn
    And admin user enters "Ladybug" selects Relationship and DOB and clicks on save btn
    Then added information is displayed and editable "Ladybug"



  Scenario: Error message while adding incomplete information
    When admin user clicks on Dependents and on add btn
    And admin user enters DOB and clicks on save btn
    Then the user will see "Required" error message