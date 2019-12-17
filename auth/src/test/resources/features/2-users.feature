Feature: Users list

  Scenario: can't get the user list if unauthenticated

    When user gets users
    Then the get 'FAILS'

  Scenario: can get the user list if authenticated
    Given user is authenticated as email "filipe@mail.co" with password "password"
    When user gets users
    Then the get 'IS SUCCESSFUL'
    Then response is not empty
