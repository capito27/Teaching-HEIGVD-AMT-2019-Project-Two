Feature: Matches

  Scenario: can't get the matches list if unauthenticated
    When user get matches
    Then the request 'FAILS'

  Scenario: can get the matches list if authenticated
    Given user is authenticated
    When user get matches
    Then the request 'IS SUCCESSFUL'
    Then response is not empty

  Scenario: can't update a match if unauthenticated
    When user update match 1000109 with the following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/3  | 4      | /stadiums/5|
    Then the request 'FAILS'

  Scenario: can't update the match if authenticated but false attribute
    Given user is authenticated
    When user update match 1000109 with the following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/13  | 4      | /stadiums/5|
    Then the request 'FAILS'

  Scenario: can update a match if authenticated
    Given user is authenticated
    When user update match 1000109 with the following attributes
      | team1   | score1 | team2  | score2 | location |
      | /teams/2 | 2  | /teams/3  | 4      | /stadiums/5|
    Then the request 'IS SUCCESSFUL'
