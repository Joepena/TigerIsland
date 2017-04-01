Feature: To test that meeples placed will add points to the players score

  Scenario: A meeple is placed on the board
    Given a player places a meeple on the board
    When the meeple is placed
    Then player's score goes up by the level the meeple is placed at squared