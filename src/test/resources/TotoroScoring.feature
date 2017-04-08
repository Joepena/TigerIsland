Feature: Totoros placed will add points to the players score

  Scenario: A totoro is placed on the board
    Given a player places a totoro on the board
    When the totoro is placed in a valid location
    Then player's score goes up by 200 points