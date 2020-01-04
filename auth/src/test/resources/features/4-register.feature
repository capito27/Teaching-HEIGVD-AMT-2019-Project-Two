Feature: Register

  Scenario: User can't register if unauthenticated
    When register with the following attributes
      | email        | firstname | lastname | password | isAdmin |
      | toto@mail.co | to        | to       | password | true    |
    Then the request 'FAILS'

  Scenario: User can't register if authenticated but not admin
    Given user is authenticated as email "user@mail.co" with password "password"
    When register with the following attributes
      | email        | firstname | lastname | password | isAdmin |
      | toto@mail.co | to        | to       | password | true    |
    Then the request 'FAILS'

  Scenario: User can't register if authenticated as admin but duplicated
    Given user is authenticated as email "admin@mail.co" with password "password"
    When register with the following attributes
      | email        | firstname | lastname | password | isAdmin |
      | user@mail.co | user      | name     | password | true    |
    Then the request 'FAILS'

  Scenario: User can register if authenticated as admin and unique mail
    Given user is authenticated as email "admin@mail.co" with password "password"
    When register with the following attributes
      | email        | firstname | lastname | password | isAdmin |
      | toto@mail.co | to        | to       | password | true    |
    Then the request 'IS SUCCESSFUL'
    Then user can authenticate as email "toto@mail.co" with password "password"
    Then remove user with email "toto@mail.co"
    Then user can't authenticate as email "toto@mail.co" with password "password"
