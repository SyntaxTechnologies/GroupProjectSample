Feature: Validate Employee Creation API

  Background:
    Given a valid token is available for email "Andrew95@gmail.com" and password "Password1234"

  @employeeCreation @api
  Scenario Outline: Validate employee creation with message response
    Given a payload is prepared with the following details:
      | emp_firstname  | emp_lastname  | emp_middle_name | emp_gender | emp_birthday | emp_status | emp_job_title |
      | <emp_firstname> | <emp_lastname> | <emp_middle_name> | <emp_gender> | <emp_birthday> | <emp_status> | <emp_job_title> |
    When the post request is sent to create an employee
    Then the status code should be <statusCode>
    And the response "Message" should be "<responseMessage>"

    Examples:
      | emp_firstname | emp_lastname | emp_middle_name | emp_gender | emp_birthday | emp_status | emp_job_title | statusCode | responseMessage                                     |
      | Mark          | Scott        | JJ              | M          | 1995-11-06   | Trainee    | QA Manager    | 201        | Employee Created                                   |
      | Matthew       | James        | Perry           | X          | 2000-11-06   | Employed   | QA Manager    | 400        | enter M for male enter F for female               |
      | Mark          | Scott        | JJ              | M          | 1995-11-06   |      | QA Manager    | 400      | Status is Empty |
       |               |              |                  |           |              |      |               | 400         |      First Name is Empty              |


  @employeeCreation @api
  Scenario Outline: Validate employee creation with error response
    Given a payload is prepared with the following details:
      | emp_firstname  | emp_lastname  | emp_middle_name | emp_gender | emp_birthday | emp_status | emp_job_title |
      | <emp_firstname> | <emp_lastname> | <emp_middle_name> | <emp_gender> | <emp_birthday> | <emp_status> | <emp_job_title> |
    When the post request is sent to create an employee
    Then the status code should be <statusCode>
    And the "Error" should be "<responseMessage>"

    Examples:
      | emp_firstname | emp_lastname | emp_middle_name | emp_gender | emp_birthday | emp_status | emp_job_title | statusCode | responseMessage                                     |
      | Mark          | Scott        | JJ              | M          | 1995/11/06   | Trainee    | QA Manager    | 400        | Please Enter a Valid Date Format. Example \"yyyy-mm-dd\"  |
      |               | Scott        | JJ              | M          | 1995-11-06   | Trainee    | QA Manager    | 400        |Enter Full Payload                            |
