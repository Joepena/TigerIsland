Feature: To test if the first tile will be correctly placed at the origin

  Scenario: The game is initialized and the board is initialized, it is the players turn
    Given there are no tiles on the board
    When the first player places a tile
    Then the first tile should be placed at the origin in whatever orientation the player chooses