Feature: Login

  Scenario: Loging in with valid email and password works
    Given user wants to login with the following attributes
      | email          | password |
      | filipe@mail.co | password |

    When user login 'WITH ALL REQUIRED FIELDS'
    Then the request 'IS SUCCESSFUL'

  Scenario: Loging in with the wrong password fails
    Given user wants to login with the following attributes
      | email          | password |
      | filipe@mail.co | password2 |

    When user login 'WITH ALL REQUIRED FIELDS'
    Then the request 'FAILS'

  Scenario: Loging in with the wrong email fails
    Given user wants to login with the following attributes
      | email          | password |
      | filipe@mai3l.co | password |

    When user login 'WITH ALL REQUIRED FIELDS'
    Then the request 'FAILS'

  Scenario: Loging in with the wrong email and password fails
    Given user wants to login with the following attributes
      | email          | password |
      | filipe@mai4l.co | password4 |

    When user login 'WITH ALL REQUIRED FIELDS'
    Then the request 'FAILS'