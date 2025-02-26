Feature: User Sign-Up

  Scenario: Successful user sign-up with randomly generated username and email
    Given a user with random valid credentials
    When the user signs up
    Then the User Sign-Up response status should be 201

  Scenario: Failed user sign-up when email is already registered
    Given a registered user with valid credentials
    When the user signs up again
    Then the User Sign-Up response status should be 422
    And the User Sign-Up response should contain the error message "Email already exists.. try logging in"