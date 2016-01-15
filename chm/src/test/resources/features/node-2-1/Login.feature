Feature: Login


###  Scenario 1 - Done
  Scenario: User able to log in (correct credentials)
    Given I login to application as 'user.admin'
    Then I should be logged in as 'user.admin'
