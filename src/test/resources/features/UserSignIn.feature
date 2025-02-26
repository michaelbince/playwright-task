Feature: User Sign-In

  Scenario: Successful sign-in with valid credentials
    Given a user with email "dayle.robel@hotmail.com" and password "3sgxgmwuoon2ag"
    When the user logs in
    Then the response status should be 200
    And the response should contain the email "dayle.robel@hotmail.com"

  Scenario: Failed sign-in with incorrect credentials
    Given an unregistered user with email "tom_marvolo@example.com" and password "wrong_password"
    When the user attempts to log in
    Then the response status should be 422
    And the response should contain the error message "Wrong email/password combination"