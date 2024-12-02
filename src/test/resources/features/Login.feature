#Author: asel@syntaxtechs.com
@login
Feature: Login

  @smoke @sprint4
  Scenario Outline: Valid admin and ess login
    When user enter "<Username>" and "<Password>"
    And user click on login button
    Then user is successfully logged in

    Examples:
      | Username  | Password    |
      | Admin     | Hum@nhrm123 |


  @regression @sprint5
  Scenario Outline: Error message validation while invalid login
    When user enter "<Username>" and "<Password>"
    And user click on login button
    Then user see "<ErrorMessage>"
    Examples:
      | Username | Password   | ErrorMessage             |
      | Admin    | Admin123   | Invalid credentials      |
      | Hello    | Syntax123! | Invalid credentials      |
      | Admin    |            | Password cannot be empty |
      |          | Syntax123! | Username cannot be empty |

