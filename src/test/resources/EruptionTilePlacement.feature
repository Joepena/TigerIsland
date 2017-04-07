Feature: Place a tile on top of existing tiles

  Scenario: The player wants to raise the level of the pieces on the board
    Given the player wants to place their tile entirely on top of one existing tile
    When the player tries to place the tile
    Then the player will be prevented from placing their tile