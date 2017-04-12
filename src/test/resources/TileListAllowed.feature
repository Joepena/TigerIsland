Feature: Placing a tile in a valid position off of a given list will be valid
  Scenario: I want to place a tile
    Given Game is started, I would like to place a tile
    When I look to see valid locations to expand terrain
    Then the I will have a list of valid tile locations to choose from
