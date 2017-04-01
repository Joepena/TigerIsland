Feature: To test that tigers placed will add points to the players score

  Scenario: A tiger is placed on the board
    Given a player places a tiger on the board
    When the tiger is placed
    Then player's score goes up by 75 points