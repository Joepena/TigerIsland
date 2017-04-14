Feature: Placing a tile in a valid position will allow the tile to be placed
  Scenario: I want to place a tile without nuking
    Given Game is started, I have a tile to place without nuking
    When I try to place the tile in a valid connected location
    Then the tile will be placed at that location

  Scenario: I want to place a tile by nuking
    Given Game is started, I have a tile to place by nuking
    When I try to place the tile in a valid nuking location
    Then the tile will nuke that location