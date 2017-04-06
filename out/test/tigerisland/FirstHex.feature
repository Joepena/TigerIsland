Feature: To test if a hex can be placed successfully on the game board

  Scenario: The game is initialized and the board is initialized, it is the players turn
    Given there are no hexes on the board
    When the first player places a hex
    Then the first hex should be placed at the origin
