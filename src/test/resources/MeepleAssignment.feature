Feature: To test if a meeple can be assigned to a hex

  Scenario: The game is initialized and the board is initialized with at least one tile on the board
    Given there is already a tile placed on the board
    When the player wants to place a meeple in a valid hex
    Then a meeple will be placed on the board