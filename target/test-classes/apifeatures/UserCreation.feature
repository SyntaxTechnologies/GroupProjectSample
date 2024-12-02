Feature: API User Creation

  # Scenarios for validating user creation functionality in the API
  @api
  Scenario Outline: User Creation Flow
    Given request with name "<name>", email "<emailPrefix>" and password "<password>"
    When Post call is made
    Then the status code for this is <statusCode>
    And the error message appears "<message>"

    Examples:
      | name   | emailPrefix | password      | statusCode | message                                           |
      | Adam   | testuser    | Password123   | 201        | User Created                                     |
      | Adam   | testuser    | Password123   | 200        | The email address you have entered is already registered |


  @api
  Scenario Outline: User Creation with Invalid or Missing Fields
    Given request is prepared with name "<name>", email "<email>" and password "<password>"
    When Post call is made
    Then the status code for this is <status_code>
    And the error message appears "<error_message>"

    Examples:
      | name  | email                  | password     | status_code | error_message                                       |
      | Adam  | Stegargdfdsfmail.com   | Password123  | 400         | Invalid Email                                      |
      | Adam  | Stegar@gdfdsfmail.com  |              | 400         | Please fill all inputs                             |
      |       | Stegar@gdfdsfmail.com  | Password123  | 400         | Please fill all inputs                             |
