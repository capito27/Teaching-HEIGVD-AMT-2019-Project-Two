Feature: Users

  Scenario: can't get the user list if unauthenticated
    When user gets users
    Then the request 'FAILS'

  Scenario: can get the user list if authenticated
    Given user is authenticated as email "filipe@mail.co" with password "password"
    When user gets users
    Then the request 'IS SUCCESSFUL'
    Then response is not empty

  Scenario: can't update the current password if unauthenticated
    When user update password with the following attributes
      | newPass   | repeatPass | oldPass  |
      | password1 | password1  | password |
    Then the request 'FAILS'

  Scenario: can't update the current password if authenticated but wrong oldPass
    Given user is authenticated as email "filipe@mail.co" with password "password"
    When user update password with the following attributes
      | newPass   | repeatPass | oldPass   |
      | password1 | password1  | password1 |
    Then the request 'FAILS'

  Scenario: can't update the current password if authenticated but wrong repeatPass
    Given user is authenticated as email "filipe@mail.co" with password "password"
    When user update password with the following attributes
      | newPass   | repeatPass | oldPass  |
      | password1 | password   | password |
    Then the request 'FAILS'

  Scenario: can update the current password if authenticated
    Given user is authenticated as email "filipe@mail.co" with password "password"
    When user update password with the following attributes
      | newPass   | repeatPass | oldPass  |
      | password1 | password1  | password |
    Then the request 'IS SUCCESSFUL'
    Then user can authenticate as email "filipe@mail.co" with password "password1"
    Then revert user password update
    Then user can authenticate as email "filipe@mail.co" with password "password"
