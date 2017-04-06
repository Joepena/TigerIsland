Feature: To create a new tile and test that it has all its necessary fields

  Scenario: The game is initialized and the board is initialized, it is the players turn
    Given the game is being played
    When i draw a tile
    Then i should have a tile composed of 3 hexes joined together with one being a volcano
