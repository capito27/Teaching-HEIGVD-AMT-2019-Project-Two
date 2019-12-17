Feature: Admins

  Scenario: can't update someone's password if authenticated but wrong repeatPass
    Given user is authenticated as email "admin@mail.co" with password "password"
    When admin update "filipe@mail.co" password with the following attributes
      | newPass   | repeatPass |
      | password1 | password   |
    Then the request 'FAILS'

  Scenario: can't update the current password if authenticated but wrong email
    Given user is authenticated as email "admin@mail.co" with password "password"
    When admin update "2filipe@mail.co" password with the following attributes
      | newPass   | repeatPass |
      | password1 | password1  |
    Then the request 'FAILS'

  Scenario: can update the current password if authenticated
    Given user is authenticated as email "admin@mail.co" with password "password"
    When admin update "filipe@mail.co" password with the following attributes
      | newPass   | repeatPass |
      | password1 | password1  |
    Then the request 'IS SUCCESSFUL'
    Then user can authenticate as email "filipe@mail.co" with password "password1"
    Then revert admin password update of user "filipe@mail.co" to "password"
    Then user can authenticate as email "admin@mail.co" with password "password"