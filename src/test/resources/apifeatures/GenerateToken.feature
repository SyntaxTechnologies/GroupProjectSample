Feature: Generate Authentication Token

  Background:
    Given request is prepared with name "Andrew", email "Andrew95@gmail.com" and password "Password1234"
    When Post call is made

  @generateToken @errorMessage @api @invalidCredentials @api
  Scenario Outline: Validate token generation with missing or incorrect fields
    Given request to generate token with email "<email>" and password "<password>" is prepared
    When POST request to generate token is called
    Then response status code is <statusCode>
    And error message "<errorMessage>" is displayed in response body

    Examples:
      | email                      | password      | statusCode | errorMessage                         |
      | Andrew95@gmail.com    |               | 400        | Please fill all inputs               |
      |                            | Password123   | 400        | Please fill all inputs               |
      | And@gmail.com             | Password123   | 400        | Email or Password is incorrect.      |
      | Andrew95@gmail.com   | Wrong111      | 400        | Email or Password is incorrect.      |

  @generateToken @api @validCredentials  @api
  Scenario: Generate token with valid credentials
    Given request to generate token with email "Andrew95@gmail.com" and password "Password1234" is prepared
    When POST request to generate token is called
    Then response status code is 200
    And token has to match JWT format
