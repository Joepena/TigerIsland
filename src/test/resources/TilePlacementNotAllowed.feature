Feature: Placing a tile in an invalid position will not allow the tile to be placed
  Scenario: I want to place a tile without nuking in an invalid location
    Given Game is started, I have a tile to place without nuking, with an invalid destination
    When I try to place the tile in an unconnected location
    Then the tile will not be placed at that location

  Scenario: I want to place a tile by nuking an invalid location
    Given Game is started, I have a tile to place by nuking, with an invalid destination
    When I try to place the tile in an invalid nuking location
    Then the tile will not nuke that location